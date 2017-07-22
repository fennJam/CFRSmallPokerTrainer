package cfr.trainer.games.poker.games.lite;

import cfr.trainer.games.poker.BetRound;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.PokerGameType;

public class Royal2Card10ChipNoLimitLite extends BaseTwoPlayerPokerLiteGame {


	public Royal2Card10ChipNoLimitLite(int raisesPerRound) {
		super(BettingLimit.NO_LIMIT,raisesPerRound);
		this.board = new int[1];
		this.visibleBoardCards = new boolean[1];
		this.bettingLimit = BettingLimit.NO_LIMIT;
		this.betRound = BetRound.TURN;
		int[] startingStacks = {10,10};
		this.playerStack = startingStacks;
		this.pokerGameType = PokerGameType.RHODE_ISLAND;

	}


	@Override
	public int[] dealCards() {
//TODO
		return null;
	}

	@Override
	public int[][] getListOfValidChanceCombinations() {
		int[][] validCardCombinationLists = new int[6840][3];
		int[] cardOrder = new int[20];

		int cardOrderPointer =0;
		// add 10-A spades
		for (int card = 8; card < 13; card++) {
			cardOrder[cardOrderPointer] = card;
			cardOrderPointer++;
		}
		// add 10-A hearts
		for (int card = 21; card < 26; card++) {
			cardOrder[cardOrderPointer] = card;
			cardOrderPointer++;
		}
		// add 10-A clubs
		for (int card = 34; card < 39; card++) {
			cardOrder[cardOrderPointer] = card;
			cardOrderPointer++;
		}
		// add 10-A diamonds
		for (int card = 47; card < 52; card++) {
			cardOrder[cardOrderPointer] = card;
			cardOrderPointer++;
		}
		
		int validComboCount = 0;
		for (int card0: cardOrder) {
			for (int card1: cardOrder) {
				if (card0 == card1) {
					continue;
				}
				for (int boardCard1: cardOrder) {
					if (boardCard1 == card1 || boardCard1 == card0) {
						continue;
					}
						int[] validCardComination = new int[3];
						validCardComination[0] = card0;
						validCardComination[1] = card1;
						validCardComination[2] =boardCard1;
						validCardCombinationLists[validComboCount] = validCardComination;
						validComboCount++;
				}
			}
		}

		return validCardCombinationLists;
	}
	
	@Override
	public Royal2Card10ChipNoLimitLite setValidChanceCombinations(int[] listOfChanceCombinations) {

		
		int boardCard1 = listOfChanceCombinations[0];
		int card0 = listOfChanceCombinations[1];
		int card1 = listOfChanceCombinations[2];

		playerHands[0] = card0;
		playerHands[1] = card1;
	
		this.board[0]=boardCard1;

		return this;
	}

	
}
