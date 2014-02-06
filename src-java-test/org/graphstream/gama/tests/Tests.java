package org.graphstream.gama.tests;

import java.io.IOException;
import java.net.UnknownHostException;

import org.graphstream.stream.netstream.NetStreamReceiver;

public class Tests {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		// test graph viewer
		new SimpleNetStreamViewer(new NetStreamReceiver(2000), true, 500, 500);
		
		// test graph viewer
		new SimpleNetStreamViewer(new NetStreamReceiver(2001), true, 500, 500);
	
	}

}