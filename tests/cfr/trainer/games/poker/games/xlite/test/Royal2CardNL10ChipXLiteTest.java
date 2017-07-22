package cfr.trainer.games.poker.games.xlite.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cfr.trainer.games.poker.games.xlite.PokerXLiteBase;
import cfr.trainer.games.poker.games.xlite.Royal2CardNL10ChipXLite;

public class Royal2CardNL10ChipXLiteTest {

	PokerXLiteBase twoCardXLiteGame;
	int[] hands = { 0, 1 };
	int[] board = { 2 };
	int[][] cardsDealt = { hands, board };

	@Before
	public final void init() {

		twoCardXLiteGame = new Royal2CardNL10ChipXLite();

	}

	@Test
	public final void constructorTest() {
		int[][] chanceCombos = twoCardXLiteGame.getListOfValidChanceCombinations();

		assertEquals(6840, chanceCombos.length);

		int[] firstCombo = chanceCombos[0];
		int[] lastCombo = chanceCombos[6839];

		assertEquals(3, firstCombo.length);
		assertEquals(3, lastCombo.length);

		assertEquals(8, firstCombo[0]);
		assertEquals(9, firstCombo[1]);
		assertEquals(10, firstCombo[2]);
		
		assertEquals(51, lastCombo[0]);
		assertEquals(50, lastCombo[1]);
		assertEquals(49, lastCombo[2]);

	}

	@Test
	public final void dealTest() {
		twoCardXLiteGame.setActionsTaken("D", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());
		assertEquals(10, twoCardXLiteGame.getAvailableActions().length);
		assertEquals('F', twoCardXLiteGame.getAvailableActions()[0]);
		assertEquals('C', twoCardXLiteGame.getAvailableActions()[1]);
		assertEquals('1', twoCardXLiteGame.getAvailableActions()[2]);
		assertEquals('2', twoCardXLiteGame.getAvailableActions()[3]);
		assertEquals('3', twoCardXLiteGame.getAvailableActions()[4]);
		assertEquals('4', twoCardXLiteGame.getAvailableActions()[5]);
		assertEquals('5', twoCardXLiteGame.getAvailableActions()[6]);
		assertEquals('6', twoCardXLiteGame.getAvailableActions()[7]);
		assertEquals('7', twoCardXLiteGame.getAvailableActions()[8]);
		assertEquals('8', twoCardXLiteGame.getAvailableActions()[9]);

		// NodeId
		assertEquals("[S,S,0]D", twoCardXLiteGame.getNodeId());
	}

	@Test
	public final void dealFoldTest() {
		twoCardXLiteGame.setActionsTaken("DF", cardsDealt);
		
		// Player
		assertEquals(1, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		
		//NodeId
		assertEquals("[S,S,1]DF",twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(-1,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(1,twoCardXLiteGame.getPayoffs()[1]);
	}

	@Test
	public final void dealCallTest() {
		twoCardXLiteGame.setActionsTaken("DC", cardsDealt);

		// Player
		assertEquals(1, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());
		assertEquals(10, twoCardXLiteGame.getAvailableActions().length);
		assertEquals('F', twoCardXLiteGame.getAvailableActions()[0]);
		assertEquals('C', twoCardXLiteGame.getAvailableActions()[1]);
		assertEquals('1', twoCardXLiteGame.getAvailableActions()[2]);
		assertEquals('2', twoCardXLiteGame.getAvailableActions()[3]);
		assertEquals('3', twoCardXLiteGame.getAvailableActions()[4]);
		assertEquals('4', twoCardXLiteGame.getAvailableActions()[5]);
		assertEquals('5', twoCardXLiteGame.getAvailableActions()[6]);
		assertEquals('6', twoCardXLiteGame.getAvailableActions()[7]);
		assertEquals('7', twoCardXLiteGame.getAvailableActions()[8]);
		assertEquals('8', twoCardXLiteGame.getAvailableActions()[9]);

		// NodeId
		assertEquals("[S,S,1]DC", twoCardXLiteGame.getNodeId());
	}
	
	@Test
	public final void dealCallCallTest() {
		twoCardXLiteGame.setActionsTaken("DCC", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertTrue(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());


		// NodeId
		assertEquals("[S,S,0]DCC", twoCardXLiteGame.getNodeId());
	}
	

	@Test
	public final void dealRaise1Test() {
		twoCardXLiteGame.setActionsTaken("D1", cardsDealt);

		// Player
		assertEquals(1, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());
		assertEquals(9, twoCardXLiteGame.getAvailableActions().length);
		assertEquals('F', twoCardXLiteGame.getAvailableActions()[0]);
		assertEquals('C', twoCardXLiteGame.getAvailableActions()[1]);
		assertEquals('1', twoCardXLiteGame.getAvailableActions()[2]);
		assertEquals('2', twoCardXLiteGame.getAvailableActions()[3]);
		assertEquals('3', twoCardXLiteGame.getAvailableActions()[4]);
		assertEquals('4', twoCardXLiteGame.getAvailableActions()[5]);
		assertEquals('5', twoCardXLiteGame.getAvailableActions()[6]);
		assertEquals('6', twoCardXLiteGame.getAvailableActions()[7]);
		assertEquals('7', twoCardXLiteGame.getAvailableActions()[8]);

		// NodeId
		assertEquals("[S,S,1]D1", twoCardXLiteGame.getNodeId());
	}
	
	@Test
	public final void dealRaise1CallTest() {
		twoCardXLiteGame.setActionsTaken("D1C", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertTrue(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());


		// NodeId
		assertEquals("[S,S,0]D1C", twoCardXLiteGame.getNodeId());
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}

	@Test
	public final void dealRaise5Test() {
		twoCardXLiteGame.setActionsTaken("D5", cardsDealt);

		// Player
		assertEquals(1, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());
		assertEquals(5, twoCardXLiteGame.getAvailableActions().length);
		assertEquals('F', twoCardXLiteGame.getAvailableActions()[0]);
		assertEquals('C', twoCardXLiteGame.getAvailableActions()[1]);
		assertEquals('1', twoCardXLiteGame.getAvailableActions()[2]);
		assertEquals('2', twoCardXLiteGame.getAvailableActions()[3]);
		assertEquals('3', twoCardXLiteGame.getAvailableActions()[4]);

		// NodeId
		assertEquals("[S,S,1]D5", twoCardXLiteGame.getNodeId());
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}



	@Test
	public final void dealRaise5CallTest() {
		twoCardXLiteGame.setActionsTaken("D5C", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertTrue(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());


		// NodeId
		assertEquals("[S,S,0]D5C", twoCardXLiteGame.getNodeId());
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}
	
	@Test
	public final void dealRaiseAllinTest() {
		twoCardXLiteGame.setActionsTaken("D8", cardsDealt);

		// Player
		assertEquals(1, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());
		assertEquals(2, twoCardXLiteGame.getAvailableActions().length);
		assertEquals('F', twoCardXLiteGame.getAvailableActions()[0]);
		assertEquals('C', twoCardXLiteGame.getAvailableActions()[1]);
		

		// NodeId
		assertEquals("[S,S,1]D8", twoCardXLiteGame.getNodeId());
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}





	@Test
	public final void dealRaiseAllInCallTest() {
		twoCardXLiteGame.setActionsTaken("D8C", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertTrue(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,0]D8C", twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(-10,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(10,twoCardXLiteGame.getPayoffs()[1]);
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}

	@Test
	public final void dealCallFoldTest() {
		twoCardXLiteGame.setActionsTaken("DCF", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,0]DCF", twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(2,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(-2,twoCardXLiteGame.getPayoffs()[1]);
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}

	@Test
	public final void dealRaise1FoldTest() {
		twoCardXLiteGame.setActionsTaken("D1F", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,0]D1F", twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(2,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(-2,twoCardXLiteGame.getPayoffs()[1]);
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}

	@Test
	public final void dealRaise5FoldTest() {
		twoCardXLiteGame.setActionsTaken("D5F", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,0]D5F", twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(2,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(-2,twoCardXLiteGame.getPayoffs()[1]);
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}

	@Test
	public final void dealRaiseAllinFoldTest() {
		twoCardXLiteGame.setActionsTaken("D8F", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,0]D8F", twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(2,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(-2,twoCardXLiteGame.getPayoffs()[1]);
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}

	@Test
	public final void dealCallRaise2Test() {
		twoCardXLiteGame.setActionsTaken("D5", cardsDealt);

		// Player
		assertEquals(1, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());
		assertEquals(5, twoCardXLiteGame.getAvailableActions().length);
		assertEquals('F', twoCardXLiteGame.getAvailableActions()[0]);
		assertEquals('C', twoCardXLiteGame.getAvailableActions()[1]);
		assertEquals('1', twoCardXLiteGame.getAvailableActions()[2]);
		assertEquals('2', twoCardXLiteGame.getAvailableActions()[3]);
		assertEquals('3', twoCardXLiteGame.getAvailableActions()[4]);

		// NodeId
		assertEquals("[S,S,1]D5", twoCardXLiteGame.getNodeId());
		
		//turned cards
		assertEquals(0, twoCardXLiteGame.getTurnedCards().length);
	}

	@Test
	public final void dealRaise1Raise2Test() {

	}

	@Test
	public final void dealRaise5Raise2Test() {

	}

	@Test
	public final void dealRaise1RaiseAllinTest() {

	}

	@Test
	public final void dealCallCallDealRaise2Test() {
		twoCardXLiteGame.setActionsTaken("DCCD2", cardsDealt);

		// Player
		assertEquals(1, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());
		assertEquals(8, twoCardXLiteGame.getAvailableActions().length);
		assertEquals('F', twoCardXLiteGame.getAvailableActions()[0]);
		assertEquals('C', twoCardXLiteGame.getAvailableActions()[1]);
		assertEquals('1', twoCardXLiteGame.getAvailableActions()[2]);
		assertEquals('2', twoCardXLiteGame.getAvailableActions()[3]);
		assertEquals('3', twoCardXLiteGame.getAvailableActions()[4]);
		assertEquals('4', twoCardXLiteGame.getAvailableActions()[5]);
		assertEquals('5', twoCardXLiteGame.getAvailableActions()[6]);
		assertEquals('6', twoCardXLiteGame.getAvailableActions()[7]);

		// NodeId
		assertEquals("[S,S,1,2]DCCD2", twoCardXLiteGame.getNodeId());
		
		//turned cards
		assertEquals(1, twoCardXLiteGame.getTurnedCards().length);
	}

	@Test
	public final void dealRaise1CallDealRaise2Test() {

	}

	@Test
	public final void dealRaise5CallDealRaise2Test() {

	}

	@Test
	public final void dealRaise2CallDealRaiseAllinTest() {

	}

	@Test
	public final void dealRaise2CallDealRaise1Test() {

	}

	@Test
	public final void dealRaise2CallDealRaise1FoldTest() {

	}

	@Test
	public final void dealRaise2CallDealRaise1Raise1Raise2Test() {

	}
	
	@Test
	public final void dealRaise2CallDealRaise1Raise1Raise2CallTest() {
		twoCardXLiteGame.setActionsTaken("D2CD112C", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,0,2]D2CD112C", twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(-8,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(8,twoCardXLiteGame.getPayoffs()[1]);
		
		//turned cards
		assertEquals(1, twoCardXLiteGame.getTurnedCards().length);
	}
	
	@Test
	public final void dealRaise2CallDealRaise1Raise1CallTest() {
		twoCardXLiteGame.setActionsTaken("D2CD11C", cardsDealt);

		// Player
		assertEquals(1, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,1,2]D2CD11C", twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(-6,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(6,twoCardXLiteGame.getPayoffs()[1]);
		
		//turned cards
		assertEquals(1, twoCardXLiteGame.getTurnedCards().length);
	}
	
	@Test
	public final void dealRaise2CallDealRaise1Raise1FoldTest() {
		twoCardXLiteGame.setActionsTaken("D2CD11F", cardsDealt);

		// Player
		assertEquals(1, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,1,2]D2CD11F", twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(-5,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(5,twoCardXLiteGame.getPayoffs()[1]);
		
		//turned cards
		assertEquals(1, twoCardXLiteGame.getTurnedCards().length);
	}
	
	@Test
	public final void dealRaise2CallDealRaise1Raise1Raise1FoldTest() {
		twoCardXLiteGame.setActionsTaken("D2CD111F", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertTrue(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,0,2]D2CD111F", twoCardXLiteGame.getNodeId());
		
		//PayOffs
		assertEquals(6,twoCardXLiteGame.getPayoffs()[0]);
		assertEquals(-6,twoCardXLiteGame.getPayoffs()[1]);
		
		//turned cards
		assertEquals(1, twoCardXLiteGame.getTurnedCards().length);
	}
	
	@Test
	public final void afterDealTest() {
		twoCardXLiteGame.setActionsTaken("DC112CD", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,0,2]DC112CD", twoCardXLiteGame.getNodeId());
		
		//turned cards
		assertEquals(1, twoCardXLiteGame.getTurnedCards().length);
		
		
		//
		// A different action sequence
		//
		twoCardXLiteGame.setActionsTaken("DC12CD", cardsDealt);

		// Player
		assertEquals(0, twoCardXLiteGame.getPlayerToAct());

		// Actions
		assertFalse(twoCardXLiteGame.isAtChanceNode());
		assertFalse(twoCardXLiteGame.isAtTerminalNode());

		// NodeId
		assertEquals("[S,S,0,2]DC12CD", twoCardXLiteGame.getNodeId());
		
		//turned cards
		assertEquals(1, twoCardXLiteGame.getTurnedCards().length);
	}
}
