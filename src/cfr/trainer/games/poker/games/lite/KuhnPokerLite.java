package cfr.trainer.games.poker.games.lite;

import java.util.ArrayList;
import java.util.List;

import cfr.trainer.games.poker.BetRound;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.PokerGameType;

public class KuhnPokerLite extends BaseTwoPlayerPokerLiteGame {

	public KuhnPokerLite() {
		super(BettingLimit.LIMIT, 1);
		this.pokerGameType = PokerGameType.SINGLE_CARD;
		this.board = new int[0];
		this.visibleBoardCards = new boolean[0];
		this.betRound=BetRound.RIVER;
		int[] startingStacks = {2,2};
		this.playerStack = startingStacks;
		this.raisesPerBettingRound = 1;
		
	}

	@Override
	public int[] dealCards() {
//TODO
		return null;
	}

	@Override
	public int[] postBlinds() throws Exception {
		bet(0, 1);
		return bet(1, 1);
	}

	@Override
	public List<List<Integer>> getListOfValidChanceCombinations() {
		List<List<Integer>> validCardCombinationLists = new ArrayList<List<Integer>>();

		for (int card0 = 9; card0 < 12; card0++) {
			for (int card1 = 9; card1 < 12; card1++) {
				if (card0 == card1) {
					continue;
				}
				List<Integer> validCardComination = new ArrayList<Integer>();
				validCardComination.add(card0);
				validCardComination.add(card1);
				validCardCombinationLists.add(validCardComination);
			}
		}

		return validCardCombinationLists;
	}

	@Override
	public KuhnPokerLite setValidChanceCombinations(List<Integer> listOfChanceCombinations) {
	
		int card0= (Integer)listOfChanceCombinations.get(0);
		int card1= (Integer)listOfChanceCombinations.get(1);
		
		
		playerHands[0]=card0;
		playerHands[1]=card1;

		return this;
	}

}
