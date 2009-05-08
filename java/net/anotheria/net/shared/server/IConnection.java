package net.anotheria.net.shared.server;

/**
 * All connections have some common methods.
 * @author lrosenberg
 */
public interface IConnection {
	/**
	 * Opens a connection
	 */
	void open();
	/**
	 * Closes a conection
	 */
	void close();
	/**
	 * Adds a connection listeners 
	 * @param listener the listener to add
	 */
	void addConnectionListener(IConnectionListener listener);
	/**
	 * Removes the given connection listener
	 * @param listener the listener to remove
	 */
	void removeConnectionListener(IConnectionListener listener);
}