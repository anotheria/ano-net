package test.udp;

import java.net.InetAddress;

import net.anotheria.net.udp.client.UDPPacketSender;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class PSender {
	public static void main(String a[]) throws Exception{
		//DatagramSocket sock = new DatagramSocket(9900);
	
		InetAddress target = InetAddress.getByName(a[0]);
		
		System.out.println("Sending to "+target+" : "+a[1]);
	
		
		UDPPacketSender sender = new UDPPacketSender(9050, 9100, a[0], Integer.parseInt(a[1]));
	
		for (int i=0; i<3; i++){
			byte data[] = ("Hello Nr. "+i+" !").getBytes();
			System.out.println("Sending "+new String(data)+")"+" ["+data+"]");
			long started = System.currentTimeMillis();
			if (i==5)
				sender.sendTo(data, "localhost", 7777);
			else
				sender.send(data);
			long ended = System.currentTimeMillis();
			System.out.println("Sent in "+(ended-started));
		}
			
		System.out.println("Sent.");
		Thread.sleep(10000);
	}
}
