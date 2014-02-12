package org.graphstream.gama.extension.sender;

import msi.gama.precompiler.ISymbolKind;
import msi.gama.precompiler.GamlAnnotations.inside;
import msi.gama.precompiler.GamlAnnotations.symbol;
import msi.gama.runtime.IScope;
import msi.gama.runtime.exceptions.GamaRuntimeException;
import msi.gaml.descriptions.IDescription;
import msi.gaml.statements.AbstractStatement;
import msi.gaml.statements.IStatement;

import org.graphstream.gama.extension.GSManager;
import org.graphstream.gama.extension.IKeywordGSAdditional;

/**
 * Implements the {@code gs_clear_senders} command.
 *
 * <pre>
 * gs_clear_senders;
 * </pre>
 * @author Thibaut D�mare
 *
 */
@symbol(name = IKeywordGSAdditional.CLEAR_SENDERS, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
public class ClearSendersStatement extends AbstractStatement implements IStatement{
	
	public ClearSendersStatement(IDescription desc) {
		super(desc);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		GSManager.clearSenders();
		return null;
	}

	
}
