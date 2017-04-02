package cfr.trainer.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cfr.poker.*;
import cfr.trainer.CardHistoryBuilder;

public class RhodeIslandCardHistoryBuilderTest {

	CardHistoryBuilder rhodeIslandCardHistoryBuilder = null;
	PokerGameType PokerGameType = PokerGameType.RHODE_ISLAND;

	final Card twoOfSpades = new Card(0);
	final Card threeOfSpades = new Card(1);
	final Card fourOfSpades = new Card(2);
	final Card twoOfHearts = new Card(13);
	final Card threeOfHearts = new Card(14);
	final Card fourOfHearts = new Card(15);

	HandSingleCard hand2OfSpades;
	Board board3OfSpades = new Board(PokerGameType.RHODE_ISLAND);
	Board board3OfHearts = new Board(PokerGameType.RHODE_ISLAND);
	Board board3and4OfSpades = new Board(PokerGameType.RHODE_ISLAND);
	Board board3and4OfHearts = new Board(PokerGameType.RHODE_ISLAND);
	Board board3OfSpadesAnd4OfHearts = new Board(PokerGameType.RHODE_ISLAND);
	Board board4OfHeartsAnd3OfSpades = new Board(PokerGameType.RHODE_ISLAND);

	@Before
	public void prepare() {

		hand2OfSpades = new HandSingleCard(twoOfSpades);

		board3OfSpades.addTurnedCard(threeOfSpades);

		board3OfHearts.addTurnedCard(threeOfHearts);

		board3and4OfSpades.addTurnedCard(threeOfSpades);
		board3and4OfSpades.addTurnedCard(fourOfSpades);

		board3and4OfHearts.addTurnedCard(threeOfHearts);
		board3and4OfHearts.addTurnedCard(fourOfHearts);

		board3OfSpadesAnd4OfHearts.addTurnedCard(threeOfSpades);
		board3OfSpadesAnd4OfHearts.addTurnedCard(fourOfHearts);

		board4OfHeartsAnd3OfSpades.addTurnedCard(fourOfHearts);
		board4OfHeartsAnd3OfSpades.addTurnedCard(threeOfSpades);
	}

	@Test
	public void print_out_cards() {
		System.out.println("twoOfSpades : "+ twoOfSpades);
		System.out.println("threeOfSpades : "+ threeOfSpades);
		System.out.println("fourOfSpades : "+ fourOfSpades);
		System.out.println("twoOfHearts : "+ twoOfHearts);
		System.out.println("threeOfHearts : "+ threeOfHearts);
		System.out.println("fourOfHearts : "+ fourOfHearts);
	}
	
	@Test
	public void Hand_on_its_own() {
		CardHistoryBuilder hand2OfSpadesHistoryBuilder = new CardHistoryBuilder(hand2OfSpades);
		assertTrue(hand2OfSpadesHistoryBuilder.isPlayerSuitedWithTurn());
		assertTrue(hand2OfSpadesHistoryBuilder.isRiverSuitedWithTurn());
		assertEquals(1, hand2OfSpadesHistoryBuilder.getCardRanks().size());
		assertEquals(CardHeight.DEUCE, hand2OfSpadesHistoryBuilder.getCardRanks().get(0));
		assertEquals("[S, S, DEUCE]", hand2OfSpadesHistoryBuilder.toString());
	}

	@Test
	public void Hand_with_suited_turn() {
		CardHistoryBuilder hand2OfSpadesHistoryBuilder = new CardHistoryBuilder(hand2OfSpades,
				board3OfSpades);
		assertTrue(hand2OfSpadesHistoryBuilder.isPlayerSuitedWithTurn());
		assertTrue(hand2OfSpadesHistoryBuilder.isRiverSuitedWithTurn());
		assertEquals(2, hand2OfSpadesHistoryBuilder.getCardRanks().size());
		assertEquals(CardHeight.DEUCE, hand2OfSpadesHistoryBuilder.getCardRanks().get(0));
		assertEquals(CardHeight.THREE, hand2OfSpadesHistoryBuilder.getCardRanks().get(1));
		assertEquals("[S, S, DEUCE,THREE]", hand2OfSpadesHistoryBuilder.toString());
	}

	@Test
	public void Hand_with_unsuited_turn() {
		CardHistoryBuilder cardHistoryBuilder = new CardHistoryBuilder(hand2OfSpades,
				board3OfHearts);
		assertFalse(cardHistoryBuilder.isPlayerSuitedWithTurn());
		assertTrue(cardHistoryBuilder.isRiverSuitedWithTurn());
		assertEquals(2, cardHistoryBuilder.getCardRanks().size());
		assertEquals(CardHeight.DEUCE, cardHistoryBuilder.getCardRanks().get(0));
		assertEquals(CardHeight.THREE, cardHistoryBuilder.getCardRanks().get(1));
		assertEquals("[O, S, DEUCE,THREE]", cardHistoryBuilder.toString());
	}

	@Test
	public void Hand_with_flush() {
		CardHistoryBuilder cardHistoryBuilder = new CardHistoryBuilder(hand2OfSpades,
				board3and4OfSpades);
		assertTrue(cardHistoryBuilder.isPlayerSuitedWithTurn());
		assertTrue(cardHistoryBuilder.isRiverSuitedWithTurn());
		assertEquals(3, cardHistoryBuilder.getCardRanks().size());
		assertEquals(CardHeight.DEUCE, cardHistoryBuilder.getCardRanks().get(0));
		assertEquals(CardHeight.THREE, cardHistoryBuilder.getCardRanks().get(1));
		assertEquals(CardHeight.FOUR, cardHistoryBuilder.getCardRanks().get(2));
		assertEquals("[S, S, DEUCE,THREE,FOUR]", cardHistoryBuilder.toString());
	}

	@Test
	public void Hand_unsuited_with_suited_board() {
		CardHistoryBuilder cardHistoryBuilder = new CardHistoryBuilder(hand2OfSpades,
				board3and4OfHearts);
		assertFalse(cardHistoryBuilder.isPlayerSuitedWithTurn());
		assertTrue(cardHistoryBuilder.isRiverSuitedWithTurn());
		assertEquals(3, cardHistoryBuilder.getCardRanks().size());
		assertEquals(CardHeight.DEUCE, cardHistoryBuilder.getCardRanks().get(0));
		assertEquals(CardHeight.THREE, cardHistoryBuilder.getCardRanks().get(1));
		assertEquals(CardHeight.FOUR, cardHistoryBuilder.getCardRanks().get(2));
		assertEquals("[O, S, DEUCE,THREE,FOUR]", cardHistoryBuilder.toString());
	}

	@Test
	public void Hand_suited_with_first_card_but_unsuited_board() {
		CardHistoryBuilder cardHistoryBuilder = new CardHistoryBuilder(hand2OfSpades,
				board3OfSpadesAnd4OfHearts);
		assertTrue(cardHistoryBuilder.isPlayerSuitedWithTurn());
		assertFalse(cardHistoryBuilder.isRiverSuitedWithTurn());
		assertEquals(3, cardHistoryBuilder.getCardRanks().size());
		assertEquals(CardHeight.DEUCE, cardHistoryBuilder.getCardRanks().get(0));
		assertEquals(CardHeight.THREE, cardHistoryBuilder.getCardRanks().get(1));
		assertEquals(CardHeight.FOUR, cardHistoryBuilder.getCardRanks().get(2));
		assertEquals("[S, O, DEUCE,THREE,FOUR]", cardHistoryBuilder.toString());
	}

	@Test
	public void Hand_suited_with_river_card_but_not_turn() {
		CardHistoryBuilder cardHistoryBuilder = new CardHistoryBuilder(hand2OfSpades,
				board4OfHeartsAnd3OfSpades);
		assertFalse(cardHistoryBuilder.isPlayerSuitedWithTurn());
		assertFalse(cardHistoryBuilder.isRiverSuitedWithTurn());
		assertEquals(3, cardHistoryBuilder.getCardRanks().size());
		assertEquals(CardHeight.DEUCE, cardHistoryBuilder.getCardRanks().get(0));
		assertEquals(CardHeight.FOUR, cardHistoryBuilder.getCardRanks().get(1));
		assertEquals(CardHeight.THREE, cardHistoryBuilder.getCardRanks().get(2));
		assertEquals("[O, O, DEUCE,FOUR,THREE]", cardHistoryBuilder.toString());
	}
}
