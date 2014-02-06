package org.graphstream.gama.tests;

import java.io.IOException;
import java.net.UnknownHostException;

import org.graphstream.stream.netstream.NetStreamReceiver;

public class Tests {

	public static void main(String[] args) throws InterruptedException, UnknownHostException, IOException {
		NetStreamReceiver receiver = new NetStreamReceiver(2001);
		new SimpleNetStreamViewer(receiver, true);
		new SimpleSinkAdapter(receiver);
	}

}