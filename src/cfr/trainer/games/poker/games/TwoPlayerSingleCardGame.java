package cfr.trainer.games.poker.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.decks.DeckStandardShuffled;

public class TwoPlayerSingleCardGame extends BaseTwoPlayerPokerGame {

	public TwoPlayerSingleCardGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		super(bettingLimit,raisesPerBettingRound);
		this.pokerGameType = PokerGameType.SINGLE_CARD;
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
		this.betRound = BetRound.RIVER;
		return players;
	}


	@Override
	public int[][] getListOfValidChanceCombinations() {
		int[][] validCardCombinationLists = new int[2652][2];

		int validComboCount = 0;
		for (int card0 = 0; card0 < 52; card0++) {
			for (int card1 = 0; card1 < 52; card1++) {
				if (card0 == card1) {
					continue;
				}
				int[] validCardComination = new int[2];
				validCardComination[0]=card0;
				validCardComination[1]=card1;
				validCardCombinationLists[validComboCount] = validCardComination;
				validComboCount++;
			}
		}

		return validCardCombinationLists;
	}

	@Override
	public TwoPlayerSingleCardGame setValidChanceCombinations(int[] listOfChanceCombinations) {
	
		int card0= listOfChanceCombinations[0];
		int card1= listOfChanceCombinations[1];
		
		
		Hand hand0 = new HandSingleCard(new Card(card0));
		Hand hand1 = new HandSingleCard(new Card(card1));

		Map<Integer, Hand> newHands = new HashMap<Integer, Hand>();
		newHands.put(0, hand0);
		newHands.put(1, hand1);
		this.setHands(newHands);

		return this;
	}

}
