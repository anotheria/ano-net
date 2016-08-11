package net.anotheria.net.tcp.server;

import java.net.Socket;

import net.anotheria.net.shared.server.IConnectionCreationContext;
/**
 * A socket associated connection creation context.
 * @author another
 *
 */
public class SocketContext implements IConnectionCreationContext{
	/**
	 * The socket for the new connection.
	 */
	private Socket socket;
	
	/**
	 * Creates an new context.
	 */
	SocketContext(){
		
	}
	
	/**
	 * Creates a new connection context for a given socket.
	 */
	SocketContext(Socket aSocket){
		socket = aSocket;
	}

	/**
	 * Returns the socket
	 * @return the socket object
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Sets the socket
	 * @param socket the socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	@Override public String toString(){
		return ""+socket;
	}
}
