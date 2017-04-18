package cfr.trainer.games.poker.nodes;

import cfr.trainer.games.poker.actions.CallAction;
import cfr.trainer.games.poker.actions.FoldAction;
import cfr.trainer.games.poker.actions.PokerAction;
import cfr.trainer.node.NodeImpl;

public class TerminalInfoSet extends NodeImpl {

	private static PokerAction[] actions = { FoldAction.getInstance(), CallAction.getInstance() };

	TerminalInfoSet(String nodeId){
		super(actions);
		nodeIdentifier = nodeId;
	}
}