package cfr.trainer.games.poker.games.lite;

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
	public int[][] getListOfValidChanceCombinations() {
		int[][] validCardCombinationArrays = new int[6][2];
		int validComboCount = 0;
		for (int card0 = 9; card0 < 12; card0++) {
			for (int card1 = 9; card1 < 12; card1++) {
				if (card0 == card1) {
					continue;
				}
				int[] validCardComination = new int[2];
				validCardComination[0]=card0;
				validCardComination[1]=card1;
				validCardCombinationArrays[validComboCount] =validCardComination;
				validComboCount++;
			}
		}

		return validCardCombinationArrays;
	}

	@Override
	public KuhnPokerLite setValidChanceCombinations(int[] listOfChanceCombinations) {
	
		int card0= (Integer)listOfChanceCombinations[0];
		int card1= (Integer)listOfChanceCombinations[1];
		
		
		playerHands[0]=card0;
		playerHands[1]=card1;

		return this;
	}

}
