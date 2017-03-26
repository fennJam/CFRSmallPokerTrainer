package cfr.trainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import poker.BetRound;
import poker.Board;
import poker.Card;
import poker.Deck;
import poker.GameType;
import poker.Hand;
import poker.HandSingleCard;
import poker.Pot;

public class PokerTrainerFirstGo {
	public static final Random random = new Random();
	public TreeMap<String, Node> nodeMap = new TreeMap<String, Node>();
	GameType gameType;
	static final int MAX_NUM_OF_RAISES_PER_ROUND = 3;
	static Double[] raiseActions = { 0.05, 0.10, 0.15, 0.20, 0.25, 0.33, 0.40, 0.50, 0.66, 1.00 };

	public static void main(String[] args) {
		int iterations = 1;
		// cfr element only written for two players
		new PokerTrainerFirstGo().train(iterations, 2, GameType.SINGLE_CARD);
	}

	public void train(int iterations, int NumOfPlayers, GameType gameType) {
		double util = 0;
		this.gameType = gameType;
		Deck deck = new Deck();
		for (int i = 0; i < iterations; i++) {
			deck.resetDeck();
			Board board = new Board(gameType);
			Map<Integer, Hand> hands = dealCards(deck, 2);
			Pot pot = new Pot(hands.keySet());
			BetRound betRound = BetRound.PRETURN;
			util += cfr(hands, board, pot, deck, betRound, "D", 1, 1);
		}
		System.out.println("Average game value: " + util / iterations);
		for (Node n : nodeMap.values())
			System.out.println(n);
	}

	private double cfr(Map<Integer, Hand> hands, Board board, Pot pot, Deck deck, BetRound betRound, String betHistory,
			double p0, double p1) {
		String playsInThisBettingRound = betHistory.substring(betHistory.lastIndexOf('D'));
		int noOfPlaysThisBettingRound = playsInThisBettingRound.length();
		int numberOfRaisesThisBettingRound = playsInThisBettingRound.replace("C", "").length();
		boolean isCall = (noOfPlaysThisBettingRound > 2 && betHistory.endsWith("C"));
		int player = noOfPlaysThisBettingRound % 2;
		int opponent = 1 - player;

		// Fold
		if (betHistory.endsWith("F")) {
			return pot.getPlayersContributionToPot(opponent);
		} else if (isCall) {
			if (betRound == BetRound.RIVER) {
				return PayOffCalculator.getPayOffsForTwoPlayerGame(hands, board, pot, gameType).get(player);
			} else {
				// Deal a card
				betHistory += 'D';
				betRound = betRound.next();
			}
		}

		// Get Node
		String cardHistory = new CardHistoryBuilder((HandSingleCard) hands.get(player), board).toString();
		// System.out.println(cardHistory);
		String infoSet = cardHistory + betHistory;
		// System.out.println(infoSet);
		Node node = nodeMap.get(infoSet);
		if (node == null) {
			node = new Node(infoSet, numberOfRaisesThisBettingRound < MAX_NUM_OF_RAISES_PER_ROUND);
			// System.out.println("new Node (" + infoSet + ", "
			// + (numberOfRaisesThisBettingRound < MAX_NUM_OF_RAISES_PER_ROUND)
			// + ") ");
			node.infoSet = infoSet;
			nodeMap.put(infoSet, node);
		}
		// recursively call cfr

		double[] strategy = node.getStrategy(player == 0 ? p0 : p1);
		int actionsAvailable = node.numOfActions;
		double[] util = new double[actionsAvailable];
		double nodeUtil = 0;
		int betToCall = pot.getPlayersContributionToPot(opponent) - pot.getPlayersContributionToPot(player);
//		System.out.println("actionsAvailable " + actionsAvailable);
		for (int a = 0; a < actionsAvailable; a++) {

			String newAction = node.getAction(a);
//			System.out.println("newAction " + newAction);
//			System.out.println("noOfPlaysThisBettingRound " + noOfPlaysThisBettingRound);
			if (newAction == "C" && noOfPlaysThisBettingRound > 1) {
				pot.addBet(player, betToCall);
//				System.out.println("newAction Call");
			} else if (newAction == "C" && noOfPlaysThisBettingRound == 1) {
//				System.out.println("newAction Check");
				// Check
			} else if (newAction == "F") {
//				System.out.println("newAction Fold");
			} else {
				Integer actionIndex = Integer.valueOf(newAction);
				Double percentageOfLimitToRaise = raiseActions[actionIndex];
				System.out.println("newAction raise " + percentageOfLimitToRaise + "% of pot");
				Double bet = (pot.potLimitMaxBet() * percentageOfLimitToRaise);
				pot.addBet(player, bet.intValue());
			}
			String nextHistory = betHistory + newAction;
//			System.out.println("nextHistory " + nextHistory);

			util[a] = player == 0 ? -cfr(hands, board, pot, deck, betRound, nextHistory, p0 * strategy[a], p1)
					: -cfr(hands, board, pot, deck, betRound, nextHistory, p0, p1 * strategy[a]);

			nodeUtil += strategy[a] * util[a];
		}
		// compute cfr

		for (int a = 0; a < actionsAvailable; a++) {
			double regret = util[a] - nodeUtil;
			node.regretSum[a] += (player == 0 ? p1 : p0) * regret;
		}

		return nodeUtil;
	}

	private String buildInfoSet(Hand hand, String history) {
		if (gameType == GameType.SINGLE_CARD) {
			String infoSet = hand.getCard(0).getHeight() + " - " + history;
		}
		return null;
	}

	private Map<Integer, Hand> dealCards(Deck deck, int numberOfPlayers) {
		Map<Integer, Hand> hands = new HashMap<Integer, Hand>();
		if (gameType == GameType.SINGLE_CARD || gameType == GameType.RHODE_ISLAND) {
			for (int player = 0; player < numberOfPlayers; player++) {
				hands.put(player, new HandSingleCard(deck.nextCard()));
			}
			return hands;
		}
		return null;

	}

	private boolean isTerminalNodeForBettingRound(String betHistoryForThisRound) {
		int plays = betHistoryForThisRound.length();
		if (plays > 1) {
			boolean folded = betHistoryForThisRound.endsWith("f");
			boolean called = betHistoryForThisRound.endsWith("c");

			return (folded || called);
		}
		return false;
	}
}