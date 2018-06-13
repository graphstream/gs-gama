# The Graphstream to GAMA user guide

## How to receive events from a GAMA simulation

The main class used in this case is called `NetStreamReceiver`. It works as a server: you define a host and its port, and this class will listen the incoming events.

**The NetStreamReceiver:**
```java
// Open a connection on localhost on the port 2009
NetStreamReceiver receiver = new NetStreamReceiver("localhost", 2009);
```

After that, you need to use a sink in order to build a graph.

### The NetStreamViewer solution

Firstly, you can use a viewer which allows to display a graph. However this solution has some limitations since the viewer can not directly process algorithms on the graph or save it.

**The NetStreamViewer class**
```java
import org.graphstream.stream.netstream.NetStreamReceiver;
import org.graphstream.ui.swingViewer.Viewer;

public class NetStreamViewer extends Viewer {
	public NetStreamViewer(NetStreamReceiver receiver) {
		this(receiver, true);
	}

	public NetStreamViewer(NetStreamReceiver receiver, boolean autoLayout) {
		super(receiver.getDefaultStream());
		addDefaultView(true);
		if (autoLayout)
			enableAutoLayout();
	}

	public NetStreamViewer(NetStreamReceiver receiver, boolean autoLayout, int width, int height) {
		this(receiver, autoLayout);
		getDefaultView().resizeFrame(width, height);
	}
}
```

**The main**
```java
import org.graphstream.stream.netstream.NetStreamReceiver;

public class MainReceiver {

	public static void main(String[] args) throws UnknownHostException, IOException {
		// Open a connection on localhost on the port 2009
		NetStreamReceiver receiver = new NetStreamReceiver("localhost", 2009);
		// Start the viewer
		new NetStreamViewer(receiver, true);
	}
}
```

### The Sink solution

The next solution is to define a `sink` to a graph. The graph will then be updated progressively with the incoming events. 

**The `sink` associated to a graph:**
```java
// Open a connection on localhost on the port 2009
NetStreamReceiver receiver = new NetStreamReceiver("localhost", 2009);
// Instantiate a graph
SingleGraph graph = new SingleGraph("test", false, false);
// Add the sink
receiver.getDefaultStream().addSink(graph);
// The graph is now updated according to the received events
// In this case, the events are not pump automatically. We need to do it manually.
while(true){
	receiver.getDefaultStream().pump();
	// A sleep to avoid an overload of the CPU
	Thread.sleep(1000);
}
```

The `sink` can be associated to numerous GraphStream class, other than a graph. For instance, it can be used to fill a file directly.

**The `sink` associated to a file:**
```java
// Open a connection on localhost on the port 2009
NetStreamReceiver receiver = new NetStreamReceiver("localhost", 2009);
// Create a file sink in order to save the events in the DGS format
FileSinkDGS fileSink = new FileSinkDGS();
// Add the sink
receiver.getDefaultStream().addSink(fileSink);
// Open the file and start to fill it according to the received events
try {
	fileSink.begin(System.getProperty("user.dir" )+File.separator+"my_graph.dgs");
} catch (IOException e) {
	e.printStackTrace();
}
// In this case, the events are not pump automatically. We need to do it manually.
while(true){
	receiver.getDefaultStream().pump();
	// The file must be flush sometimes due to the buffer limit
	try {
		fileSink.flush();
	} catch (IOException e) {
		e.printStackTrace();
	}
	// A sleep to avoid an overload of the CPU
	Thread.sleep(1000);
}
```

Eventually, you also can used your own SinkAdapter and execute particular algorithms when specific events are received.

**The SaveWhenStepSinkAdapter class:** for instance, this class compute the betweenness centrality of the graph and save it in the [DGS format](http://graphstream-project.org/doc/Advanced-Concepts/The-DGS-File-Format_1.1/) at each step event received.
```java
import java.io.File;
import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.SinkAdapter;
import org.graphstream.stream.netstream.NetStreamReceiver;

public class SaveWhenStepSinkAdapter extends SinkAdapter {
	
	private Graph graph;

	public SaveWhenStepSinkAdapter(NetStreamReceiver receiver) {
		graph = new SingleGraph("my_graph", false, false);
		receiver.getDefaultStream().addSink(graph);
		receiver.getDefaultStream().addSink(this);
	}

	@Override
	public void stepBegins(String sourceId, long timeId, double step) {
		BetweennessCentrality bcb = new BetweennessCentrality();
		bcb.setUnweighted();
		bcb.setCentralityAttributeName("BetweennessCentrality");
		bcb.init(graph);
		bcb.compute();

		try {
			graph.write(System.getProperty("user.dir" )+File.separator+graph.getAttribute("name")+"_"+sourceId+"_"+timeId+"_"+step+".dgs");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```

## How to send events to a GAMA simulation

Theoretically, you just need to use the `NetStreamSender` class to send events to a GAMA simulation.

**The simplest way:**
```java
NetStreamSender sender = new NetStreamSender("localhost", 3001);
sender.stepBegins("foo", 0, 1);
sender.graphAttributeAdded("foo", 1, "an_attribute", 1);
sender.nodeAdded("foo", 2, "node1");
sender.nodeAdded("foo", 3, "node2");
sender.edgeAdded("foo", 4, "edge12", "node1", "node2", false);
sender.nodeAttributeAdded("foo", 5, "node1", "my_attribute", "my_value");
sender.edgeAttributeAdded("foo", 6, "edge12", "another_attribute", 1);
sender.close();
```

But you also can associate a `sink` between an object `graph` and the sender.

**The other way to make the same thing:**
```java
NetStreamSender sender = new NetStreamSender("localhost", 3001);
SingleGraph graph = new SingleGraph("foo");
graph.addSink(sender);
graph..stepBegins("foo", 0, 1);
graph.addAttribute("an_attribute", 1);
graph.addNode("node1");
graph.addNode("node2");
graph.addEdge("edge12", "node1", "node2", false);
graph.getNode("node1").addAttribute("my_attribute", "my_value");
graph.getEdge("edge12").addAttribute("another_attribute", 1);
sender.close();
```
