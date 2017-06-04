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
	public KuhnPoker setValidChanceCombinations(List<Integer> listOfChanceCombinations) {
	
		Integer card0= (Integer)listOfChanceCombinations.get(0);
		Integer card1= (Integer)listOfChanceCombinations.get(1);
		
		
		Hand hand0 = new HandSingleCard(new Card(card0));
		Hand hand1 = new HandSingleCard(new Card(card1));

		Map<Integer, Hand> newHands = new HashMap<Integer, Hand>();
		newHands.put(0, hand0);
		newHands.put(1, hand1);
		this.setHands(newHands);

		return this;
	}

}
