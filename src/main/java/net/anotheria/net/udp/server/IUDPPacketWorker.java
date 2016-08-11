package net.anotheria.net.udp.server;

/**
 * TODO please remined another to comment this class
 * @author another
 */
public interface IUDPPacketWorker {
	public void proceedIncomingPacket(byte[] data, UDPSenderInfo senderInfo);
}
