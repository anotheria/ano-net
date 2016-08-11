package net.anotheria.net.shared.server;

/**
 * A server and connection listener which just prints out to system-out.
 * @author another
 *
 */
public class ServerLogger implements IServerListener, IConnectionListener{
	public ServerLogger(IServer targetServer){
		targetServer.addServerListener(this);
	}

	public void connectionClosed(IConnection connection) {
		out("connection closed: "+connection);
		
	}

	public void connectionOpened(IConnection connection) {
		out("connection opened: "+connection);
		
	}

	public void connectionCreated(IConnection connection) {
		connection.addConnectionListener(this);
		out("connection created: "+connection);
		
	}

	public void connectionRemoved(IConnection connection) {
		out("connection removed: "+connection);
	}
	
	private void out(Object o){
		System.out.println("[W3ServerLogger] "+o);
	}
	
}
