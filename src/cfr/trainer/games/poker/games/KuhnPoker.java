package cfr.trainer.games.poker.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfr.trainer.games.Game;
import cfr.trainer.games.poker.BetRound;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.Board;
import cfr.trainer.games.poker.Card;
import cfr.trainer.games.poker.Hand;
import cfr.trainer.games.poker.HandSingleCard;
import cfr.trainer.games.poker.PokerGameType;
import cfr.trainer.games.poker.Pot;
import cfr.trainer.games.poker.actions.DealAction;
import cfr.trainer.games.poker.decks.Deck;
import cfr.trainer.games.poker.decks.DeckKuhnShuffled;
import cfr.trainer.games.poker.decks.DeckStandardUnShuffled;

public class KuhnPoker extends BaseTwoPlayerPokerGame {

	public KuhnPoker() {
		super(BettingLimit.LIMIT, 1);
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

	@Override
	public List<Game> getListOfGamesWithAllPossibleChanceNodes() {
		int player0 = 0;
		int player1 = 1;

		List<Game> games = new ArrayList<Game>();

		for (int i = 9; i < 12; i++) {
			Hand hand0 = new HandSingleCard(new Card(i));
			for (int j = 9; j < 12; j++) {
				if (i == j) {
					continue;
				}
				Hand hand1 = new HandSingleCard(new Card(j));

				KuhnPoker game = new KuhnPoker();
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

}
