package cfr.trainer.games.poker.nodes;

import java.util.ArrayList;
import java.util.List;

import cfr.trainer.games.poker.Pot;
import cfr.trainer.games.poker.actions.CallAction;
import cfr.trainer.games.poker.actions.FoldAction;
import cfr.trainer.games.poker.actions.PokerAction;
import cfr.trainer.games.poker.actions.RaiseAction;
import cfr.trainer.node.NodeImpl;

public class PotLimitPokerInfoSet extends NodeImpl {

	static final int BIG_BLIND = 2;

	PotLimitPokerInfoSet(String nodeId, Pot pot) {
		super(constructActionArray(pot));
		nodeIdentifier = nodeId;
	}

	private static PokerAction[] constructActionArray(Pot pot){
		
		List <PokerAction>  infoSetActionsList = new ArrayList<PokerAction>();
		infoSetActionsList.add(FoldAction.getInstance());
		infoSetActionsList.add(CallAction.getInstance());
		
		
		int lastbet = pot.getLastBetValue();
		int minRaise = Math.max(lastbet,BIG_BLIND);
		int maxRaise = (2*lastbet)+pot.getTotalPotSize();
		
		for(int bet=minRaise;bet<maxRaise+1;bet++){
			infoSetActionsList.add(new RaiseAction(bet));
		}
		
		PokerAction[] infoSetActionsArray = infoSetActionsList.toArray(new PokerAction[infoSetActionsList.size()]);
		return infoSetActionsArray;
		
		
	}

}
