package cfr.trainer.node;

import cfr.trainer.action.Action;

public class NodeImpl implements Node {

//	protected String nodeIdentifier;
	private double[] strategy;
	public double[] regretSum;
	private double[] strategySum;

	protected NodeImpl(Action[] actions) {
		int actionsLength = actions.length;
		this.strategy = new double[actionsLength];
		this.regretSum = new double[actionsLength];
		this.strategySum = new double[actionsLength];
	}

	@Override
	public double[] recalculateStrategy(double realizationWeight) {
		double normalizingSum = 0;
		int numOfActions = strategy.length;
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
			strategySum[a] += realizationWeight * strategy[a];
		}
		return strategy;
	}

	@Override
	public double[] getAverageStrategy() {
		int numOfActions = strategy.length;
		double[] avgStrategy = new double[numOfActions];
		double normalizingSum = 0;
		for (int a = 0; a < numOfActions; a++)
			normalizingSum += strategySum[a];
		for (int a = 0; a < numOfActions; a++)
			if (normalizingSum > 0)
				avgStrategy[a] = strategySum[a] / normalizingSum;
			else
				avgStrategy[a] = 1.0 / numOfActions;
		return avgStrategy;
	}

	@Override
	public String toString() {
		String strategiesString = "";
		double[] avgStrategy = getAverageStrategy();
		for (int i = 0; i < strategy.length; i++) {
			strategiesString +=": "+avgStrategy[i]+"    ";
		}
		return strategiesString;
	}

//	@Override
//	public String getNodeIdentifier() {
//		return nodeIdentifier;
//	}

	@Override
	public int numOfActions() {
		return strategy.length;
	}

	@Override
	public double[] getStrategy() {
		return strategy;
	}

	@Override
	public double[] getRegretSum() {
		return regretSum;
	}

	@Override
	public double[] setRegretSum(double[] regretSum) {
		this.regretSum = regretSum;
		return regretSum;
	}

}
