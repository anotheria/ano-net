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
package net.anotheria.net.tcp.client.object2object;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import net.anotheria.net.shared.server.AbstractConnection;

/**
 * A connection which is capable of sending objects. Client side.
 * @author lrosenberg
 *
 */
public class O2OClientConnection extends AbstractConnection{
	
	private Socket socket;
	
	private String host;
	private int port;
	
	private InputStream in;
	private ObjectInputStream oIn;
	private OutputStream out;
	private ObjectOutputStream oOut;
	
	private int counter;
	
	public O2OClientConnection(String aHost, int aPort){
		host = aHost;
		port = aPort;
	}
	
	public O2OClientConnection connect() throws UnknownHostException, IOException{
		socket = new Socket(InetAddress.getByName(host) ,port);
		return this;
		
	}
	
	@Override public void open() {
		try{
			connect();
		}catch(Exception e){
			throw new RuntimeException("Couldn't connect: "+e.getMessage());
		}
		super.open();
	}
	
	@Override public void close(){
		try{
			socket.close();
		}catch(Exception e){
			throw new RuntimeException("Couldn't close: "+e.getMessage());
		}
	}
	
	public static O2OClientConnection createConnection(String host, int port) throws UnknownHostException, IOException{
		return new O2OClientConnection(host, port).connect();
	}
	
	public Object sendObjectAndReturnResult(Object toSend) throws IOException, ClassNotFoundException{
		if (out==null)
			out = socket.getOutputStream();
		if (oOut == null)
			oOut = new ObjectOutputStream(out);
		
		out.write(10);
		oOut.writeObject(toSend);
		oOut.flush();
		
		if (in == null)
			in = socket.getInputStream();
		if (oIn==null)
			oIn = new ObjectInputStream(in);
		in.read();
		Object ret = oIn.readObject();
		
		counter++;
		if (counter==100){
			oOut.reset();
		}
		
		return ret;
	}
}