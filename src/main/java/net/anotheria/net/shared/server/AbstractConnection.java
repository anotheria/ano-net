package net.anotheria.net.shared.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for IConnection implementations.
 * @author lrosenberg
 */
public abstract class AbstractConnection implements IConnection{
	/**
	 * The list with listeners.
	 */
	private List<IConnectionListener> listeners;
	
	/**
	 * Creates a new abstract connection with an empty listeners list.
	 */
	protected AbstractConnection(){
		listeners = new ArrayList<IConnectionListener>();
	}

	@Override public void addConnectionListener(IConnectionListener listener) {
		listeners.add(listener);	
	}

	@Override public void close() {
		for (int i=0; i<listeners.size(); i++)
			listeners.get(i).connectionClosed(this);
	}

	@Override public void open() {
		for (int i=0; i<listeners.size(); i++)
			listeners.get(i).connectionOpened(this);
	}

	@Override public void removeConnectionListener(IConnectionListener listener) {
		listeners.remove(listener);
	}
}
