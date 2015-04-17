/*
 * Copyright 2014 Thibaut Démare
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.graphstream.gama.extension.receiver;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gama.util.GamaList;

import org.graphstream.stream.SinkAdapter;
import org.graphstream.stream.netstream.NetStreamReceiver;
import org.graphstream.stream.sync.SinkTime;
import org.graphstream.stream.thread.ThreadProxyPipe;

/**
 * A receiver.
 *
 * A NetStreamReceiver plus helper methods that do the real job for all the
 * receiver primitives.
 * 
 * This class is almost a copy of the one from gs-netlogo but updated to run with Gama
 * 
 * @author Stefan Balev, modified by Thibaut Démare
 *
 */
public class GSReceiver extends SinkAdapter {
	protected SinkTime sinkTime;
	protected NetStreamReceiver nsReceiver;
	protected ThreadProxyPipe pipe;
	protected Attributes graphAttributes;
	protected Map<String, Attributes> nodeAttributes;
	protected Map<String, Attributes> edgeAttributes;
	protected Queue<Double> steps;
	protected Set<String> attributeFilter;

	public GSReceiver(SinkTime sinkTime, String host, int port,
			Set<String> attributeFilter) throws GamaRuntimeException {
		this.sinkTime = sinkTime;
		this.attributeFilter = attributeFilter;
		try {
			nsReceiver = new NetStreamReceiver(host, port);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		pipe = nsReceiver.getDefaultStream();
		pipe.addSink(this);
		graphAttributes = new Attributes();
		nodeAttributes = new HashMap<String, Attributes>();
		edgeAttributes = new HashMap<String, Attributes>();
		steps = new LinkedList<Double>();
	}

	public GamaList receiveGraphAttribute(String attribute) {
		pipe.pump();
		return graphAttributes.get(attribute);
	}

	public GamaList receiveNodeAttribute(String nodeId, String attribute) {
		pipe.pump();
		Attributes a = nodeAttributes.get(nodeId);
		return a == null ? null : a.get(attribute);
	}

	public GamaList receiveEdgeAttribute(String edgeId,	String attribute) {
		pipe.pump();
		Attributes a = edgeAttributes.get(edgeId);
		return a == null ? null : a.get(attribute);
	}

	public Double waitStep() {
		while (steps.isEmpty()) {
			// In new version of graphstream, we can use the following method :
			//pipe.blockingPump();
			// But the gs-core used by gama, do not allow us to use it, so we must use the following try/catch
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {

			}

			pipe.pump();
		}
		return steps.remove();
	}

	public void flush() {
		pipe.pump();
		graphAttributes = new Attributes();
		nodeAttributes.clear();
		edgeAttributes.clear();
		steps.clear();
	}

	public void close() {
		nsReceiver.quit();
	}

	// Sink methods

	@Override
	public void edgeAttributeAdded(String sourceId, long timeId, String edgeId,
			String attribute, Object value) {
		if (sinkTime.isNewEvent(sourceId, timeId)
				&& (attributeFilter == null || attributeFilter
				.contains(attribute))) {
			Attributes a = edgeAttributes.get(edgeId);
			if (a == null) {
				a = new Attributes();
				edgeAttributes.put(edgeId, a);
			}
			a.add(attribute, value);
		}
	}

	@Override
	public void edgeAttributeChanged(String sourceId, long timeId,
			String edgeId, String attribute, Object oldValue, Object newValue) {
		edgeAttributeAdded(sourceId, timeId, edgeId, attribute, newValue);
	}

	@Override
	public void graphAttributeAdded(String sourceId, long timeId,
			String attribute, Object value) {
		if (sinkTime.isNewEvent(sourceId, timeId)
				&& (attributeFilter == null || attributeFilter
				.contains(attribute)))
			graphAttributes.add(attribute, value);
	}

	@Override
	public void graphAttributeChanged(String sourceId, long timeId,
			String attribute, Object oldValue, Object newValue) {
		graphAttributeAdded(sourceId, timeId, attribute, newValue);
	}

	@Override
	public void nodeAttributeAdded(String sourceId, long timeId, String nodeId,
			String attribute, Object value) {
		if (sinkTime.isNewEvent(sourceId, timeId)
				&& (attributeFilter == null || attributeFilter
				.contains(attribute))) {
			Attributes a = nodeAttributes.get(nodeId);
			if (a == null) {
				a = new Attributes();
				nodeAttributes.put(nodeId, a);
			}
			a.add(attribute, value);
		}
	}

	@Override
	public void nodeAttributeChanged(String sourceId, long timeId,
			String nodeId, String attribute, Object oldValue, Object newValue) {
		nodeAttributeAdded(sourceId, timeId, nodeId, attribute, newValue);
	}

	@Override
	public void stepBegins(String sourceId, long timeId, double step) {
		if (sinkTime.isNewEvent(sourceId, timeId))
			steps.add(step);
	}
}