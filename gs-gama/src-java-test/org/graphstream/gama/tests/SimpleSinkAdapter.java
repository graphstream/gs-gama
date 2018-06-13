package org.graphstream.gama.tests;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.SinkAdapter;
import org.graphstream.stream.netstream.NetStreamReceiver;

public class SimpleSinkAdapter extends SinkAdapter {
	
	private Graph graph;

	public SimpleSinkAdapter(NetStreamReceiver receiver) {
		graph = new SingleGraph("test", false, false);
		receiver.getDefaultStream().addSink(graph);
		receiver.getDefaultStream().addSink(this);
	}

	@Override
	public void stepBegins(String sourceId, long timeId, double step) {
		System.out.println("Edges attributes :");
		for(Edge e : graph.getEachEdge()){
			for(String o : e.getAttributeKeySet()){
				System.out.println(e.getAttribute(o).getClass());
				if(e.getAttribute(o).getClass().isArray()){
					Object[] array = (Object[])e.getAttribute(o);
					System.out.print('\t');
					for(Object ob : array)
						System.out.print(ob);
					System.out.println();
				}
				else{
					System.out.println("\tvalue = "+e.getAttribute(o));
				}
			}
		}
		System.out.println("Nodes attributes :");
		for(Node e : graph.getEachNode()){
			for(String o : e.getAttributeKeySet()){
				System.out.println(e.getAttribute(o).getClass());
				if(e.getAttribute(o).getClass().isArray()){
					Object[] array = (Object[])e.getAttribute(o);
					System.out.print('\t');
					for(Object ob : array)
						System.out.print(ob);
					System.out.println();
				}
				else{
					System.out.println("\tvalue = "+e.getAttribute(o));
				}			
			}
		}
		System.out.println("Graph attributes :");
		for(String o : graph.getAttributeKeySet()){
			System.out.println(graph.getAttribute(o).getClass());
			if(graph.getAttribute(o).getClass().isArray()){
				Object[] array = (Object[])graph.getAttribute(o);
				System.out.print('\t');
				for(Object ob : array)
					System.out.print(ob);
				System.out.println();
			}
			else{
				System.out.println("\tvalue = "+graph.getAttribute(o));
			}
		}
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}
}