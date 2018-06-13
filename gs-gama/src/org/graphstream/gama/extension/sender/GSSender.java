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
package org.graphstream.gama.extension.sender;
import java.io.IOException;
import java.net.UnknownHostException;

import org.graphstream.stream.netstream.NetStreamSender;
import org.graphstream.stream.sync.SourceTime;

/**
 * A sender.
 *
 * A NetStreamSender plus helper methods that do the real job for all the sender
 * primitives.
 * 
 * This class is an adapted copy of the GSSender from the netlogo extension.
 * 
 * @author Stefan, changed by Thibaut
 *
 */
// TODO:  change the "sop" by a GAMA exception
public class GSSender {
	protected String sourceId;
	protected SourceTime sourceTime;
	protected NetStreamSender nsSender;

	public GSSender(String sourceId, SourceTime sourceTime, String host,
			int port) {
		this.sourceId = sourceId;
		this.sourceTime = sourceTime;
		try {
			nsSender = new NetStreamSender(host, port);
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendNodeAdded(String nodeId) {
		nsSender.nodeAdded(sourceId, newEvent(), nodeId);
	}

	public void sendEdgeAdded(String edgeId, String fromId, String toId, boolean directed) {
		nsSender.edgeAdded(sourceId, newEvent(), edgeId, fromId, toId, directed);
	}

	public void sendNodeRemoved(String nodeId) {
		nsSender.nodeRemoved(sourceId, newEvent(), nodeId);
	}

	public void sendEdgeRemoved(String edgeId) {
		nsSender.edgeRemoved(sourceId, newEvent(), edgeId);
	}

	public void sendGraphAttributeAdded(String attribute, Object value) {
		nsSender.graphAttributeAdded(sourceId, newEvent(), attribute, value);
	}

	public void sendNodeAttributeAdded(String nodeId, String attribute,
			Object value) {
		nsSender.nodeAttributeAdded(sourceId, newEvent(), nodeId,
				attribute, value);
	}

	public void sendEdgeAttributeAdded(String edgeId,
			String attribute, Object value) {
		nsSender.edgeAttributeAdded(sourceId, newEvent(), edgeId,
				attribute, value);
	}

	public void sendGraphAttributeRemoved(String attribute) {
		nsSender.graphAttributeRemoved(sourceId, newEvent(), attribute);
	}

	public void sendNodeAttributeRemoved(String nodeId, String attribute) {
		nsSender.nodeAttributeRemoved(sourceId, newEvent(), nodeId,
				attribute);
	}

	public void sendEdgeAttributeRemoved(String edgeId,
			String attribute) {
		nsSender.edgeAttributeRemoved(sourceId, newEvent(),
				edgeId, attribute);
	}

	public void sendGraphCleared() {
		nsSender.graphCleared(sourceId, newEvent());
	}

	public void sendStepBegins(double step) {
		nsSender.stepBegins(sourceId, newEvent(), step);
	}

	public void close() {
		try {
			nsSender.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	protected long newEvent() {
		return sourceTime.newEvent();
	}

}