package cfr.trainer;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cfr.trainer.games.Game;
import cfr.trainer.games.GameDescription;
import cfr.trainer.games.GameFactory;
import cfr.trainer.node.InfoSetFactory;
import cfr.trainer.node.Node;

public class CFRPlusTrainer {

	public Map<String, double[]> regretMap = new HashMap<String, double[]>();
	double util = 0;
	int utilCount;
	double averageGameValue = 0;

	public static void main(String[] args) throws Exception {
		int iterations = 1;
		new CFRPlusTrainer().train(GameDescription.ROYAL_RHODE_ISLAND_HEADSUP_NO_LIMIT_POKER, iterations);
	}

	public void train(GameDescription gameDescription, int iterations) throws Exception {

		Game gameStructure = GameFactory.setUpGame(gameDescription, 3);
		List<List<Integer>> validChanceCombinations = gameStructure.getListOfValidChanceCombinations();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
			int comboSize = validChanceCombinations.size();
			int combo=0;
			for (List<Integer> validCombo : validChanceCombinations) {
				combo++;
				printProgress(startTime, comboSize, combo);
				Game game = GameFactory.setUpGame(gameDescription, 2);
				game.startGame();
				game.setValidChanceCombinations(validCombo);
				util += cfrPlus(game, 1, 1, i);
//				util += cfrPlus(game, 1, 1,1, i);
			}
		}
		averageGameValue = util / (iterations * validChanceCombinations.size() * 2);
		System.out.println("Average game value: " +averageGameValue+ "\n Nodes: " + regretMap.size());
		writeStrategyMapToJSONFile(getStrategyMap(),gameDescription);
	}

	private double cfrPlus(Game game, double p0, double p1, int iteration) throws Exception {
		if (game.isAtTerminalNode()) {
			return game.getPayOffs().get(game.getPlayerToAct());
		}
		if (game.isAtChanceNode()) {
			game.performChanceAction();
		}

		// Get Node
		String nodeId = game.getNodeIdWithActionMemory();

		double[] regretSums = regretMap.get(nodeId);
		if (regretSums == null) {
			Node node = InfoSetFactory.buildInformationSet(nodeId, game);
			regretSums = new double[node.numOfActions()];
			regretMap.put(nodeId, regretSums);
		}
		// recursively call cfr
		int player = game.getPlayerToAct();

		double[] strategy = calculateStrategy(regretSums);
		int actionsAvailable = regretSums.length;
		double[] util = new double[actionsAvailable];
		double nodeUtil = 0;

		for (int action = 0; action < actionsAvailable; action++) {
			Game copyOfGame = GameFactory.copyGame(game);
			copyOfGame.performAction(player, game.getPossibleActions().get(action));

			util[action] = player == 0 ? -cfrPlus(copyOfGame, p0 * strategy[action], p1, iteration)
					: -cfrPlus(copyOfGame, p0, p1 * strategy[action], iteration);

			nodeUtil += strategy[action] * util[action];
		}

		// compute cfr
//		if (playerToTrain == player) {
			for (int a = 0; a < actionsAvailable; a++) {
				double regret = util[a] - nodeUtil;
				double weightedRegret = regret* iteration;
				double regretSum = (regretSums[a] + (player == 0 ? p1 : p0) * weightedRegret);
				regretSums[a] = Math.max(regretSum, 0);
			}
//		}
		return nodeUtil;
	}

	public double getAverageGameValue() {
		return averageGameValue;
	}

	public Map<String, double[]> getRegretMap() {
		return regretMap;
	}

	private void writeStrategyMapToJSONFile(Map<String, double[]> regretMap,GameDescription gameDescription)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File("C:\\Users\\James\\Desktop\\StrategyMaps\\CFRPlus+"+gameDescription+".json"), regretMap);
	}

	private double[] calculateStrategy(double[] regretSum) {
		int numOfActions = regretSum.length;
		double[] strategy = new double[numOfActions];
		double normalizingSum = 0;
		for (int a = 0; a < numOfActions; a++) {
			// only taking positive regrets
			strategy[a] = regretSum[a] > 0 ? regretSum[a] : 0;
			normalizingSum += strategy[a];
		}
		for (int a = 0; a < numOfActions; a++) {
			if (normalizingSum > 0)
				strategy[a] /= normalizingSum;
			else
				strategy[a] = 1.0 / numOfActions;
		}
		return strategy;
	}

	public Map<String, double[]> getStrategyMap() {
		Map<String, double[]> strategyMap = new HashMap<>();
		for (Entry<String, double[]> entry : regretMap.entrySet()) {
			strategyMap.put(entry.getKey(), calculateStrategy(entry.getValue()));
		}
		return strategyMap;
	}
	
	
	private static void printProgress(long startTime, long total, long current) {
	    long eta = current == 0 ? 0 : 
	        (total - current) * (System.currentTimeMillis() - startTime) / current;

	    String etaHms = current == 0 ? "N/A" : 
	            String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
	                    TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
	                    TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

	    StringBuilder string = new StringBuilder(140);   
	    int percent = (int) (current * 100 / total);
	    string
	        .append('\r')
	        .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
	        .append(String.format(" %d%% [", percent))
	        .append(String.join("", Collections.nCopies(percent, "=")))
	        .append('>')
	        .append(String.join("", Collections.nCopies(100 - percent, " ")))
	        .append(']')
	        .append(String.join("", Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
	        .append(String.format(" %d/%d, ETA: %s", current, total, etaHms));

	    System.out.print(string);
	}
}
