package cfr.poker;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cfr.poker.PokerGameType;
import hand.evaluators.SmallHandEvaluator;

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

}
