package cfr.poker.nodes;

import cfr.poker.BettingLimit;
import cfr.poker.games.PokerGame;
import cfr.trainer.NodeImpl;

public class PokerInfoSetFactory {

	
	public static NodeImpl  buildInformationSet(PokerGame pokerGame){
		String nodeId = pokerGame.getNodeId();
		if(!pokerGame.raisesAllowed()){
			return new TerminalInfoSet(nodeId);
		}else if(pokerGame.getBettingLimit() == BettingLimit.LIMIT){
			return new LimitPokerInfoSet(nodeId);
		}
		
		return null;
		
	}
}
