package cfr.trainer.games;

import cfr.poker.BettingLimit;
import cfr.poker.PokerGameType;
import cfr.poker.games.*;

public class GameFactory {

	public static PokerGame setUpGame(GameDescription gameType) {

		if (gameType == GameDescription.TWOCARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		} else if (gameType == GameDescription.SINGLECARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 1);

		}else if (gameType == GameDescription.KUHN_POKER){
			return new KuhnPoker();
		}

		return null;

	}

	public static PokerGame copyGame(PokerGame game) {
		 if(game.getGameType()==PokerGameType.SINGLE_CARD){
		 PokerGame copiedGame = new
		 TwoPlayerSingleCardGame(BettingLimit.LIMIT,3);
		 return copiedGame.importGameProperties(game);
		 }else if(game.getGameType()==PokerGameType.TWO_CARD){
		 PokerGame copiedGame = new
		 TwoPlayerTwoCardGame(BettingLimit.LIMIT,3);
		 return copiedGame.importGameProperties(game);
		 }
		
		return null;
	}
}