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
import cfr.trainer.games.poker.games.TwoPlayerSingleCardGame;
import cfr.trainer.games.poker.games.TwoPlayerTwoCardGame;

public class TwoPlayerSingleCardGameTest {

	@Test
	public final void testConstructor() {
		PokerGame twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);

		assertEquals(0, twoPlayerSingleCardGame.getActionsTaken().size());
		assertEquals(BettingLimit.LIMIT, twoPlayerSingleCardGame.getBettingLimit());
		assertNull(twoPlayerSingleCardGame.getBettingRound());
		assertNull(twoPlayerSingleCardGame.getBoard());
		assertEquals(PokerGameType.SINGLE_CARD, twoPlayerSingleCardGame.getPokerGameType());
		assertNull(twoPlayerSingleCardGame.getHands().get(0));
		assertNull(twoPlayerSingleCardGame.getHands().get(1));
		// assertNull(twoPlayerSingleCardGame.getNodeId());
		// assertNull(twoPlayerSingleCardGame.getPayOffs());
		assertEquals(2, twoPlayerSingleCardGame.getPlayers().length);
		assertEquals(Integer.valueOf(0), twoPlayerSingleCardGame.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), twoPlayerSingleCardGame.getPlayers()[1]);
		assertTrue(twoPlayerSingleCardGame.getPot() instanceof Pot);
		assertEquals(0, twoPlayerSingleCardGame.getRaiseCount());
		assertEquals(3, twoPlayerSingleCardGame.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());

		assertFalse(twoPlayerSingleCardGame.isAtTerminalNode());

	}

	@Test
	public final void startGameTest() {
		PokerGame twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);

		twoPlayerSingleCardGame.startGame();

		assertEquals(1, twoPlayerSingleCardGame.getActionsTaken().size());
		assertTrue(twoPlayerSingleCardGame.getActionsTaken().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, twoPlayerSingleCardGame.getBettingLimit());

		assertEquals(BetRound.RIVER, twoPlayerSingleCardGame.getBettingRound());
		assertEquals("No cards turned", twoPlayerSingleCardGame.getBoard().toString());
		assertEquals(PokerGameType.SINGLE_CARD, twoPlayerSingleCardGame.getPokerGameType());

		assertEquals(2, twoPlayerSingleCardGame.getHands().size());
		assertTrue(twoPlayerSingleCardGame.getHands().get(0) instanceof Hand);
		assertTrue(twoPlayerSingleCardGame.getHands().get(1) instanceof Hand);

		//// assertNull(twoPlayerSingleCardGame.getNodeId());
		//// assertNull(twoPlayerSingleCardGame.getPayOffs());

		assertEquals(2, twoPlayerSingleCardGame.getPlayers().length);
		assertEquals(Integer.valueOf(0), twoPlayerSingleCardGame.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), twoPlayerSingleCardGame.getPlayers()[1]);

		assertTrue(twoPlayerSingleCardGame.getPot() instanceof Pot);
		assertEquals(3, twoPlayerSingleCardGame.getPot().getTotalPotSize());
		assertEquals(3, twoPlayerSingleCardGame.getPot().getRoundPotSize());
		assertEquals(1, twoPlayerSingleCardGame.getPot().getPlayersContributionToPot(0));
		assertEquals(2, twoPlayerSingleCardGame.getPot().getPlayersContributionToPot(1));

		assertEquals(0, twoPlayerSingleCardGame.getRaiseCount());
		assertEquals(3, twoPlayerSingleCardGame.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());

		assertFalse(twoPlayerSingleCardGame.isAtTerminalNode());

	}

	@Test
	public final void importGameTest() {
		PokerGame twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		PokerGame twoPlayerSingleCardGameToBeCopied = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		twoPlayerSingleCardGameToBeCopied.startGame();
		twoPlayerSingleCardGame.importGameProperties(twoPlayerSingleCardGameToBeCopied);

		assertEquals(1, twoPlayerSingleCardGame.getActionsTaken().size());
		assertTrue(twoPlayerSingleCardGame.getActionsTaken().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, twoPlayerSingleCardGame.getBettingLimit());

		assertEquals(BetRound.RIVER, twoPlayerSingleCardGame.getBettingRound());
		assertEquals("No cards turned", twoPlayerSingleCardGame.getBoard().toString());
		assertEquals(PokerGameType.SINGLE_CARD, twoPlayerSingleCardGame.getPokerGameType());

		assertEquals(2, twoPlayerSingleCardGame.getHands().size());
		assertTrue(twoPlayerSingleCardGame.getHands().get(0) instanceof Hand);
		assertTrue(twoPlayerSingleCardGame.getHands().get(1) instanceof Hand);

		//// assertNull(twoPlayerSingleCardGame.getNodeId());
		//// assertNull(twoPlayerSingleCardGame.getPayOffs());

		assertEquals(2, twoPlayerSingleCardGame.getPlayers().length);
		assertEquals(Integer.valueOf(0), twoPlayerSingleCardGame.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), twoPlayerSingleCardGame.getPlayers()[1]);

		assertTrue(twoPlayerSingleCardGame.getPot() instanceof Pot);
		assertEquals(3, twoPlayerSingleCardGame.getPot().getTotalPotSize());
		assertEquals(3, twoPlayerSingleCardGame.getPot().getRoundPotSize());
		assertEquals(1, twoPlayerSingleCardGame.getPot().getPlayersContributionToPot(0));
		assertEquals(2, twoPlayerSingleCardGame.getPot().getPlayersContributionToPot(1));

		assertEquals(0, twoPlayerSingleCardGame.getRaiseCount());
		assertEquals(3, twoPlayerSingleCardGame.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());

		assertFalse(twoPlayerSingleCardGame.isAtTerminalNode());
		
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		
//		Check copied game is not affected by new games action
		assertEquals(1, twoPlayerSingleCardGameToBeCopied.getActionsTaken().size());
		assertTrue(twoPlayerSingleCardGameToBeCopied.getActionsTaken().contains(DealAction.getInstance()));

		assertEquals(BettingLimit.LIMIT, twoPlayerSingleCardGameToBeCopied.getBettingLimit());

		assertEquals(BetRound.RIVER, twoPlayerSingleCardGameToBeCopied.getBettingRound());
		assertEquals("No cards turned", twoPlayerSingleCardGameToBeCopied.getBoard().toString());
		assertEquals(PokerGameType.SINGLE_CARD, twoPlayerSingleCardGameToBeCopied.getPokerGameType());

		assertEquals(2, twoPlayerSingleCardGameToBeCopied.getHands().size());
		assertTrue(twoPlayerSingleCardGameToBeCopied.getHands().get(0) instanceof Hand);
		assertTrue(twoPlayerSingleCardGameToBeCopied.getHands().get(1) instanceof Hand);

		//// assertNull(twoPlayerSingleCardGameToBeCopied.getNodeId());
		//// assertNull(twoPlayerSingleCardGameToBeCopied.getPayOffs());

		assertEquals(2, twoPlayerSingleCardGameToBeCopied.getPlayers().length);
		assertEquals(Integer.valueOf(0), twoPlayerSingleCardGame.getPlayers()[0]);
		assertEquals(Integer.valueOf(1), twoPlayerSingleCardGame.getPlayers()[1]);

		assertTrue(twoPlayerSingleCardGameToBeCopied.getPot() instanceof Pot);
		assertEquals(3, twoPlayerSingleCardGameToBeCopied.getPot().getTotalPotSize());
		assertEquals(3, twoPlayerSingleCardGameToBeCopied.getPot().getRoundPotSize());
		assertEquals(1, twoPlayerSingleCardGameToBeCopied.getPot().getPlayersContributionToPot(0));
		assertEquals(2, twoPlayerSingleCardGameToBeCopied.getPot().getPlayersContributionToPot(1));

		assertEquals(0, twoPlayerSingleCardGameToBeCopied.getRaiseCount());
		assertEquals(3, twoPlayerSingleCardGameToBeCopied.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerSingleCardGameToBeCopied.raisesAllowed());
		

	}

	@Test
	public final void raisesAllowedTest() {
		PokerGame twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);

		twoPlayerSingleCardGame.startGame();

		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(1, new RaiseAction(1));
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());

		twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 2);

		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());

		twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 2);

		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());

		twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 0);
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());
	}

	@Test
	public final void actionIsTerminalCallForTheBettingRoundTest() {
		PokerGame twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);

		twoPlayerSingleCardGame.startGame();

		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		twoPlayerSingleCardGame.performAction(1, CallAction.getInstance());
		assertTrue(twoPlayerSingleCardGame.isAtTerminalNode());

		twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		twoPlayerSingleCardGame.startGame();

		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));
		twoPlayerSingleCardGame.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerSingleCardGame.isAtTerminalNode());
		
		twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		twoPlayerSingleCardGame.performAction(0, CallAction.getInstance());
		assertFalse(twoPlayerSingleCardGame.isAtTerminalNode());

		PokerGame twoPlayerTwoCardGame = new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);

		twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(1, CallAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(0, DealAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(1, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(0, CallAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());

		twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());

		twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		twoPlayerTwoCardGame.startGame();

		twoPlayerTwoCardGame.performAction(0, DealAction.getInstance());
		twoPlayerTwoCardGame.performAction(1, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());
	}

	@Test
	public final void getPlayerToActTest() {
		PokerGame twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);

		twoPlayerSingleCardGame.startGame();
		assertEquals(0, twoPlayerSingleCardGame.getPlayerToAct());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));

		assertEquals(1, twoPlayerSingleCardGame.getPlayerToAct());
		twoPlayerSingleCardGame.performAction(1, new RaiseAction(1));

		assertEquals(0, twoPlayerSingleCardGame.getPlayerToAct());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));

		assertEquals(1, twoPlayerSingleCardGame.getPlayerToAct());
	}

	@Test
	public final void getNodeIdTest() {
		TwoPlayerSingleCardGame twoPlayerSingleCardGame = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		Hand twoOfSpades = new HandSingleCard(new Card(0));
		Map<Integer, Hand> testHands = new HashMap<Integer, Hand>();
		testHands.put(0, twoOfSpades);
		testHands.put(1, twoOfSpades);

		twoPlayerSingleCardGame.startGame();
		twoPlayerSingleCardGame.setHands(testHands);
		assertEquals(0, twoPlayerSingleCardGame.getPlayerToAct());
		twoPlayerSingleCardGame.performAction(0, new RaiseAction(1));

		assertEquals("[S,S,2]D,1", twoPlayerSingleCardGame.getNodeIdWithActionMemory());
		assertEquals("[S,S,2]5 raisesAllowed : true", twoPlayerSingleCardGame.getNodeIdWithSummaryState());

		assertEquals(1, twoPlayerSingleCardGame.getPlayerToAct());
	}

	@Test
	public void getListOfGamesWithAllPossibleChanceNodesTest(){
		
		
		TwoPlayerSingleCardGame game = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		List<List<Integer>> chanceComboList = game.getListOfValidChanceCombinations();
		
		assertEquals(2652,chanceComboList.size());
		
		Game game0 = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3).setValidChanceCombinations(chanceComboList.get(0));
		Game game2651 = new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3).setValidChanceCombinations(chanceComboList.get(2651));
		
		assertTrue(game0 instanceof TwoPlayerSingleCardGame);
		assertTrue(game2651 instanceof TwoPlayerSingleCardGame);
		
		TwoPlayerSingleCardGame TwoPlayerSingleCardGame0 = (TwoPlayerSingleCardGame) game0;
		TwoPlayerSingleCardGame TwoPlayerSingleCardGame2651 = (TwoPlayerSingleCardGame) game2651;
		
		assertEquals(CardHeight.DEUCE, TwoPlayerSingleCardGame0.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.THREE, TwoPlayerSingleCardGame0.getHands().get(1).getCard(0).getHeight());
		
		assertEquals(CardHeight.ACE, TwoPlayerSingleCardGame2651.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.KING, TwoPlayerSingleCardGame2651.getHands().get(1).getCard(0).getHeight());
		
		assertEquals(CardSuit.SPADES, TwoPlayerSingleCardGame0.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardSuit.SPADES, TwoPlayerSingleCardGame0.getHands().get(1).getCard(0).getSuit());
		
		assertEquals(CardSuit.DIAMONDS, TwoPlayerSingleCardGame2651.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardSuit.DIAMONDS, TwoPlayerSingleCardGame2651.getHands().get(1).getCard(0).getSuit());
	}
	
	
}
