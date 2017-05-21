package cfr.trainer.games.poker.games;

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
import cfr.trainer.games.poker.games.TwoPlayerTwoCardGameTest;
import cfr.trainer.games.poker.games.TwoPlayerTwoCardGame;

public class TwoPlayerTwoCardGameTest {

	@Test
	public final void testConstructor() {
		PokerGame TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		assertEquals(0, TwoPlayerTwoCardGameTest.getActions().size());
		assertEquals(BettingLimit.LIMIT, TwoPlayerTwoCardGameTest.getBettingLimit());
		assertEquals(BetRound.TURN,TwoPlayerTwoCardGameTest.getBettingRound());
		assertNull(TwoPlayerTwoCardGameTest.getBoard());
		assertEquals(PokerGameType.TWO_CARD, TwoPlayerTwoCardGameTest.getPokerGameType());
		assertNull(TwoPlayerTwoCardGameTest.getHands());
		// assertNull(TwoPlayerTwoCardGameTest.getNodeId());
		// assertNull(TwoPlayerTwoCardGameTest.getPayOffs());
		assertEquals(2, TwoPlayerTwoCardGameTest.getPlayers().length);
		assertEquals(0, TwoPlayerTwoCardGameTest.getPlayers()[0]);
		assertEquals(1, TwoPlayerTwoCardGameTest.getPlayers()[1]);
		assertTrue(TwoPlayerTwoCardGameTest.getPot() instanceof Pot);
		assertEquals(0, TwoPlayerTwoCardGameTest.getRaiseCount());
		assertEquals(3, TwoPlayerTwoCardGameTest.getRaisesAllowedPerBettingRound());
		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());

		assertFalse(TwoPlayerTwoCardGameTest.isAtTerminalNode());

	}

	@Test
	public final void startGameTest() {
		PokerGame TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		TwoPlayerTwoCardGameTest.startGame();

		assertEquals(1, TwoPlayerTwoCardGameTest.getActions().size());
		assertTrue(TwoPlayerTwoCardGameTest.getActions().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, TwoPlayerTwoCardGameTest.getBettingLimit());

		assertEquals(BetRound.TURN, TwoPlayerTwoCardGameTest.getBettingRound());
		assertEquals("No cards turned", TwoPlayerTwoCardGameTest.getBoard().toString());
		assertEquals(PokerGameType.TWO_CARD, TwoPlayerTwoCardGameTest.getPokerGameType());

		assertEquals(2, TwoPlayerTwoCardGameTest.getHands().size());
		assertTrue(TwoPlayerTwoCardGameTest.getHands().get(0) instanceof Hand);
		assertTrue(TwoPlayerTwoCardGameTest.getHands().get(1) instanceof Hand);

		//// assertNull(TwoPlayerTwoCardGameTest.getNodeId());
		//// assertNull(TwoPlayerTwoCardGameTest.getPayOffs());

		assertEquals(2, TwoPlayerTwoCardGameTest.getPlayers().length);
		assertEquals(0, TwoPlayerTwoCardGameTest.getPlayers()[0]);
		assertEquals(1, TwoPlayerTwoCardGameTest.getPlayers()[1]);

		assertTrue(TwoPlayerTwoCardGameTest.getPot() instanceof Pot);
		assertEquals(3, TwoPlayerTwoCardGameTest.getPot().getTotalPotSize());
		assertEquals(3, TwoPlayerTwoCardGameTest.getPot().getRoundPotSize());
		assertEquals(1, TwoPlayerTwoCardGameTest.getPot().getPlayersContributionToPot(0));
		assertEquals(2, TwoPlayerTwoCardGameTest.getPot().getPlayersContributionToPot(1));

		assertEquals(0, TwoPlayerTwoCardGameTest.getRaiseCount());
		assertEquals(3, TwoPlayerTwoCardGameTest.getRaisesAllowedPerBettingRound());
		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());

		assertFalse(TwoPlayerTwoCardGameTest.isAtTerminalNode());

	}

	@Test
	public final void importGameTest() {
		PokerGame TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		PokerGame TwoPlayerTwoCardGameTestToBeCopied = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		TwoPlayerTwoCardGameTestToBeCopied.startGame();
		TwoPlayerTwoCardGameTest.importGameProperties(TwoPlayerTwoCardGameTestToBeCopied);

		assertEquals(1, TwoPlayerTwoCardGameTest.getActions().size());
		assertTrue(TwoPlayerTwoCardGameTest.getActions().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, TwoPlayerTwoCardGameTest.getBettingLimit());

		assertEquals(BetRound.TURN, TwoPlayerTwoCardGameTest.getBettingRound());
		assertEquals("No cards turned", TwoPlayerTwoCardGameTest.getBoard().toString());
		assertEquals(PokerGameType.TWO_CARD, TwoPlayerTwoCardGameTest.getPokerGameType());

		assertEquals(2, TwoPlayerTwoCardGameTest.getHands().size());
		assertTrue(TwoPlayerTwoCardGameTest.getHands().get(0) instanceof Hand);
		assertTrue(TwoPlayerTwoCardGameTest.getHands().get(1) instanceof Hand);

		//// assertNull(TwoPlayerTwoCardGameTest.getNodeId());
		//// assertNull(TwoPlayerTwoCardGameTest.getPayOffs());

		assertEquals(2, TwoPlayerTwoCardGameTest.getPlayers().length);
		assertEquals(0, TwoPlayerTwoCardGameTest.getPlayers()[0]);
		assertEquals(1, TwoPlayerTwoCardGameTest.getPlayers()[1]);

		assertTrue(TwoPlayerTwoCardGameTest.getPot() instanceof Pot);
		assertEquals(3, TwoPlayerTwoCardGameTest.getPot().getTotalPotSize());
		assertEquals(3, TwoPlayerTwoCardGameTest.getPot().getRoundPotSize());
		assertEquals(1, TwoPlayerTwoCardGameTest.getPot().getPlayersContributionToPot(0));
		assertEquals(2, TwoPlayerTwoCardGameTest.getPot().getPlayersContributionToPot(1));

		assertEquals(0, TwoPlayerTwoCardGameTest.getRaiseCount());
		assertEquals(3, TwoPlayerTwoCardGameTest.getRaisesAllowedPerBettingRound());
		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());

		assertFalse(TwoPlayerTwoCardGameTest.isAtTerminalNode());
		
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		
//		Check copied game is not affected by new games action
		assertEquals(1, TwoPlayerTwoCardGameTestToBeCopied.getActions().size());
		assertTrue(TwoPlayerTwoCardGameTestToBeCopied.getActions().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, TwoPlayerTwoCardGameTestToBeCopied.getBettingLimit());

		assertEquals(BetRound.TURN, TwoPlayerTwoCardGameTestToBeCopied.getBettingRound());
		assertEquals("No cards turned", TwoPlayerTwoCardGameTestToBeCopied.getBoard().toString());
		assertEquals(PokerGameType.TWO_CARD, TwoPlayerTwoCardGameTestToBeCopied.getPokerGameType());

		assertEquals(2, TwoPlayerTwoCardGameTestToBeCopied.getHands().size());
		assertTrue(TwoPlayerTwoCardGameTestToBeCopied.getHands().get(0) instanceof Hand);
		assertTrue(TwoPlayerTwoCardGameTestToBeCopied.getHands().get(1) instanceof Hand);

		//// assertNull(TwoPlayerTwoCardGameTestToBeCopied.getNodeId());
		//// assertNull(TwoPlayerTwoCardGameTestToBeCopied.getPayOffs());

		assertEquals(2, TwoPlayerTwoCardGameTestToBeCopied.getPlayers().length);
		assertEquals(0, TwoPlayerTwoCardGameTestToBeCopied.getPlayers()[0]);
		assertEquals(1, TwoPlayerTwoCardGameTestToBeCopied.getPlayers()[1]);

		assertTrue(TwoPlayerTwoCardGameTestToBeCopied.getPot() instanceof Pot);
		assertEquals(3, TwoPlayerTwoCardGameTestToBeCopied.getPot().getTotalPotSize());
		assertEquals(3, TwoPlayerTwoCardGameTestToBeCopied.getPot().getRoundPotSize());
		assertEquals(1, TwoPlayerTwoCardGameTestToBeCopied.getPot().getPlayersContributionToPot(0));
		assertEquals(2, TwoPlayerTwoCardGameTestToBeCopied.getPot().getPlayersContributionToPot(1));

		assertEquals(0, TwoPlayerTwoCardGameTestToBeCopied.getRaiseCount());
		assertEquals(3, TwoPlayerTwoCardGameTestToBeCopied.getRaisesAllowedPerBettingRound());
		assertTrue(TwoPlayerTwoCardGameTestToBeCopied.raisesAllowed());
		

	}

	@Test
	public final void raisesAllowedTest() {
		PokerGame TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		TwoPlayerTwoCardGameTest.startGame();

		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());
		TwoPlayerTwoCardGameTest.performAction(1, new RaiseAction(1));
		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertFalse(TwoPlayerTwoCardGameTest.raisesAllowed());

		TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 2);

		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertFalse(TwoPlayerTwoCardGameTest.raisesAllowed());

		TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 2);

		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertTrue(TwoPlayerTwoCardGameTest.raisesAllowed());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertFalse(TwoPlayerTwoCardGameTest.raisesAllowed());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		assertFalse(TwoPlayerTwoCardGameTest.raisesAllowed());

		TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 0);
		assertFalse(TwoPlayerTwoCardGameTest.raisesAllowed());
	}

	@Test
	public final void actionIsTerminalCallForTheBettingRoundTest() {
		PokerGame TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		TwoPlayerTwoCardGameTest.startGame();

		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		TwoPlayerTwoCardGameTest.performAction(1, CallAction.getInstance());
		assertTrue(TwoPlayerTwoCardGameTest.isAtChanceNode());
		
		TwoPlayerTwoCardGameTest.performAction(0, DealAction.getInstance());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		TwoPlayerTwoCardGameTest.performAction(1, CallAction.getInstance());
		assertTrue(TwoPlayerTwoCardGameTest.isAtTerminalNode());

		TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		TwoPlayerTwoCardGameTest.startGame();

		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));
		TwoPlayerTwoCardGameTest.performAction(1, FoldAction.getInstance());
		assertTrue(TwoPlayerTwoCardGameTest.isAtTerminalNode());
		
		TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		TwoPlayerTwoCardGameTest.performAction(0, CallAction.getInstance());
		assertFalse(TwoPlayerTwoCardGameTest.isAtTerminalNode());

		PokerGame twoPlayerTwoCardGame = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(1, CallAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(0, DealAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(1, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(0, CallAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());

		TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());

		TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performChanceAction();
		twoPlayerTwoCardGame.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());
	}

	@Test
	public final void getPlayerToActTest() {
		Game TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		PokerGame POKERGAMETwoPlayerTwoCardGameTest = (PokerGame)TwoPlayerTwoCardGameTest;

		TwoPlayerTwoCardGameTest.startGame();
		assertEquals(0, TwoPlayerTwoCardGameTest.getPlayerToAct());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));

		assertEquals(1, TwoPlayerTwoCardGameTest.getPlayerToAct());
		TwoPlayerTwoCardGameTest.performAction(1, new RaiseAction(1));

		assertEquals(0, TwoPlayerTwoCardGameTest.getPlayerToAct());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));

		assertEquals(1, TwoPlayerTwoCardGameTest.getPlayerToAct());
		assertFalse(POKERGAMETwoPlayerTwoCardGameTest.raisesAllowed());
		TwoPlayerTwoCardGameTest.performAction(0, CallAction.getInstance());
		
		assertTrue(TwoPlayerTwoCardGameTest.isAtChanceNode());
		TwoPlayerTwoCardGameTest.performChanceAction();
		
		assertEquals(0, TwoPlayerTwoCardGameTest.getPlayerToAct());
		
		
	}

	@Test
	public final void getNodeIdTest() {
		TwoPlayerTwoCardGame TwoPlayerTwoCardGameTest = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		Hand twoOfSpades = new HandSingleCard(new Card(0));
		Map<Integer, Hand> testHands = new HashMap<Integer, Hand>();
		testHands.put(0, twoOfSpades);
		testHands.put(1, twoOfSpades);

		TwoPlayerTwoCardGameTest.startGame();
		TwoPlayerTwoCardGameTest.setHands(testHands);
		assertEquals(0, TwoPlayerTwoCardGameTest.getPlayerToAct());
		TwoPlayerTwoCardGameTest.performAction(0, new RaiseAction(1));

		assertEquals("[S, S, DEUCE]DEAL RAISE1", TwoPlayerTwoCardGameTest.getNodeIdWithActionMemory());
		assertEquals("[S, S, DEUCE]5 raisesAllowed : true", TwoPlayerTwoCardGameTest.getNodeIdWithGameState());

		assertEquals(1, TwoPlayerTwoCardGameTest.getPlayerToAct());
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
		
		TwoPlayerTwoCardGame TwoPlayerTwoCardGameTest0 = (TwoPlayerTwoCardGame) game0;
		TwoPlayerTwoCardGame TwoPlayerTwoCardGameTest132599 = (TwoPlayerTwoCardGame) game132599;
		
		assertEquals(CardHeight.DEUCE, TwoPlayerTwoCardGameTest0.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.THREE, TwoPlayerTwoCardGameTest0.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardHeight.FOUR, TwoPlayerTwoCardGameTest0.getBoard().getCard(0).getHeight());
		
		assertEquals(CardHeight.ACE, TwoPlayerTwoCardGameTest132599.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.KING, TwoPlayerTwoCardGameTest132599.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardHeight.QUEEN, TwoPlayerTwoCardGameTest132599.getBoard().getCard(0).getHeight());
		
		assertEquals(CardSuit.SPADES, TwoPlayerTwoCardGameTest0.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardSuit.SPADES, TwoPlayerTwoCardGameTest0.getHands().get(1).getCard(0).getSuit());
		assertEquals(CardSuit.SPADES, TwoPlayerTwoCardGameTest0.getBoard().getCard(0).getSuit());
		
		assertEquals(CardSuit.DIAMONDS, TwoPlayerTwoCardGameTest132599.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardSuit.DIAMONDS, TwoPlayerTwoCardGameTest132599.getHands().get(1).getCard(0).getSuit());
		assertEquals(CardSuit.DIAMONDS, TwoPlayerTwoCardGameTest132599.getBoard().getCard(0).getSuit());
		
	}
	
	
}
