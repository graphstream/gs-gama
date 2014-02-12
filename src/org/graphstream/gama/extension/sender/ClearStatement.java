package org.graphstream.gama.extension.sender;

import org.graphstream.gama.extension.GSManager;
import org.graphstream.gama.extension.IKeywordGSAdditional;

import msi.gama.precompiler.GamlAnnotations.facet;
import msi.gama.precompiler.GamlAnnotations.facets;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.precompiler.ISymbolKind;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.descriptions.IDescription;
import msi.gaml.expressions.IExpression;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.statements.IStatement;
import msi.gaml.types.IType;

/**
 * Implements the {@code gs_clear} command.
 *
 * <pre>
 * gs_clear gs_sender_id:senderId;
 * </pre>
 * @author Thibaut Démare
 *
 */
@symbol(name = IKeywordGSAdditional.CLEAR, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(value = { @facet(name = IKeywordGSAdditional.SENDERID, type = IType.STRING, optional = false) })
public class ClearStatement extends AbstractStatement implements IStatement{
	
	final IExpression senderid;
	
	public ClearStatement(IDescription desc) {
		super(desc);
		senderid = getFacet(IKeywordGSAdditional.SENDERID);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String senderId = (String)(senderid.value(scope));
		GSSender sender = GSManager.getSender(senderId);
		sender.sendGraphCleared();
		return null;
	}

	
}
