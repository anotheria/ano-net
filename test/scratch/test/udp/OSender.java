package test.udp;

import java.net.InetAddress;

import net.anotheria.net.udp.client.UDPObjectSender;


/**
 * TODO please remined another to comment this class
 * @author another
 */
public class OSender {
	public static void main(String a[]) throws Exception{
	
		InetAddress target = InetAddress.getByName(a[0]);
		
		System.out.println("Sending to "+target+" : "+a[1]);
		UDPObjectSender sender = new UDPObjectSender(9000,9050, a[0], 9999);
	
		for (int i=0; i<5; i++){
			String msg = "Hello Nr. "+i+" !";
			long started = System.currentTimeMillis();
			sender.send(msg);
			long ended = System.currentTimeMillis();
			System.out.println("Sent in "+(ended-started));
		}
			
		System.out.println("Sent.");
		Thread.sleep(5000);
	}

}
