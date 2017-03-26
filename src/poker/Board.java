package poker;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private Card[] cards;
	private Boolean[] isVisible;
	private GameType gameType;

	public Board() {
	}

	public Board(GameType gameType, Deck deck) {
		if (gameType == GameType.SINGLE_CARD) {
			this.gameType = GameType.SINGLE_CARD;
			cards = new Card[0];
			isVisible = new Boolean[0];
		} else if (gameType == GameType.TWO_CARD) {
			this.gameType = GameType.TWO_CARD;
			cards = new Card[1];
			isVisible = new Boolean[1];
			cards[0] = deck.nextCard();
			isVisible[0] = false;
		} else if (gameType == GameType.RHODE_ISLAND) {
			this.gameType = GameType.RHODE_ISLAND;
			cards = new Card[2];
			isVisible = new Boolean[2];
			cards[0] = deck.nextCard();
			isVisible[0] = false;
			cards[1] = deck.nextCard();
			isVisible[1] = false;
		} else {
		}
	}

	public Board(GameType gameType) {
		setGameType(gameType);
	}

	public Board setGameType(GameType gameType) {
		if (gameType == GameType.SINGLE_CARD) {
			cards = new Card[0];
			isVisible = new Boolean[0];
		} else if (gameType == GameType.TWO_CARD) {
			cards = new Card[1];
			isVisible = new Boolean[1];
			isVisible[0] = false;
		} else if (gameType == GameType.RHODE_ISLAND) {
			cards = new Card[2];
			isVisible = new Boolean[2];
			isVisible[0] = false;
			isVisible[1] = false;
		} else {
		}
		return this;
	}

	public boolean addTurnedCard(Card card) {
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] == null) {
				cards[i] = card;
				isVisible[i] = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a list of the turned board cards
	 */
	public List<Card> getTurnedCards() {
		List<Card> visibleCards = new ArrayList<Card>();
		for (int i = 0; i < isVisible.length; i++) {
			if (isVisible[i] == true) {
				visibleCards.add(cards[i]);
			}
		}
		return visibleCards;
	}

	/**
	 * Returns a specific card on this board
	 */
	public Card getCard(int index) {
		return cards[index];
	}

	/**
	 * Returns the number of cards on the board
	 */
	public int getNumberOfTurnedCards() {
		return getTurnedCards().size();
	}

	/**
	 * Turn the next unturned card
	 */
	public boolean turnNextCard() {
		for (int i = 0; i < isVisible.length; i++) {
			if (isVisible[i] == false) {
				isVisible[i] = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the board is of size 0,1 or all the cards are suited
	 */
	public boolean isSuited() {
		List<Card> turnedCards = getTurnedCards();
		if (turnedCards.size() > 1) {
			for (int i = 0; i < turnedCards.size() - 1; i++) {
				if (turnedCards.get(i).getSuit() != turnedCards.get(i + 1).getSuit()) {
					return false;
				}
			}
		}
		return true;
	}

	public List<CardSuit> getTurnedSuits() {
		List<CardSuit> suits = new ArrayList<CardSuit>();
		List<Card> turnedCards = getTurnedCards();
		for (int i = 0; i < turnedCards.size(); i++) {
			suits.add(turnedCards.get(i).getSuit());

		}
		return suits;
	}

	public List<CardHeight> getTurnedCardsRanks() {
		List<CardHeight> ranks = new ArrayList<CardHeight>();
		List<Card> turnedCards = getTurnedCards();
		for (int i = 0; i < turnedCards.size(); i++) {
			ranks.add(turnedCards.get(i).getHeight());

		}
		return ranks;
	}

	/**
	 * Returns a string representation of the cards that are turned on the board
	 */
	public String toString() {
		List<Card> turnedCards = getTurnedCards();
		if (turnedCards.size() == 0) {
			return "No cards turned";
		} else {
			String str = "[";
			for (int i = 0; i < (turnedCards.size() - 1); i++)
				str += turnedCards.get(i).toString() + ",";
			str += turnedCards.get(turnedCards.size() - 1).toString() + "]";
			return str;
		}
	}

	public int getNoOfTurnedCards() {
		if (isVisible == null || isVisible.length == 0) {
			return 0;
		}
		int noOfTurnedCards = 0;
		for (boolean visible : isVisible) {
			if (visible) {
				noOfTurnedCards++;
			}
		}
		return noOfTurnedCards;
	}

	public int getNoOfUnturnedCards() {

		if (isVisible == null || isVisible.length == 0) {
			return 0;
		}

		int noOfUnturnedCards = 0;
		for (boolean visible : isVisible) {
			if (!visible) {
				noOfUnturnedCards++;
			}
		}
		return noOfUnturnedCards;
	}

	public Card[] getCards() {
		return cards;
	}

	public Boolean[] getIsVisible() {
		return isVisible;
	}

	public GameType getGameType() {
		return gameType;
	}

	public Board importBoardProperties(Board board) {
		if (board.getCards() == null || board.getIsVisible() == null) {
			this.cards = null;
			this.isVisible = null;
		} else {
			this.cards = board.getCards();
			this.isVisible = board.getIsVisible();
		}
		this.gameType = board.gameType;
		return this;
	}
}
