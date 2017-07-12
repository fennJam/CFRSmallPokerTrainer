package cfr.trainer.games.poker.games.xlite;

public class RoyalRhodeIslandNL10ChipXLite extends PokerXLiteBase{

	public RoyalRhodeIslandNL10ChipXLite() {
		super(2, 3, 3, 10, true);
	}

	@Override
	void postBlinds() {
		pot[0]=1;
		pot[1]=2;
		
	}

	@Override
	public int[][] getListOfValidChanceCombinations() {
		int[][] validCardCombinationLists = new int[116280][4];
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
					for (int boardCard2 : cardOrder) {
						if (boardCard2 == card1 || boardCard2 == card0 || boardCard2 == boardCard1) {
							continue;
						}
						int[] validCardComination = new int[4];
						validCardComination[0] = card0;
						validCardComination[1] = card1;
						validCardComination[2] =boardCard1;
						validCardComination[3]=boardCard2;
						validCardCombinationLists[validComboCount] = validCardComination;
						validComboCount++;
					}
				}
			}
		}

		return validCardCombinationLists;
	}
	
}
