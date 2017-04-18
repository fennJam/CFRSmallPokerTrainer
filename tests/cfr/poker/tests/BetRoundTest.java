package cfr.poker.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cfr.trainer.games.poker.BetRound;

public class BetRoundTest {

	@Test
	public void testIncremintor() {
		
		cfr.trainer.games.poker.BetRound betRound = BetRound.PRETURN;
		assertEquals(BetRound.TURN,betRound.next());
		betRound = betRound.next();
		assertEquals(BetRound.RIVER,betRound.next());
	}

}
