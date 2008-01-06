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

public abstract class AbstractServer implements IServer{
	private List<IServerListener> listeners;
	private IConnectionFactory connectionFactory;
	
	protected AbstractServer(){
		listeners = new ArrayList<IServerListener>();
	}
	
	protected AbstractServer(IConnectionFactory aConnectionFactory){
		this();
		connectionFactory = aConnectionFactory;
	}

	public void addServerListener(IServerListener listener){
		listeners.add(listener);
	}
	
	public void removeServerListener(IServerListener listener){
		listeners.remove(listener);
	}
	
	protected void notifyConnectionCreated(IConnection con){
		for (int i=0; i<listeners.size(); i++)
			listeners.get(i).connectionCreated(con);
	}

	protected void notifyConnectionRemoved(IConnection con){
		for (int i=0; i<listeners.size(); i++)
			listeners.get(i).connectionRemoved(con);
	}

	public IConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(IConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

}

