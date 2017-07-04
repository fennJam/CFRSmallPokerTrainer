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
import cfr.trainer.games.poker.decks.RoyalDeckShuffled;

public class TwoPlayerRoyalRhodeIslandGame extends BaseTwoPlayerPokerGame {
	
	
	public TwoPlayerRoyalRhodeIslandGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		super(bettingLimit, raisesPerBettingRound);
		this.pokerGameType = PokerGameType.RHODE_ISLAND;
		this.betRound = BetRound.PRETURN;
		deck = new RoyalDeckShuffled();
	}

	@Override
	public Map<Integer, PokerPlayer> dealCards() {
		int numOfPlayers = players.size();
		for (int player = 0; player < numOfPlayers; player++) {
			HandSingleCard hand = new HandSingleCard(deck.nextCard());
			players.get(player).setHand(hand);
		}
		board = new Board(pokerGameType, deck);
		this.betRound = BetRound.PRETURN;
		return players;
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

	@Override
	public TwoPlayerRoyalRhodeIslandGame setValidChanceCombinations(int[] listOfChanceCombinations) {

		int card0 = listOfChanceCombinations[0];
		int card1 = listOfChanceCombinations[1];
		int boardCard1 = listOfChanceCombinations[2];
		int boardCard2 = listOfChanceCombinations[3];

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