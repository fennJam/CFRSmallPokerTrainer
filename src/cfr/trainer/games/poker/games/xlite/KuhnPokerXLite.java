package cfr.trainer.games.poker.games.xlite;

public class KuhnPokerXLite extends PokerXLiteBase {
	
	
	public KuhnPokerXLite() {
		super(0, 1, 1, 2, false);
	}

	
	@Override
	void postBlinds() {
		this.pot[0]=1;
		this.pot[1]=1;
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
	
	
}
