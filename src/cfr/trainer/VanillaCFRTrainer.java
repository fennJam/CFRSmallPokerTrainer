package cfr.trainer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cfr.trainer.games.GameDescription;
import cfr.trainer.games.GameFactory;
import cfr.trainer.games.poker.actions.DealAction;
import cfr.trainer.games.poker.games.PokerGame;
import cfr.trainer.games.poker.nodes.PokerInfoSetFactory;
import cfr.trainer.node.NodeImpl;

public class VanillaCFRTrainer {

	public Map<String, NodeImpl> nodeMap = new HashMap<String, NodeImpl>();
	double util = 0;
	int iterations = 0;
	double averageGameValue = 0;

	public static void main(String[] args) {
		int iterations = 200000;
		new VanillaCFRTrainer().train(GameDescription.KUHN_POKER, iterations);
	}

	public void train(GameDescription gameType, int iterations) {
		this.iterations = iterations;
		PokerGame gameStructure = GameFactory.setUpGame(gameType);
		List<PokerGame> listGames = gameStructure.getListOfGamesWithAllPossibleChanceNodes();
		for (int i = 0; i < iterations; i++) {
			for (PokerGame game : listGames) {
				util += cfr(game, 1, 1);
			}
		}
		averageGameValue = util / (iterations * listGames.size());
		System.out.println("Average game value: " + averageGameValue);
		for (Entry<String, NodeImpl> n : nodeMap.entrySet())
			System.out.println(n.getKey() + " : " + n.getValue());
	}

	private double cfr(PokerGame pokerGame, double p0, double p1) {

		if (pokerGame.isAtTerminalNode()) {
			return pokerGame.getPayOffs().get(pokerGame.getPlayerToAct());
		}
		
		if(pokerGame.lastActionIsTerminalCallForTheBettingRound()){
			pokerGame.performAction(0, DealAction.getInstance());
		}

		// Get Node
		String nodeId = pokerGame.getNodeIdWithActionMemory();

		NodeImpl node = nodeMap.get(nodeId);
		if (node == null) {
			// TODO remove poker references
			node = PokerInfoSetFactory.buildInformationSet(nodeId,pokerGame);
			nodeMap.put(nodeId, node);
		}
		// recursively call cfr
		int player = pokerGame.getPlayerToAct();
		double[] strategy = node.recalculateStrategy(player == 0 ? p0 : p1);
		int actionsAvailable = node.getActions().length;
		double[] util = new double[actionsAvailable];
		double nodeUtil = 0;

		for (int action = 0; action < actionsAvailable; action++) {
			// TODO remove poker references
			PokerGame copyOfGame = GameFactory.copyGame(pokerGame);
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
		return averageGameValue;
	}

	public Map<String, NodeImpl> getNodeMap() {
		return nodeMap;
	}
}