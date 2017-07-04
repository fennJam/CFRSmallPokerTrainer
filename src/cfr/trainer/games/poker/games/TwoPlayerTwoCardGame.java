package cfr.trainer.games.poker.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.decks.DeckStandardShuffled;

public class TwoPlayerTwoCardGame extends BaseTwoPlayerPokerGame {

	public TwoPlayerTwoCardGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		super(bettingLimit, raisesPerBettingRound);
		this.pokerGameType = PokerGameType.TWO_CARD;
		this.betRound = BetRound.TURN;
		deck = new DeckStandardShuffled();
	}

	@Override
	public Map<Integer, PokerPlayer> dealCards() {
		int numOfPlayers = players.size();
		for (int player = 0; player < numOfPlayers; player++) {
			HandSingleCard hand = new HandSingleCard(deck.nextCard());
			players.get(player).setHand(hand);
		}
		board = new Board(pokerGameType, deck);
		this.betRound = BetRound.TURN;
		return players;
	}

	@Override
	public int[][] getListOfValidChanceCombinations() {
		int[][] validCardCombinationLists = new int[132600][3];

		int validComboCount = 0;
		for (int card0 = 0; card0 < 52; card0++) {
			for (int card1 = 0; card1 < 52; card1++) {
				if (card0 == card1) {
					continue;
				}
				for (int boardCard1 = 0; boardCard1 < 52; boardCard1++) {
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

	@Override
	public TwoPlayerTwoCardGame setValidChanceCombinations(int[] listOfChanceCombinations) {

		int card0 = listOfChanceCombinations[0];
		int card1 = listOfChanceCombinations[1];
		int boardCard1 = listOfChanceCombinations[2];

		Hand hand0 = new HandSingleCard(new Card(card0));
		Hand hand1 = new HandSingleCard(new Card(card1));

		Map<Integer, Hand> newHands = new HashMap<Integer, Hand>();
		newHands.put(0, hand0);
		newHands.put(1, hand1);
		this.setHands(newHands);
		if (this.board == null) {
			this.board = new Board(this.pokerGameType);
		}
		this.board.setCard(new Card(boardCard1), 0, false);

		return this;
	}

}
