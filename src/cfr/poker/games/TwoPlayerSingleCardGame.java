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
		hands = new HashMap<Integer, Hand>();
		for (int player = 0; player < numOfPlayers; player++) {
			hands.put(player, new HandSingleCard(deck.nextCard()));
		}
		board = new Board(pokerGameType, deck);
		this.betRound = BetRound.RIVER;
		return hands;
	}







}
