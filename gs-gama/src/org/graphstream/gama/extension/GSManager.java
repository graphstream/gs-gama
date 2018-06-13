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
package org.graphstream.gama.extension;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;

import org.graphstream.gama.extension.receiver.GSReceiver;
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
 * This class is almost a copy of the one from gs-netlogo but updated to run with gama
 * 
 * @author Stefan Balev, modified by Thibaut Démare
 *
 */
public class GSManager {
	private static String sourceId;
	private static SourceTime sourceTime;
	private static SinkTime sinkTime;

	private static Map<String, GSSender> senders;
	private static Map<String, GSReceiver> receivers;

	static {
		sourceId = "gs-gama@"
				+ ManagementFactory.getRuntimeMXBean().getName();
		sourceTime = new SourceTime(sourceId);
		sinkTime = new SinkTime();
		sourceTime.setSinkTime(sinkTime);
		senders = new HashMap<String, GSSender>();
		receivers = new HashMap<String, GSReceiver>();
	}


	public static GSSender getSender(String senderId) throws GamaRuntimeException {
		GSSender sender = senders.get(senderId);
		if (sender == null)
			throw new RuntimeException("Sender \"" + senderId
					+ "\" does not exist");
		return sender;
	}

	public static GSSender removeSender(String senderId) throws GamaRuntimeException {
		GSSender sender = senders.remove(senderId);
		if (sender == null)
			throw new RuntimeException("Sender \"" + senderId
					+ "\" does not exist");
		return sender;
	}
	
	public static void addSender(String senderId, String host, int port) throws GamaRuntimeException {
		GSSender sender = senders.get(senderId);
		if (sender != null)
			throw new RuntimeException("Sender \"" + senderId
					+ "\" already exists");
		sender = new GSSender(sourceId, sourceTime, host, port);
		senders.put(senderId, sender);
	}

	public static void clearSenders() {
		for (GSSender sender : senders.values())
			if(sender != null)
				sender.close();
		senders.clear();
	}

	public static GSReceiver getReceiver(String receiverId)
			throws GamaRuntimeException { 
		GSReceiver receiver = receivers.get(receiverId);
		if (receiver == null)
			throw new RuntimeException("Receiver \"" + receiverId
					+ "\" does not exist");
		return receiver;
	}

	public static void addReceiver(IScope scope, String receiverId, String host, int port,
			Set<String> attributeFilter) throws GamaRuntimeException {
		GSReceiver receiver = receivers.get(receiverId);
		if (receiver != null)
			throw new RuntimeException("Receiver \"" + receiverId
					+ "\" already exists");
		receiver = new GSReceiver(scope, sinkTime, host, port, attributeFilter);
		receivers.put(receiverId, receiver);
	}

	public static void clearReceivers() {
		for (GSReceiver receiver : receivers.values())
			receiver.close();
		receivers.clear();
	}
}