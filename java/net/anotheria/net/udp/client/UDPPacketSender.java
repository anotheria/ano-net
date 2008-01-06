package net.anotheria.net.udp.client;

import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class UDPPacketSender {
	
	private static Logger log = Logger.getLogger(UDPPacketSender.class);
	
	private String defaultHost;
	private String currentHost;
	private int defaultPort;
	private int currentPort;
	
	private DatagramSocket socket;
	//private int sourcePort;
	
	private int minPort, maxPort;
	
	public UDPPacketSender(int aSourcePort){
		this(aSourcePort, aSourcePort);
	}
	
	public UDPPacketSender(int aMinPort, int aMaxPort){
		minPort = aMinPort;
		maxPort = aMaxPort;
	}
	
	public UDPPacketSender(int aSourcePort, String aDefaultHost, int aDefaultPort){
		this(aSourcePort, aSourcePort, aDefaultHost, aDefaultPort);
	}
	
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
	
	
	public void send(byte[] data){
		sendTo(data, defaultHost, defaultPort);
	}
	
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
