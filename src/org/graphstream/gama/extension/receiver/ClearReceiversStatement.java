package org.graphstream.gama.extension.receiver;

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

@symbol(name = IKeywordGSAdditional.CLEAR_RECEIVERS, kind = ISymbolKind.SINGLE_STATEMENT, with_sequence = false)
@inside(kinds = { ISymbolKind.BEHAVIOR, ISymbolKind.SEQUENCE_STATEMENT, ISymbolKind.LAYER })
public class ClearReceiversStatement extends AbstractStatement implements IStatement{
	
	public ClearReceiversStatement(IDescription desc) {
		super(desc);
	}

	@Override
	protected Object privateExecuteIn(IScope scope) throws GamaRuntimeException {
		GSManager.clearReceivers();
		return null;
	}

	
}
