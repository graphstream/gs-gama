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
 * Implements the {@code gs_add_edge} command.
 *
 * <pre>
 * gs_add_edge gs_sender_id:senderId gs_edge_id:edgeId gs_node_id_from:nodeId gs_node_id_to:nodeId gs_is_directed:dir;
 * </pre>
 * @author Thibaut Démare
 *
 */
@symbol(name = IKeywordGSAdditional.ADD_EDGE, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(value = { @facet(name = IKeywordGSAdditional.SENDERID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.EDGE_ID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.NODE_ID_FROM, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.NODE_ID_TO, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.IS_DIRECTED, type = IType.BOOL, optional = false)})
public class AddEdgeStatement extends AbstractStatement implements IStatement{
	
	final IExpression senderid;
	final IExpression edgeid;
	final IExpression nodefrom;
	final IExpression nodeto;
	final IExpression directed;
	
	public AddEdgeStatement(IDescription desc) {
		super(desc);
		senderid = getFacet(IKeywordGSAdditional.SENDERID);
		edgeid = getFacet(IKeywordGSAdditional.EDGE_ID);
		nodefrom = getFacet(IKeywordGSAdditional.NODE_ID_FROM);
		nodeto = getFacet(IKeywordGSAdditional.NODE_ID_TO);
		directed = getFacet(IKeywordGSAdditional.IS_DIRECTED);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String s = (String)(senderid.value(scope));
		String eid = (String)(edgeid.value(scope));
		String nfrom = (String)(nodefrom.value(scope));
		String nto = (String)(nodeto.value(scope));
		boolean d = (Boolean)(directed.value(scope));
		GSSender sender = GSManager.getSender(s);
		sender.sendEdgeAdded(eid, nfrom, nto, d);
		return null;
	}

	
}