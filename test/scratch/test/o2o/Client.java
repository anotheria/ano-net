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
