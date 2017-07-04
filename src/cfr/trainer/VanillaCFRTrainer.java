package cfr.trainer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cfr.trainer.games.Game;
import cfr.trainer.games.GameDescription;
import cfr.trainer.games.GameFactory;
import cfr.trainer.node.InfoSetFactory;
import cfr.trainer.node.Node;

public class VanillaCFRTrainer {

	public Map<String, Node> nodeMap = new HashMap<>();
	double util = 0;
	double averageGameValue = 0;
	int nodeCount = 0;

	public static void main(String[] args) throws Exception {
		int iterations = 1;
		new VanillaCFRTrainer().train(GameDescription.ROYAL_RHODE_ISLAND_HEADSUP_NO_LIMIT_POKER, iterations);
	}

	public void train(GameDescription gameType, int iterations) throws Exception {
		Game gameStructure = GameFactory.setUpGame(gameType, 3);
		int[][] validChanceCombinations = gameStructure.getListOfValidChanceCombinations();
		for (int i = 0; i < iterations; i++) {
			int chanceCombo = 0;
			for (int[] chanceCombination : validChanceCombinations) {
				chanceCombo++;
//				System.out.println("Chance Combo:"+chanceCombo);
				Game game = GameFactory.setUpGame(gameType, 2);
				game.setValidChanceCombinations(chanceCombination);
				game.startGame();
				util += cfr(game, 1, 1);
			}
		}
		averageGameValue = util / (iterations * validChanceCombinations.length);
		System.out.println("Average game value: " + averageGameValue);
		writeStrategyMapToJSONFile(nodeMap);
	}

	private double cfr(Game game, double p0, double p1) throws Exception {

		if (game.isAtTerminalNode()) {
			return game.getPayOffs().get(game.getPlayerToAct());
		}

		if (game.isAtChanceNode()) {
			game.performChanceAction();
		}

		// Get Node
		String nodeId = game.getNodeIdWithActionMemory();
		// String nodeId = game.getNodeIdWithGameState();

		Node node = nodeMap.get(nodeId);
		if (node == null) {
			node = InfoSetFactory.buildInformationSet(nodeId, game);
			nodeMap.put(nodeId, node);
			nodeCount++;
//			System.out.println("Node Count : " + nodeCount);
		}
		// recursively call cfr
		int player = game.getPlayerToAct();
		double[] strategy = node.recalculateStrategy(player == 0 ? p0 : p1);
		int actionsAvailable = node.numOfActions();
		double[] util = new double[actionsAvailable];
		double nodeUtil = 0;

		for (int action = 0; action < actionsAvailable; action++) {
			Game copyOfGame = GameFactory.copyGame(game);
			copyOfGame.performAction(player, game.getPossibleActions().get(action));

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

	private void writeStrategyMapToJSONFile(Map nodeMap)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File("C:\\Users\\James\\Desktop\\StrategyMaps\\user.json"), nodeMap);
	}

}