package cfr.trainer.games.poker.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.decks.Deck;
import cfr.trainer.games.poker.decks.DeckStandardShuffled;

public class TwoPlayerTwoCardGame extends BaseTwoPlayerPokerGame {

	public TwoPlayerTwoCardGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		super(bettingLimit, raisesPerBettingRound);
		this.pokerGameType = PokerGameType.TWO_CARD;
		this.betRound = BetRound.TURN;
	}

	@Override
	public Map<Integer, Hand> dealCards() {
		deck = new DeckStandardShuffled();
		int numOfPlayers = players.length;
		hands = new HashMap<Integer, Hand>();
		for (int player = 0; player < numOfPlayers; player++) {
			hands.put(player, new HandSingleCard(deck.nextCard()));
		}
		board = new Board(pokerGameType, deck);
		this.betRound = BetRound.TURN;
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
				for (int boardCard1 = 0; boardCard1 < 52; boardCard1++) {
					if (boardCard1 == card1|| boardCard1 == card0) {
						continue;
					}
				List<Integer> validCardComination = new ArrayList<Integer>();
				validCardComination.add(card0);
				validCardComination.add(card1);
				validCardComination.add(boardCard1);
				validCardCombinationLists.add(validCardComination);
				}
			}
		}

		return validCardCombinationLists;
	}

	@Override
	public TwoPlayerTwoCardGame setValidChanceCombinations(List<Integer> listOfChanceCombinations) {
	
		Integer card0= listOfChanceCombinations.get(0);
		Integer card1= listOfChanceCombinations.get(1);
		Integer boardCard1= listOfChanceCombinations.get(2);
		
		
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

		return this;
	}

	
}
