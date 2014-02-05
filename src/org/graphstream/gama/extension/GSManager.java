package org.graphstream.gama.extension;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.graphstream.gama.extension.sender.GSSender;

import org.graphstream.stream.sync.SinkTime;
import org.graphstream.stream.sync.SourceTime;

/**
 * This is the main extension class.
 *
 * It maintains a common sync mechanism for all senders and receivers, as well
 * as methods to access senders and receivers by their id, to add and to remove
 * them.
 *
 * @author Stefan Balev
 *
 */
public class GSManager {
	private static String sourceId;
	private static SourceTime sourceTime;
	private static SinkTime sinkTime;

	private static Map<String, GSSender> senders;
//	private static Map<String, GSReceiver> receivers;

	static {
		sourceId = "gs-gama@"
				+ ManagementFactory.getRuntimeMXBean().getName();
		sourceTime = new SourceTime(sourceId);
		sinkTime = new SinkTime();
		sourceTime.setSinkTime(sinkTime);
		senders = new HashMap<String, GSSender>();
//		receivers = new HashMap<String, GSReceiver>();
	}


	public static GSSender getSender(String senderId)  {
		GSSender sender = senders.get(senderId);
		if (sender == null)
			System.out.println("Sender \"" + senderId
					+ "\" does not exist");
		return sender;
	}

	public static void addSender(String senderId, String host, int port) {
		GSSender sender = senders.get(senderId);
		if (sender != null)
			System.out.println("Sender \"" + senderId
					+ "\" already exists");
		sender = new GSSender(sourceId, sourceTime, host, port);
		senders.put(senderId, sender);
	}

	public static void clearSenders() {
		for (GSSender sender : senders.values())
			sender.close();
		senders.clear();
	}

	/*public static GSReceiver getReceiver(String receiverId)
			throws ExtensionException {
		GSReceiver receiver = receivers.get(receiverId);
		if (receiver == null)
			throw new ExtensionException("Receiver \"" + receiverId
					+ "\" does not exist");
		return receiver;
	}

	public static void addReceiver(String receiverId, String host, int port,
			Set<String> attributeFilter) throws ExtensionException {
		GSReceiver receiver = receivers.get(receiverId);
		if (receiver != null)
			throw new ExtensionException("Receiver \"" + receiverId
					+ "\" already exists");
		receiver = new GSReceiver(sinkTime, host, port, attributeFilter);
		receivers.put(receiverId, receiver);
	}

	public static void clearReceivers() {
		for (GSReceiver receiver : receivers.values())
			receiver.close();
		receivers.clear();
	}*/
}