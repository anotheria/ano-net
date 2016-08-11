package net.anotheria.net.shared.server;

/**
 * Base class for server-generated exceptions.
 * @author lrosenberg
 *
 */
public class ServerException extends Exception{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	public ServerException(String aMessage){
		super(aMessage);
	}
}
