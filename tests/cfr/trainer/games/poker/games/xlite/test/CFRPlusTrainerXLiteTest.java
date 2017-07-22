package cfr.trainer.games.poker.games.xlite.test;

import static org.junit.Assert.*;

import org.junit.Test;

import cfr.trainer.xlite.CFRPlusTrainerXLite;

public class CFRPlusTrainerXLiteTest {
	CFRPlusTrainerXLite cFRPlusTrainerXLite =new CFRPlusTrainerXLite();
	@Test
	public final void testActionSummariser() {
		String actions1 = "D25";
		assertEquals("D25",CFRPlusTrainerXLite.summariseActions(actions1));
		
		String actions2 = "D[2, 5]D782";
		
		assertEquals("D[2, 5]D98",CFRPlusTrainerXLite.summariseActions(actions2));
		
		String actions3 = "D12345";
		
		assertEquals("D96",CFRPlusTrainerXLite.summariseActions(actions3));
		
		String actions4 = "D12345D782";
		
		assertEquals("D12345D98",CFRPlusTrainerXLite.summariseActions(actions4));
	}

}
