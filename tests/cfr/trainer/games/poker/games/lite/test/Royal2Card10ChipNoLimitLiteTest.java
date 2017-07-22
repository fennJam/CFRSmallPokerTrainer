package cfr.trainer.games.poker.games.lite.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cfr.trainer.games.poker.games.lite.Royal2Card10ChipNoLimitLite;

public class Royal2Card10ChipNoLimitLiteTest{

	Royal2Card10ChipNoLimitLite royal2Card10ChipNoLimitLite;
	
	@Before
	public void init(){
		royal2Card10ChipNoLimitLite = new Royal2Card10ChipNoLimitLite(3);
	}
	@Test
	public final void validComboTest() {
		int[][] validChanceCombinations = royal2Card10ChipNoLimitLite.getListOfValidChanceCombinations();
		assertEquals(6840,validChanceCombinations.length);
	}

}
