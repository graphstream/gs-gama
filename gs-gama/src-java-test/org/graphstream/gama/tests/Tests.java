package org.graphstream.gama.tests;

import java.io.IOException;
import java.net.UnknownHostException;

import org.graphstream.stream.netstream.NetStreamReceiver;
import org.graphstream.stream.netstream.NetStreamSender;

public class Tests {

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		// Receive event
		NetStreamReceiver receiver = new NetStreamReceiver(2001);
		new SimpleNetStreamViewer(receiver, true);
		new SimpleSinkAdapter(receiver);
		
		// Send event
		System.out.println("Press enter and then it will start to send value");
		System.in.read();
		
		NetStreamSender sender = new NetStreamSender(3001);
		long timeId = 0;
		System.out.println("Start sending data.");
		sender.stepBegins("foo", timeId++, 1);
		
		sender.graphAttributeAdded("foo", timeId++, "a", 1);
		sender.graphAttributeAdded("foo", timeId++, "a", true);
		sender.graphAttributeAdded("foo", timeId++, "a", "bar");
		Integer[] array = new Integer[3];
		array[0] = 0; array[1] = 1; array[2] = 2;
		sender.graphAttributeAdded("foo", timeId++, "a", array);
		sender.graphAttributeAdded("foo", 0, "a", "old");

		sender.stepBegins("foo", timeId++, 1);

		sender.nodeAttributeAdded("foo", timeId++, "0", "na", "bar");
		sender.nodeAttributeChanged("foo", timeId++, "0", "na", "bar", "newbar");

		sender.stepBegins("foo", timeId++, 2);

		sender.edgeAttributeAdded("foo", timeId++, "0_1", "ea", array);
		sender.close();
	}

}