package net.anotheria.net.udp.server;

import java.net.InetAddress;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public class UDPSenderInfo {
	private InetAddress address;
	private int port;
	
	public UDPSenderInfo(InetAddress anAdress, int aPort){
		this.address = anAdress;
		this.port = aPort;
	}
	
	public String toString(){
		return address.getCanonicalHostName()+" / "+address.getHostAddress()+" : "+port;
	}
	/**
	 * @return
	 */
	public InetAddress getAddress() {
		return address;
	}

	/**
	 * @return
	 */
	public int getPort() {
		return port;
	}

}
