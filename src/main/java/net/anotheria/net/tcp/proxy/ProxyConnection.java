package net.anotheria.net.tcp.proxy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import net.anotheria.net.shared.server.IConnection;
import net.anotheria.net.tcp.server.AbstractTCPConnection;

/**
 * A proxy connection which proxies a connection to a tcp server.
 * This class is yet considered experimental. 
 * @author lrosenberg
 */
public class ProxyConnection extends AbstractTCPConnection implements IConnection{


	private String host;
	private int port;
	
	private static AtomicInteger counter = new AtomicInteger();
	private int myCount = counter.incrementAndGet();
	
	public static final long SLEEP_TIME = 50;
	public static final long TIMEOUT_SECONDS_IN = 1;
	public static final long TIMEOUT_SECONDS_OUT = 2;

	
	public ProxyConnection(Socket inSocket, String aHost, int aPort){
		super(inSocket);
		host = aHost;
		port = aPort;
	}
	
	public void close() {
		System.out.println("CLOSE");
		super.close();
	}

	public void open() {
		System.out.println("OPEN");
		super.open();
	}

	OutputStream proxy_out_out;
	InputStream proxy_out_in;
	Socket outgoing;
	OutputStream in_out;
	
	private void outerclose(){
		try{
			proxy_out_out.close();
			proxy_out_in.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void run(){
		
		FileOutputStream fOut = null;
		
		try{
			System.out.println("================= START =============== ");
			//create outgoing connection
			fOut = new FileOutputStream(myCount+"_in.txt");
			
			outgoing = new Socket(host, port);
			proxy_out_out = outgoing.getOutputStream();
			proxy_out_in = outgoing.getInputStream();
			System.out.println("OUTGOING SOCKET CREATED ");
			
			System.out.println("READING ... ");
			InputStream in_in = getSocket().getInputStream();
			in_out = getSocket().getOutputStream();

			new StreamConnector(proxy_out_in, in_out);
			
			boolean reading = true;
			int in_no_reading = 0;
			while(reading){
				
				int avail = in_in.available();
				if (avail>0){
					byte[] data = new byte[avail];
					in_in.read(data);
					proxy_out_out.write(data);
					fOut.write(data);
					System.out.print(new String(data));
					in_no_reading = 0;
				}else{
					in_no_reading ++;
					if (in_no_reading > TIMEOUT_SECONDS_IN*1000/SLEEP_TIME){
						System.out.println("Closing in stream (NOT)");
						//in_in.close();
						System.out.println("Closing proxy out out (NOT)");
						//proxy_out_out.close();
						reading=false;
						break;
					}
					
				}
				
			}
			
			

			
			//proxy_out_in.close();
			System.out.println("Closing proxy_out_out");
			System.out.println("Closed proxy_out_out");
			
			//in_out.close();
			//in_in.close();
			
			
			//Thread.currentThread().sleep(5000);
			System.out.println("=== SERVED ====");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if (fOut!=null)
				try{
					fOut.close();
				}catch(IOException ignored){}
		}
		//close();
		
	}
	
	class StreamConnector extends Thread{
		private InputStream input;
		private OutputStream output;
		
		StreamConnector(InputStream anInputStream, OutputStream anOutputStream){
			input = anInputStream;
			output = anOutputStream;
			start();
		}
		
		
		public void run(){
			FileOutputStream fOut = null;
			try{
				boolean running = true;
				fOut = new FileOutputStream(myCount+"_out.txt");
				int nodata = 0;
				System.out.println("%%% Started reader ");
				while (running){
					int num = input.available();
					if (num==0){
						try{
							Thread.sleep(SLEEP_TIME);
						}catch(Exception igrnoed){}
						nodata++;
						
						if (nodata>TIMEOUT_SECONDS_OUT*1000/SLEEP_TIME){
							output.close();
							input.close();
							running = false;
							break;
						}
					}else{
						System.out.println("%%% Reading "+num+" bytes");
						nodata = 0;
						byte[] data = new byte[num];
						input.read(data);
						output.write(data);
						output.flush();
						fOut.write(data);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				//try{
					//outerclose();
				//}catch(Exception ign){}
			}finally{
				if (fOut!=null){
					try{
						fOut.close();
					}catch(IOException e){}
				}
			}
		}
	}
}
