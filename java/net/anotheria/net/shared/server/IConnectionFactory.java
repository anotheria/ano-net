package net.anotheria.net.shared.server;

/**
 * Factory for creation of protocol dependent connection objects.
 * @author lrosenberg
 */
public interface IConnectionFactory {
	/**
	 * Creates a new connection parametrized by the context
	 * @param context connection creation context
	 * @return a new connection object
	 */
	public IConnection createConnection(IConnectionCreationContext context);
}

