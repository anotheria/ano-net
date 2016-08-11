package net.anotheria.net.tcp.proxy;

import net.anotheria.net.shared.server.IConnection;
import net.anotheria.net.shared.server.IConnectionCreationContext;
import net.anotheria.net.shared.server.IConnectionFactory;
import net.anotheria.net.tcp.server.SocketContext;
/**
 * A factory for proxy connections.
 * @author lrosenberg
 */
public class ProxyConnectionFactory implements IConnectionFactory{

	private String host;
	private int port;
	
	public ProxyConnectionFactory(String aHost, int aPort){
		host = aHost;
		port = aPort;
	}
	
	public IConnection createConnection(IConnectionCreationContext context) {
		return new ProxyConnection(((SocketContext)context).getSocket(), host, port);
	}

}
