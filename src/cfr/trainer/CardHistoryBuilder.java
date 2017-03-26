package cfr.trainer;

import java.util.ArrayList;
import java.util.List;

import poker.Board;
import poker.Card;
import poker.CardHeight;
import poker.CardSuit;
import poker.Hand;
import poker.HandSingleCard;

public class CardHistoryBuilder {
	boolean playerSuitedWithTurn = true;
	boolean riverSuitedWithTurn = true;
	List<CardHeight> cardRanks = new ArrayList<CardHeight>();

	public CardHistoryBuilder(HandSingleCard hand) {
		cardRanks.add(hand.getRank());
	}

	public CardHistoryBuilder(HandSingleCard hand, Board board) {
		// Add the players hand to the history
		CardSuit playerSuit = hand.getSuit();
		cardRanks.add(hand.getRank());
		// Add the board to the history

		if (board.getNumberOfTurnedCards() != 0 && playerSuit != board.getCard(0).getSuit()) {
			playerSuitedWithTurn = false;
		}
		riverSuitedWithTurn = board.isSuited();
		cardRanks.addAll(board.getTurnedCardsRanks());
	}

	public boolean isPlayerSuitedWithTurn() {
		return playerSuitedWithTurn;
	}

	public boolean isRiverSuitedWithTurn() {
		return riverSuitedWithTurn;
	}

	public List<CardHeight> getCardRanks() {
		return cardRanks;
	}

	/**
	 * Returns a string representation of the card history
	 */
	public String toString() {
		String str = "[" + (playerSuitedWithTurn ? "S" : "O") + ", " + (riverSuitedWithTurn ? "S" : "O") + ", ";
		for (int i = 0; i < cardRanks.size() - 1; i++)
			str += cardRanks.get(i).toString() + ",";

		str += cardRanks.get(cardRanks.size() - 1).toString() + "]";
		return str;
	}
}
