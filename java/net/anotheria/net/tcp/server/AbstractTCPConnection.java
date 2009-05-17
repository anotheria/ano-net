/* ------------------------------------------------------------------------- *
$Source$
$Author$
$Date$
$Revision$


Copyright 2004-2005 by FriendScout24 GmbH, Munich, Germany.
All rights reserved.

This software is the confidential and proprietary information
of FriendScout24 GmbH. ("Confidential Information").  You
shall not disclose such Confidential Information and shall use
it only in accordance with the terms of the license agreement
you entered into with FriendScout24 GmbH.
See www.friendscout24.de for details.
** ------------------------------------------------------------------------- */
package net.anotheria.net.tcp.server;

import java.net.Socket;

import net.anotheria.net.shared.server.AbstractConnection;

/**
 * Base class for TCP connections.
 * @author lrosenberg
 *
 */
public abstract class AbstractTCPConnection extends AbstractConnection implements Runnable{
	/**
	 * The underlying socket.
	 */
	private Socket socket;
	
	/**
	 * Creates a new AbstractTCPConnection tied to a socket.
	 * @param aSocket the socket to tie the connection to
	 */
	protected AbstractTCPConnection(Socket aSocket){
		super();
		socket = aSocket;
	}
	/**
	 * Closes the connection.
	 */
	public void close() {
		try{
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		super.close();
		
	}

	/**
	 * Opens a connection and creates a reader.
	 */
	public void open() {
		super.open();
		new Thread(this).start();
	}

	/**
	 * Returns the underlying socket.
	 * @return the socket
	 */
	protected Socket getSocket() {
		return socket;
	}

	/**
	 * Sets the socket for this connection.
	 * @param socket the socket to set.
	 */
	protected void setSocket(Socket socket) {
		this.socket = socket;
	}

}
