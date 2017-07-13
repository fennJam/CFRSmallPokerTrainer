package cfr.trainer.games.poker.games.xlite.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cfr.trainer.games.poker.games.xlite.PokerXLiteBase;
import cfr.trainer.games.poker.games.xlite.RoyalRhodeIslandNL10ChipXLite;

public class PokerXLiteBaseTest {

	PokerXLiteBase pokerXLiteBase;
	int[] hands = { 0, 1 };
	int[] board = { 2, 3 };
	int[][] cardsDealt = { hands, board };

	@Before
	public final void init() {

		pokerXLiteBase = new RoyalRhodeIslandNL10ChipXLite();

	}

	@Test
	public final void testAfterDeal() {
		pokerXLiteBase.setActionsTaken("D", cardsDealt);
		// Player
		assertEquals(0, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertFalse(pokerXLiteBase.isAtChanceNode());
		assertFalse(pokerXLiteBase.isAtTerminalNode());
		assertEquals(10, pokerXLiteBase.getAvailableActions().length);
		assertEquals('F', pokerXLiteBase.getAvailableActions()[0]);
		assertEquals('C', pokerXLiteBase.getAvailableActions()[1]);
		assertEquals('1', pokerXLiteBase.getAvailableActions()[2]);
		assertEquals('2', pokerXLiteBase.getAvailableActions()[3]);
		assertEquals('3', pokerXLiteBase.getAvailableActions()[4]);
		assertEquals('4', pokerXLiteBase.getAvailableActions()[5]);
		assertEquals('5', pokerXLiteBase.getAvailableActions()[6]);
		assertEquals('6', pokerXLiteBase.getAvailableActions()[7]);
		assertEquals('7', pokerXLiteBase.getAvailableActions()[8]);
		assertEquals('8', pokerXLiteBase.getAvailableActions()[9]);
		
		//NodeId
		assertEquals("[S,S,0]D",pokerXLiteBase.getNodeId());

	}

	@Test
	public final void testAfterDealRaise() {
		pokerXLiteBase.setActionsTaken("D2", cardsDealt);

		// Player
		assertEquals(1, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertFalse(pokerXLiteBase.isAtChanceNode());
		assertFalse(pokerXLiteBase.isAtTerminalNode());
		assertEquals(8, pokerXLiteBase.getAvailableActions().length);
		assertEquals('F', pokerXLiteBase.getAvailableActions()[0]);
		assertEquals('C', pokerXLiteBase.getAvailableActions()[1]);
		assertEquals('1', pokerXLiteBase.getAvailableActions()[2]);
		assertEquals('2', pokerXLiteBase.getAvailableActions()[3]);
		assertEquals('3', pokerXLiteBase.getAvailableActions()[4]);
		assertEquals('4', pokerXLiteBase.getAvailableActions()[5]);
		assertEquals('5', pokerXLiteBase.getAvailableActions()[6]);
		assertEquals('6', pokerXLiteBase.getAvailableActions()[7]);
		
		//NodeId
		assertEquals("[S,S,1]D2",pokerXLiteBase.getNodeId());
	}

	@Test
	public final void testAfterDealRaiseRaise() {
		pokerXLiteBase.setActionsTaken("D23", cardsDealt);

		// Player
		assertEquals(0, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertFalse(pokerXLiteBase.isAtChanceNode());
		assertFalse(pokerXLiteBase.isAtTerminalNode());
		assertEquals(5, pokerXLiteBase.getAvailableActions().length);
		assertEquals('F', pokerXLiteBase.getAvailableActions()[0]);
		assertEquals('C', pokerXLiteBase.getAvailableActions()[1]);
		assertEquals('1', pokerXLiteBase.getAvailableActions()[2]);
		assertEquals('2', pokerXLiteBase.getAvailableActions()[3]);
		assertEquals('3', pokerXLiteBase.getAvailableActions()[4]);
		
		//NodeId
		assertEquals("[S,S,0]D23",pokerXLiteBase.getNodeId());
	}

	@Test
	public final void testAfterDealRaiseRaiseRaise() {
		pokerXLiteBase.setActionsTaken("D231", cardsDealt);

		// Player
		assertEquals(1, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertFalse(pokerXLiteBase.isAtChanceNode());
		assertFalse(pokerXLiteBase.isAtTerminalNode());
		assertEquals(2, pokerXLiteBase.getAvailableActions().length);
		assertEquals('F', pokerXLiteBase.getAvailableActions()[0]);
		assertEquals('C', pokerXLiteBase.getAvailableActions()[1]);
		
		//NodeId
		assertEquals("[S,S,1]D231",pokerXLiteBase.getNodeId());
	}

	@Test
	public final void testAfterDealRaiseRaiseRaiseCall() {
		pokerXLiteBase.setActionsTaken("D231C", cardsDealt);

		// Player
		assertEquals(0, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertTrue(pokerXLiteBase.isAtChanceNode());
		assertFalse(pokerXLiteBase.isAtTerminalNode());
		
		//NodeId
		assertEquals("[S,S,0]D231C",pokerXLiteBase.getNodeId());

	}

	@Test
	public final void testAfterDealRaiseRaiseRaiseCallDeal() {
		pokerXLiteBase.setActionsTaken("D231CD", cardsDealt);

		// Player
		assertEquals(0, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertFalse(pokerXLiteBase.isAtChanceNode());
		assertFalse(pokerXLiteBase.isAtTerminalNode());
		assertEquals(4, pokerXLiteBase.getAvailableActions().length);
		assertEquals('F', pokerXLiteBase.getAvailableActions()[0]);
		assertEquals('C', pokerXLiteBase.getAvailableActions()[1]);
		assertEquals('1', pokerXLiteBase.getAvailableActions()[2]);
		assertEquals('2', pokerXLiteBase.getAvailableActions()[3]);
		
		//NodeId
		assertEquals("[S,S,0,2]D231CD",pokerXLiteBase.getNodeId());

	}

	@Test
	public final void testAfterDealRaiseRaiseRaiseCallDealRaise() {
		pokerXLiteBase.setActionsTaken("D111CD2", cardsDealt);

		// Player
		assertEquals(1, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertFalse(pokerXLiteBase.isAtChanceNode());
		assertFalse(pokerXLiteBase.isAtTerminalNode());
		assertEquals(5, pokerXLiteBase.getAvailableActions().length);
		assertEquals('F', pokerXLiteBase.getAvailableActions()[0]);
		assertEquals('C', pokerXLiteBase.getAvailableActions()[1]);
		assertEquals('1', pokerXLiteBase.getAvailableActions()[2]);
		assertEquals('2', pokerXLiteBase.getAvailableActions()[3]);
		assertEquals('3', pokerXLiteBase.getAvailableActions()[4]);
		
		//NodeId
		assertEquals("[S,S,1,2]D111CD2",pokerXLiteBase.getNodeId());
	}

	@Test
	public final void testAfterDealRaiseRaiseFold() {
		pokerXLiteBase.setActionsTaken("D11F", cardsDealt);

		// Player
		assertEquals(1, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertFalse(pokerXLiteBase.isAtChanceNode());
		assertTrue(pokerXLiteBase.isAtTerminalNode());
		// Payoffs
		assertEquals(-3, pokerXLiteBase.getPayoffs()[0]);
		assertEquals(+3, pokerXLiteBase.getPayoffs()[1]);
		
		//NodeId
		assertEquals("[S,S,1]D11F",pokerXLiteBase.getNodeId());
	}


	@Test
	public final void testAfterDealAllInCall() {
		pokerXLiteBase.setActionsTaken("D116C", cardsDealt);

		// Player
		assertEquals(0, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertTrue(pokerXLiteBase.isAtChanceNode());
		assertTrue(pokerXLiteBase.isAtTerminalNode());
		
		//NodeId
		assertEquals("[S,S,0]D116C",pokerXLiteBase.getNodeId());
		
		// Payoffs
		assertEquals(-10, pokerXLiteBase.getPayoffs()[0]);
		assertEquals(+10, pokerXLiteBase.getPayoffs()[1]);
	}
	
	@Test
	public final void testAfterDealCall() {
		pokerXLiteBase.setActionsTaken("DC", cardsDealt);

		// Player
		assertEquals(1, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertFalse(pokerXLiteBase.isAtChanceNode());
		assertFalse(pokerXLiteBase.isAtTerminalNode());
		assertEquals(10, pokerXLiteBase.getAvailableActions().length);
		assertEquals('F', pokerXLiteBase.getAvailableActions()[0]);
		assertEquals('C', pokerXLiteBase.getAvailableActions()[1]);
		assertEquals('1', pokerXLiteBase.getAvailableActions()[2]);
		assertEquals('2', pokerXLiteBase.getAvailableActions()[3]);
		assertEquals('3', pokerXLiteBase.getAvailableActions()[4]);
		assertEquals('4', pokerXLiteBase.getAvailableActions()[5]);
		assertEquals('5', pokerXLiteBase.getAvailableActions()[6]);
		assertEquals('6', pokerXLiteBase.getAvailableActions()[7]);
		assertEquals('7', pokerXLiteBase.getAvailableActions()[8]);
		assertEquals('8', pokerXLiteBase.getAvailableActions()[9]);
		
		//NodeId
		assertEquals("[S,S,1]DC",pokerXLiteBase.getNodeId());
	}

	@Test
	public final void testAfterDealCallCheck() {
		pokerXLiteBase.setActionsTaken("DCC", cardsDealt);

		// Player
		assertEquals(0, pokerXLiteBase.getPlayerToAct());

		// Actions
		assertTrue(pokerXLiteBase.isAtChanceNode());
		assertFalse(pokerXLiteBase.isAtTerminalNode());
		
		//NodeId
		assertEquals("[S,S,0]DCC",pokerXLiteBase.getNodeId());
	}
	
	@Test
	public final void getTurnedCardsTest() {
		pokerXLiteBase.setActionsTaken("D111", cardsDealt);
		assertEquals(0,pokerXLiteBase.getTurnedCards().length);
		
		pokerXLiteBase.setActionsTaken("D111CD1", cardsDealt);
		assertEquals(1,pokerXLiteBase.getTurnedCards().length);
		assertEquals(2,pokerXLiteBase.getTurnedCards()[0]);
		
		pokerXLiteBase.setActionsTaken("D111CD1CD1", cardsDealt);
		assertEquals(2,pokerXLiteBase.getTurnedCards().length);
		assertEquals(2,pokerXLiteBase.getTurnedCards()[0]);
		assertEquals(3,pokerXLiteBase.getTurnedCards()[1]);
	}
		
}
