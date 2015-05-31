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

import java.util.HashSet;
import java.util.Set;

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
 * Implements the {@code gs_add_receiver} command.
 *
 * <pre>
 * gs_add_receiver gs_receiver_id:receiverId gs_host:host gs_port:port;
 * (gs_add_receiver gs_receiver_id:receiverId gs_host:host gs_port:port gs_attributes_filtered:listAttributes;)
 * </pre>
 * @author Thibaut Démare
 *
 */
@symbol(name = IKeywordGSAdditional.ADD_RECEIVER, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(value = { @facet(name = IKeywordGSAdditional.RECEIVERID, type = IType.STRING, optional = false),
		@facet(name = IKeywordGSAdditional.HOST, type = IType.STRING, optional = false),
		@facet(name = IKeywordGSAdditional.PORT, type = IType.INT, optional = false),
		@facet(name = IKeywordGSAdditional.ATTRIBUTE_FILTER, type = IType.LIST, optional = true)})
public class AddReceiverStatement extends AbstractStatement implements IStatement{

	final IExpression receiverid;
	final IExpression host;
	final IExpression port;
	final IExpression filter;

	public AddReceiverStatement(IDescription desc) {
		super(desc);
		receiverid = getFacet(IKeywordGSAdditional.RECEIVERID);
		host = getFacet(IKeywordGSAdditional.HOST);
		port = getFacet(IKeywordGSAdditional.PORT);
		filter = getFacet(IKeywordGSAdditional.ATTRIBUTE_FILTER);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String r = (String)(receiverid.value(scope));
		String h = (String)(host.value(scope));
		int p = (Integer)(port.value(scope));
		Set filterSet = null;
		if(filter != null)
			filterSet = new HashSet((msi.gama.util.GamaList) filter.value(scope));
		GSManager.addReceiver(scope, r, h, p, filterSet);
		return null;
	}
}