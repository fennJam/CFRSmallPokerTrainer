package cfr.trainer.games.test;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import cfr.trainer.games.Game;
import cfr.trainer.games.GameDescription;
import cfr.trainer.games.GameFactory;
import cfr.trainer.games.GameType;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.actions.CallAction;
import cfr.trainer.games.poker.actions.DealAction;
import cfr.trainer.games.poker.games.BaseTwoPlayerPokerGame;
import cfr.trainer.games.poker.games.KuhnPoker;
import cfr.trainer.games.poker.games.TwoPlayerRhodeIslandGame;
import cfr.trainer.games.poker.games.TwoPlayerSingleCardGame;
import cfr.trainer.games.poker.games.TwoPlayerTwoCardGame;

public class GameFactoryTest {

	@Test
	public final void KUHN_POKERTest() throws Exception {
		Game kuhnGame = GameFactory.setUpGame(GameDescription.KUHN_POKER,3);
		assertEquals(GameType.POKER, kuhnGame.getGameType());
		assertTrue(kuhnGame instanceof KuhnPoker);
		BaseTwoPlayerPokerGame twoPlayerPokerGame = (BaseTwoPlayerPokerGame) kuhnGame;
		assertEquals(0, twoPlayerPokerGame.getActions().size());
		kuhnGame.startGame();
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));

		Game copiedKuhnGame = new KuhnPoker();

		try {
			copiedKuhnGame = GameFactory.copyGame(kuhnGame);
		} catch (Exception e) {
			fail("copy action threw the error : " + e.toString());
		}

		assertEquals(GameType.POKER, copiedKuhnGame.getGameType());
		// kuhn game is treated the same as any other two player single card
		// game once it has started
		assertTrue(copiedKuhnGame instanceof TwoPlayerSingleCardGame);
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));

	}

	@Test
	public final void SINGLECARD_HEADSUP_LIMIT_POKERTest() throws Exception {

		Game game = GameFactory.setUpGame(GameDescription.SINGLECARD_HEADSUP_LIMIT_POKER,3);
		assertEquals(GameType.POKER, game.getGameType());
		assertTrue(game instanceof TwoPlayerSingleCardGame);
		BaseTwoPlayerPokerGame twoPlayerPokerGame = (BaseTwoPlayerPokerGame) game;
		assertEquals(0, twoPlayerPokerGame.getActions().size());
		assertEquals(BettingLimit.LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
		game.startGame();
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));

		Game copiedGame = new TwoPlayerSingleCardGame(BettingLimit.NO_LIMIT, 1);

		try {
			copiedGame = GameFactory.copyGame(game);
		} catch (Exception e) {
			fail("copy action threw the error : " + e.toString());
		}

		assertEquals(GameType.POKER, copiedGame.getGameType());
		assertTrue(copiedGame instanceof TwoPlayerSingleCardGame);
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));
		assertEquals(BettingLimit.LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
	}

	@Ignore
	@Test
	public final void SINGLECARD_HEADSUP_POT_LIMIT_POKERTest() throws Exception {

		Game game = GameFactory.setUpGame(GameDescription.SINGLECARD_HEADSUP_POT_LIMIT_POKER,3);
		assertEquals(GameType.POKER, game.getGameType());
		assertTrue(game instanceof TwoPlayerSingleCardGame);
		BaseTwoPlayerPokerGame twoPlayerPokerGame = (BaseTwoPlayerPokerGame) game;
		assertEquals(0, twoPlayerPokerGame.getActions().size());
		assertEquals(BettingLimit.POT_LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
		game.startGame();
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));

		Game copiedGame = new TwoPlayerSingleCardGame(BettingLimit.NO_LIMIT, 1);

		try {
			copiedGame = GameFactory.copyGame(game);
		} catch (Exception e) {
			fail("copy action threw the error : " + e.toString());
		}

		assertEquals(GameType.POKER, copiedGame.getGameType());
		assertTrue(copiedGame instanceof TwoPlayerSingleCardGame);
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));
		assertEquals(BettingLimit.POT_LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
	}

	@Test
	public final void TWOCARD_HEADSUP_LIMIT_POKERTest() throws Exception {
		Game game = GameFactory.setUpGame(GameDescription.TWOCARD_HEADSUP_LIMIT_POKER,3);
		assertEquals(GameType.POKER, game.getGameType());
		assertTrue(game instanceof TwoPlayerTwoCardGame);
		BaseTwoPlayerPokerGame twoPlayerPokerGame = (BaseTwoPlayerPokerGame) game;
		assertEquals(0, twoPlayerPokerGame.getActions().size());
		assertEquals(BettingLimit.LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
		game.startGame();
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));

		Game copiedGame = new TwoPlayerSingleCardGame(BettingLimit.NO_LIMIT, 1);

		try {
			copiedGame = GameFactory.copyGame(game);
		} catch (Exception e) {
			fail("copy action threw the error : " + e.toString());
		}

		assertEquals(GameType.POKER, copiedGame.getGameType());
		assertTrue(copiedGame instanceof TwoPlayerTwoCardGame);
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));
		assertEquals(BettingLimit.LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
	}

	@Ignore
	@Test
	public final void TWOCARD_HEADSUP_POT_LIMIT_POKERTest() throws Exception {
		Game game = GameFactory.setUpGame(GameDescription.TWOCARD_HEADSUP_POT_LIMIT_POKER,3);
		assertEquals(GameType.POKER, game.getGameType());
		assertTrue(game instanceof TwoPlayerTwoCardGame);
		BaseTwoPlayerPokerGame twoPlayerPokerGame = (BaseTwoPlayerPokerGame) game;
		assertEquals(0, twoPlayerPokerGame.getActions().size());
		assertEquals(BettingLimit.POT_LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
		game.startGame();
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));

		Game copiedGame = new TwoPlayerSingleCardGame(BettingLimit.NO_LIMIT, 1);

		try {
			copiedGame = GameFactory.copyGame(game);
		} catch (Exception e) {
			fail("copy action threw the error : " + e.toString());
		}

		assertEquals(GameType.POKER, copiedGame.getGameType());
		assertTrue(copiedGame instanceof TwoPlayerTwoCardGame);
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));
		assertEquals(BettingLimit.POT_LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
	}

	@Test
	public final void RHODE_ISLAND_HEADSUP_LIMIT_POKERTest() throws Exception {

		Game game = GameFactory.setUpGame(GameDescription.RHODE_ISLAND_HEADSUP_LIMIT_POKER,3);
		assertEquals(GameType.POKER, game.getGameType());
		assertTrue(game instanceof TwoPlayerRhodeIslandGame);
		BaseTwoPlayerPokerGame twoPlayerPokerGame = (BaseTwoPlayerPokerGame) game;
		assertEquals(0, twoPlayerPokerGame.getActions().size());
		assertEquals(BettingLimit.LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
		game.startGame();
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));

		Game copiedGame = new TwoPlayerSingleCardGame(BettingLimit.NO_LIMIT, 1);

		try {
			copiedGame = GameFactory.copyGame(game);
		} catch (Exception e) {
			fail("copy action threw the error : " + e.toString());
		}

		assertEquals(GameType.POKER, copiedGame.getGameType());
		assertTrue(copiedGame instanceof TwoPlayerRhodeIslandGame);
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));
		assertEquals(BettingLimit.LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
	}

	@Ignore
	@Test
	public final void RHODE_ISLAND_HEADSUP_POT_LIMIT_POKERTest() throws Exception {
		Game game = GameFactory.setUpGame(GameDescription.RHODE_ISLAND_HEADSUP_POT_LIMIT_POKER,3);
		assertEquals(GameType.POKER, game.getGameType());
		assertTrue(game instanceof TwoPlayerRhodeIslandGame);
		BaseTwoPlayerPokerGame twoPlayerPokerGame = (BaseTwoPlayerPokerGame) game;
		assertEquals(0, twoPlayerPokerGame.getActions().size());
		assertEquals(BettingLimit.POT_LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
		game.startGame();
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));

		Game copiedGame = new TwoPlayerSingleCardGame(BettingLimit.NO_LIMIT, 1);

		try {
			copiedGame = GameFactory.copyGame(game);
		} catch (Exception e) {
			fail("copy action threw the error : " + e.toString());
		}

		assertEquals(GameType.POKER, copiedGame.getGameType());
		assertTrue(copiedGame instanceof TwoPlayerRhodeIslandGame);
		assertEquals(1, twoPlayerPokerGame.getActions().size());
		assertEquals(DealAction.getInstance(), twoPlayerPokerGame.getActions().get(0));
		assertEquals(BettingLimit.POT_LIMIT, twoPlayerPokerGame.getBettingLimit());
		assertEquals(3, twoPlayerPokerGame.getRaisesAllowedPerBettingRound());
	}

}
