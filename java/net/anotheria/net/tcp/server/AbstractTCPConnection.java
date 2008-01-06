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

public abstract class AbstractTCPConnection extends AbstractConnection implements Runnable{
	private Socket socket;
	
	protected AbstractTCPConnection(Socket aSocket){
		super();
		socket = aSocket;
	}
	
	public void close() {
		try{
			socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		super.close();
		
	}

	public void open() {
		super.open();
		new Thread(this).start();
	}

	protected Socket getSocket() {
		return socket;
	}

	protected void setSocket(Socket socket) {
		this.socket = socket;
	}

}

/* ------------------------------------------------------------------------- *
 * $Log$
 * Revision 1.2  2008/01/06 19:07:50  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/25 13:02:28  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/24 15:58:43  lrosenberg
 * *** empty log message ***
 *
 */