package net.anotheria.net.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import net.anotheria.net.shared.server.AbstractServer;
import net.anotheria.net.shared.server.IConnection;
import net.anotheria.net.shared.server.IConnectionFactory;
import net.anotheria.net.shared.server.ServerException;

/**
 * A basic server for tcp connections.
 * @author lrosenberg
 *
 */
public class BasicTCPServer extends AbstractServer implements Runnable{
	/**
	 * The port to listen on.
	 */
	private int port;
	/**
	 * The listening socket.
	 */
	private ServerSocket serverSocket;
	/**
	 * True as long as we are running.
	 */
	private volatile boolean running;
	
	/**
	 * Creates a new server with the given port and connection factory.
	 * @param aPort the port to listen to.
	 * @param conFactory
	 */
	public BasicTCPServer(int aPort, IConnectionFactory conFactory){
		super(conFactory);
		port = aPort;
	}
	

	@Override public void startServer() throws ServerException{
		try{
			serverSocket = new ServerSocket(port);
		}catch(IOException e){
			throw new ServerException("Couldn't bind port: "+port+" : "+e.getMessage());
		}
		new Thread(this).start();
	}
	
	@Override public void run(){
		setRunning(true);
		while(isRunning()){
			try{
				Socket s = serverSocket.accept();
				IConnection newConnection = getConnectionFactory().createConnection(new SocketContext(s));
				notifyConnectionCreated(newConnection);
				newConnection.open();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * Stops the server.
	 */
	@Override public void stopServer() {
		setRunning(false);
		
	}

	/**
	 * Returns true if the server is running
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Used to stop the server by stoping the thread.
	 * @param running false if the server has to be stoped.
	 */
	public void setRunning(boolean aRunning) {
		this.running = aRunning;
	}
}
