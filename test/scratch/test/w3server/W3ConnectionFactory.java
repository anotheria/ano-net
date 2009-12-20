package test.w3server;

import net.anotheria.net.shared.server.IConnection;
import net.anotheria.net.shared.server.IConnectionCreationContext;
import net.anotheria.net.shared.server.IConnectionFactory;
import net.anotheria.net.tcp.server.SocketContext;

public class W3ConnectionFactory implements IConnectionFactory{

	public IConnection createConnection(IConnectionCreationContext context) {
		return new W3Connection(((SocketContext)context).getSocket());
	}

}
