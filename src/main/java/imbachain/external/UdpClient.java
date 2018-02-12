package imbachain.external;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import imbachain.infrastructure.BaseNode;
import imbachain.infrastructure.NodeInterface;

@Component
public class UdpClient {

	@Autowired
	private NodeInterface node;

	@ServiceActivator(inputChannel = "udp-channel")
	public void getData(byte[] message) {
		node.handleUdpMessage(new String(message));
	}

	public void sendMessage(String message, String host, int port) throws IOException {
		InetSocketAddress sock = new InetSocketAddress("localhost", 5555);
		byte[] udpMessage = message.getBytes();
		DatagramPacket packet = null;
		try (DatagramSocket socket = new DatagramSocket();) {
			packet = new DatagramPacket(udpMessage, udpMessage.length, sock);
			socket.send(packet);
			socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void broadcastMessage(String message, Collection<BaseNode> peers) {
		byte[] udpMessage = message.getBytes();
		DatagramPacket packet = null;
		try (DatagramSocket socket = new DatagramSocket();) {
			for (BaseNode peer : peers) {
				packet = new DatagramPacket(udpMessage, udpMessage.length,
						new InetSocketAddress(peer.getAddress(), peer.getPort()));
				socket.send(packet);
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}