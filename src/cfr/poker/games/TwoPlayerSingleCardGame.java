package cfr.poker.games;

import java.util.HashMap;
import java.util.Map;

import cfr.poker.*;

public class TwoPlayerSingleCardGame extends BaseTwoPlayerPokerGame {

	public TwoPlayerSingleCardGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		super(bettingLimit,raisesPerBettingRound);
		this.pokerGameType = PokerGameType.SINGLE_CARD;
	}

	@Override
	public Map<Integer, Hand> dealCards(Deck deck) {
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







}
