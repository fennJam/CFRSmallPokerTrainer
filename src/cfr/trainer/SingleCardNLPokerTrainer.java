package cfr.trainer;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import cfr.games.PokerGame;
import cfr.games.PokerGameFactory;
import poker.Deck;
import poker.GameType;
import poker.HandSingleCard;

public class SingleCardNLPokerTrainer {
	private static final Map<String, Integer> NODE_ACTIONS_X_BET_STRATEGY;
	static {
		Map<String, Integer> strategyMap = new HashMap<String, Integer>();
		strategyMap.put("1", 50);
		strategyMap.put("2", 100);
		strategyMap.put("3", 100);
		strategyMap.put("4", 100);
		strategyMap.put("5", 100);
		strategyMap.put("6", 100);
		strategyMap.put("7", 100);
		strategyMap.put("8", 100);
		strategyMap.put("9", 100);
		NODE_ACTIONS_X_BET_STRATEGY = Collections.unmodifiableMap(strategyMap);
	}

	public static final Random random = new Random();
	public TreeMap<String, Node> nodeMap = new TreeMap<String, Node>();


	public static void main(String[] args) {
		int iterations = 1000000;
		new SingleCardNLPokerTrainer().train(GameType.TWO_CARD, iterations);
	}

	public void train(GameType gameType, int iterations) {
		double util = 0;
		for (int i = 0; i < iterations; i++) {
			PokerGame game = PokerGameFactory.setUpGame(gameType, 2,1);
			game.dealCards(new Deck());
			util += cfr(game, "D", 1, 1);
			if(i%10000==0){
			System.out.println("Average game value: " + new BigDecimal(util / iterations).toPlainString());
			}
		}
		System.out.println("Average game value: " + util / iterations);
//		for (Node n : nodeMap.values())
//			System.out.println(n);
	}

	private double cfr(PokerGame game, String history, double p0, double p1) {
		String playsInThisBettingRound = history.substring(history.lastIndexOf('D'));
		int player = playsInThisBettingRound.length() % 2;
		int opponent = 1 - player;
		
//		System.out.println("hands,"+game.getHand(player)+" Pot pot, "+game.getPot().getTotalPotSize()+" String history, "+history);

		if (history.endsWith("F")) {
			// fold
			return game.getPot().getPlayersContributionToPot(opponent);
		}
		if (playsInThisBettingRound.length() > 1 && history.endsWith("C")) {
			// Call
			if (game.isAtTerminalBettingRound()) {
				return game.getPayOffs().get(player);

			} else {
				game.turnNextCard();
				history += "D";
			}
		}

		// Get Node
		String cardHistory = new CardHistoryBuilder((HandSingleCard) game.getHand(player), game.getBoard()).toString();

		String infoSet = cardHistory + history;

		Node node = nodeMap.get(infoSet);
		if (node == null) {
			node = new Node(infoSet, isBelowMaxNumberOfRaises(playsInThisBettingRound, game.getRaisesPerBettingRound()));
			node.infoSet = infoSet;
			nodeMap.put(infoSet, node);
		}
		// recursively call cfr

		double[] strategy = node.getStrategy(player == 0 ? p0 : p1);
		int actionsAvailable = node.numOfActions;
		double[] util = new double[actionsAvailable];
		double nodeUtil = 0;
		int amountToCall = game.getPot().getPlayersContributionToPot(opponent) - game.getPot().getPlayersContributionToPot(player);

		for (int a = 0; a < actionsAvailable; a++) {
			PokerGame copyOfGame = PokerGameFactory.copyGame(game);
			String action = node.getAction(a);
			if (action.equals("F")) {
			} else if (action.equals("C")) {
				// call/check
				copyOfGame.bet(player, amountToCall);
			} else {
				Integer bet = Integer.parseInt(action);
				copyOfGame.bet(player, bet);
			}
			String nextHistory = history + action;
			util[a] = player == 0 ? -cfr(copyOfGame, nextHistory, p0 * strategy[a], p1)
					: -cfr(copyOfGame, nextHistory, p0, p1 * strategy[a]);
			nodeUtil += strategy[a] * util[a];
		}

		// compute cfr

		for (int a = 0; a < actionsAvailable; a++) {
			double regret = util[a] - nodeUtil;
			node.regretSum[a] += (player == 0 ? p1 : p0) * regret;
		}

		return nodeUtil;
	}

	public boolean isBelowMaxNumberOfRaises(String playsInThisBettingRound, int maxRaises) {
		return playsInThisBettingRound.replace("C", "").length() <= maxRaises;
	}


}
