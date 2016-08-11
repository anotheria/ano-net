package net.anotheria.net.shared.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all different kinds of servers 
 */
public abstract class AbstractServer implements IServer{
	/**
	 * The listeners
	 */
	private List<IServerListener> listeners;
	/**
	 * The connection factory for connection object creation.
	 */
	private IConnectionFactory connectionFactory;
	
	/**
	 * Creates a new server
	 */
	protected AbstractServer(){
		listeners = new ArrayList<IServerListener>();
	}
	
	/**
	 * Creates a new server with specified connection factory.
	 * @param aConnectionFactory the connection factor to use for incoming connections
	 */
	protected AbstractServer(IConnectionFactory aConnectionFactory){
		this();
		connectionFactory = aConnectionFactory;
	}

	@Override public void addServerListener(IServerListener listener){
		listeners.add(listener);
	}
	
	@Override public void removeServerListener(IServerListener listener){
		listeners.remove(listener);
	}
	
	/**
	 * Calls the connectionCreated method in all listeners
	 * @param con the newly created connection
	 */
	protected void notifyConnectionCreated(IConnection con){
		for (int i=0; i<listeners.size(); i++)
			listeners.get(i).connectionCreated(con);
	}

	/**
	 * Calls the connectionRemoved method in all listeners
	 * @param con the removed connection
	 */
	protected void notifyConnectionRemoved(IConnection con){
		for (int i=0; i<listeners.size(); i++)
			listeners.get(i).connectionRemoved(con);
	}

	/**
	 * Returns the connection factory
	 * @return the connection factory object
	 */
	public IConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	/**
	 * Sets a new connection factory
	 * @param connectionFactory connection factory to use for new incoming connections
	 */
	public void setConnectionFactory(IConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

}

