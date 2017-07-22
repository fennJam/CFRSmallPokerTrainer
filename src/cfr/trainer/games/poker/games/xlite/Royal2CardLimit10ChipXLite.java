package cfr.trainer.games.poker.games.xlite;

public class Royal2CardLimit10ChipXLite extends PokerXLiteBase {

	public Royal2CardLimit10ChipXLite() {
		super(1, 1, 2, 10, false);
		this.startingStacks=10;
	}

	@Override
	void postBlinds() {
		pot[0] = 1;
		pot[1] = 2;

	}

	@Override
	public int[][] getListOfValidChanceCombinations() {
		int[][] validCardCombinationLists = new int[6840][3];
		int[] cardOrder = new int[20];

		int cardOrderPointer = 0;
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
		for (int card0 : cardOrder) {
			for (int card1 : cardOrder) {
				if (card0 == card1) {
					continue;
				}
				for (int boardCard1 : cardOrder) {
					if (boardCard1 == card1 || boardCard1 == card0) {
						continue;
					}
					int[] validCardComination = new int[3];
					validCardComination[0] = card0;
					validCardComination[1] = card1;
					validCardComination[2] = boardCard1;
					validCardCombinationLists[validComboCount] = validCardComination;
					validComboCount++;

				}
			}
		}

		return validCardCombinationLists;
	}

}
