package cfr.trainer;

import cfr.poker.BettingLimit;
import cfr.poker.PokerGameType;
import cfr.poker.games.*;

public class GameFactory {

	public static PokerGame setUpGame(GameType gameType) {

		if (gameType == GameType.TWOCARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		} else if (gameType == GameType.SINGLECARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 1);

		}else if (gameType == GameType.KUHN_POKER){
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
