package cfr.poker.nodes;

import cfr.poker.actions.CallAction;
import cfr.poker.actions.FoldAction;
import cfr.poker.actions.PokerAction;
import cfr.trainer.NodeImpl;

public class TerminalInfoSet extends NodeImpl {

	private static PokerAction[] actions = {CallAction.getInstance(), FoldAction.getInstance() };

	TerminalInfoSet(){
		super(actions);
	}
}