package org.graphstream.gama.extension.receiver;

import msi.gama.common.interfaces.IKeyword;
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
 * Implements the {@code gs_get_node_attribute} command.
 *
 * <pre>
 * gs_get_node_attribute gs_receiver_id:receiverId gs_node_id:nodeId gs_attribute_name:name returns:list;
 * </pre>
 * @author Thibaut Démare
 *
 */
@symbol(name = IKeywordGSAdditional.GET_NODE_ATTRIBUTE, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(value = { @facet(name = IKeywordGSAdditional.RECEIVERID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.NODE_ID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.ATTRIBUTE_NAME, type = IType.STRING, optional = false),
		 @facet(name = IKeyword.RETURNS, type = IType.LIST, optional = false)})
public class GetNodeAttributeStatement extends AbstractStatement implements IStatement{
	
	final IExpression receiverid;
	final IExpression nodeid;
	final IExpression attname;
	final String returns;
	
	public GetNodeAttributeStatement(IDescription desc) {
		super(desc);
		receiverid = getFacet(IKeywordGSAdditional.RECEIVERID);
		nodeid = getFacet(IKeywordGSAdditional.NODE_ID);
		attname = getFacet(IKeywordGSAdditional.ATTRIBUTE_NAME);
		returns = getLiteral(IKeyword.RETURNS);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String r = (String)(receiverid.value(scope));
		String eid = (String)(nodeid.value(scope));
		String an = (String)(attname.value(scope));
		GSReceiver receiver = GSManager.getReceiver(r);
		Object av = receiver.receiveNodeAttribute(eid, an);
		// and we return the attribute list
		scope.setVarValue(returns, av);
		return av;
	}
}