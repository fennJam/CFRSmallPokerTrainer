package cfr.trainer.games.poker;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cfr.trainer.games.poker.PokerGameType;
import cfr.trainer.games.poker.hand_evaluators.SmallHandEvaluator;

public class PayOffCalculator {

	// return hand strengths for all hands passed in through the constructor
	public static Map<Integer, Integer> getHandStrengths(Map<Integer, Hand> hands, Board board, PokerGameType PokerGameType) {
		Map<Integer, Integer> handStrengths = new HashMap<Integer, Integer>();
		// store the hand and table cards together
		for (Entry<Integer, Hand> playerHand : hands.entrySet()) {
			handStrengths.put(playerHand.getKey(), getHandStrength(playerHand.getValue(), board, PokerGameType));
		}
		return handStrengths;

	}

	public static Integer getHandStrength(Hand playerHand, Board board, PokerGameType pokerGameType) {
		if (pokerGameType == PokerGameType.RHODE_ISLAND) {
			// store the hand and table cards together
			HandSingleCard playerSingleCardHand = (HandSingleCard) playerHand;
			long combinationCode = 0l;
			combinationCode = combinationCode | playerSingleCardHand.getCard().getNumber();
			for (Card card : board.getTurnedCards())
				combinationCode = combinationCode | card.getNumber();

			return SmallHandEvaluator.evaluateRhodeIslandHand(combinationCode);

		} else if (pokerGameType == PokerGameType.SINGLE_CARD) {
			// store the hand and table cards together
			HandSingleCard playerSingleCardHand = (HandSingleCard) playerHand;
			long combinationCode = 0l;
			combinationCode = combinationCode | playerSingleCardHand.getCard().getNumber();

			return SmallHandEvaluator.evaluateSingleCardPokerHand(combinationCode);

		}else if (pokerGameType == PokerGameType.TWO_CARD) {

			// store the hand and table cards together
			HandSingleCard playerSingleCardHand = (HandSingleCard) playerHand;
			long combinationCode = 0l;
			combinationCode = combinationCode | playerSingleCardHand.getCard().getNumber();
			for (Card card : board.getTurnedCards())
				combinationCode = combinationCode | card.getNumber();

			return SmallHandEvaluator.evaluateTwoCardPokerHand(combinationCode);

		
		}

		return null;
	}

	public static Map<Integer, Integer> getPayOffsForTwoPlayerGame(Map<Integer, Hand> hands, Board board, Pot pot,
			PokerGameType PokerGameType) {
		Map<Integer, Integer> payoffs = new HashMap<Integer, Integer>();
		Map<Integer, Integer> handStrengths = getHandStrengths(hands, board, PokerGameType);
		Integer player0Strength = handStrengths.get(0);
		Integer player1Strength = handStrengths.get(1);
		if (player0Strength > player1Strength) {
			Integer player1Loss = pot.getPlayersContributionToPot(1);
			payoffs.put(0, player1Loss);
			payoffs.put(1, -player1Loss);
		} else if (player0Strength < player1Strength) {
			Integer player0Loss = pot.getPlayersContributionToPot(0);
			payoffs.put(0, -player0Loss);
			payoffs.put(1, player0Loss);
		} else {
			payoffs.put(0, 0);
			payoffs.put(1, 0);
		}

		return payoffs;
	}
	
	
	// return hand strengths for all hands passed in through the constructor
	public static int[] getHandStrengths(int[] playerCards, int[] board, PokerGameType pokerGameType)  {
		int[] handStrengths = new int[2];
		// store the hand and table cards together
		for (int player=0 ; player<playerCards.length;player++) {
			handStrengths[player] = getHandStrength(playerCards[player], board, pokerGameType);
		}
		return handStrengths;

	}
	
	public static Integer getHandStrength(int playerCard, int[] board, PokerGameType pokerGameType) {
		if (pokerGameType == PokerGameType.RHODE_ISLAND) {
			// store the hand and table cards together
			long combinationCode = 0l;
			combinationCode = combinationCode | getCardNumber(playerCard);
			for (int boardCard : board)
				combinationCode = combinationCode | getCardNumber(boardCard);

			return SmallHandEvaluator.evaluateRhodeIslandHand(combinationCode);

		} else if (pokerGameType == PokerGameType.SINGLE_CARD) {
			// store the hand and table cards together
			long combinationCode = 0l;
			combinationCode = combinationCode |  getCardNumber(playerCard);

			return SmallHandEvaluator.evaluateSingleCardPokerHand(combinationCode);

		}else if (pokerGameType == PokerGameType.TWO_CARD) {

			// store the hand and table cards together
			long combinationCode = 0l;
			combinationCode = combinationCode |  getCardNumber(playerCard);
			for (int boardCard : board)
				combinationCode = combinationCode |  getCardNumber(boardCard);;

			return SmallHandEvaluator.evaluateTwoCardPokerHand(combinationCode);

		
		}

		return null;
	}
	
	/**
	 * Returns the number of the card as a long.
	 */
	public static long getCardNumber(int card)
	{
		int suitShift = card / 13;
		int heightShift = card % 13;
		return (1l << (16*suitShift + heightShift));
	}

	
	public static Map<Integer, Integer> getPayOffsForTwoPlayerLiteGame(int[] playerCards, int[] board, int[] pot,
			PokerGameType pokerGameType) {
		Map<Integer, Integer> payoffs = new HashMap<Integer, Integer>();
		int[] handStrengths = getHandStrengths(playerCards, board, pokerGameType);
		Integer player0Strength = handStrengths[0];
		Integer player1Strength = handStrengths[1];
		if (player0Strength > player1Strength) {
			Integer player1Loss = pot[1];
			payoffs.put(0, player1Loss);
			payoffs.put(1, -player1Loss);
		} else if (player0Strength < player1Strength) {
			Integer player0Loss = pot[0];
			payoffs.put(0, -player0Loss);
			payoffs.put(1, player0Loss);
		} else {
			payoffs.put(0, 0);
			payoffs.put(1, 0);
		}

		return payoffs;
	}
	
}
