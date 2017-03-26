package cfr.trainer.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cfr.trainer.SingleCardNLPokerTrainer;

public class PokerTrainerTest {
	SingleCardNLPokerTrainer singleCardNLPokerTrainer;

	@Before
	public void init() {
		singleCardNLPokerTrainer = new SingleCardNLPokerTrainer();
	}
	
	@Test
	public void isBelowMaxNumberOfRaisesTest() {
		assertFalse(singleCardNLPokerTrainer.isBelowMaxNumberOfRaises("C5323", 3));
		assertFalse(singleCardNLPokerTrainer.isBelowMaxNumberOfRaises("C532", 3));
		assertFalse(singleCardNLPokerTrainer.isBelowMaxNumberOfRaises("532", 3));
		assertTrue(singleCardNLPokerTrainer.isBelowMaxNumberOfRaises("532", 4));
		assertTrue(singleCardNLPokerTrainer.isBelowMaxNumberOfRaises("32", 3));
	}

	
//	isTerminalBettingRound
	
}
