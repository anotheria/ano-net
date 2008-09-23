package net.anotheria.net.udp.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

import net.anotheria.util.queue.IQueue;
import net.anotheria.util.queue.StandardQueueFactory;


/**
 * TODO please remined another to comment this class
 * @author another
 */
public class UDPPacketReceiver extends Thread{
	private List<IUDPPacketWorker> workers;
	private int port;
	
	private IQueue inQueue;
	
	
	public UDPPacketReceiver(int aPort){
		port = aPort;
		inQueue = new StandardQueueFactory().createQueue(500);
		workers = new ArrayList<IUDPPacketWorker>();
	}
	
	public void run(){
		byte data[] = new byte[64000];
		new QueueWorker(inQueue).start();
		DatagramPacket rec = new DatagramPacket(data, 64000);
		try{
			DatagramSocket server = new DatagramSocket(port);
			while(true){
				server.receive(rec);
				byte[] newData = new byte[rec.getLength()-rec.getOffset()];
				System.arraycopy(data, rec.getOffset(), newData, 0, rec.getLength());
				TMPDataHolder holder = new TMPDataHolder(new UDPSenderInfo(rec.getAddress(), rec.getPort()), newData);
				inQueue.putElement(holder);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void notifyWorkers(UDPSenderInfo info, byte[] data){
		for (int i=0; i<workers.size(); i++){
			IUDPPacketWorker worker = workers.get(i);
			try{
				worker.proceedIncomingPacket(data, info);
			}catch(Throwable t){
				System.out.println("Worker "+worker+" didn't caught throwable: "+t.getMessage());
				t.printStackTrace();
			}
		}
	}
	
	class QueueWorker extends Thread{
		
		private IQueue queue;

		QueueWorker(IQueue aQueue){
			this.queue = aQueue;
		}
		
		
		public void run(){
			while(true){
				if (queue.hasElements()){
					TMPDataHolder holder = (TMPDataHolder)queue.nextElement();
					System.out.println("Queue proceeding "+holder);
					if (holder==null)
						System.out.println("Strange holder == 0");
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
