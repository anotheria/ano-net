package test.o2o;

import net.anotheria.net.shared.server.ServerLogger;
import net.anotheria.net.tcp.server.object2object.O2OServer;

public class StartServer {
	public static void main(String a[]) throws Exception{
		O2OServer server = new O2OServer(9999,true, new EchoWorkerFactory());
		System.out.println("Server created.");
		new ServerLogger(server);
		server.startServer();
		System.out.println("Server started.");
	}
}
