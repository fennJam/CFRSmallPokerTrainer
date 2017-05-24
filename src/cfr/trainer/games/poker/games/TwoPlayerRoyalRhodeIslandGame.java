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
import cfr.trainer.games.poker.decks.Deck;
import cfr.trainer.games.poker.decks.RoyalDeckShuffled;

public class TwoPlayerRoyalRhodeIslandGame extends BaseTwoPlayerPokerGame {
	
	
	public TwoPlayerRoyalRhodeIslandGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		super(bettingLimit, raisesPerBettingRound);
		this.pokerGameType = PokerGameType.RHODE_ISLAND;
		this.betRound = BetRound.PRETURN;
	}

	@Override
	public Map<Integer, Hand> dealCards() {
		Deck deck = new RoyalDeckShuffled();
		int numOfPlayers = players.length;
		hands = new HashMap<Integer, Hand>();
		for (int player = 0; player < numOfPlayers; player++) {
			hands.put(player, new HandSingleCard(deck.nextCard()));
		}
		board = new Board(pokerGameType, deck);
		this.betRound = BetRound.PRETURN;
		return hands;
	}

	@Override
	public List<List<Integer>> getListOfValidChanceCombinations() {
		List<List<Integer>> validCardCombinationLists = new ArrayList<List<Integer>>();
		List<Integer> cardOrder = new ArrayList<>();

		// add 10-A spades
		for (int card = 8; card < 13; card++) {
			cardOrder.add(card);
		}
		// add 10-A hearts
		for (int card = 21; card < 26; card++) {
			cardOrder.add(card);
		}
		// add 10-A clubs
		for (int card = 34; card < 39; card++) {
			cardOrder.add(card);
		}
		// add 10-A diamonds
		for (int card = 47; card < 52; card++) {
			cardOrder.add(card);
		}
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
						List<Integer> validCardComination = new ArrayList<Integer>();
						validCardComination.add(card0);
						validCardComination.add(card1);
						validCardComination.add(boardCard1);
						validCardComination.add(boardCard2);
						validCardCombinationLists.add(validCardComination);
					}
				}
			}
		}

		return validCardCombinationLists;
	}

	@Override
	public TwoPlayerRoyalRhodeIslandGame setValidChanceCombinations(List<Integer> listOfChanceCombinations) {

		Integer card0 = listOfChanceCombinations.get(0);
		Integer card1 = listOfChanceCombinations.get(1);
		Integer boardCard1 = listOfChanceCombinations.get(2);
		Integer boardCard2 = listOfChanceCombinations.get(3);

		Hand hand0 = new HandSingleCard(new Card(card0));
		Hand hand1 = new HandSingleCard(new Card(card1));

		Map<Integer, Hand> newHands = new HashMap<Integer, Hand>();
		newHands.put(0, hand0);
		newHands.put(1, hand1);
		this.setHands(newHands);
		if(this.board==null){
			this.board = new Board(this.pokerGameType);
		}
		this.board.setCard(new Card(boardCard1), 0, false);
		this.board.setCard(new Card(boardCard2), 1, false);

		return this;
	}

}