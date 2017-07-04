package cfr.trainer.games.poker.games.lite.test;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import cfr.trainer.games.poker.CardHeight;
import cfr.trainer.games.poker.games.lite.KuhnPokerLite;

public class KuhnPokerLiteTest {
	@Test
	public void getListOfGamesWithAllPossibleChanceNodesTest(){
		KuhnPokerLite gameTemplate = new KuhnPokerLite();
		
		List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

		assertEquals(6,chanceComboList.size());
		
		KuhnPokerLite game0 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(0));
		KuhnPokerLite game1 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(1));
		KuhnPokerLite game2 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(2));
		KuhnPokerLite game3 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(3));
		KuhnPokerLite game4 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(4));
		KuhnPokerLite game5 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(5));
		
		
		
		assertEquals(9, game0.getHands()[0]%13);
		assertEquals(10, game0.getHands()[1]%13);
		
		assertEquals(9, game1.getHands()[0]%13);
		assertEquals(11, game1.getHands()[1]%13);
		
		assertEquals(10,game2.getHands()[0]%13);
		assertEquals(9, game2.getHands()[1]%13);
		
		assertEquals(10, game3.getHands()[0]%13);
		assertEquals(11, game3.getHands()[1]%13);
		
		assertEquals(11, game4.getHands()[0]%13);
		assertEquals(9, game4.getHands()[1]%13);
		
		assertEquals(11, game5.getHands()[0]%13);
		assertEquals(10, game5.getHands()[1]%13);
		
	}
	
	
}
