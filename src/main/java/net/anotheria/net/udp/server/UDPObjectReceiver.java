package net.anotheria.net.udp.server;

import net.anotheria.net.util.ByteArraySerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A receiver for udp objects in udp packets to build udp servers upon.
 * @author lrosenberg
 */
public class UDPObjectReceiver implements IUDPPacketWorker{

	private static Logger log = LoggerFactory.getLogger(UDPObjectReceiver.class);

	/**
	 * The underlying packet receiver.
	 */
	private UDPPacketReceiver packetReceiver;
	/**
	 * Workers for incoming objects.
	 */
	private List<IUDPObjectWorker> workers;
	
	/**
	 * Creates a new UDPObjectReceiver that listens at the given port.
	 * @param port the port to listen at
	 */
	public UDPObjectReceiver(int port){
		packetReceiver = new UDPPacketReceiver(port);
		packetReceiver.addWorker(this);
		workers = new ArrayList<IUDPObjectWorker>();
	}

	/**
	 * Starts the receiver.
	 */
	public void start(){
		packetReceiver.start();
	}


	/* (non-Javadoc)
	 * @see net.anotheria.net.server.udp.IUDPPacketWorker#proceedIncomingPacket(byte[], net.anotheria.net.server.udp.UDPSenderInfo)
	 */
	@Override public void proceedIncomingPacket(byte[] data, UDPSenderInfo senderInfo) {
		Object o = null;
		try{
			o = ByteArraySerializer.deserializeObject(data);
		}catch(IOException e){
			log.error("proceedIncomingPacket.cant deserialize! ", e);
			return;
		}
		for (int i=0; i<workers.size(); i++){
			workers.get(i).proceedIncomingObject(o, senderInfo);
		}
	}
	
	/**
	 * Adds a worker to this receiver.
	 * @param worker the worker to add
	 */
	public void addWorker(IUDPObjectWorker worker){
		workers.add(worker);
	}
	
	/**
	 * Removes the worker from this receiver. The worker needs to implement equals properly (or be the same object).
	 * @param worker the worker to remove or an object equal to the worker to remove.
	 */
	public void removeWorker(IUDPObjectWorker worker){
		workers.remove(worker);
	}

}
