package net.anotheria.net.tcp.server.object2object;

import net.anotheria.net.tcp.server.BasicTCPServer;

/**
 * An object2object server implementation. This is a server which is capable of exchanging serializeable objects via tcp.
 * @author lrosenberg
 */
public class O2OServer extends BasicTCPServer{
	/**
	 * Creates a new O2OServer.
	 * @param port the port to listen to.
	 * @param keepAlive whether the connections have to be keepalived.
	 * @param workerFactory the factory to be used for workers.
	 */
	public O2OServer(int port, boolean keepAlive, IO2OWorkerFactory workerFactory){
		super(port, new O2OConnectionFactory(workerFactory, keepAlive));
	}
}
