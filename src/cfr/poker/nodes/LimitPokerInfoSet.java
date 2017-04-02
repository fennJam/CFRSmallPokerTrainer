package cfr.poker.nodes;

import cfr.poker.actions.*;
import cfr.trainer.NodeImpl;

public class LimitPokerInfoSet extends NodeImpl {
	static PokerAction[] singleRaiseoption = { new CallAction(), new FoldAction(), new RaiseAction(1) };

	LimitPokerInfoSet() {
		super(singleRaiseoption);

	}

}
