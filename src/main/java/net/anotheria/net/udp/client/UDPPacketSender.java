package net.anotheria.net.udp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Utility class which sends packets over udp.
 * @author another
 */
public class UDPPacketSender {
	
	/**
	 * The logger.
	 */
	private static Logger log = LoggerFactory.getLogger(UDPPacketSender.class);

	/**
	 * Default target host. If no host is given as parameter this one will be used.
	 */
	private String defaultHost;
	/**
	 * Currently assinged host. Actually last used host.
	 */
	private String currentHost;
	/**
	 * The default port. If no port is given this one will be used.
	 */ 
	private int defaultPort;
	/**
	 * Currently assgined port. The last used port.
	 */
	private int currentPort;
	
	/**
	 * The socket to use as send socket.
	 */
	private DatagramSocket socket;
	
	/**
	 * The min port in the outgoing port range.
	 */
	private int minPort;
	/**
	 * The max port in the outgoing port range.
	 */
	private int maxPort;
	
	/**
	 * Creates a new UDPPacketSender with the given port as sender port. 
	 * @param aSourcePort the port to use as outgoing port.
	 */
	public UDPPacketSender(int aSourcePort){
		this(aSourcePort, aSourcePort);
	}
	
	/**
	 * Creates a new UDPPacketSender with a given port range for the outgoing port.
	 * @param aMinPort the min port in the outgoing port range
	 * @param aMaxPort the max port in the outgoing port range
	 */
	public UDPPacketSender(int aMinPort, int aMaxPort){
		minPort = aMinPort;
		maxPort = aMaxPort;
	}
	
	/**
	 * Creates a new UDPPacketSender with a given outgoing port, default target host and port.
	 * @param aSourcePort the port to use as outgoing port.
	 * @param aDefaultHost the default target host.
	 * @param aDefaultPort the default target port.
	 */
	public UDPPacketSender(int aSourcePort, String aDefaultHost, int aDefaultPort){
		this(aSourcePort, aSourcePort, aDefaultHost, aDefaultPort);
	}

	/**
	 * Creates a new UDPPacketSender with outgoing port range, a default target host and port. 
	 * @param aMinPort the min port in the outgoing port range
	 * @param aMaxPort the max port in the outgoing port range
	 * @param aDefaultHost the default target host.
	 * @param aDefaultPort the default target port.
	 */
	public UDPPacketSender(int aMinPort, int aMaxPort, String aDefaultHost, int aDefaultPort){
		minPort = aMinPort;
		maxPort = aMaxPort;
		
		defaultHost = aDefaultHost;		
		defaultPort = aDefaultPort;
		try{
			initConnection(defaultHost, defaultPort);
		}catch(Exception e){
			log.error("UDPPacketSender("+aMinPort+", "+ aMaxPort+", "+ aDefaultHost+", "+aDefaultPort+")", e);
			throw new RuntimeException("Can't initConnection to "+defaultHost+", "+defaultPort);
		}
	}
	
	/**
	 * Sends some data to the default host and port.
	 * @param data the data to send.
	 */
	public void send(byte[] data){
		sendTo(data, defaultHost, defaultPort);
	}
	
	/**
	 * Sends some data to the given host and port.
	 * @param data the data to send.
	 * @param host the host to send the data to.
	 * @param port the port to send the data to.
	 */
	public void sendTo(byte[] data, String host, int port){
		//System.out.println("Sending "+data.length+" to "+host+" : "+port);
		try{
			initConnection(host, port);
			DatagramPacket packet = new DatagramPacket(data, data.length);
			socket.send(packet);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("SendTo "+host+":"+port+" failed: "+e.getMessage());
		}
	}
	
	/**
	 * Initializes a connection to a host,port combination. 
	 * @param host the target host.
	 * @param port the target port.
	 * @throws UnknownHostException if the host can't be resolved.
	 * @throws SocketException if a socket has been closed or any other socket errors happen.
	 */
	private synchronized void initConnection(String host, int port) throws UnknownHostException, SocketException{
		if (currentHost!=null && currentHost.equals(host) && currentPort==port)
			return;
		currentHost = host;
		currentPort = port;
		
		log.info("Creating new connection... " +host+":"+port);
		if (socket==null){
			for (int i=minPort; i<=maxPort; i++){
				try{
					//System.out.println("Probing port "+i);
					socket = new DatagramSocket(i);
					//System.out.println("bind to port "+i);
					break;
				}catch(BindException e){
						
				}
			}
		}
		
		if (socket==null){
			log.error("initConnection: Out of ports.");
			throw new RuntimeException("Can't open a socket, out of ports...");
		}
		
		if (socket.isConnected())
			socket.disconnect();
		socket.connect(InetAddress.getByName(host), port);
		
	}
}
