package cfr.trainer.games;

import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.decks.RoyalDeckShuffled;
import cfr.trainer.games.poker.games.*;
import cfr.trainer.games.poker.games.lite.KuhnPokerLite;
import cfr.trainer.games.poker.games.lite.PokerGameLite;
import cfr.trainer.games.poker.games.lite.RoyalRhodeIsland10ChipNoLimitLite;

public class GameFactory {

	public static Game setUpGame(GameDescription gameDescription, int numberOfRaisesPerBettingRound) throws Exception {

		if (gameDescription == GameDescription.ROYAL_RHODE_ISLAND_10_CHIP_LITE) {
			return new RoyalRhodeIsland10ChipNoLimitLite(numberOfRaisesPerBettingRound);
		} else if (gameDescription == GameDescription.TWOCARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerTwoCardGame(BettingLimit.LIMIT, numberOfRaisesPerBettingRound);
		} else if (gameDescription == GameDescription.SINGLECARD_HEADSUP_LIMIT_POKER) {
			return new TwoPlayerSingleCardGame(BettingLimit.LIMIT, numberOfRaisesPerBettingRound);
		} else if (gameDescription == GameDescription.KUHN_POKER_LITE) {
			return new KuhnPokerLite();
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
		if (game instanceof PokerGame) {

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
			} else if (pokerGame instanceof KuhnPoker) {
				PokerGame copiedGame = new KuhnPoker();
				return copiedGame.importGameProperties(pokerGame);
			}
		} else if (game instanceof PokerGameLite) {
			PokerGameLite pokerGameLite = (PokerGameLite) game;
			if (game instanceof KuhnPokerLite) {
				PokerGameLite copiedGame = new KuhnPokerLite();
				return copiedGame.importGameProperties(pokerGameLite);
			}else if (game instanceof RoyalRhodeIsland10ChipNoLimitLite) {
				PokerGameLite copiedGame = new RoyalRhodeIsland10ChipNoLimitLite(((PokerGameLite) game).getRaisesAllowedPerBettingRound());
				return copiedGame.importGameProperties(pokerGameLite);
			}
		} else {
			throw new Exception("The copyGame method in the class GameFactory does not cater for the GameType "
					+ game);
		}

		return null;
	}
}
