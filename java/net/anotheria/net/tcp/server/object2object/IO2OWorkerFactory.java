package net.anotheria.net.tcp.server.object2object;
/**
 * The worker factory is used to create workers for processing incoming objects and requests. 
 * @author lrosenberg
 */
public interface IO2OWorkerFactory {
	/**
	 * Creates a new worker.
	 * @return the newly created worker.
	 */
	public IO2OWorker createWorker();
}
