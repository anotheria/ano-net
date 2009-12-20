package test.w3server;

import net.anotheria.net.shared.server.IConnection;
import net.anotheria.net.shared.server.IConnectionListener;
import net.anotheria.net.shared.server.IServer;
import net.anotheria.net.shared.server.IServerListener;

public class W3ServerLogger implements IServerListener, IConnectionListener{
	public W3ServerLogger(IServer targetServer){
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
