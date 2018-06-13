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
package org.graphstream.gama.extension.sender;

import msi.gama.precompiler.ISymbolKind;
import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.statements.IStatement;
import msi.gaml.types.IType;

import org.graphstream.gama.extension.GSManager;
import org.graphstream.gama.extension.IKeywordGSAdditional;

/**
 * Implements the {@code gs_add_node_attribute} command.
 *
 * <pre>
 * gs_add_node_attribute gs_sender_id:senderId gs_node_id:nodeId gs_attribute_name:name gs_attribute_value:value;
 * </pre>
 * @author Thibaut D�mare
 *
 */
@symbol(name = IKeywordGSAdditional.ADD_NODE_ATTRIBUTE, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(value = { @facet(name = IKeywordGSAdditional.SENDERID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.NODE_ID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.ATTRIBUTE_NAME, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.ATTRIBUTE_VALUE, type = IType.NONE, optional = false)})
public class AddNodeAttributeStatement extends AbstractStatement implements IStatement{
	
	final IExpression senderid;
	final IExpression nodeid;
	final IExpression attname;
	final IExpression attval;
	
	public AddNodeAttributeStatement(IDescription desc) {
		super(desc);
		senderid = getFacet(IKeywordGSAdditional.SENDERID);
		nodeid = getFacet(IKeywordGSAdditional.NODE_ID);
		attname = getFacet(IKeywordGSAdditional.ATTRIBUTE_NAME);
		attval = getFacet(IKeywordGSAdditional.ATTRIBUTE_VALUE);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String s = (String)(senderid.value(scope));
		String eid = (String)(nodeid.value(scope));
		String an = (String)(attname.value(scope));
		Object av = attval.value(scope);
		GSSender sender = GSManager.getSender(s);
		// If it is a GamaList, it must be cast to an array
		if(av instanceof msi.gama.util.GamaList){
			Object[] av_ar = ((msi.gama.util.GamaList) av).toArray();
			sender.sendNodeAttributeAdded(eid, an, av_ar);
		}
		else {
			sender.sendNodeAttributeAdded(eid, an, av);
		}
		return null;
	}

	
}