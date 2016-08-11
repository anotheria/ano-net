package test.o2o;

import net.anotheria.net.tcp.server.object2object.IO2OWorker;
import net.anotheria.net.tcp.server.object2object.IO2OWorkerFactory;

public class EchoWorkerFactory implements IO2OWorkerFactory{

	public IO2OWorker createWorker() {
		return new EchoWorker();
	}

}
