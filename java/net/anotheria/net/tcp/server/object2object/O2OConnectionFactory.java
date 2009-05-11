package net.anotheria.net.tcp.server.object2object;

import net.anotheria.net.shared.server.IConnection;
import net.anotheria.net.shared.server.IConnectionCreationContext;
import net.anotheria.net.shared.server.IConnectionFactory;
import net.anotheria.net.tcp.server.SocketContext;

/**
 * A factory for creation of object to object connections.
 * @author lrosenberg
 */
public class O2OConnectionFactory implements IConnectionFactory{
	
	/**
	 * The workers factory.
	 */
	private IO2OWorkerFactory workerFactory;
	/*
	 * True if keepalive is set.
	 */
	private boolean keepAlive;
	
	/**
	 * Creates a new factory.
	 * @param aWorkerFactory the worker factory.
	 * @param aKeepAlive keepalive.
	 */
	O2OConnectionFactory(IO2OWorkerFactory aWorkerFactory, boolean aKeepAlive){
		workerFactory = aWorkerFactory;
		keepAlive = aKeepAlive;
	}

	/**
	 * Creates a new connection with given context.
	 */
	public IConnection createConnection(IConnectionCreationContext context) {
		O2OConnection newConnection = new O2OConnection(((SocketContext)context).getSocket(), 
				workerFactory.createWorker(),
				keepAlive);
		return newConnection;
	}
}
