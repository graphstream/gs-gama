package org.graphstream.gama.extension;

import msi.gama.common.interfaces.IKeyword;

public class IKeywordGSAdditional implements IKeyword {
	// New commands
	
	public static final String ADD_EDGE = "gs_add_edge";
	public static final String ADD_EDGE_ATTRIBUTE = "gs_add_edge_attribute";
	public static final String ADD_NODE = "gs_add_node";
	public static final String ADD_NODE_ATTRIBUTE = "gs_add_node_attribute";
	public static final String ADD_SENDER = "gs_add_sender";
	public static final String CLEAR = "gs_clear";
	public static final String CLEAR_SENDERS = "gs_clear_senders";
	public static final String CLOSE = "gs_close";
	public static final String REMOVE_EDGE = "gs_remove_edge";
	public static final String REMOVE_EDGE_ATTRIBUTE = "gs_remove_edge_attribute";
	public static final String REMOVE_NODE = "gs_remove_node";
	public static final String REMOVE_NODE_ATTRIBUTE = "gs_remove_node_attribute";
	public static final String STEP = "gs_step";
	
	// Facets associated to a command
	
	public static final String ATTRIBUTE_NAME = "gs_attribute_name";
	public static final String ATTRIBUTE_VALUE = "gs_attribute_value";
	public static final String EDGE_ID = "gs_edge_id";
	public static final String HOST = "gs_host";
	public static final String IS_DIRECTED = "gs_is_directed";
	public static final String NODE_ID = "gs_node_id";
	public static final String NODE_ID_FROM = "gs_node_id_from";
	public static final String NODE_ID_TO = "gs_node_id_to";
	public static final String PORT = "gs_port";
	public static final String SENDERID = "gs_sender_id";
	public static final String STEP_NUMBER = "gs_step_number";
	
}
