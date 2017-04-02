package cfr.trainer;

import java.util.ArrayList;
import java.util.List;

import cfr.poker.Board;
import cfr.poker.Card;
import cfr.poker.CardHeight;
import cfr.poker.CardSuit;
import cfr.poker.Hand;
import cfr.poker.HandSingleCard;
import cfr.poker.HandType;

public class CardHistoryBuilder {
	boolean playerSuitedWithTurn = true;
	boolean riverSuitedWithTurn = true;
	List<CardHeight> cardRanks = new ArrayList<CardHeight>();

	public CardHistoryBuilder(HandSingleCard hand) {
		cardRanks.add(hand.getRank());
	}

	public CardHistoryBuilder(Hand hand, Board board) {
		if (hand.getHandType().equals(HandType.SINGLECARD)) {
			HandSingleCard handSingleCard = (HandSingleCard) hand;
			// Add the players hand to the history
			CardSuit playerSuit = handSingleCard.getSuit();
			cardRanks.add(handSingleCard.getRank());
			// Add the board to the history

			if (board.getNumberOfTurnedCards() != 0 && playerSuit != board.getCard(0).getSuit()) {
				playerSuitedWithTurn = false;
			}
			riverSuitedWithTurn = board.isSuited();
			cardRanks.addAll(board.getTurnedCardsRanks());
		}else{
			throw new Error("Card History Builder not implemented for handType: "+hand.getHandType());
		}
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
