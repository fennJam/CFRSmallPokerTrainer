package cfr.trainer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cfr.trainer.games.Game;
import cfr.trainer.games.GameDescription;
import cfr.trainer.games.GameFactory;
import cfr.trainer.node.InfoSetFactory;
import cfr.trainer.node.NodeImpl;

public class CFRPlusTrainer {

	public Map<String, NodeImpl> nodeMap = new HashMap<String, NodeImpl>();
	double util = 0;
	int utilCount;
	double averageGameValue = 0;

	public static void main(String[] args) throws Exception {
		int iterations = 1;
		new CFRPlusTrainer().train(GameDescription.ROYAL_RHODE_ISLAND_HEADSUP_NO_LIMIT_POKER, iterations);
	}

	public void train(GameDescription gameDescription, int iterations) throws Exception {
		utilCount = 0;
		Game gameStructure = GameFactory.setUpGame(gameDescription,2);
		List<List<Integer>> validChanceCombinations = gameStructure.getListOfValidChanceCombinations();
		for (int i = 0; i < iterations; i++) {
			int combo =0;
			for (List<Integer> validCombo : validChanceCombinations) {
				combo++;
				System.out.println("Combination :"+combo );
				Game game = GameFactory.setUpGame(gameDescription,2);
				game.startGame();
				game.setValidChanceCombinations(validCombo);
				util += cfrPlus(game, 1, 1, 0);
				util += cfrPlus(game, 1, 1, 1);
				utilCount += 2;
			}
		}
		averageGameValue = util / (iterations * validChanceCombinations.size()*2);
		System.out.println("Average game value: " + util / iterations + "\n Nodes: " + nodeMap.size());
		writeStrategyMapToJSONFile(nodeMap);
	}

	private double cfrPlus(Game game, double p0, double p1, int playerToTrain) throws Exception {

		if (game.isAtTerminalNode()) {
			return game.getPayOffs().get(game.getPlayerToAct());
		}
		if (game.isAtChanceNode()) {
			game.performChanceAction();
		}

		// Get Node
		String nodeId = game.getNodeIdWithActionMemory();
//		String nodeId = game.getNodeIdWithGameState();

		NodeImpl node = nodeMap.get(nodeId);
		if (node == null) {
			node = InfoSetFactory.buildInformationSet(nodeId, game);
			nodeMap.put(nodeId, node);
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

			util[action] = player == 0 ? -cfrPlus(copyOfGame, p0 * strategy[action], p1, playerToTrain)
					: -cfrPlus(copyOfGame, p0, p1 * strategy[action], playerToTrain);

			nodeUtil += strategy[action] * util[action];
		}

		// compute cfr
		if (playerToTrain == player) {
			for (int a = 0; a < actionsAvailable; a++) {
				double regret = util[a] - nodeUtil;
				double regretSum = node.regretSum[a] + (player == 0 ? p1 : p0) * regret;
				node.regretSum[a] = Math.max(regretSum, 0);
			}
		}
		return nodeUtil;
	}

	public double getAverageGameValue() {
		return averageGameValue;
	}

	public Map<String, NodeImpl> getNodeMap() {
		return nodeMap;
	}
	
	private void writeStrategyMapToJSONFile(Map nodeMap)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File("C:\\Users\\James\\Desktop\\StrategyMaps\\user.json"), nodeMap);
	}
}
