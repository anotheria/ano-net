package test.w3server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.anotheria.net.tcp.server.AbstractTCPConnection;

public class W3Connection extends AbstractTCPConnection {
	
	W3Connection(Socket s){
		super(s);
	}

	public void run(){
		try{
			InputStream oIn = new BufferedInputStream(getSocket().getInputStream());
			int r;
			String line = "";
			boolean cr = false;
			String headers = "";
			while( (r=oIn.read())!=-1){
				if (r=='\r')
					continue;
				if (r=='\n' && cr){
					System.out.println("=== HEADERS END ====");
					break;
				}
				
				if (r=='\n')
					cr = true;
				else
					cr = false;

				line += (char)r;
				
				if (cr){
					System.out.print(line);
					headers += line;
					line = "";
				}
				
				
			}
			
			OutputStream out = new BufferedOutputStream(getSocket().getOutputStream());
			for (int i=0; i<RESP.length; i++){
				out.write((RESP[i]+"\n").getBytes());
			}
			out.write(headers.getBytes());
			out.close();
			oIn.close();
			System.out.println("=== SERVED ====");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		close();
	}
	
	private static final String RESP[] = {
		"HTTP/1.x 200 OK",
		"Date: Thu, 05 Jan 2006 15:42:20 GMT",
		"Server: W3Server 0.0.0.1",
		"Connection: Close",
		"\n"
	};

}
