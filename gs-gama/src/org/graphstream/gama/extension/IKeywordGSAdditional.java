/*
 * Copyright 2014 Thibaut D�mare
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

import msi.gama.common.interfaces.IKeyword;

/**
 * This class contains all keywords used by the gs-gama plug-in.
 * @author Thibaut D�mare
 *
 */
public class IKeywordGSAdditional implements IKeyword {
	// New commands
		// Sender command
	public static final String ADD_EDGE = "gs_add_edge";
	public static final String ADD_EDGE_ATTRIBUTE = "gs_add_edge_attribute";
	public static final String ADD_GRAPH_ATTRIBUTE = "gs_add_graph_attribute";
	public static final String ADD_NODE = "gs_add_node";
	public static final String ADD_NODE_ATTRIBUTE = "gs_add_node_attribute";
	public static final String ADD_SENDER = "gs_add_sender";
	public static final String CLEAR = "gs_clear";
	public static final String CLEAR_SENDERS = "gs_clear_senders";
	public static final String CLOSE = "gs_close";
	public static final String REMOVE_EDGE = "gs_remove_edge";
	public static final String REMOVE_EDGE_ATTRIBUTE = "gs_remove_edge_attribute";
	public static final String REMOVE_GRAPH_ATTRIBUTE = "gs_remove_graph_attribute";
	public static final String REMOVE_NODE = "gs_remove_node";
	public static final String REMOVE_NODE_ATTRIBUTE = "gs_remove_node_attribute";
	public static final String STEP = "gs_step";
		//Receiver command
	public static final String ADD_RECEIVER = "gs_add_receiver";
	public static final String CLEAR_RECEIVERS = "gs_clear_receivers";
	public static final String GET_EDGE_ATTRIBUTE = "gs_get_edge_attribute";
	public static final String GET_NODE_ATTRIBUTE = "gs_get_node_attribute";
	public static final String GET_GRAPH_ATTRIBUTE = "gs_get_graph_attribute";
	public static final String WAIT_STEP = "gs_wait_step";
	public static final String FLUSH = "gs_flush";

	// Facets associated to a command
	public static final String ATTRIBUTE_FILTER = "gs_attributes_filtered";
	public static final String ATTRIBUTE_NAME = "gs_attribute_name";
	public static final String ATTRIBUTE_VALUE = "gs_attribute_value";
	public static final String EDGE_ID = "gs_edge_id";
	public static final String HOST = "gs_host";
	public static final String IS_DIRECTED = "gs_is_directed";
	public static final String NODE_ID = "gs_node_id";
	public static final String NODE_ID_FROM = "gs_node_id_from";
	public static final String NODE_ID_TO = "gs_node_id_to";
	public static final String PORT = "gs_port";
	public static final String RECEIVERID = "gs_receiver_id";
	public static final String RETURN = "gs_return";
	public static final String SENDERID = "gs_sender_id";
	public static final String STEP_NUMBER = "gs_step_number";
}
