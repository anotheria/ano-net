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
package test.w3server;

import net.anotheria.net.shared.server.IConnection;
import net.anotheria.net.shared.server.IConnectionCreationContext;
import net.anotheria.net.shared.server.IConnectionFactory;
import net.anotheria.net.tcp.server.SocketContext;

public class W3ConnectionFactory implements IConnectionFactory{

	public IConnection createConnection(IConnectionCreationContext context) {
		return new W3Connection(((SocketContext)context).getSocket());
	}

}

/* ------------------------------------------------------------------------- *
 * $Log$
 * Revision 1.3  2008/01/06 19:10:21  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.2  2006/01/25 13:02:28  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/05 16:53:23  lrosenberg
 * *** empty log message ***
 *
 */