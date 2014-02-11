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


@symbol(name = IKeywordGSAdditional.ADD_GRAPH_ATTRIBUTE, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(value = { @facet(name = IKeywordGSAdditional.SENDERID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.ATTRIBUTE_NAME, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.ATTRIBUTE_VALUE, type = IType.NONE, optional = false)})
public class AddGraphAttributeStatement extends AbstractStatement implements IStatement{
	
	final IExpression senderid;
	final IExpression attname;
	final IExpression attval;
	
	public AddGraphAttributeStatement(IDescription desc) {
		super(desc);
		senderid = getFacet(IKeywordGSAdditional.SENDERID);
		attname = getFacet(IKeywordGSAdditional.ATTRIBUTE_NAME);
		attval = getFacet(IKeywordGSAdditional.ATTRIBUTE_VALUE);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String s = (String)(senderid.value(scope));
		String an = (String)(attname.value(scope));
		Object av = attval.value(scope);
		GSSender sender = GSManager.getSender(s);
		// If it is a GamaList, it must be cast to an array
		if(av instanceof msi.gama.util.GamaList){
			Object[] av_ar = ((msi.gama.util.GamaList) av).toArray();
			sender.sendGraphAttributeAdded(an, av_ar);
		}
		else {
			sender.sendGraphAttributeAdded(an, av);
		}
		return null;
	}

	
}