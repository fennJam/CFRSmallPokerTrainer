package cfr.trainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import hand.evaluators.SmallHandEvaluator;
import poker.Board;
import poker.Card;
import poker.GameType;
import poker.Hand;
import poker.HandSingleCard;
import poker.Pot;

public class PayOffCalculator {

	// return hand strengths for all hands passed in through the constructor
	public static Map<Integer, Integer> getHandStrengths(Map<Integer, Hand> hands, Board board, GameType gameType) {
		Map<Integer, Integer> handStrengths = new HashMap<Integer, Integer>();
		// store the hand and table cards together
		for (Entry<Integer, Hand> playerHand : hands.entrySet()) {
			handStrengths.put(playerHand.getKey(), getHandStrength(playerHand.getValue(), board, gameType));
		}
		return handStrengths;

	}

	public static Integer getHandStrength(Hand playerHand, Board board, GameType gameType) {
		if (gameType == GameType.RHODE_ISLAND) {
			// store the hand and table cards together
			HandSingleCard playerSingleCardHand = (HandSingleCard) playerHand;
			long combinationCode = 0l;
			combinationCode = combinationCode | playerSingleCardHand.getCard().getNumber();
			for (Card card : board.getTurnedCards())
				combinationCode = combinationCode | card.getNumber();

			return SmallHandEvaluator.evaluateRhodeIslandHand(combinationCode);

		} else if (gameType == GameType.SINGLE_CARD) {
			// store the hand and table cards together
			HandSingleCard playerSingleCardHand = (HandSingleCard) playerHand;
			long combinationCode = 0l;
			combinationCode = combinationCode | playerSingleCardHand.getCard().getNumber();

			return SmallHandEvaluator.evaluateSingleCardPokerHand(combinationCode);

		}else if (gameType == GameType.TWO_CARD) {

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
			GameType gameType) {
		Map<Integer, Integer> payoffs = new HashMap<Integer, Integer>();
		Map<Integer, Integer> handStrengths = getHandStrengths(hands, board, gameType);
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

}
