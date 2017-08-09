package cfr.trainer.games.poker.games.lite.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cfr.trainer.games.poker.actions.CallAction;
import cfr.trainer.games.poker.actions.DealAction;
import cfr.trainer.games.poker.actions.RaiseAction;
import cfr.trainer.games.poker.games.lite.Royal2Card10ChipNoLimitLite;
import cfr.trainer.games.poker.games.lite.RoyalRhodeIsland10ChipNoLimitLite;

public class Royal2Card10ChipNoLimitLiteTest {

	Royal2Card10ChipNoLimitLite royal2Card10ChipNoLimitLite;

	@Before
	public void init() {
		royal2Card10ChipNoLimitLite = new Royal2Card10ChipNoLimitLite(3);
	}

	@Test
	public final void validComboTest() {
		int[][] validChanceCombinations = royal2Card10ChipNoLimitLite.getListOfValidChanceCombinations();
		assertEquals(6840, validChanceCombinations.length);
	}

	@Test
	public final void summaryStateTests() throws Exception {

		royal2Card10ChipNoLimitLite.startGame();
		int[] validChanceCombo = new int[4];
		// add Ad,Kd,Qd,JD
		validChanceCombo[0] = 51;
		validChanceCombo[1] = 50;
		validChanceCombo[2] = 49;

		royal2Card10ChipNoLimitLite.setValidChanceCombinations(validChanceCombo);

		royal2Card10ChipNoLimitLite.performAction(0, new RaiseAction(5));
		
		assertEquals("[S,S,10]D5", royal2Card10ChipNoLimitLite.getNodeIdWithSummaryState());
		
		royal2Card10ChipNoLimitLite.performAction(1, new RaiseAction(1));

		assertEquals("[S,S,11]D51", royal2Card10ChipNoLimitLite.getNodeIdWithSummaryState());
		
		royal2Card10ChipNoLimitLite.performAction(1, CallAction.getInstance());

		assertEquals("[S,S,10]D51C", royal2Card10ChipNoLimitLite.getNodeIdWithSummaryState());
		
		royal2Card10ChipNoLimitLite.performAction(1, DealAction.getInstance());

		assertEquals("[S,S,11,12]D[5, 1]D", royal2Card10ChipNoLimitLite.getNodeIdWithSummaryState());
		
		royal2Card10ChipNoLimitLite.performAction(1, new RaiseAction(2));

		assertEquals("[S,S,10,12]D[5, 1]D2", royal2Card10ChipNoLimitLite.getNodeIdWithSummaryState());
	}

}
