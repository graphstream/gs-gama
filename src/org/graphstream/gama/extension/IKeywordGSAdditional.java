package org.graphstream.gama.extension;

import msi.gama.common.interfaces.IKeyword;

public class IKeywordGSAdditional implements IKeyword {
	// New commands
	
	public static final String ADD = "gs_add";
	public static final String ADD_ATTRIBUTE = "gs_add_attribute";
	public static final String ADD_SENDER = "gs_add_sender";
	public static final String CLEAR = "gs_clear";
	public static final String CLEAR_SENDERS = "gs_clear_senders";
	public static final String REMOVE = "gs_remove";
	public static final String REMOVE_ATTRIBUTE = "gs_remove_attribute";
	public static final String STEP = "gs_step";
	
	// Facets associated to a command
	
	public static final String SENDERID = "gs_sender_id";
}
