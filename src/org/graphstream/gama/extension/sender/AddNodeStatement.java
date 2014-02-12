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
 * Implements the {@code gs_add_node} command.
 *
 * <pre>
 * gs_add_node gs_sender_id:senderId gs_node_id:nodeId;
 * </pre>
 * @author Thibaut D�mare
 *
 */
@symbol(name = IKeywordGSAdditional.ADD_NODE, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(value = { @facet(name = IKeywordGSAdditional.SENDERID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.NODE_ID, type = IType.STRING, optional = false)})
public class AddNodeStatement extends AbstractStatement implements IStatement{
	
	final IExpression senderid;
	final IExpression nodeid;
	
	public AddNodeStatement(IDescription desc) {
		super(desc);
		senderid = getFacet(IKeywordGSAdditional.SENDERID);
		nodeid = getFacet(IKeywordGSAdditional.NODE_ID);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String s = (String)(senderid.value(scope));
		String nid = (String)(nodeid.value(scope));
		GSSender sender = GSManager.getSender(s);
		sender.sendNodeAdded(nid);
		return null;
	}

	
}