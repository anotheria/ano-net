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

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractConnection implements IConnection{
	private List<IConnectionListener> listeners;
	
	protected AbstractConnection(){
		listeners = new ArrayList<IConnectionListener>();
	}

	public void addConnectionListener(IConnectionListener listener) {
		listeners.add(listener);	
	}

	public void close() {
		for (int i=0; i<listeners.size(); i++)
			listeners.get(i).connectionClosed(this);
	}

	public void open() {
		for (int i=0; i<listeners.size(); i++)
			listeners.get(i).connectionOpened(this);
	}

	public void removeConnectionListener(IConnectionListener listener) {
		listeners.remove(listener);
		
	}
}

/* ------------------------------------------------------------------------- *
 * $Log$
 * Revision 1.2  2008/01/06 19:07:05  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/25 13:02:28  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2006/01/05 16:53:10  lrosenberg
 * *** empty log message ***
 *
 */