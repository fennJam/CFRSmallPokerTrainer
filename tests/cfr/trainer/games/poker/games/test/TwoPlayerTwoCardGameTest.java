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
import cfr.trainer.games.poker.games.PokerGame;
import cfr.trainer.games.poker.games.TwoPlayerTwoCardGame;
import cfr.trainer.games.poker.games.test.TwoPlayerTwoCardGameTest;

public class TwoPlayerTwoCardGameTest {

	@Test
	public final void testConstructor() {
		PokerGame twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		assertEquals(0, twoPlayerTwoCardGameTest.getActionsTaken().size());
		assertEquals(BettingLimit.LIMIT, twoPlayerTwoCardGameTest.getBettingLimit());
		assertEquals(BetRound.TURN,twoPlayerTwoCardGameTest.getBettingRound());
		assertNull(twoPlayerTwoCardGameTest.getBoard());
		assertEquals(PokerGameType.TWO_CARD, twoPlayerTwoCardGameTest.getPokerGameType());
		assertNull(twoPlayerTwoCardGameTest.getHands().get(0));
		assertNull(twoPlayerTwoCardGameTest.getHands().get(1));
		// assertNull(TwoPlayerTwoCardGameTest.getNodeId());
		// assertNull(TwoPlayerTwoCardGameTest.getPayOffs());
		assertEquals(2, twoPlayerTwoCardGameTest.getPlayers().length);
		assertEquals(Integer.valueOf(0), twoPlayerTwoCardGameTest.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), twoPlayerTwoCardGameTest.getPlayers()[1]);
		assertTrue(twoPlayerTwoCardGameTest.getPot() instanceof Pot);
		assertEquals(0, twoPlayerTwoCardGameTest.getRaiseCount());
		assertEquals(3, twoPlayerTwoCardGameTest.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());

		assertFalse(twoPlayerTwoCardGameTest.isAtTerminalNode());

	}

	@Test
	public final void startGameTest() throws Exception {
		PokerGame twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		twoPlayerTwoCardGameTest.startGame();

		assertEquals(1, twoPlayerTwoCardGameTest.getActionsTaken().size());
		assertTrue(twoPlayerTwoCardGameTest.getActionsTaken().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, twoPlayerTwoCardGameTest.getBettingLimit());

		assertEquals(BetRound.TURN, twoPlayerTwoCardGameTest.getBettingRound());
		assertEquals("No cards turned", twoPlayerTwoCardGameTest.getBoard().toString());
		assertEquals(PokerGameType.TWO_CARD, twoPlayerTwoCardGameTest.getPokerGameType());

		assertEquals(2, twoPlayerTwoCardGameTest.getHands().size());
		assertTrue(twoPlayerTwoCardGameTest.getHands().get(0) instanceof Hand);
		assertTrue(twoPlayerTwoCardGameTest.getHands().get(1) instanceof Hand);

		//// assertNull(TwoPlayerTwoCardGameTest.getNodeId());
		//// assertNull(TwoPlayerTwoCardGameTest.getPayOffs());

		assertEquals(2, twoPlayerTwoCardGameTest.getPlayers().length);
		assertEquals(Integer.valueOf(0), twoPlayerTwoCardGameTest.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), twoPlayerTwoCardGameTest.getPlayers()[1]);

		assertTrue(twoPlayerTwoCardGameTest.getPot() instanceof Pot);
		assertEquals(3, twoPlayerTwoCardGameTest.getPot().getTotalPotSize());
		assertEquals(3, twoPlayerTwoCardGameTest.getPot().getRoundPotSize());
		assertEquals(1, twoPlayerTwoCardGameTest.getPot().getPlayersContributionToPot(0));
		assertEquals(2, twoPlayerTwoCardGameTest.getPot().getPlayersContributionToPot(1));

		assertEquals(0, twoPlayerTwoCardGameTest.getRaiseCount());
		assertEquals(3, twoPlayerTwoCardGameTest.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());

		assertFalse(twoPlayerTwoCardGameTest.isAtTerminalNode());

	}

	@Test
	public final void importGameTest() throws Exception {
		PokerGame twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		PokerGame twoPlayerTwoCardGameTestToBeCopied = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGameTestToBeCopied.startGame();
		twoPlayerTwoCardGameTest.importGameProperties(twoPlayerTwoCardGameTestToBeCopied);

		assertEquals(1, twoPlayerTwoCardGameTest.getActionsTaken().size());
		assertTrue(twoPlayerTwoCardGameTest.getActionsTaken().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, twoPlayerTwoCardGameTest.getBettingLimit());

		assertEquals(BetRound.TURN, twoPlayerTwoCardGameTest.getBettingRound());
		assertEquals("No cards turned", twoPlayerTwoCardGameTest.getBoard().toString());
		assertEquals(PokerGameType.TWO_CARD, twoPlayerTwoCardGameTest.getPokerGameType());

		assertEquals(2, twoPlayerTwoCardGameTest.getHands().size());
		assertTrue(twoPlayerTwoCardGameTest.getHands().get(0) instanceof Hand);
		assertTrue(twoPlayerTwoCardGameTest.getHands().get(1) instanceof Hand);

		//// assertNull(TwoPlayerTwoCardGameTest.getNodeId());
		//// assertNull(TwoPlayerTwoCardGameTest.getPayOffs());

		assertEquals(2, twoPlayerTwoCardGameTest.getPlayers().length);
		assertEquals(Integer.valueOf(0), twoPlayerTwoCardGameTest.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), twoPlayerTwoCardGameTest.getPlayers()[1]);

		assertTrue(twoPlayerTwoCardGameTest.getPot() instanceof Pot);
		assertEquals(3, twoPlayerTwoCardGameTest.getPot().getTotalPotSize());
		assertEquals(3, twoPlayerTwoCardGameTest.getPot().getRoundPotSize());
		assertEquals(1, twoPlayerTwoCardGameTest.getPot().getPlayersContributionToPot(0));
		assertEquals(2, twoPlayerTwoCardGameTest.getPot().getPlayersContributionToPot(1));

		assertEquals(0, twoPlayerTwoCardGameTest.getRaiseCount());
		assertEquals(3, twoPlayerTwoCardGameTest.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());

		assertFalse(twoPlayerTwoCardGameTest.isAtTerminalNode());
		
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		
//		Check copied game is not affected by new games action
		assertEquals(1, twoPlayerTwoCardGameTestToBeCopied.getActionsTaken().size());
		assertTrue(twoPlayerTwoCardGameTestToBeCopied.getActionsTaken().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, twoPlayerTwoCardGameTestToBeCopied.getBettingLimit());

		assertEquals(BetRound.TURN, twoPlayerTwoCardGameTestToBeCopied.getBettingRound());
		assertEquals("No cards turned", twoPlayerTwoCardGameTestToBeCopied.getBoard().toString());
		assertEquals(PokerGameType.TWO_CARD, twoPlayerTwoCardGameTestToBeCopied.getPokerGameType());

		assertEquals(2, twoPlayerTwoCardGameTestToBeCopied.getHands().size());
		assertTrue(twoPlayerTwoCardGameTestToBeCopied.getHands().get(0) instanceof Hand);
		assertTrue(twoPlayerTwoCardGameTestToBeCopied.getHands().get(1) instanceof Hand);

		//// assertNull(TwoPlayerTwoCardGameTestToBeCopied.getNodeId());
		//// assertNull(TwoPlayerTwoCardGameTestToBeCopied.getPayOffs());

		assertEquals(2, twoPlayerTwoCardGameTestToBeCopied.getPlayers().length);
		assertEquals(Integer.valueOf(0), twoPlayerTwoCardGameTest.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), twoPlayerTwoCardGameTest.getPlayers()[1]);

		assertTrue(twoPlayerTwoCardGameTestToBeCopied.getPot() instanceof Pot);
		assertEquals(3, twoPlayerTwoCardGameTestToBeCopied.getPot().getTotalPotSize());
		assertEquals(3, twoPlayerTwoCardGameTestToBeCopied.getPot().getRoundPotSize());
		assertEquals(1, twoPlayerTwoCardGameTestToBeCopied.getPot().getPlayersContributionToPot(0));
		assertEquals(2, twoPlayerTwoCardGameTestToBeCopied.getPot().getPlayersContributionToPot(1));

		assertEquals(0, twoPlayerTwoCardGameTestToBeCopied.getRaiseCount());
		assertEquals(3, twoPlayerTwoCardGameTestToBeCopied.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerTwoCardGameTestToBeCopied.raisesAllowed());
		

	}

	@Test
	public final void raisesAllowedTest() throws Exception {
		PokerGame twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		twoPlayerTwoCardGameTest.startGame();

		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());
		twoPlayerTwoCardGameTest.performAction(1, new RaiseAction(1));
		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertFalse(twoPlayerTwoCardGameTest.raisesAllowed());

		twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 2);

		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertFalse(twoPlayerTwoCardGameTest.raisesAllowed());

		twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 2);

		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertTrue(twoPlayerTwoCardGameTest.raisesAllowed());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertFalse(twoPlayerTwoCardGameTest.raisesAllowed());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertFalse(twoPlayerTwoCardGameTest.raisesAllowed());

		twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 0);
		assertFalse(twoPlayerTwoCardGameTest.raisesAllowed());
	}

	@Test
	public final void actionIsTerminalCallForTheBettingRoundTest() throws Exception {
		PokerGame twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		twoPlayerTwoCardGameTest.startGame();

		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGameTest.performAction(1, CallAction.getInstance());
		assertTrue(twoPlayerTwoCardGameTest.isAtChanceNode());
		
		twoPlayerTwoCardGameTest.performAction(0, DealAction.getInstance());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGameTest.performAction(1, CallAction.getInstance());
		assertTrue(twoPlayerTwoCardGameTest.isAtTerminalNode());

		twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGameTest.startGame();

		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGameTest.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGameTest.isAtTerminalNode());
		
		twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGameTest.performAction(0, CallAction.getInstance());
		assertFalse(twoPlayerTwoCardGameTest.isAtTerminalNode());

		PokerGame twoPlayerTwoCardGame = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(1, CallAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(0, DealAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(1, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(0, CallAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());

		twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());

		twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performChanceAction();
		twoPlayerTwoCardGame.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());
	}

	@Test
	public final void getPlayerToActTest() throws Exception {
		Game twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		PokerGame pOKERGAMETwoPlayerTwoCardGameTest = (PokerGame)twoPlayerTwoCardGameTest;

		twoPlayerTwoCardGameTest.startGame();
		assertEquals(0, twoPlayerTwoCardGameTest.getPlayerToAct());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));

		assertEquals(1, twoPlayerTwoCardGameTest.getPlayerToAct());
		twoPlayerTwoCardGameTest.performAction(1, new RaiseAction(1));

		assertEquals(0, twoPlayerTwoCardGameTest.getPlayerToAct());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));

		assertEquals(1, twoPlayerTwoCardGameTest.getPlayerToAct());
		assertFalse(pOKERGAMETwoPlayerTwoCardGameTest.raisesAllowed());
		twoPlayerTwoCardGameTest.performAction(0, CallAction.getInstance());
		
		assertTrue(twoPlayerTwoCardGameTest.isAtChanceNode());
		twoPlayerTwoCardGameTest.performChanceAction();
		
		assertEquals(0, twoPlayerTwoCardGameTest.getPlayerToAct());
		
		
	}

	@Test
	public final void getNodeIdTest() {
		TwoPlayerTwoCardGame twoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		Hand twoOfSpades = new HandSingleCard(new Card(0));
		Map<Integer, Hand> testHands = new HashMap<Integer, Hand>();
		testHands.put(0, twoOfSpades);
		testHands.put(1, twoOfSpades);

		twoPlayerTwoCardGameTest.startGame();
		twoPlayerTwoCardGameTest.setHands(testHands);
		assertEquals(0, twoPlayerTwoCardGameTest.getPlayerToAct());
		twoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));

		assertEquals("[S,S,2]D,1", twoPlayerTwoCardGameTest.getNodeIdWithActionMemory());
		assertEquals("[S,S,2]5 raisesAllowed : true", twoPlayerTwoCardGameTest.getNodeIdWithSummaryState());

		assertEquals(1, twoPlayerTwoCardGameTest.getPlayerToAct());
	}

	@Test
	public void getListOfGamesWithAllPossibleChanceNodesTest(){
		
		TwoPlayerTwoCardGame game = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		List<List<Integer>> chanceComboList = game.getListOfValidChanceCombinations();
		
		assertEquals(132600,chanceComboList.size());
		
		Game game0 = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3).setValidChanceCombinations(chanceComboList.get(0));
		Game game132599 = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3).setValidChanceCombinations(chanceComboList.get(132599));
		
		assertTrue(game0 instanceof TwoPlayerTwoCardGame);
		assertTrue(game132599 instanceof TwoPlayerTwoCardGame);
		
		TwoPlayerTwoCardGame twoPlayerTwoCardGameTest0 = (TwoPlayerTwoCardGame) game0;
		TwoPlayerTwoCardGame twoPlayerTwoCardGameTest132599 = (TwoPlayerTwoCardGame) game132599;
		
		assertEquals(CardHeight.DEUCE, twoPlayerTwoCardGameTest0.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.THREE, twoPlayerTwoCardGameTest0.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardHeight.FOUR, twoPlayerTwoCardGameTest0.getBoard().getCard(0).getHeight());
		
		assertEquals(CardHeight.ACE, twoPlayerTwoCardGameTest132599.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.KING, twoPlayerTwoCardGameTest132599.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardHeight.QUEEN, twoPlayerTwoCardGameTest132599.getBoard().getCard(0).getHeight());
		
		assertEquals(CardSuit.SPADES, twoPlayerTwoCardGameTest0.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardSuit.SPADES, twoPlayerTwoCardGameTest0.getHands().get(1).getCard(0).getSuit());
		assertEquals(CardSuit.SPADES, twoPlayerTwoCardGameTest0.getBoard().getCard(0).getSuit());
		
		assertEquals(CardSuit.DIAMONDS, twoPlayerTwoCardGameTest132599.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardSuit.DIAMONDS, twoPlayerTwoCardGameTest132599.getHands().get(1).getCard(0).getSuit());
		assertEquals(CardSuit.DIAMONDS, twoPlayerTwoCardGameTest132599.getBoard().getCard(0).getSuit());
		
	}
	
	
}
