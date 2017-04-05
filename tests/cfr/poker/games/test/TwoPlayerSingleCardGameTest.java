package cfr.poker.games.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cfr.poker.*;
import cfr.poker.actions.DealAction;
import cfr.poker.games.PokerGame;
import cfr.poker.games.TwoPlayerSingleCardGame;

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
	
}
