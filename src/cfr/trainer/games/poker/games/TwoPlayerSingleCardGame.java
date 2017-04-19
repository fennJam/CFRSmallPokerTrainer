package cfr.trainer.games.poker.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.decks.Deck;
import cfr.trainer.games.poker.decks.DeckKuhnShuffled;

public class TwoPlayerSingleCardGame extends BaseTwoPlayerPokerGame {

	public TwoPlayerSingleCardGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		super(bettingLimit,raisesPerBettingRound);
		this.pokerGameType = PokerGameType.SINGLE_CARD;
	}

	@Override
	public List<PokerGame> getListOfGamesWithAllPossibleChanceNodes() {
		int player0 = 0;
		int player1 = 1;

		List<PokerGame> games = new ArrayList<PokerGame>();

		for (int i = 0; i < 52; i++) {
			Hand hand0 = new HandSingleCard(new Card(i));
			for (int j = 0; j < 52; j++) {
				if (i == j) {
					continue;
				}
				Hand hand1 = new HandSingleCard(new Card(j));

				TwoPlayerSingleCardGame game = new TwoPlayerSingleCardGame(bettingLimit,raisesPerBettingRound);
				game.startGame();
				Map<Integer, Hand> newHands = new HashMap<Integer, Hand>();
				newHands.put(player0, hand0);
				newHands.put(player1, hand1);
				game.setHands(newHands);
				games.add(game);
			}
		}

		return games;

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


}
