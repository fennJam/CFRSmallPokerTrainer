package cfr.trainer.games.poker.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfr.trainer.games.poker.BetRound;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.Board;
import cfr.trainer.games.poker.Card;
import cfr.trainer.games.poker.Hand;
import cfr.trainer.games.poker.HandSingleCard;
import cfr.trainer.games.poker.PokerGameType;
import cfr.trainer.games.poker.PokerPlayer;
import cfr.trainer.games.poker.Pot;
import cfr.trainer.games.poker.decks.DeckKuhnShuffled;

public class KuhnPoker extends BaseTwoPlayerPokerGame {

	public KuhnPoker() {
		super(BettingLimit.LIMIT, 1);
		this.pokerGameType = PokerGameType.SINGLE_CARD;
		deck = new DeckKuhnShuffled();
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
	public Pot postBlinds() {
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
	public KuhnPoker setValidChanceCombinations(int[] listOfChanceCombinations) {
	
		int card0= (Integer)listOfChanceCombinations[0];
		int card1= (Integer)listOfChanceCombinations[1];
		
		
		Hand hand0 = new HandSingleCard(new Card(card0));
		Hand hand1 = new HandSingleCard(new Card(card1));

		Map<Integer, Hand> newHands = new HashMap<Integer, Hand>();
		newHands.put(0, hand0);
		newHands.put(1, hand1);
		this.setHands(newHands);

		return this;
	}

}
