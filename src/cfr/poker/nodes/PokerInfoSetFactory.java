package cfr.poker.nodes;

import java.util.Collection;

import cfr.poker.BettingLimit;
import cfr.poker.games.PokerGame;
import cfr.trainer.NodeImpl;

public class PokerInfoSetFactory {

	
	public static NodeImpl  buildInformationSet(PokerGame pokerGame){
		if(pokerGame.raisesAllowed()){
			return new TerminalInfoSet();
		}else if(pokerGame.getBettingLimit() == BettingLimit.LIMIT){
			return new LimitPokerInfoSet();
		}
		
		return null;
		
	}
}
