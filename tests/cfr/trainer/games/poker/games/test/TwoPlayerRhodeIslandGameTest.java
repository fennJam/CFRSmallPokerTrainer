package cfr.trainer.games.poker.games.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cfr.trainer.games.Game;
import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.actions.CallAction;
import cfr.trainer.games.poker.actions.DealAction;
import cfr.trainer.games.poker.actions.FoldAction;
import cfr.trainer.games.poker.actions.RaiseAction;
import cfr.trainer.games.poker.decks.RoyalDeckShuffled;
import cfr.trainer.games.poker.games.PokerGame;
import cfr.trainer.games.poker.games.TwoPlayerRhodeIslandGame;
import cfr.trainer.games.poker.games.TwoPlayerTwoCardGame;

public class TwoPlayerRhodeIslandGameTest {

	@Test
	public final void testConstructor() {
		PokerGame TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);

		assertEquals(0, TwoPlayerRhodeIslandGame.getActionsTaken().size());
		assertEquals(BettingLimit.LIMIT, TwoPlayerRhodeIslandGame.getBettingLimit());
		assertEquals(BetRound.PRETURN, TwoPlayerRhodeIslandGame.getBettingRound());
		assertNull(TwoPlayerRhodeIslandGame.getBoard());
		assertEquals(PokerGameType.RHODE_ISLAND, TwoPlayerRhodeIslandGame.getPokerGameType());
		assertNull(TwoPlayerRhodeIslandGame.getHands().get(0));
		assertNull(TwoPlayerRhodeIslandGame.getHands().get(1));
		assertEquals("", TwoPlayerRhodeIslandGame.getNodeIdWithActionMemory());
		assertEquals(2, TwoPlayerRhodeIslandGame.getPlayers().length);
		assertEquals(Integer.valueOf(0), TwoPlayerRhodeIslandGame.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), TwoPlayerRhodeIslandGame.getPlayers()[1]);
		assertTrue(TwoPlayerRhodeIslandGame.getPot() instanceof Pot);
		assertEquals(0, TwoPlayerRhodeIslandGame.getRaiseCount());
		assertEquals(3, TwoPlayerRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());

		assertFalse(TwoPlayerRhodeIslandGame.isAtTerminalNode());

	}

	@Test
	public final void startGameTest() throws Exception {
		PokerGame TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		TwoPlayerRhodeIslandGame.setDeck(new RoyalDeckShuffled().unShuffleDeck());
		TwoPlayerRhodeIslandGame.startGame();

		assertEquals(1, TwoPlayerRhodeIslandGame.getActionsTaken().size());
		assertTrue(TwoPlayerRhodeIslandGame.getActionsTaken().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, TwoPlayerRhodeIslandGame.getBettingLimit());

		assertEquals(BetRound.PRETURN, TwoPlayerRhodeIslandGame.getBettingRound());
		assertEquals("No cards turned", TwoPlayerRhodeIslandGame.getBoard().toString());
		assertEquals(PokerGameType.RHODE_ISLAND, TwoPlayerRhodeIslandGame.getPokerGameType());

		assertEquals(2, TwoPlayerRhodeIslandGame.getHands().size());
		assertTrue(TwoPlayerRhodeIslandGame.getHands().get(0) instanceof Hand);
		assertTrue(TwoPlayerRhodeIslandGame.getHands().get(1) instanceof Hand);

		 assertEquals("[S,S,A]D",TwoPlayerRhodeIslandGame.getNodeIdWithActionMemory());
		 
		assertEquals(2, TwoPlayerRhodeIslandGame.getPlayers().length);
		assertEquals(Integer.valueOf(0), TwoPlayerRhodeIslandGame.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), TwoPlayerRhodeIslandGame.getPlayers()[1]);

		assertTrue(TwoPlayerRhodeIslandGame.getPot() instanceof Pot);
		assertEquals(3, TwoPlayerRhodeIslandGame.getPot().getTotalPotSize());
		assertEquals(3, TwoPlayerRhodeIslandGame.getPot().getRoundPotSize());
		assertEquals(1, TwoPlayerRhodeIslandGame.getPot().getPlayersContributionToPot(0));
		assertEquals(2, TwoPlayerRhodeIslandGame.getPot().getPlayersContributionToPot(1));

		assertEquals(0, TwoPlayerRhodeIslandGame.getRaiseCount());
		assertEquals(3, TwoPlayerRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());

		assertFalse(TwoPlayerRhodeIslandGame.isAtTerminalNode());

	}

	@Test
	public final void importGameTest() throws Exception {
		PokerGame TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		PokerGame TwoPlayerRhodeIslandGameToBeCopied = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		TwoPlayerRhodeIslandGameToBeCopied.startGame();
		TwoPlayerRhodeIslandGame.importGameProperties(TwoPlayerRhodeIslandGameToBeCopied);

		assertEquals(1, TwoPlayerRhodeIslandGame.getActionsTaken().size());
		assertTrue(TwoPlayerRhodeIslandGame.getActionsTaken().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, TwoPlayerRhodeIslandGame.getBettingLimit());

		assertEquals(BetRound.PRETURN, TwoPlayerRhodeIslandGame.getBettingRound());
		assertEquals("No cards turned", TwoPlayerRhodeIslandGame.getBoard().toString());
		assertEquals(PokerGameType.RHODE_ISLAND, TwoPlayerRhodeIslandGame.getPokerGameType());

		assertEquals(2, TwoPlayerRhodeIslandGame.getHands().size());
		assertTrue(TwoPlayerRhodeIslandGame.getHands().get(0) instanceof Hand);
		assertTrue(TwoPlayerRhodeIslandGame.getHands().get(1) instanceof Hand);

		//// assertNull(TwoPlayerRhodeIslandGame.getNodeId());
		//// assertNull(TwoPlayerRhodeIslandGame.getPayOffs());

		assertEquals(2, TwoPlayerRhodeIslandGame.getPlayers().length);
		assertEquals(Integer.valueOf(0), TwoPlayerRhodeIslandGame.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), TwoPlayerRhodeIslandGame.getPlayers()[1]);

		assertTrue(TwoPlayerRhodeIslandGame.getPot() instanceof Pot);
		assertEquals(3, TwoPlayerRhodeIslandGame.getPot().getTotalPotSize());
		assertEquals(3, TwoPlayerRhodeIslandGame.getPot().getRoundPotSize());
		assertEquals(1, TwoPlayerRhodeIslandGame.getPot().getPlayersContributionToPot(0));
		assertEquals(2, TwoPlayerRhodeIslandGame.getPot().getPlayersContributionToPot(1));

		assertEquals(0, TwoPlayerRhodeIslandGame.getRaiseCount());
		assertEquals(3, TwoPlayerRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());

		assertFalse(TwoPlayerRhodeIslandGame.isAtTerminalNode());

		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));

		// Check copied game is not affected by new games action
		assertEquals(1, TwoPlayerRhodeIslandGameToBeCopied.getActionsTaken().size());
		assertTrue(TwoPlayerRhodeIslandGameToBeCopied.getActionsTaken().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, TwoPlayerRhodeIslandGameToBeCopied.getBettingLimit());

		assertEquals(BetRound.PRETURN, TwoPlayerRhodeIslandGameToBeCopied.getBettingRound());
		assertEquals("No cards turned", TwoPlayerRhodeIslandGameToBeCopied.getBoard().toString());
		assertEquals(PokerGameType.RHODE_ISLAND, TwoPlayerRhodeIslandGameToBeCopied.getPokerGameType());

		assertEquals(2, TwoPlayerRhodeIslandGameToBeCopied.getHands().size());
		assertTrue(TwoPlayerRhodeIslandGameToBeCopied.getHands().get(0) instanceof Hand);
		assertTrue(TwoPlayerRhodeIslandGameToBeCopied.getHands().get(1) instanceof Hand);

		//// assertNull(TwoPlayerRhodeIslandGameToBeCopied.getNodeId());
		//// assertNull(TwoPlayerRhodeIslandGameToBeCopied.getPayOffs());

		assertEquals(2, TwoPlayerRhodeIslandGameToBeCopied.getPlayers().length);
		assertEquals(Integer.valueOf(0), TwoPlayerRhodeIslandGame.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), TwoPlayerRhodeIslandGame.getPlayers()[1]);

		assertTrue(TwoPlayerRhodeIslandGameToBeCopied.getPot() instanceof Pot);
		assertEquals(3, TwoPlayerRhodeIslandGameToBeCopied.getPot().getTotalPotSize());
		assertEquals(3, TwoPlayerRhodeIslandGameToBeCopied.getPot().getRoundPotSize());
		assertEquals(1, TwoPlayerRhodeIslandGameToBeCopied.getPot().getPlayersContributionToPot(0));
		assertEquals(2, TwoPlayerRhodeIslandGameToBeCopied.getPot().getPlayersContributionToPot(1));

		assertEquals(0, TwoPlayerRhodeIslandGameToBeCopied.getRaiseCount());
		assertEquals(3, TwoPlayerRhodeIslandGameToBeCopied.getRaisesAllowedPerBettingRound());
		assertTrue(TwoPlayerRhodeIslandGameToBeCopied.raisesAllowed());

	}

	@Test
	public final void raisesAllowedTest() throws Exception {
		PokerGame TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);

		TwoPlayerRhodeIslandGame.startGame();

		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());
		TwoPlayerRhodeIslandGame.performAction(1, new RaiseAction(1));
		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		assertFalse(TwoPlayerRhodeIslandGame.raisesAllowed());

		TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 2);

		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		assertFalse(TwoPlayerRhodeIslandGame.raisesAllowed());

		TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 2);

		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		assertTrue(TwoPlayerRhodeIslandGame.raisesAllowed());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		assertFalse(TwoPlayerRhodeIslandGame.raisesAllowed());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		assertFalse(TwoPlayerRhodeIslandGame.raisesAllowed());

		TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 0);
		assertFalse(TwoPlayerRhodeIslandGame.raisesAllowed());
	}

	@Test
	public final void actionIsTerminalCallForTheBettingRoundTest() throws Exception {
		PokerGame TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);

		TwoPlayerRhodeIslandGame.startGame();

		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		TwoPlayerRhodeIslandGame.performAction(1, CallAction.getInstance());
		assertTrue(TwoPlayerRhodeIslandGame.isAtChanceNode());
		
		TwoPlayerRhodeIslandGame.performChanceAction();
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		TwoPlayerRhodeIslandGame.performAction(1, CallAction.getInstance());
		assertTrue(TwoPlayerRhodeIslandGame.isAtChanceNode());
		
		TwoPlayerRhodeIslandGame.performChanceAction();
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		TwoPlayerRhodeIslandGame.performAction(1, CallAction.getInstance());
		assertTrue(TwoPlayerRhodeIslandGame.isAtTerminalNode());
		

		TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		TwoPlayerRhodeIslandGame.startGame();

		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));
		TwoPlayerRhodeIslandGame.performAction(1, FoldAction.getInstance());
		assertTrue(TwoPlayerRhodeIslandGame.isAtTerminalNode());

		TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		TwoPlayerRhodeIslandGame.performAction(0, CallAction.getInstance());
		assertFalse(TwoPlayerRhodeIslandGame.isAtTerminalNode());

		PokerGame twoPlayerTwoCardGame = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(1, CallAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(0, DealAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(1, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(0, CallAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());

		TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());

		TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, DealAction.getInstance());
		twoPlayerTwoCardGame.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());
	}

	@Test
	public final void getPlayerToActTest() throws Exception {
		PokerGame TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);

		TwoPlayerRhodeIslandGame.startGame();
		assertEquals(0, TwoPlayerRhodeIslandGame.getPlayerToAct());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));

		assertEquals(1, TwoPlayerRhodeIslandGame.getPlayerToAct());
		TwoPlayerRhodeIslandGame.performAction(1, new RaiseAction(1));

		assertEquals(0, TwoPlayerRhodeIslandGame.getPlayerToAct());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));

		assertEquals(1, TwoPlayerRhodeIslandGame.getPlayerToAct());
	}

	@Test
	public final void getNodeIdTest() {
		TwoPlayerRhodeIslandGame TwoPlayerRhodeIslandGame = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		Hand twoOfSpades = new HandSingleCard(new Card(0));
		Map<Integer, Hand> testHands = new HashMap<Integer, Hand>();
		testHands.put(0, twoOfSpades);
		testHands.put(1, twoOfSpades);

		TwoPlayerRhodeIslandGame.startGame();
		TwoPlayerRhodeIslandGame.setHands(testHands);
		assertEquals(0, TwoPlayerRhodeIslandGame.getPlayerToAct());
		TwoPlayerRhodeIslandGame.performAction(0, new RaiseAction(1));

		assertEquals("[S,S,2]D,1", TwoPlayerRhodeIslandGame.getNodeIdWithActionMemory());
		assertEquals("[S,S,2]5 raisesAllowed : true", TwoPlayerRhodeIslandGame.getNodeIdWithSummaryState());

		assertEquals(1, TwoPlayerRhodeIslandGame.getPlayerToAct());
	}

	@Test
	public void getListOfGamesWithAllPossibleChanceNodesTest() {
		TwoPlayerRhodeIslandGame game = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3);
		int[][] chanceComboList = game.getListOfValidChanceCombinations();

		assertEquals(6497400, chanceComboList.length);

		Game game0 = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3).setValidChanceCombinations(chanceComboList[0]);
		Game game6497399 = new TwoPlayerRhodeIslandGame(BettingLimit.LIMIT, 3).setValidChanceCombinations(chanceComboList[6497399]);

		assertTrue(game0 instanceof TwoPlayerRhodeIslandGame);
		assertTrue(game6497399 instanceof TwoPlayerRhodeIslandGame);

		TwoPlayerRhodeIslandGame TwoPlayerRhodeIslandGame0 = (TwoPlayerRhodeIslandGame) game0;
		TwoPlayerRhodeIslandGame TwoPlayerRhodeIslandGame6497399 = (TwoPlayerRhodeIslandGame) game6497399;

		assertEquals(CardHeight.DEUCE, TwoPlayerRhodeIslandGame0.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.THREE, TwoPlayerRhodeIslandGame0.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardHeight.FOUR, TwoPlayerRhodeIslandGame0.getBoard().getCard(0).getHeight());
		assertEquals(CardHeight.FIVE, TwoPlayerRhodeIslandGame0.getBoard().getCard(1).getHeight());

		assertEquals(CardHeight.ACE, TwoPlayerRhodeIslandGame6497399.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.KING, TwoPlayerRhodeIslandGame6497399.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardHeight.QUEEN, TwoPlayerRhodeIslandGame6497399.getBoard().getCard(0).getHeight());
		assertEquals(CardHeight.JACK, TwoPlayerRhodeIslandGame6497399.getBoard().getCard(1).getHeight());

		assertEquals(CardSuit.SPADES, TwoPlayerRhodeIslandGame0.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardSuit.SPADES, TwoPlayerRhodeIslandGame0.getHands().get(1).getCard(0).getSuit());
		assertEquals(CardSuit.SPADES, TwoPlayerRhodeIslandGame0.getBoard().getCard(0).getSuit());
		assertEquals(CardSuit.SPADES, TwoPlayerRhodeIslandGame0.getBoard().getCard(1).getSuit());

		assertEquals(CardSuit.DIAMONDS, TwoPlayerRhodeIslandGame6497399.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardSuit.DIAMONDS, TwoPlayerRhodeIslandGame6497399.getHands().get(1).getCard(0).getSuit());
		assertEquals(CardSuit.DIAMONDS, TwoPlayerRhodeIslandGame6497399.getBoard().getCard(0).getSuit());
		assertEquals(CardSuit.DIAMONDS, TwoPlayerRhodeIslandGame6497399.getBoard().getCard(1).getSuit());
	}

}
