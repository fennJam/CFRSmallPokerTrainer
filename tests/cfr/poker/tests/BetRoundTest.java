package cfr.poker.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cfr.poker.BetRound;

public class BetRoundTest {

	@Test
	public void testIncremintor() {
		
		cfr.poker.BetRound betRound = BetRound.PRETURN;
		assertEquals(BetRound.TURN,betRound.next());
		betRound = betRound.next();
		assertEquals(BetRound.RIVER,betRound.next());
	}

}
