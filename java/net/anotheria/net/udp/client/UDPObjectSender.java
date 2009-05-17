package net.anotheria.net.udp.client;

import java.io.IOException;
import java.io.Serializable;

import org.apache.log4j.Logger;

import net.anotheria.net.util.ByteArraySerializer;


/**
 * This is a utility class for sending objects over an udp connection. It sneds them over an underlying UDPPacketSender.
 * @author lrosenberg
 */
public class UDPObjectSender {
	/**
	 * The logger.
	 */
	private static Logger log = Logger.getLogger(UDPObjectSender.class);
	
	/**
	 * The underlying udp packet sender.
	 */
	private UDPPacketSender sender;
	
	/**
	 * Creates a new UDPObjectSender bound to a given port.
	 * @param aSourcePort the port to listen to.
	 */
	public UDPObjectSender(int aSourcePort){
		this(aSourcePort, aSourcePort);
	}
	
	/**
	 * Creates a new UDPObjectSender bound to a first free port in the given port range.
	 * @param aMinPort the minimum port range.
	 * @param aMaxPort the maximum port range.
	 */
	public UDPObjectSender(int aMinPort, int aMaxPort){
		sender = new UDPPacketSender(aMinPort, aMaxPort);
	}
	
	public UDPObjectSender(int aSourcePort, String aDefaultHost, int aDefaultPort){
		this(aSourcePort, aSourcePort, aDefaultHost, aDefaultPort);
	}

	public UDPObjectSender(int aMinPort, int aMaxPort, String aDefaultHost, int aDefaultPort){
		sender = new UDPPacketSender(aMinPort, aMaxPort, aDefaultHost, aDefaultPort);
	}
	
	/**
	 * Sends an object to the other side. 
	 * @param o the object to send.
	 * @throws IOException
	 */
	public void send(Object o) throws IOException{
		if (log.isDebugEnabled())
			log.debug("Sending object "+o);
		sender.send(ByteArraySerializer.serializeObject(((Serializable)o)));
	}
	
	/**
	 * Sends an object to specified host and port.
	 * @param o the object to send.
	 * @param host the host to send the object to.
	 * @param port the port to send the object to.
	 * @throws IOException
	 */
	public void sendTo(Object o, String host, int port) throws IOException{
		if (log.isDebugEnabled())
			log.debug("Sending object "+o+", to: "+host+":"+port);
		sender.sendTo(ByteArraySerializer.serializeObject((Serializable)o), host, port);
	}

}
