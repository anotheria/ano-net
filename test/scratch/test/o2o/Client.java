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

import net.anotheria.net.tcp.client.object2object.O2OClientConnection;

public class Client {
	public static void main(String a[]) throws Exception{
		String host = a[0];
		int port = Integer.parseInt(a[1]);
		String toSend = a[2];
		
		System.out.println("Sending : "+toSend+" to "+host+":"+port);
		
		O2OClientConnection connection = O2OClientConnection.createConnection(host, port);
		Object reply = connection.sendObjectAndReturnResult(toSend);
		System.out.println("server replied: "+reply);
		connection.close();
		
		
	}
}

/* ------------------------------------------------------------------------- *
 * $Log$
 * Revision 1.2  2008/01/06 19:10:42  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/25 13:02:28  lrosenberg
 * *** empty log message ***
 *
 */