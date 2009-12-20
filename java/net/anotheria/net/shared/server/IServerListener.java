package net.anotheria.net.shared.server;

public interface IServerListener {
	void connectionCreated(IConnection connection);
	void connectionRemoved(IConnection connection);
}

