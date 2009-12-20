package test.w3server;

import net.anotheria.net.tcp.server.BasicTCPServer;

public class W3Server {
	public static void main(String a[]) throws Exception{
		BasicTCPServer server = new BasicTCPServer(1080, new W3ConnectionFactory());
		new W3ServerLogger(server);
		server.startServer();
		System.out.println("READY.");
	}
}