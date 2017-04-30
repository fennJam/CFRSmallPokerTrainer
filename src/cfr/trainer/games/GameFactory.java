package cfr.trainer.games;

import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.PokerGameType;
import cfr.trainer.games.poker.games.*;

public class GameFactory {

	public static Game setUpGame(GameDescription gameType) {

		if (gameType == GameDescription.TWOCARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		} else if (gameType == GameDescription.SINGLECARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);

		} else if (gameType == GameDescription.KUHN_POKER) {
			return new KuhnPoker();
		}

		return null;

	}

	public static Game copyGame(Game game) {
		if (game.getGameType() == GameType.POKER) {

			PokerGame pokerGame = (PokerGame) game;
			if (pokerGame.getPokerGameType() == PokerGameType.SINGLE_CARD) {
				PokerGame copiedGame = new TwoPlayerSingleCardGame(pokerGame.getBettingLimit(),
						pokerGame.getRaisesAllowedPerBettingRound());
				return copiedGame.importGameProperties(pokerGame);
			} else if (pokerGame.getPokerGameType() == PokerGameType.TWO_CARD) {
				PokerGame copiedGame = new TwoPlayerTwoCardGame(pokerGame.getBettingLimit(),
						pokerGame.getRaisesAllowedPerBettingRound());
				return copiedGame.importGameProperties(pokerGame);
			} else if (pokerGame.getPokerGameType() == PokerGameType.RHODE_ISLAND) {
				PokerGame copiedGame = new TwoPlayerRhodeIslandGame(pokerGame.getBettingLimit(),
						pokerGame.getRaisesAllowedPerBettingRound());
				return copiedGame.importGameProperties(pokerGame);
			}
		}

		return null;
	}
}
