package cfr.trainer.games.poker.games.test;


import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import cfr.trainer.games.poker.CardHeight;
import cfr.trainer.games.poker.games.KuhnPoker;

public class KuhnPokerTest {
	@Test
	public void getListOfGamesWithAllPossibleChanceNodesTest(){
		KuhnPoker gameTemplate = new KuhnPoker();
		
		List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

		assertEquals(6,chanceComboList.size());
		
		KuhnPoker game0 = new KuhnPoker().setValidChanceCombinations(chanceComboList.get(0));
		KuhnPoker game1 = new KuhnPoker().setValidChanceCombinations(chanceComboList.get(1));
		KuhnPoker game2 = new KuhnPoker().setValidChanceCombinations(chanceComboList.get(2));
		KuhnPoker game3 = new KuhnPoker().setValidChanceCombinations(chanceComboList.get(3));
		KuhnPoker game4 = new KuhnPoker().setValidChanceCombinations(chanceComboList.get(4));
		KuhnPoker game5 = new KuhnPoker().setValidChanceCombinations(chanceComboList.get(5));
		
		
		
		assertEquals(CardHeight.JACK, game0.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.QUEEN, game0.getHands().get(1).getCard(0).getHeight());
		
		assertEquals(CardHeight.JACK, game1.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.KING, game1.getHands().get(1).getCard(0).getHeight());
		
		assertEquals(CardHeight.QUEEN, game2.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.JACK, game2.getHands().get(1).getCard(0).getHeight());
		
		assertEquals(CardHeight.QUEEN, game3.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.KING, game3.getHands().get(1).getCard(0).getHeight());
		
		assertEquals(CardHeight.KING, game4.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.JACK, game4.getHands().get(1).getCard(0).getHeight());
		
		assertEquals(CardHeight.KING, game5.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardHeight.QUEEN, game5.getHands().get(1).getCard(0).getHeight());
		
	}
	
	
}
