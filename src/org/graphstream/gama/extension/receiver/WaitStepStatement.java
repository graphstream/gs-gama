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
 * Implements the {@code gs_wait_step} command.
 *
 * <pre>
 * gs_wait_step gs_receiver_id:receiverId;
 * (gs_wait_step gs_receiver_id:receiverId gs_return:returnVar;)
 * </pre>
 * @author Thibaut D�mare
 *
 */
@symbol(name = IKeywordGSAdditional.WAIT_STEP, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
@facets(value = { @facet(name = IKeywordGSAdditional.RECEIVERID, type = IType.STRING, optional = false),
		 @facet(name = IKeywordGSAdditional.RETURN, type = IType.FLOAT, optional = false)})
public class WaitStepStatement extends AbstractStatement implements IStatement{
	
	final IExpression receiverid;
	final String returnVal;
	
	public WaitStepStatement(IDescription desc) {
		super(desc);
		receiverid = getFacet(IKeywordGSAdditional.RECEIVERID);
		returnVal = getLiteral(IKeywordGSAdditional.RETURN);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		String r = (String)(receiverid.value(scope));
		GSReceiver receiver = GSManager.getReceiver(r);
		Float step = new Float(receiver.waitStep());
		// and we return the attribute list
		scope.setVarValue(returnVal, step);
		return step;
	}
}