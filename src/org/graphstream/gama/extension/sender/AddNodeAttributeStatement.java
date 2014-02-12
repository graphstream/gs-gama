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
 * @author Thibaut Démare
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