package net.anotheria.net.udp.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.anotheria.net.util.ByteArraySerializer;


/**
 * TODO please remined another to comment this class
 * @author another
 */
public class UDPObjectReceiver implements IUDPPacketWorker{
	
	private static Logger log = Logger.getLogger(UDPObjectReceiver.class);

	private UDPPacketReceiver packetReceiver;
	private List<IUDPObjectWorker> workers;
	
	public UDPObjectReceiver(int port){
		packetReceiver = new UDPPacketReceiver(port);
		packetReceiver.addWorker(this);
		workers = new ArrayList<IUDPObjectWorker>();
	}
	
	public void start(){
		packetReceiver.start();
	}


	/* (non-Javadoc)
	 * @see net.anotheria.net.server.udp.IUDPPacketWorker#proceedIncomingPacket(byte[], net.anotheria.net.server.udp.UDPSenderInfo)
	 */
	public void proceedIncomingPacket(byte[] data, UDPSenderInfo senderInfo) {
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
	
	public void addWorker(IUDPObjectWorker worker){
		workers.add(worker);
	}
	
	public void removeWorker(IUDPObjectWorker worker){
		workers.remove(worker);
	}

}
