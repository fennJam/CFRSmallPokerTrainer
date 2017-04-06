package cfr.poker.games.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cfr.poker.*;
import cfr.poker.actions.CallAction;
import cfr.poker.actions.DealAction;
import cfr.poker.actions.FoldAction;
import cfr.poker.actions.RaiseAction;
import cfr.poker.games.PokerGame;
import cfr.poker.games.TwoPlayerSingleCardGame;
import cfr.poker.games.TwoPlayerTwoCardGame;

public class TwoPlayerSingleCardGameTest {

	@Test
	public final void testConstructor() {
		PokerGame twoPlayerSingleCardGame =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		
		assertEquals(0,twoPlayerSingleCardGame.getActions().size());
		assertEquals(BettingLimit.LIMIT,twoPlayerSingleCardGame.getBettingLimit());
		assertNull(twoPlayerSingleCardGame.getBettingRound());
		assertNull(twoPlayerSingleCardGame.getBoard());
		assertEquals(PokerGameType.SINGLE_CARD,twoPlayerSingleCardGame.getGameType());
		assertNull(twoPlayerSingleCardGame.getHands());
//		assertNull(twoPlayerSingleCardGame.getNodeId());
//		assertNull(twoPlayerSingleCardGame.getPayOffs());
		assertEquals(2,twoPlayerSingleCardGame.getPlayers().length);
		assertEquals(0,twoPlayerSingleCardGame.getPlayers()[0]);
		assertEquals(1,twoPlayerSingleCardGame.getPlayers()[1]);
		assertTrue(twoPlayerSingleCardGame.getPot() instanceof Pot);
		assertEquals(0,twoPlayerSingleCardGame.getRaiseCount());
		assertEquals(3,twoPlayerSingleCardGame.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		
		assertFalse(twoPlayerSingleCardGame.isAtTerminalNode());
		
	}

	
	@Test
	public final void startGameTest() {
		PokerGame twoPlayerSingleCardGame =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		
		twoPlayerSingleCardGame.startGame();
		
		assertEquals(1,twoPlayerSingleCardGame.getActions().size());
		assertTrue(twoPlayerSingleCardGame.getActions().contains(DealAction.getInstance()));
		
		assertEquals(BettingLimit.LIMIT,twoPlayerSingleCardGame.getBettingLimit());
		
		assertEquals(BetRound.RIVER,twoPlayerSingleCardGame.getBettingRound());
		assertEquals("No cards turned",twoPlayerSingleCardGame.getBoard().toString());
		assertEquals(PokerGameType.SINGLE_CARD,twoPlayerSingleCardGame.getGameType());
		
		assertEquals(2,twoPlayerSingleCardGame.getHands().size());
		assertTrue(twoPlayerSingleCardGame.getHands().get(0) instanceof Hand);
		assertTrue(twoPlayerSingleCardGame.getHands().get(1) instanceof Hand);

		
////		assertNull(twoPlayerSingleCardGame.getNodeId());
////		assertNull(twoPlayerSingleCardGame.getPayOffs());
		
		assertEquals(2,twoPlayerSingleCardGame.getPlayers().length);
		assertEquals(0,twoPlayerSingleCardGame.getPlayers()[0]);
		assertEquals(1,twoPlayerSingleCardGame.getPlayers()[1]);
		
		assertTrue(twoPlayerSingleCardGame.getPot() instanceof Pot);
		assertEquals(3,twoPlayerSingleCardGame.getPot().getTotalPotSize());
		assertEquals(3,twoPlayerSingleCardGame.getPot().getRoundPotSize());
		assertEquals(1,twoPlayerSingleCardGame.getPot().getPlayersContributionToPot(0));
		assertEquals(2,twoPlayerSingleCardGame.getPot().getPlayersContributionToPot(1));
		
		assertEquals(0,twoPlayerSingleCardGame.getRaiseCount());
		assertEquals(3,twoPlayerSingleCardGame.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		
		assertFalse(twoPlayerSingleCardGame.isAtTerminalNode());
		
	}
	
	@Test
	public final void importGameTest() {
		PokerGame twoPlayerSingleCardGame =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		PokerGame twoPlayerSingleCardGameToBeCopied =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		twoPlayerSingleCardGameToBeCopied.startGame();
		twoPlayerSingleCardGame.importGameProperties(twoPlayerSingleCardGameToBeCopied);
		
		
		
		assertEquals(1,twoPlayerSingleCardGame.getActions().size());
		assertTrue(twoPlayerSingleCardGame.getActions().contains(DealAction.getInstance()));
		
		assertEquals(BettingLimit.LIMIT,twoPlayerSingleCardGame.getBettingLimit());
		
		assertEquals(BetRound.RIVER,twoPlayerSingleCardGame.getBettingRound());
		assertEquals("No cards turned",twoPlayerSingleCardGame.getBoard().toString());
		assertEquals(PokerGameType.SINGLE_CARD,twoPlayerSingleCardGame.getGameType());
		
		assertEquals(2,twoPlayerSingleCardGame.getHands().size());
		assertTrue(twoPlayerSingleCardGame.getHands().get(0) instanceof Hand);
		assertTrue(twoPlayerSingleCardGame.getHands().get(1) instanceof Hand);

		
////		assertNull(twoPlayerSingleCardGame.getNodeId());
////		assertNull(twoPlayerSingleCardGame.getPayOffs());
		
		assertEquals(2,twoPlayerSingleCardGame.getPlayers().length);
		assertEquals(0,twoPlayerSingleCardGame.getPlayers()[0]);
		assertEquals(1,twoPlayerSingleCardGame.getPlayers()[1]);
		
		assertTrue(twoPlayerSingleCardGame.getPot() instanceof Pot);
		assertEquals(3,twoPlayerSingleCardGame.getPot().getTotalPotSize());
		assertEquals(3,twoPlayerSingleCardGame.getPot().getRoundPotSize());
		assertEquals(1,twoPlayerSingleCardGame.getPot().getPlayersContributionToPot(0));
		assertEquals(2,twoPlayerSingleCardGame.getPot().getPlayersContributionToPot(1));
		
		assertEquals(0,twoPlayerSingleCardGame.getRaiseCount());
		assertEquals(3,twoPlayerSingleCardGame.getRaisesAllowedPerBettingRound());
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		
		assertFalse(twoPlayerSingleCardGame.isAtTerminalNode());
		
	}
	
	@Test
	public final void raisesAllowedTest() {
		PokerGame twoPlayerSingleCardGame =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		
		twoPlayerSingleCardGame.startGame();
		
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(1,new RaiseAction(1));
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());
		
		twoPlayerSingleCardGame =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 2);
		
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());
		
		twoPlayerSingleCardGame =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 2);
		
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		assertTrue(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());
		
		twoPlayerSingleCardGame =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 0);
		assertFalse(twoPlayerSingleCardGame.raisesAllowed());
	}
	
	
	@Test
	public final void actionIsTerminalCallForTheBettingRoundTest() {
		PokerGame twoPlayerSingleCardGame =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		
		twoPlayerSingleCardGame.startGame();
		
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		twoPlayerSingleCardGame.performAction(0, CallAction.getInstance());
		assertTrue(twoPlayerSingleCardGame.isAtTerminalNode());
		
		twoPlayerSingleCardGame.startGame();
		
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		twoPlayerSingleCardGame.performAction(0, FoldAction.getInstance());
		assertTrue(twoPlayerSingleCardGame.isAtTerminalNode());
		
		
		PokerGame twoPlayerTwoCardGame =	new TwoPlayerTwoCardGame(BettingLimit.LIMIT, 3);
		
		twoPlayerTwoCardGame.startGame();
		
		twoPlayerTwoCardGame.performAction(0,new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(0, CallAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(0, DealAction.getInstance());
		assertFalse(twoPlayerTwoCardGame.isAtTerminalNode());
		twoPlayerTwoCardGame.performAction(0,new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(0, CallAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());
		
		
		twoPlayerTwoCardGame.startGame();
		
		twoPlayerTwoCardGame.performAction(0,new RaiseAction(1));
		twoPlayerTwoCardGame.performAction(0, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());
		
		
twoPlayerTwoCardGame.startGame();
		
		twoPlayerTwoCardGame.performAction(0,DealAction.getInstance());
		twoPlayerTwoCardGame.performAction(0, FoldAction.getInstance());
		assertTrue(twoPlayerTwoCardGame.isAtTerminalNode());
	}
	
	@Test
	public final void getPlayerToActTest() {
		PokerGame twoPlayerSingleCardGame =	new TwoPlayerSingleCardGame(BettingLimit.LIMIT, 3);
		
		twoPlayerSingleCardGame.startGame();
		assertEquals(0,twoPlayerSingleCardGame.getPlayerToAct());
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));

		
		assertEquals(1,twoPlayerSingleCardGame.getPlayerToAct());
		twoPlayerSingleCardGame.performAction(1,new RaiseAction(1));
		
		assertEquals(0,twoPlayerSingleCardGame.getPlayerToAct());
		twoPlayerSingleCardGame.performAction(0,new RaiseAction(1));
		
		
		assertEquals(1,twoPlayerSingleCardGame.getPlayerToAct());
	}
	
}
