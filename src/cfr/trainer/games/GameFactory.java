package cfr.trainer.games;

import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.decks.RoyalDeckShuffled;
import cfr.trainer.games.poker.games.*;

public class GameFactory {

	public static Game setUpGame(GameDescription gameDescription, int numberOfRaisesPerBettingRound) throws Exception {

		if (gameDescription == GameDescription.TWOCARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerTwoCardGame(BettingLimit.LIMIT, numberOfRaisesPerBettingRound);
		} else if (gameDescription == GameDescription.SINGLECARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerSingleCardGame(BettingLimit.LIMIT, numberOfRaisesPerBettingRound);

		} else if (gameDescription == GameDescription.KUHN_POKER) {
			return new KuhnPoker();
		} else if (gameDescription == GameDescription.RHODE_ISLAND_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, numberOfRaisesPerBettingRound);
		} else if (gameDescription == GameDescription.ROYAL_RHODE_ISLAND_HEADSUP_LIMIT_POKER) {
			PokerGame game = new TwoPlayerRoyalRhodeIslandGame(BettingLimit.LIMIT, numberOfRaisesPerBettingRound);
			game.setDeck(new RoyalDeckShuffled().unShuffleDeck());
			return game;
		} else if (gameDescription == GameDescription.ROYAL_RHODE_ISLAND_HEADSUP_NO_LIMIT_POKER) {
			return new TwoPlayerRoyalRhodeIslandGame(BettingLimit.NO_LIMIT, numberOfRaisesPerBettingRound);
		} else {
			throw new Exception("The setUpGame method in the class GameFactory does not cater for the GameDescription "
					+ gameDescription);
		}

	}

	public static Game copyGame(Game game) throws Exception {
		GameType gameType = game.getGameType();
		if (gameType == GameType.POKER) {

			PokerGame pokerGame = (PokerGame) game;
			if (pokerGame instanceof TwoPlayerSingleCardGame) {
				PokerGame copiedGame = new TwoPlayerSingleCardGame(pokerGame.getBettingLimit(),
						pokerGame.getRaisesAllowedPerBettingRound());
				return copiedGame.importGameProperties(pokerGame);
			} else if (pokerGame instanceof TwoPlayerTwoCardGame) {
				PokerGame copiedGame = new TwoPlayerTwoCardGame(pokerGame.getBettingLimit(),
						pokerGame.getRaisesAllowedPerBettingRound());
				return copiedGame.importGameProperties(pokerGame);
			} else if (pokerGame instanceof TwoPlayerRhodeIslandGame) {
				PokerGame copiedGame = new TwoPlayerRhodeIslandGame(pokerGame.getBettingLimit(),
						pokerGame.getRaisesAllowedPerBettingRound());
				return copiedGame.importGameProperties(pokerGame);
			} else if (pokerGame instanceof TwoPlayerRoyalRhodeIslandGame) {
				PokerGame copiedGame = new TwoPlayerRoyalRhodeIslandGame(pokerGame.getBettingLimit(),
						pokerGame.getRaisesAllowedPerBettingRound());
				return copiedGame.importGameProperties(pokerGame);
			}else if (pokerGame instanceof KuhnPoker) {
				PokerGame copiedGame = new KuhnPoker();
				return copiedGame.importGameProperties(pokerGame);
			}
		} else {
			throw new Exception(
					"The copyGame method in the class GameFactory does not cater for the GameType " + gameType);
		}

		return null;
	}
}
