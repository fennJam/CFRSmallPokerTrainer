package cfr.trainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import cfr.poker.games.PokerGame;
import cfr.poker.nodes.PokerInfoSetFactory;

public class MonteCarloCFRTrainer {

	public Map<String, NodeImpl> nodeMap = new HashMap<String, NodeImpl>();
	double util = 0;
	int iterations = 0;

	public static void main(String[] args) {
		int iterations = 20000000;
		new MonteCarloCFRTrainer().train(GameType.KUHN_POKER, iterations);
	}

	public void train(GameType gameType, int iterations) {
		this.iterations = iterations;
		for (int i = 0; i < iterations; i++) {
			Game game = GameFactory.setUpGame(gameType);
			game.startGame();
			util += cfr(game, 1, 1);
		}
		System.out.println("Average game value: " + util / iterations);
		for (Entry<String, NodeImpl> n : nodeMap.entrySet())
			System.out.println(n.getKey() + " : " + n.getValue());
	}

	private double cfr(Game game, double p0, double p1) {

		if (game.isAtTerminalNode()) {
			return game.getPayOffs().get(game.getPlayerToAct());
		}

		// Get Node
		String nodeId = game.getNodeId();

		NodeImpl node = nodeMap.get(nodeId);
		if (node == null) {
			// TODO remove poker references
			node = PokerInfoSetFactory.buildInformationSet((PokerGame) game);
			nodeMap.put(nodeId, node);
		}
		// recursively call cfr
		int player = game.getPlayerToAct();
		double[] strategy = node.recalculateStrategy(player == 0 ? p0 : p1);
		int actionsAvailable = node.getActions().length;
		double[] util = new double[actionsAvailable];
		double nodeUtil = 0;

		for (int action = 0; action < actionsAvailable; action++) {
			// TODO remove poker references
			Game copyOfGame = GameFactory.copyGame((PokerGame) game);
			copyOfGame.performAction(player, node.getActions()[action]);

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

	public double getAverageGameValue() {
		return (util / iterations);
	}

	public Map<String, NodeImpl> getNodeMap() {
		return nodeMap;
	}
}