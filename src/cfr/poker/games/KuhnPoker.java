package cfr.poker.games;

import java.util.HashMap;
import java.util.Map;

import cfr.poker.BetRound;
import cfr.poker.BettingLimit;
import cfr.poker.Board;
import cfr.poker.Hand;
import cfr.poker.HandSingleCard;
import cfr.poker.PokerGameType;
import cfr.poker.Pot;
import cfr.poker.decks.Deck;
import cfr.poker.decks.DeckKuhnShuffled;

public class KuhnPoker extends BaseTwoPlayerPokerGame {

	public KuhnPoker() {
		super(BettingLimit.LIMIT,1);
		this.pokerGameType = PokerGameType.SINGLE_CARD;
	}

	@Override
	public Map<Integer, Hand> dealCards(Deck deck) {
		deck = new DeckKuhnShuffled();
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
	public Pot postBlinds() {
		bet(0, 1);
		return bet(1, 1);
	}







}
