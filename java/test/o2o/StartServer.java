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
package test.o2o;

import net.anotheria.net.shared.server.ServerLogger;
import net.anotheria.net.tcp.server.object2object.O2OServer;

public class StartServer {
	public static void main(String a[]) throws Exception{
		O2OServer server = new O2OServer(9999,true, new EchoWorkerFactory());
		System.out.println("Server created.");
		new ServerLogger(server);
		server.startServer();
		System.out.println("Server started.");
	}
}

/* ------------------------------------------------------------------------- *
 * $Log$
 * Revision 1.3  2008/01/06 19:10:42  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.2  2006/01/25 13:02:28  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/24 15:58:43  lrosenberg
 * *** empty log message ***
 *
 */