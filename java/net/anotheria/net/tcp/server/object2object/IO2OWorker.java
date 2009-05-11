package net.anotheria.net.tcp.server.object2object;

/**
 * THe worker is used to proceed incoming requests and return a result to send back.
 * @author lrosenberg 
 */
public interface IO2OWorker {
	/**
	 * Called for each incoming object.
	 * @param in the incoming parameter object.
	 * @return a returnvalue object
	 */
	public Object processObjectAndReturnResult(Object in);
}

