package cfr.poker.nodes;

import cfr.poker.actions.CallAction;
import cfr.poker.actions.FoldAction;
import cfr.poker.actions.PokerAction;
import cfr.trainer.NodeImpl;

public class TerminalInfoSet extends NodeImpl {

	private static PokerAction[] actions = { new FoldAction(), new CallAction() };

	TerminalInfoSet(){
		super(actions);
	}
}