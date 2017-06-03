package cfr.trainer.games.poker.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.decks.Deck;
import cfr.trainer.games.poker.decks.DeckStandardShuffled;

public class TwoPlayerSingleCardGame extends BaseTwoPlayerPokerGame {

	public TwoPlayerSingleCardGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		super(bettingLimit,raisesPerBettingRound);
		this.pokerGameType = PokerGameType.SINGLE_CARD;
	}
	
	
	@Override
	public Map<Integer, Hand> dealCards() {
		deck = new DeckStandardShuffled();
		int numOfPlayers = players.length;
		this.hands = new HashMap<Integer, Hand>();
		for (int player = 0; player < numOfPlayers; player++) {
			HandSingleCard hand = new HandSingleCard(deck.nextCard());
			this.hands.put(player, hand);
		}
		board = new Board(pokerGameType, deck);
		this.betRound = BetRound.RIVER;
		return hands;
	}


	@Override
	public List<List<Integer>> getListOfValidChanceCombinations() {
		List<List<Integer>> validCardCombinationLists = new ArrayList<List<Integer>>();

		for (int card0 = 0; card0 < 52; card0++) {
			for (int card1 = 0; card1 < 52; card1++) {
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
	public TwoPlayerSingleCardGame setValidChanceCombinations(List<Integer> listOfChanceCombinations) {
	
		Integer card0= listOfChanceCombinations.get(0);
		Integer card1= listOfChanceCombinations.get(1);
		
		
		Hand hand0 = new HandSingleCard(new Card(card0));
		Hand hand1 = new HandSingleCard(new Card(card1));

		Map<Integer, Hand> newHands = new HashMap<Integer, Hand>();
		newHands.put(0, hand0);
		newHands.put(1, hand1);
		this.setHands(newHands);

		return this;
	}

}
