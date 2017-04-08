package cfr.trainer;

import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import cfr.poker.Deck;
import cfr.poker.games.PokerGame;
import cfr.poker.nodes.PokerInfoSetFactory;

public class VanillaCFRTrainer {

	public static final Random random = new Random();
	public TreeMap<String, NodeImpl> nodeMap = new TreeMap<String, NodeImpl>();


	public static void main(String[] args) {
		int iterations = 1000;
		new VanillaCFRTrainer().train(GameType.SINGLECARD_HEADSUP_LIMIT_POKER, iterations);
	}

	public void train(GameType gameType, int iterations) {
		double util = 0;
		for (int i = 0; i < iterations; i++) {
			Game game = GameFactory.setUpGame(gameType);
			game.startGame();
			util += cfr(game, 1, 1);
		}
		System.out.println("Average game value: " + util / iterations);
		for (Entry<String, NodeImpl> n : nodeMap.entrySet())
			System.out.println(n.getKey()+" : "+n.getValue());
	}

	private double cfr(Game game, double p0, double p1) {

		if (game.isAtTerminalNode()) {
			return game.getPayOffs().get(game.getPlayerToAct());
		}

		// Get Node
		String nodeId = game.getNodeId();


		NodeImpl node = nodeMap.get(nodeId);
		if (node == null) {
//			TODO remove poker references
//			Not returning correct node
			node = PokerInfoSetFactory.buildInformationSet((PokerGame) game);
			nodeMap.put(nodeId, node);
		}
		// recursively call cfr
		int player = game.getPlayerToAct();
		double[] strategy = node.recalculateStrategy(player == 0 ? p0 : p1);
		int actionsAvailable = node.getActions().length;
		double[] util = new double[actionsAvailable];
		double nodeUtil = 0;

		for (int action=0;action<actionsAvailable;action++) {
//			TODO remove poker references
			Game copyOfGame = GameFactory.copyGame((PokerGame)game);
			copyOfGame.performAction(player, node.getActions()[action]);
//			System.out.println("copyOfGame.performAction(player, node.getActions()[action]);"+player+" : "+node.getActions()[action]);

			util[action] = player == 0 ? -cfr(copyOfGame, p0 * strategy[action], p1)
					: -cfr(copyOfGame, p0, p1 * strategy[action]);
			nodeUtil += strategy[action] * util[action];
		}

		// compute cfr

		for (int a = 0; a < actionsAvailable; a++) {
			double regret = util[a] - nodeUtil;
			node.regretSum[a] += (player == 0 ? p1 : p0) * regret;
		}

		return nodeUtil;
	}


}
