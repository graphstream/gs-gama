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

@symbol(name = IKeywordGSAdditional.ADD_RECEIVER, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SINGLE_STATEMENT })
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
		Set filterSet = new HashSet((msi.gama.util.GamaList) filter.value(scope));
		if (filterSet.size() == 0)
			filterSet = null;
		GSManager.addReceiver(r, h, p, filterSet);
		return null;
	}
}