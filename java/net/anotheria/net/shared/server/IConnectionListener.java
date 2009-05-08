package net.anotheria.net.shared.server;

/**
 * A listener object which is notified on connection state changes.
 * @author lrosenberg
 *
 */
public interface IConnectionListener {
	/**
	 * Called whenever a connection has been closed.
	 * @param connection the connection that has been closed
	 */
	public void connectionClosed(IConnection connection);
	/**
	 * Called whenever a connection has been opened
	 * @param connection the connection that has been opened.
	 */
	public void connectionOpened(IConnection connection);
}
