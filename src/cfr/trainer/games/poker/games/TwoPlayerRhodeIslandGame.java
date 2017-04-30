package cfr.trainer.games.poker.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfr.trainer.games.Game;
import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.decks.Deck;

public class TwoPlayerRhodeIslandGame extends BaseTwoPlayerPokerGame {

	public TwoPlayerRhodeIslandGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		super(bettingLimit, raisesPerBettingRound);
		this.pokerGameType = PokerGameType.RHODE_ISLAND;
		this.betRound = BetRound.PRETURN;
	}

	@Override
	public Map<Integer, Hand> dealCards(Deck deck) {
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
	public List<Game> getListOfGamesWithAllPossibleChanceNodes() {
		int player0 = 0;
		int player1 = 1;

		List<Game> games = new ArrayList<Game>();

		for (int player1Card = 0; player1Card < 52; player1Card++) {
			Hand hand0 = new HandSingleCard(new Card(player1Card));
			for (int player2Card = 0; player2Card < 52; player2Card++) {
				if (player1Card == player2Card) {
					continue;
				}

				Hand hand1 = new HandSingleCard(new Card(player2Card));

				for (int boardCard1 = 0; boardCard1 < 52; boardCard1++) {
					if (boardCard1 == player2Card || boardCard1 == player1Card) {
						continue;
					}
					for (int boardCard2 = 0; boardCard2 < 52; boardCard2++) {
						if (boardCard2 == boardCard1 || boardCard2 == player2Card || boardCard2 == player1Card) {
							continue;
						}

						TwoPlayerRhodeIslandGame game = new TwoPlayerRhodeIslandGame(bettingLimit, raisesPerBettingRound);
						game.startGame();
						Map<Integer, Hand> newHands = new HashMap<Integer, Hand>();
						newHands.put(player0, hand0);
						newHands.put(player1, hand1);

						Board board = new Board();
						board.setPokerGameType(PokerGameType.TWO_CARD);
						board.setCard(new Card(boardCard1), 0, false);
						board.setPokerGameType(PokerGameType.TWO_CARD);
						board.setCard(new Card(boardCard2), 1, false);

						game.setHands(newHands);
						games.add(game);
					}
				}
			}
		}
		return games;
	}

}
