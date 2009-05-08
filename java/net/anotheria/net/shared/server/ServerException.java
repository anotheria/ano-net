package net.anotheria.net.shared.server;

/**
 * Base class for server-generated exceptions.
 * @author lrosenberg
 *
 */
public class ServerException extends Exception{
	public ServerException(String aMessage){
		super(aMessage);
	}
}
