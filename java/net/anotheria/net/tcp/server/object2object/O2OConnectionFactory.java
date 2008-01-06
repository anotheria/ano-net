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
package net.anotheria.net.tcp.server.object2object;

import net.anotheria.net.shared.server.IConnection;
import net.anotheria.net.shared.server.IConnectionCreationContext;
import net.anotheria.net.shared.server.IConnectionFactory;
import net.anotheria.net.tcp.server.SocketContext;

public class O2OConnectionFactory implements IConnectionFactory{
	
	private IO2OWorkerFactory workerFactory;
	private boolean keepAlive;
	
	O2OConnectionFactory(IO2OWorkerFactory aWorkerFactory, boolean aKeepAlive){
		workerFactory = aWorkerFactory;
		keepAlive = aKeepAlive;
	}

	public IConnection createConnection(IConnectionCreationContext context) {
		O2OConnection newConnection = new O2OConnection(((SocketContext)context).getSocket(), 
				workerFactory.createWorker(),
				keepAlive);
		return newConnection;
	}

}

/* ------------------------------------------------------------------------- *
 * $Log$
 * Revision 1.2  2008/01/06 19:08:09  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/25 13:02:28  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/24 15:58:43  lrosenberg
 * *** empty log message ***
 *
 */