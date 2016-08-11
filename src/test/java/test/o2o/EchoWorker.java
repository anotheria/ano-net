package test.o2o;

import net.anotheria.net.tcp.server.object2object.IO2OWorker;

public class EchoWorker implements IO2OWorker{

	public Object processObjectAndReturnResult(Object in) {
		System.out.println("Received: "+in);
		return new String("Thank you for sending: "+in);
	}

}
