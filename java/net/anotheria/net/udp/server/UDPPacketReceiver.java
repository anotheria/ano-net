package net.anotheria.net.udp.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.anotheria.util.queue.IQueue;
import net.anotheria.util.queue.StandardQueueFactory;


/**
 * A helper object which is bound to a port and proceeds all incoming datagram packets on this port. The reception of the packets is performed in one thread, the processing of the 
 * received packets in another thread. Both threads are separated by a queue. 
 * The UDPPacketReceiver supports multiple workers. For each incomign packet all workers are notified (sequentially).
 * @author another
 */
public class UDPPacketReceiver extends Thread{
	/**
	 * List with worker objects.
	 */
	private List<IUDPPacketWorker> workers;
	/**
	 * Port for listening.
	 */
	private int port;
	
	/**
	 * Queue for storage of received packets for processing.
	 */
	private IQueue<TMPDataHolder> inQueue;
	
	private static Logger log = Logger.getLogger(UDPPacketReceiver.class);
	
	public UDPPacketReceiver(int aPort){
		port = aPort;
		inQueue = new StandardQueueFactory<TMPDataHolder>().createQueue(500);
		workers = new ArrayList<IUDPPacketWorker>();
	}
	
	public void run(){
		byte data[] = new byte[64000];
		new QueueWorker(inQueue).start();
		DatagramPacket rec = new DatagramPacket(data, 64000);
		
		DatagramSocket server = null;
		try{
			server = new DatagramSocket(port);
		}catch(SocketException e){
			log.error("Can't init server socket on port "+port+", stoped packed receiving.", e);
			return;
		}
		
		while(true){
			try{
				server.receive(rec);
				byte[] newData = new byte[rec.getLength()-rec.getOffset()];
				System.arraycopy(data, rec.getOffset(), newData, 0, rec.getLength());
				TMPDataHolder holder = new TMPDataHolder(new UDPSenderInfo(rec.getAddress(), rec.getPort()), newData);
				inQueue.putElement(holder);
			}catch(Exception e){
				log.error("Caught exception in packet processing (continue listening)", e);
			}
		}
			
	}
	
	private void notifyWorkers(UDPSenderInfo info, byte[] data){
		for (int i=0; i<workers.size(); i++){
			IUDPPacketWorker worker = workers.get(i);
			try{
				worker.proceedIncomingPacket(data, info);
			}catch(Throwable t){
				log.error("Worker "+worker+" didn't caught throwable: ", t);
			}
		}
	}
	
	class QueueWorker extends Thread{
		
		private IQueue<TMPDataHolder> queue;

		QueueWorker(IQueue<TMPDataHolder> aQueue){
			this.queue = aQueue;
		}
		
		
		public void run(){
			while(true){
				if (queue.hasElements()){
					TMPDataHolder holder = queue.nextElement();
					if (holder==null)
						log.warn("Strange, data packet was null");
					else
						notifyWorkers(holder.info, holder.data);
				}else{
					try{
						sleep(50);
					}catch(Exception e){}
				}
			}
		}
	}
	
	public void addWorker(IUDPPacketWorker worker){
		workers.add(worker);
	}
	
	public void removeWorker(IUDPPacketWorker worker){
		workers.remove(worker);
	}
}

class TMPDataHolder{
	UDPSenderInfo info;
	byte[] data;
	
	TMPDataHolder(UDPSenderInfo anInfo, byte[] aData){
		this.info = anInfo;
		this.data = aData;
	}
	
	public String toString(){
		return ""+info+" "+data.length;
	}
	
}
