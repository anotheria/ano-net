package test.udp;

import net.anotheria.net.udp.server.IUDPPacketWorker;
import net.anotheria.net.udp.server.UDPPacketReceiver;
import net.anotheria.net.udp.server.UDPSenderInfo;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class Pserver implements IUDPPacketWorker{
	
	
	public static void main(String a[]){
		UDPPacketReceiver receiver = new UDPPacketReceiver(9999);
		receiver.addWorker(new Pserver());
		receiver.start();
		System.out.println("Received started");
	}
	/* (non-Javadoc)
	 * @see net.anotheria.net.server.udp.IUDPPacketWorker#proceedIncomingPacket(byte[], net.anotheria.net.server.udp.UDPSenderInfo)
	 */
	public void proceedIncomingPacket(byte[] data, UDPSenderInfo senderInfo) {
		System.out.println("Got "+data.length+" bytes from "+senderInfo);
		System.out.println("--->"+(new String(data))+"<---");
	}

}
 