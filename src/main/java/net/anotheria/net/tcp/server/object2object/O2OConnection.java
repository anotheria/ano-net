package net.anotheria.net.tcp.server.object2object;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.anotheria.net.tcp.server.AbstractTCPConnection;

/**
 * A connection to exchange objects via an underlying tcp connection. 
 * @author lrosenberg
 */
public class O2OConnection extends AbstractTCPConnection{
	
	/**
	 * True if the connection is to be kept over multiple requests.
	 */
	private boolean keepAlive;
	/**
	 * The worker to proceed incoming requests.
	 */
	private IO2OWorker worker;
	
	/**
	 * Creates a new object to object connection.
	 * @param s the socket to bind the connection too.
	 * @param aWorker the worker for proceeding incoming requests.
	 * @param aKeepAlive whether the connection should be keep alived after request has been sent. 
	 */
	O2OConnection(Socket s, IO2OWorker aWorker, boolean aKeepAlive){
		super(s);
		worker = aWorker;
		keepAlive = aKeepAlive;
	}
	
	/**
	 * Creates a new keep alived object to object connection.
	 * @param s the socket to bind the connection too.
	 * @param aWorker the worker for proceeding incoming requests.
	 */
	O2OConnection(Socket s, IO2OWorker aWorker){
		this(s, aWorker, true);
	}
	
	@Override public void run(){
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
	
	/**
	 * Receives an object over the line.
	 * @param in the input stream to read from.
	 * @param oIn the objectinputstream ontop of the input stream.
	 * @return a read object.
	 * @throws IOException 
	 * @throws ClassNotFoundException
	 */
	private Object receiveObject(InputStream in, ObjectInputStream oIn) throws IOException, ClassNotFoundException{
		in.read();
		Object o = oIn.readObject();
		return o;
	}
	
	/**
	 * Sends an object over the line.
	 * @param toSend the object to send.
	 * @param out the output stream.
	 * @param oOut the object output stream ontop of the output stream.
	 * @throws Exception thrown if something got wrong.
	 */
	private void sendObject(Object toSend, OutputStream out, ObjectOutputStream oOut) throws Exception{
		out.write(10);
		oOut.writeObject(toSend);
		oOut.flush();
	}
	
	/**
	 * Returns the value of the keepalive attribute.
	 * @return true if the connection should be keepalived between requests.
	 */
	public boolean isKeepAlive() {
		return keepAlive;
	}

	/**
	 * Sets the value of the keepalive attribute.
	 * @param keepAlive the new value to set.
	 */
	public void setKeepAlive(boolean aKeepAlive) {
		keepAlive = aKeepAlive;
	}
	
	
}
