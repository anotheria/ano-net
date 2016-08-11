package test.udp;

import net.anotheria.net.udp.server.IUDPObjectWorker;
import net.anotheria.net.udp.server.UDPObjectReceiver;
import net.anotheria.net.udp.server.UDPSenderInfo;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class OServer implements IUDPObjectWorker{
	
	
public static void main(String a[]){
	UDPObjectReceiver receiver = new UDPObjectReceiver(9999);
	receiver.addWorker(new OServer());
	receiver.start();
	System.out.println("Object Received started");
}
/* (non-Javadoc)
 * @see net.anotheria.net.server.udp.IUDPPacketWorker#proceedIncomingPacket(byte[], net.anotheria.net.server.udp.UDPSenderInfo)
 */
public void proceedIncomingObject(Object o, UDPSenderInfo senderInfo) {
	try{
		System.out.println("Got object  from "+senderInfo);
		System.out.println("--->"+o+"<---");
	}catch(Exception e){
		System.out.println("Exception: "+e.getMessage());
	}
}


}
