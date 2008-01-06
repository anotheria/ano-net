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
package net.anotheria.net.shared.server;


public class ServerLogger implements IServerListener, IConnectionListener{
	public ServerLogger(IServer targetServer){
		targetServer.addServerListener(this);
	}

	public void connectionClosed(IConnection connection) {
		out("connection closed: "+connection);
		
	}

	public void connectionOpened(IConnection connection) {
		out("connection opened: "+connection);
		
	}

	public void connectionCreated(IConnection connection) {
		connection.addConnectionListener(this);
		out("connection created: "+connection);
		
	}

	public void connectionRemoved(IConnection connection) {
		out("connection removed: "+connection);
	}
	
	private void out(Object o){
		System.out.println("[W3ServerLogger] "+o);
	}
	
}

/* ------------------------------------------------------------------------- *
 * $Log$
 * Revision 1.1  2006/01/25 13:02:28  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/24 15:58:43  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.2  2006/01/06 23:46:40  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/05 16:53:23  lrosenberg
 * *** empty log message ***
 *
 */