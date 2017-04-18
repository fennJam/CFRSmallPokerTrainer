package cfr.trainer.games.poker.nodes;

import cfr.trainer.games.poker.actions.*;
import cfr.trainer.node.NodeImpl;

public class LimitPokerInfoSet extends NodeImpl {
	static PokerAction[] singleRaiseoption = { FoldAction.getInstance(),CallAction.getInstance(),  new RaiseAction(1) };

	LimitPokerInfoSet(String nodeId) {
		super(singleRaiseoption);
		nodeIdentifier = nodeId;
	}

}
