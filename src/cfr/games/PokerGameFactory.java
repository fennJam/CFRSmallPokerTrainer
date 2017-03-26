package cfr.games;

import java.util.Collection;

import poker.GameType;

public class PokerGameFactory {

	public static PokerGame setUpGame(GameType gameType, Collection<Integer> players, int raisesPerBettingRound){
		if(gameType==GameType.SINGLE_CARD){
			return new SingleCardGame(players, raisesPerBettingRound);
		}else if(gameType==GameType.TWO_CARD){
			return new TwoCardGame(players,raisesPerBettingRound);
		}
		
		return null;
		
	}
	
	
	public static PokerGame setUpGame(GameType gameType, int players, int raisesPerBettingRound){
		if(gameType==GameType.SINGLE_CARD){
			return new SingleCardGame(players,raisesPerBettingRound);
		}else if(gameType==GameType.TWO_CARD){
			return new TwoCardGame(players,raisesPerBettingRound);
		}
		
		return null;
		
	}
	
	public static PokerGame copyGame(PokerGame game){
		if(game.getGameType()==GameType.SINGLE_CARD){
			PokerGame copiedGame = new SingleCardGame(0,0);
			return copiedGame.importGameProperties(game);
		}else if(game.getGameType()==GameType.TWO_CARD){
			PokerGame copiedGame = new TwoCardGame(0,0);
			return copiedGame.importGameProperties(game);
		}
		
		return null;
	}
}
