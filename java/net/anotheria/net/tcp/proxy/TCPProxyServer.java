package net.anotheria.net.tcp.proxy;

import net.anotheria.net.tcp.server.BasicTCPServer;


public class TCPProxyServer extends BasicTCPServer{
	public TCPProxyServer(int localPort, String targetHost, int targetPort){
		super(localPort, new ProxyConnectionFactory(targetHost, targetPort));
	}
	
	public static void main(String a[]) throws Exception{
		int localPort = Integer.parseInt(a[0]);
		String targetHost = a[1];
		int targetPort = Integer.parseInt(a[2]);
		
		System.out.println("Creating a new TCP Proxy localhost:"+localPort+" --> "+targetHost+":"+targetPort);
		TCPProxyServer server = new TCPProxyServer(localPort, targetHost, targetPort);
		//new Thread(server).start();
		server.startServer();
	}
}
