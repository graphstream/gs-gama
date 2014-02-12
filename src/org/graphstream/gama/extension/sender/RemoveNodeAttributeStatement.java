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
 * Implements the {@code gs_remove_node_attribute} command.
 *
 * <pre>
 * gs_remove_node_attribute gs_sender_id:senderId gs_node_id:nodeId gs_attribute_name:name;
 * </pre>
 * @author Thibaut Démare
 *
 */
@symbol(name = IKeywordGSAdditional.REMOVE_NODE_ATTRIBUTE, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(value = { @facet(name = IKeywordGSAdditional.SENDERID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.NODE_ID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.ATTRIBUTE_NAME, type = IType.STRING, optional = false)})
public class RemoveNodeAttributeStatement extends AbstractStatement implements IStatement{
	
	final IExpression senderid;
	final IExpression nodeid;
	final IExpression attname;
	
	public RemoveNodeAttributeStatement(IDescription desc) {
		super(desc);
		senderid = getFacet(IKeywordGSAdditional.SENDERID);
		nodeid = getFacet(IKeywordGSAdditional.NODE_ID);
		attname = getFacet(IKeywordGSAdditional.ATTRIBUTE_NAME);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String s = (String)(senderid.value(scope));
		String eid = (String)(nodeid.value(scope));
		String an = (String)(attname.value(scope));
		GSSender sender = GSManager.getSender(s);
		sender.sendNodeAttributeRemoved(eid, an);
		return null;
	}

	
}