package cfr.trainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cfr.trainer.games.Game;
import cfr.trainer.games.GameDescription;
import cfr.trainer.games.GameFactory;
import cfr.trainer.node.InfoSetFactory;
import cfr.trainer.node.Node;

public class VanillaCFRTrainer {

	public Map<String, Node> nodeMap = new HashMap<>();
	double util = 0;
	double averageGameValue = 0;

	public static void main(String[] args) throws Exception {
		int iterations = 200000;
		new VanillaCFRTrainer().train(GameDescription.KUHN_POKER, iterations);
	}

	public void train(GameDescription gameType, int iterations) throws Exception {
		Game gameStructure = GameFactory.setUpGame(gameType,3);
		List<List<Integer>> validChanceCombinations = gameStructure.getListOfValidChanceCombinations();
		for (int i = 0; i < iterations; i++) {
			for (List<Integer> chanceCombination : validChanceCombinations) {
				Game game = GameFactory.setUpGame(gameType,3);
				game.setValidChanceCombinations(chanceCombination);
				game.startGame();
				util += cfr(game, 1, 1);
			}
		}
		averageGameValue = util / (iterations * validChanceCombinations.size());
		System.out.println("Average game value: " + averageGameValue);
		for (Entry<String, NodeImpl> n : nodeMap.entrySet())
			System.out.println(n.getKey() + " : " + n.getValue());
	}

	private double cfr(Game game, double p0, double p1) throws Exception {

		if (game.isAtTerminalNode()) {
			return game.getPayOffs().get(game.getPlayerToAct());
		}
		
		if(game.isAtChanceNode()){
			game.performChanceAction();
		}

		// Get Node
		String nodeId = game.getNodeIdWithActionMemory();
//		String nodeId = game.getNodeIdWithGameState();

		Node node = nodeMap.get(nodeId);
		if (node == null) {
			node = InfoSetFactory.buildInformationSet(nodeId,game);
			nodeMap.put(nodeId, node);
//			nodeCount++;
//			System.out.println("Node Count : "+nodeCount);
		}
		// recursively call cfr
		int player = game.getPlayerToAct();
		double[] strategy = node.recalculateStrategy(player == 0 ? p0 : p1);
		int actionsAvailable = node.getActions().length;
		double[] util = new double[actionsAvailable];
		double nodeUtil = 0;

		for (int action = 0; action < actionsAvailable; action++) {
			Game copyOfGame = GameFactory.copyGame(game);
			copyOfGame.performAction(player, node.getActions()[action]);

			util[action] = player == 0 ? -cfr(copyOfGame, p0 * strategy[action], p1)
					: -cfr(copyOfGame, p0, p1 * strategy[action]);

			nodeUtil += strategy[action] * util[action];
		}

		// compute cfr
		double[] regretSum = node.getRegretSum();
		for (int a = 0; a < actionsAvailable; a++) {
			double regret = util[a] - nodeUtil;
			regretSum[a] += (player == 0 ? p1 : p0) * regret;
		}

		return nodeUtil;
	}

	public double getAverageGameValue() {
		return averageGameValue;
	}

	public Map<String, Node> getNodeMap() {
		return nodeMap;
	}
}