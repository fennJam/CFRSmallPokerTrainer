package cfr.poker.games;

import java.util.Collection;

import poker.BettingLimit;
import poker.GameType;

public class PokerGameFactory {

	public static PokerGame setUpGame(GameType gameType, BettingLimit bettingLimit,Collection<Integer> players, int raisesPerBettingRound){
		if(gameType==GameType.SINGLE_CARD){
			return new SingleCardGame(players,bettingLimit, raisesPerBettingRound);
		}else if(gameType==GameType.TWO_CARD){
			return new TwoCardGame(players,bettingLimit,raisesPerBettingRound);
		}
		
		return null;
		
	}
	
	
	public static PokerGame setUpGame(GameType gameType, BettingLimit bettingLimit, int players, int raisesPerBettingRound){
		if(gameType==GameType.SINGLE_CARD){
			return new SingleCardGame(players,bettingLimit,raisesPerBettingRound);
		}else if(gameType==GameType.TWO_CARD){
			return new TwoCardGame(players,bettingLimit,raisesPerBettingRound);
		}
		
		return null;
		
	}
	
	public static PokerGame copyGame(PokerGame game){
		if(game.getGameType()==GameType.SINGLE_CARD){
			PokerGame copiedGame = new SingleCardGame(0,BettingLimit.LIMIT,0);
			return copiedGame.importGameProperties(game);
		}else if(game.getGameType()==GameType.TWO_CARD){
			PokerGame copiedGame = new TwoCardGame(0,BettingLimit.LIMIT,0);
			return copiedGame.importGameProperties(game);
		}
		
		return null;
	}
}
