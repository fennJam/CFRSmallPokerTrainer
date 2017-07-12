package cfr.trainer_multithreaded;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cfr.trainer.action.Action;
import cfr.trainer.games.Game;
import cfr.trainer.games.GameDescription;
import cfr.trainer.games.GameFactory;
import cfr.trainer.node.InfoSetFactory;
import cfr.trainer.node.Node;

public class CFRPlusTrainerMultiThreaded {

	public Map<String, SynchronisedRegretsArray> regretMap = new ConcurrentHashMap<>(300000);
	double util = 0;
	int utilCount;
	double averageGameValue = 0;

	public static void main(String[] args) throws Exception {
		int iterations = 45000;
		new CFRPlusTrainerMultiThreaded().train(GameDescription.KUHN_POKER_LITE, iterations,
				4);
	}

	public void train(GameDescription gameDescription, int iterations, int threads) throws Exception {

		ExecutorService executor = Executors.newFixedThreadPool(threads);

		Game gameStructure = GameFactory.setUpGame(gameDescription, 3);
		int[][] validChanceCombinations = gameStructure.getListOfValidChanceCombinations();
//		 long startTime = System.currentTimeMillis();
			for (int i = 0; i < iterations; i++) {
//				int comboSize = validChanceCombinations.size();
//				int combo=0+(comboSize*i);
				for (int[] validCombo : validChanceCombinations) {
//					combo++;
//					printProgress(startTime, comboSize*iterations, combo);
				Game game = GameFactory.setUpGame(gameDescription, 2);
				game.startGame();
				game.setValidChanceCombinations(validCombo);
				Runnable runnable1 = new CfrPlusThread(game, 1, 1, i);
				executor.execute(runnable1);
				Runnable runnable2 = new CfrPlusThread(game, 1, 1, i);
				executor.execute(runnable2);
			}

		}
		executor.shutdown();
		averageGameValue = util / (iterations * validChanceCombinations.length * 2);
		System.out.println("Average game value: " + averageGameValue + "\n Nodes: " + regretMap.size());
		System.out.println("Map : " + regretMap.toString());
		writeStrategyMapToJSONFile(getStrategyMap(), gameDescription);
	}

	public double getAverageGameValue() {
		return averageGameValue;
	}

	public Map<String, SynchronisedRegretsArray> getRegretMap() {
		return regretMap;
	}

	private void writeStrategyMapToJSONFile(Map<String, double[]> regretMap, GameDescription gameDescription)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(
				new File("C:\\Users\\James\\Desktop\\StrategyMaps\\CFRPlus+" + gameDescription + ".json"), regretMap);
	}

	public Map<String, double[]> getStrategyMap() {
		Map<String, double[]> strategyMap = new HashMap<>();
		for (Entry<String, SynchronisedRegretsArray> entry : regretMap.entrySet()) {
			strategyMap.put(entry.getKey(), calculateStrategy(entry.getValue()));
		}
		return strategyMap;
	}

	private double[] calculateStrategy(SynchronisedRegretsArray regretSum) {
		int numOfActions = regretSum.getArray().length;
		double[] strategy = new double[numOfActions];
		double normalizingSum = 0;
		for (int a = 0; a < numOfActions; a++) {
			// only taking positive regrets
			double regret = regretSum.getValue(a);
			strategy[a] = regret > 0 ? regret : 0;
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

	synchronized private void incrementUtility(double increment){
		this.util+=increment;
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

	private class CfrPlusThread implements Runnable {
		Game game;
		double p0;
		double p1;
//		int playerToTrain;
		int iteration;

		CfrPlusThread(Game game, double p0, double p1, int iteration) {
			this.game = game;
			this.p0 = p0;
			this.p1 = p1;
//			this.playerToTrain = playerToTrain;
			this.iteration = iteration;
		}

		private double cfrPlus(Game game, double p0, double p1, int iteration) throws Exception {
			if (game.isAtTerminalNode()) {
				return game.getPayOffs()[game.getPlayerToAct()];
			}
			if (game.isAtChanceNode()) {
				game.performChanceAction();
			}

			// Get Node
			String nodeId = game.getNodeIdWithActionMemory();

			SynchronisedRegretsArray regretSums = regretMap.get(nodeId);
			Action[] availableActions = game.constructActionArray();
			if (regretSums == null) {
				regretSums =new SynchronisedRegretsArray(availableActions.length);
				regretMap.put(nodeId, regretSums);
			}
			// recursively call cfr
			int player = game.getPlayerToAct();

			double[] strategy = calculateStrategy(regretSums);
			int actionsAvailable = regretSums.getLength();
			double[] util = new double[actionsAvailable];
			double nodeUtil = 0;

			for (int action = 0; action < actionsAvailable; action++) {
				Game copyOfGame = GameFactory.copyGame(game);
				copyOfGame.performAction(player, availableActions[action]);

				util[action] = player == 0 ? -cfrPlus(copyOfGame, p0 * strategy[action], p1, iteration)
						: -cfrPlus(copyOfGame, p0, p1 * strategy[action], iteration);

				nodeUtil += strategy[action] * util[action];
			}

			// compute cfr
//			if (playerToTrain == player) {
				for (int a = 0; a < actionsAvailable; a++) {
					double regret = util[a] - nodeUtil;
					double weightedRegret = regret * iteration;
					double regretSum = (regretSums.getValue(a) + (player == 0 ? p1 : p0) * weightedRegret);
					regretSums.setValue(a, Math.max(regretSum, 0));
//				}
			}
			return nodeUtil;
		}

		@Override
		public void run() {
			try {
				double utility= cfrPlus(this.game, this.p0, this.p1, this.iteration);
				incrementUtility(utility);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
