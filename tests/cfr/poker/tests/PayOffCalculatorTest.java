package cfr.poker.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cfr.poker.*;

public class PayOffCalculatorTest {

	final Card spades2 = new Card(0);
	final Card spades3 = new Card(1);
	final Card spades4 = new Card(2);
	final Card spades5 = new Card(3);
	final Card spadesJ = new Card(9);
	final Card spadesQ = new Card(10);
	final Card spadesK = new Card(11);
	final Card spadesA = new Card(12);
	
	final Card hearts2 = new Card(13);
	final Card hearts3 = new Card(14);
	final Card hearts4 = new Card(15);
	final Card hearts5 = new Card(16);
	final Card heartsJ = new Card(22);
	final Card heartsQ = new Card(23);
	final Card heartsK = new Card(24);
	final Card heartsA = new Card(25);
	
	final Card clubs3 = new Card(27);
	final Card diamonds3 = new Card(40);

	final Hand hSpades2 = new HandSingleCard(new Card(0));
	final Hand hSpades3 = new HandSingleCard(new Card(1));
	final Hand hSpades4 = new HandSingleCard(new Card(2));
	final Hand hSpades5 = new HandSingleCard(new Card(3));
	final Hand hSpadesJ = new HandSingleCard(new Card(9));
	final Hand hSpadesQ = new HandSingleCard(new Card(10));
	final Hand hSpadesK = new HandSingleCard(new Card(11));
	final Hand hSpadesA = new HandSingleCard(new Card(12));
	final Hand hHearts2 = new HandSingleCard(new Card(13));
	final Hand hHearts3 = new HandSingleCard(new Card(14));
	final Hand hHearts4 = new HandSingleCard(new Card(15));
	final Hand hHearts5 = new HandSingleCard(new Card(16));
	final Hand hHeartsJ = new HandSingleCard(new Card(22));
	final Hand hHeartsQ = new HandSingleCard(new Card(23));
	final Hand hHeartsK = new HandSingleCard(new Card(24));
	final Hand hHeartsA = new HandSingleCard(new Card(25));

	Board board;
	Map<Integer,Hand> hands;

	@Before
	public void init() {
		board = new Board().setPokerGameType(PokerGameType.RHODE_ISLAND);
		hands = new HashMap<Integer,Hand>();
	}

	@Ignore
	@Test
	public void print_out_cards() {
		System.out.println("twoOfSpades : " + spades2);
		System.out.println("threeOfSpades : " + spades3);
		System.out.println("fourOfSpades : " + spades4);
		System.out.println("fiveOfSpades : " + spades5);
		System.out.println("jackOfSpades : " + spadesJ);
		System.out.println("queenOfSpades : " + spadesQ);
		System.out.println("kingOfSpades : " + spadesK);
		System.out.println("aceOfSpades : " + spadesA);

		System.out.println("twoOfHearts : " + hearts2);
		System.out.println("threeOfHearts : " + hearts3);
		System.out.println("fourOfHearts : " + hearts4);
		System.out.println("fiveOfHearts : " + hearts5);
		System.out.println("jackOfHearts : " + heartsJ);
		System.out.println("queenOfHearts : " + heartsQ);
		System.out.println("kingOfHearts : " + heartsK);
		System.out.println("aceOfHearts : " + heartsA);
		
		System.out.println("threeOfClubs : " + clubs3);
		System.out.println("threeOfDiamonds : " + diamonds3);

	}

	@Test
	public void high_card() {
		board.addTurnedCard(spades5);
		board.addTurnedCard(heartsJ);
		
		hands.put(0, hSpades2);
		hands.put(1, hSpades3);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(1, hSpades2);
		hands.put(0, hSpades3);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void high_card_tie() {
		board.addTurnedCard(spades5);
		board.addTurnedCard(heartsJ);
		
		hands.put(0, hSpades2);
		hands.put(1, hHearts2);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertEquals(strengths.get(0),strengths.get(1));
		
		hands.clear();
		
		hands.put(1, hSpades2);
		hands.put(0, hHearts2);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertEquals(strengths.get(0),strengths.get(1));
	}

	
	@Test
	public void high_card_vs_pair() {
		board.addTurnedCard(spades5);
		board.addTurnedCard(heartsJ);
		
		hands.put(0, hHearts5);
		hands.put(1, hSpades2);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades2);
		hands.put(1, hHearts5);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
	@Test
	public void high_card_A_vs_pair_2() {
		board.addTurnedCard(spades2);
		board.addTurnedCard(heartsJ);
		
		hands.put(0, hHearts2);
		hands.put(1, hSpadesA);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hHeartsA);
		hands.put(1, hHearts2);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
	
	@Test
	public void high_card_vs_run() {
		board.addTurnedCard(spades5);
		board.addTurnedCard(hearts4);
		
		hands.put(0, hHearts2);
		hands.put(1, hSpades3);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades3);
		hands.put(1, hHearts2);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void high_card_vs_run_to_3() {
		board.addTurnedCard(spades2);
		board.addTurnedCard(hearts3);
		
		hands.put(0, hHeartsJ);
		hands.put(1, hSpadesA);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpadesA);
		hands.put(1, hHeartsJ);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}

	@Test
	public void high_card_vs_flush() {
		board.addTurnedCard(heartsJ);
		board.addTurnedCard(hearts4);
		
		hands.put(0, hHearts2);
		hands.put(1, hSpades3);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades3);
		hands.put(1, hHearts2);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
	@Test
	public void high_card_vs_trips() {
		board.addTurnedCard(clubs3);
		board.addTurnedCard(hearts3);
		
		hands.put(0, hHearts2);
		hands.put(1, hSpades3);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades3);
		hands.put(1, hHearts2);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void high_card_vs_str_flush() {
		board.addTurnedCard(heartsJ);
		board.addTurnedCard(heartsQ);
		
		hands.put(0, hHeartsK);
		hands.put(1, hSpades3);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades3);
		hands.put(1, hHeartsK);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
	@Test
	public void pair_vs_pair() {
		board.addTurnedCard(clubs3);
		board.addTurnedCard(hearts4);
		
		hands.put(0, hHearts3);
		hands.put(1, hSpades4);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades4);
		hands.put(1, hHearts3);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void pair_vs_pair_and_kickers() {
		board.addTurnedCard(spades4);
		board.addTurnedCard(hearts4);
		
		hands.put(0, hHeartsK);
		hands.put(1, hSpadesQ);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpadesQ);
		hands.put(1, hHeartsK);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
	@Test
	public void pair_vs_pair_tie() {
		board.addTurnedCard(spades4);
		board.addTurnedCard(hearts4);
		
		hands.put(0, hHeartsK);
		hands.put(1, hSpadesK);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertEquals(strengths.get(0),strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpadesK);
		hands.put(1, hHeartsK);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertEquals(strengths.get(0),strengths.get(1));
	}
	
	
	@Test
	public void pair4_vs_pairA() {
		board.addTurnedCard(spades5);
		board.addTurnedCard(hearts4);
		
		hands.put(0, hHearts5);
		hands.put(1, hSpades4);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades4);
		hands.put(1, hHearts5);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
	@Test
	public void pair_A_vs_run() {
		board.addTurnedCard(clubs3);
		board.addTurnedCard(hearts4);
		
		hands.put(0, hHearts3);
		hands.put(1, hSpades2);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades2);
		hands.put(1, hHearts3);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void pair_vs_flush() {
		board.addTurnedCard(spadesA);
		board.addTurnedCard(spades4);
		
		hands.put(0, hHeartsA);
		hands.put(1, hSpades2);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades2);
		hands.put(1, hHearts3);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void pair_vs_trips() {
		board.addTurnedCard(clubs3);
		board.addTurnedCard(spades3);
		
		hands.put(0, hHearts3);
		hands.put(1, hSpades2);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades2);
		hands.put(1, hHearts3);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
	@Test
	public void pair_vs_str_flush() {
		board.addTurnedCard(spadesQ);
		board.addTurnedCard(spadesK);
		
		hands.put(0, hHeartsQ);
		hands.put(1, hSpadesJ);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpadesA);
		hands.put(1, hHeartsQ);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void flush_vs_flush() {
		board.addTurnedCard(spadesJ);
		board.addTurnedCard(spades3);
		
		hands.put(0, hSpadesA);
		hands.put(1, hSpades2);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades2);
		hands.put(1, hSpadesA);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
	@Test
	public void flush_vs_run() {
		board.addTurnedCard(spadesJ);
		board.addTurnedCard(spadesQ);
		
		hands.put(0, hSpadesA);
		hands.put(1, hHeartsK);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hHeartsK);
		hands.put(1, hSpadesA);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void flush_to_K_vs_run_to_3() {
		board.addTurnedCard(spades2);
		board.addTurnedCard(spades3);
		
		hands.put(0, hSpadesK);
		hands.put(1, hHeartsA);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hHeartsA);
		hands.put(1, hSpadesK);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void flush_to_A_vs_run_to_4() {
		board.addTurnedCard(spades4);
		board.addTurnedCard(spades3);
		
		hands.put(0, hSpadesA);
		hands.put(1, hHearts2);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hHearts5);
		hands.put(1, hSpadesA);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void run_vs_run() {
		board.addTurnedCard(hearts4);
		board.addTurnedCard(hearts3);
		
		hands.put(0, hSpades2);
		hands.put(1, hSpades5);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)<strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades5);
		hands.put(1, hSpades2);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)>strengths.get(1));
	}
	
	@Test
	public void run_vs_run_tie() {
		board.addTurnedCard(hearts4);
		board.addTurnedCard(clubs3);
		
		hands.put(0, hHearts5);
		hands.put(1, hSpades5);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertEquals(strengths.get(0),strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades5);
		hands.put(1, hHearts5);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertEquals(strengths.get(0),strengths.get(1));
	}
	
	@Test
	public void run_vs_strFlush() {
		board.addTurnedCard(hearts4);
		board.addTurnedCard(hearts3);
		
		hands.put(0, hHearts2);
		hands.put(1, hSpades5);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hSpades5);
		hands.put(1, hHearts2);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
	@Test
	public void trips_vs_trips() {
		board.addTurnedCard(clubs3);
		board.addTurnedCard(diamonds3);
		
		hands.put(0, hSpades3);
		hands.put(1, hHearts3);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertEquals(strengths.get(0),strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hHearts3);
		hands.put(1, hSpades3);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertEquals(strengths.get(0),strengths.get(1));
	}
	
	@Test
	public void strFlush_vs_strFlush() {
		board.addTurnedCard(heartsQ);
		board.addTurnedCard(heartsK);
		
		hands.put(0, hHeartsA);
		hands.put(1, hHeartsJ);
		
		Map<Integer, Integer> strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		assertTrue(strengths.get(0)>strengths.get(1));
		
		hands.clear();
		
		hands.put(0, hHeartsJ);
		hands.put(1, hHeartsA);

		strengths = PayOffCalculator.getHandStrengths(hands, board, PokerGameType.RHODE_ISLAND);
		
		assertTrue(strengths.get(0)<strengths.get(1));
	}
	
}
