package net.anotheria.net.udp.client;

import java.io.IOException;
import java.io.Serializable;

import org.apache.log4j.Logger;

import net.anotheria.net.util.ByteArraySerializer;


/**
 * TODO please remined another to comment this class
 * @author another
 */
public class UDPObjectSender {
	
	private static Logger log = Logger.getLogger(UDPObjectSender.class);
	
	private UDPPacketSender sender;
	
	public UDPObjectSender(int aSourcePort){
		this(aSourcePort, aSourcePort);
	}
	
	public UDPObjectSender(int aMinPort, int aMaxPort){
		sender = new UDPPacketSender(aMinPort, aMaxPort);
	}
	
	public UDPObjectSender(int aSourcePort, String aDefaultHost, int aDefaultPort){
		this(aSourcePort, aSourcePort, aDefaultHost, aDefaultPort);
	}

	public UDPObjectSender(int aMinPort, int aMaxPort, String aDefaultHost, int aDefaultPort){
		sender = new UDPPacketSender(aMinPort, aMaxPort, aDefaultHost, aDefaultPort);
	}
	
	
	public void send(Object o) throws IOException{
		if (log.isDebugEnabled())
			log.debug("Sending object "+o);
		sender.send(ByteArraySerializer.serializeObject(((Serializable)o)));
	}
	
	public void sendTo(Object o, String host, int port) throws IOException{
		if (log.isDebugEnabled())
			log.debug("Sending object "+o+", to: "+host+":"+port);
		sender.sendTo(ByteArraySerializer.serializeObject((Serializable)o), host, port);
	}

}
