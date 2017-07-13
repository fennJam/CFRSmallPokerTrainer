package cfr.trainer.xlite;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cfr.trainer.games.poker.games.xlite.KuhnPokerXLite;
import cfr.trainer.games.poker.games.xlite.PokerGameXLite;

public class CFRPlusTrainerXLite {

	public Map<String, double[]> regretMap = new UnifiedMap<String, double[]>();
	double util = 0;
	int utilCount;
	double averageGameValue = 0;
	static PokerGameXLite game =  new KuhnPokerXLite();

	public static void main(String[] args) throws Exception {
		int iterations = 1;
//		PokerGameXLite game =  new RoyalRhodeIslandNL10ChipXLite();
		new CFRPlusTrainerXLite().train(game,iterations);
	}

	public void train(PokerGameXLite game,int iterations) throws Exception {

		int[][] validChanceCombinations = game.getListOfValidChanceCombinations();
//		long startTime = System.currentTimeMillis();
		for (int i = 0; i < iterations; i++) {
//			long totalCombos = validChanceCombinations.length;
//			int combo = 0;
			for (int[] validCombo : validChanceCombinations) {
//				combo++;
//				printProgress(startTime, totalCombos, combo);
				// System.out.println("Combination :" + combo);

				String actionsTaken = "D";
				int[] board ={};
				int[]hands ={validCombo[0],validCombo[1]};
				int[][] deal = {hands,board};
				util += cfrPlus(actionsTaken,deal, 1, 1,0, i);
				util += cfrPlus(actionsTaken,deal,  1, 1,1, i);
			}
		}
		averageGameValue = util / (iterations * validChanceCombinations.length*2);
		System.out.println("Average game value: " + averageGameValue + "\n Nodes: " + regretMap.size());
		writeStrategyMapToJSONFile(regretMap);
	}

	private double cfrPlus(String actionsTaken,int[][] deal, double p0, double p1,int playerToTrain, int iteration) throws Exception {
	game.setActionsTaken(actionsTaken, deal);
		if (game.isAtTerminalNode()) {
			return game.getPayoffs()[game.getPlayerToAct()];
		}
		if (game.isAtChanceNode()) {
			actionsTaken+="D";
		}

		// Get Node
		String nodeId = game.getNodeId();
		// String nodeId = game.getNodeIdWithGameState();
		char[] actionsAvailable = game.getAvailableActions();
		int noOfActionsAvailable = actionsAvailable.length; 
		
		double[] regretSums = regretMap.get(nodeId);
		if (regretSums == null) {
			regretSums = new double[noOfActionsAvailable];
			regretMap.put(nodeId, regretSums);
		}
		// recursively call cfr
		int player = game.getPlayerToAct();

		double[] strategy = calculateStrategy(regretSums);
		double[] util = new double[noOfActionsAvailable];
		double nodeUtil = 0;

		for (int actionIndex=0;actionIndex<noOfActionsAvailable;actionIndex++) {
			String newActionsTaken= actionsTaken+actionsAvailable[actionIndex];

			util[actionIndex] = player == 0 ? -cfrPlus( newActionsTaken,deal, p0 * strategy[actionIndex], p1, playerToTrain,iteration)
					: -cfrPlus(newActionsTaken, deal, p0, p1 * strategy[actionIndex],playerToTrain, iteration);

			nodeUtil += strategy[actionIndex] * util[actionIndex];
		}

		// compute cfr
		 if (playerToTrain == player) {
		for (int a = 0; a < noOfActionsAvailable; a++) {
			double regret = util[a] - nodeUtil;
			// double regretSum = regretSums[a] + (player == 0 ? p1 : p0) *
			// regret;
			// node.regretSum[a] = Math.max(regretSum, 0);
			double weightedRegret = regret * iteration;
			double regretSum = (regretSums[a] + (player == 0 ? p1 : p0) * weightedRegret);
			regretSums[a] = Math.max(regretSum, 0);
			 }
		}
		return nodeUtil;
	}

	public double getAverageGameValue() {
		return averageGameValue;
	}

	public Map<String, double[]> getRegretMap() {
		return regretMap;
	}

	private void writeStrategyMapToJSONFile(Map nodeMap)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter()
				.writeValue(new File("C:\\Users\\James\\Desktop\\StrategyMaps\\user.json"), nodeMap);
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
		long eta = current == 0 ? 0 : (total - current) * (System.currentTimeMillis() - startTime) / current;

		String etaHms = current == 0 ? "N/A"
				: String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
						TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
						TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

		StringBuilder string = new StringBuilder(140);
		int percent = (int) (current * 100 / total);
		string.append('\r')
				.append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
				.append(String.format(" %d%% [", percent)).append(String.join("", Collections.nCopies(percent, "=")))
				.append('>').append(String.join("", Collections.nCopies(100 - percent, " "))).append(']')
				.append(String.join("",
						Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
				.append(String.format(" %d/%d, ETA: %s", current, total, etaHms));

		System.out.print(string);
	}

}
