package cfr.poker.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cfr.trainer.games.poker.Board;
import cfr.trainer.games.poker.Card;
import cfr.trainer.games.poker.PokerGameType;
import cfr.trainer.games.poker.decks.Deck;
import cfr.trainer.games.poker.decks.DeckStandardShuffled;
import cfr.trainer.games.poker.decks.DeckStandardUnShuffled;

public class BoardTest {



	@Test
	public void SingleCardConstructorTest() {
		Deck deck = new DeckStandardShuffled();
		Card expectedCard = deck.peekAtNextCard();
		Board board = new Board(PokerGameType.SINGLE_CARD,deck);
		
		assertEquals(0,board.getCards().length);
		
		assertEquals(0,board.getIsVisible().length);
		
		assertEquals(PokerGameType.SINGLE_CARD,board.getPokerGameType());
		
		assertEquals(0,board.getTurnedCards().size()); board.getTurnedCards();
		
		assertEquals(0,board.getTurnedSuits().size());
		
		assertTrue(board.isSuited());
		
		assertEquals(false,board.addTurnedCard(expectedCard));
//		check that a card was not added to the board;
		assertEquals(0,board.getCards().length);
		
		
	}
	
	@Test
	public void TwoCardConstructorTest() {
		Deck deck = new DeckStandardShuffled();
		Card expectedCard = deck.peekAtNextCard();
		Board board = new Board(PokerGameType.TWO_CARD,deck);
		
		assertEquals(1,board.getCards().length);
		
		assertEquals(expectedCard.toString(),board.getCards()[0].toString());
		
		assertEquals(1,board.getIsVisible().length);
		
		assertArrayEquals(new Boolean[]{false},board.getIsVisible());
		
		assertEquals(PokerGameType.TWO_CARD,board.getPokerGameType());
		
		assertEquals(0,board.getTurnedCards().size());
		
		assertEquals(0,board.getTurnedSuits().size());
		
		assertTrue(board.isSuited());
		
		assertEquals(false,board.addTurnedCard(expectedCard));
//		check that a card was not added to the board;
		assertEquals(1,board.getCards().length);
		
		
	}
	
	@Test
	public void RhodeIslandConstructorTest() {
		Deck deck = new DeckStandardUnShuffled();
		Card expectedCard = deck.peekAtNextCard();
		Board board = new Board(PokerGameType.RHODE_ISLAND,deck);
		
		assertEquals(2,board.getCards().length);
		
		assertEquals(2,board.getIsVisible().length);
		
		assertArrayEquals(new Boolean[]{false,false},board.getIsVisible());
		
		assertEquals(PokerGameType.RHODE_ISLAND,board.getPokerGameType());
		
		assertEquals(0,board.getTurnedCards().size());
		
		assertEquals(0,board.getTurnedSuits().size());
		
		assertTrue(board.isSuited());
		
		assertEquals(false,board.addTurnedCard(expectedCard));
//		check that a card was not added to the board;
		assertEquals(2,board.getCards().length);
		
		
	}

	@Test
	public void TurnNextCardTest() {
		Deck deck = new DeckStandardUnShuffled();
		Card expectedCard = deck.peekAtNextCard();
		Board board = new Board(PokerGameType.RHODE_ISLAND,deck);
		
		assertEquals(2,board.getCards().length);
		
		assertEquals(2,board.getIsVisible().length);
		
		assertArrayEquals(new Boolean[]{false,false},board.getIsVisible());
		
		assertEquals(PokerGameType.RHODE_ISLAND,board.getPokerGameType());
		
		assertEquals(0,board.getTurnedCards().size());
		
		assertEquals(0,board.getTurnedSuits().size());
		
		assertTrue(board.isSuited());
		
		assertEquals(false,board.addTurnedCard(expectedCard));
//		check that a card was not added to the board;
		assertEquals(2,board.getCards().length);
		
		board.turnNextCard();
		
assertEquals(2,board.getCards().length);
		
		assertEquals(2,board.getIsVisible().length);
		
		assertArrayEquals(new Boolean[]{true,false},board.getIsVisible());
		
		assertEquals(PokerGameType.RHODE_ISLAND,board.getPokerGameType());
		
		assertEquals(1,board.getTurnedCards().size());
		
		assertEquals(1,board.getTurnedSuits().size());
		
		assertTrue(board.isSuited());
		
		assertEquals(false,board.addTurnedCard(expectedCard));
//		check that a card was not added to the board;
		assertEquals(2,board.getCards().length);
		
		board.turnNextCard();
		
assertEquals(2,board.getCards().length);
		
		assertEquals(2,board.getIsVisible().length);
		
		assertArrayEquals(new Boolean[]{true,true},board.getIsVisible());
		
		assertEquals(PokerGameType.RHODE_ISLAND,board.getPokerGameType());
		
		assertEquals(2,board.getTurnedCards().size());
		
		assertEquals(2,board.getTurnedSuits().size());
//		unshuffled deck
		assertTrue(board.isSuited());
		
		assertEquals(false,board.addTurnedCard(expectedCard));
//		check that a card was not added to the board;
		assertEquals(2,board.getCards().length);
	}
	
}
