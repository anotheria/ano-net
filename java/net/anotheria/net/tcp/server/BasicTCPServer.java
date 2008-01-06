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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import net.anotheria.net.shared.server.AbstractServer;
import net.anotheria.net.shared.server.IConnection;
import net.anotheria.net.shared.server.IConnectionFactory;
import net.anotheria.net.shared.server.ServerException;

public class BasicTCPServer extends AbstractServer implements Runnable{
	
	private int port;
	private ServerSocket serverSocket;
	private volatile boolean running;
	
	public BasicTCPServer(int aPort, IConnectionFactory conFactory){
		super(conFactory);
		port = aPort;
	}
	

	public void startServer() throws ServerException{
		try{
			serverSocket = new ServerSocket(port);
		}catch(IOException e){
			throw new ServerException("Couldn't bind port: "+port+" : "+e.getMessage());
		}
		new Thread(this).start();
	}
	
	public void run(){
		setRunning(true);
		while(isRunning()){
			try{
				Socket s = serverSocket.accept();
				IConnection newConnection = getConnectionFactory().createConnection(new SocketContext(s));
				notifyConnectionCreated(newConnection);
				newConnection.open();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	public void stopServer() {
		setRunning(false);
		
	}


	public boolean isRunning() {
		return running;
	}


	public void setRunning(boolean running) {
		this.running = running;
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
 * Revision 1.2  2006/01/05 16:53:16  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/04 16:17:27  lrosenberg
 * *** empty log message ***
 *
 */