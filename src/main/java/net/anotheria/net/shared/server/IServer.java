package net.anotheria.net.shared.server;
/**
 * Defines a server.
 * @author lrosenberg
 */
public interface IServer {
	/**
	 * Starts the server
	 * @throws ServerException
	 */
	public void startServer() throws ServerException;
	/**
	 * Stops the server
	 * @throws ServerException
	 */
	public void stopServer() throws ServerException;
	/**
	 * Adds a listener to this server
	 * @param listener listener to add
	 */
	public void addServerListener(IServerListener listener);
	/**
	 * Removes a listener from this server.
	 * @param listener listener to remove
	 */
	public void removeServerListener(IServerListener listener);
	
	
	
}
