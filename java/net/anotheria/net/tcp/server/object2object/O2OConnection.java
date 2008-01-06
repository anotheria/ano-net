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

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.anotheria.net.tcp.server.AbstractTCPConnection;

public class O2OConnection extends AbstractTCPConnection{
	
	private boolean keepAlive;
	private IO2OWorker worker;
	
	O2OConnection(Socket s, IO2OWorker aWorker, boolean aKeepAlive){
		super(s);
		worker = aWorker;
		keepAlive = aKeepAlive;
	}
	
	O2OConnection(Socket s, IO2OWorker aWorker){
		this(s, aWorker, true);
	}
	
	public void run(){
		//in case we were 2 fast
		while(worker==null)
			try{
				Thread.sleep(50);
			}catch(InterruptedException ignored){}
		int counter = 0;
		InputStream in = null;
		ObjectInputStream oIn = null;
		OutputStream out = null;
		ObjectOutputStream oOut = null;
		do{
			try{
				if (in==null)
					in = getSocket().getInputStream();
				if (oIn==null)
					oIn = new ObjectInputStream(in);
				if (out==null)
					out = getSocket().getOutputStream();
				if (oOut==null)
					oOut = new ObjectOutputStream(out);
				counter++;
				Object inObject = receiveObject(in, oIn);
				Object outObject = worker.processObjectAndReturnResult(inObject);
				sendObject(outObject, out, oOut);
				if (counter == 100 && oOut!=null)
					oOut.reset();
			}catch(EOFException e0){
				System.out.println("Connection closed");
				keepAlive = false;
			}catch(IOException e1){
				e1.printStackTrace();
			}catch(ClassNotFoundException e2){
				e2.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}while(keepAlive);
		close();
	}
	
	private Object receiveObject(InputStream in, ObjectInputStream oIn) throws IOException, ClassNotFoundException{
		in.read();
		Object o = oIn.readObject();
		return o;
	}
	
	private void sendObject(Object toSend, OutputStream out, ObjectOutputStream oOut) throws Exception{
		out.write(10);
		oOut.writeObject(toSend);
		oOut.flush();
	}
	
	

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
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