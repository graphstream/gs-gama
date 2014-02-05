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

@symbol(name = IKeywordGSAdditional.ADD_SENDER, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SINGLE_STATEMENT })
@facets(value = { @facet(name = IKeywordGSAdditional.SENDERID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.HOST, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.PORT, type = IType.INT, optional = false)})
public class AddSenderStatement extends AbstractStatement implements IStatement{
	
	final IExpression senderid;
	final IExpression host;
	final IExpression port;
	
	public AddSenderStatement(IDescription desc) {
		super(desc);
		senderid = getFacet(IKeywordGSAdditional.SENDERID);
		host = getFacet(IKeywordGSAdditional.HOST);
		port = getFacet(IKeywordGSAdditional.PORT);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String s = (String)(senderid.value(scope));
		String h = (String)(host.value(scope));
		int p = (Integer)(port.value(scope));
		GSManager.addSender(s, h, p);
		return null;
	}

	
}