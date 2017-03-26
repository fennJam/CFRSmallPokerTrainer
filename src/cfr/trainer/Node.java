package cfr.trainer;

import java.util.Arrays;

public class Node {
	boolean canRaise;
	String infoSet;
	int numOfActions;
	private final static String[] raiseOptionActions = { "F", "C", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private final static String[] noRaiseOptionActions = { "F", "C", };
	private String[] actions;
	private double[] strategy;
	double[] regretSum;
	double[] strategySum;

	Node(String infoSet, boolean canRaise) {
		this.infoSet = infoSet;
		this.canRaise = canRaise;

		if (canRaise) {
			actions = raiseOptionActions;
			numOfActions = actions.length;
			strategy = new double[numOfActions];
			regretSum = new double[numOfActions];
			strategySum = new double[numOfActions];
		} else {
			actions = noRaiseOptionActions;
			numOfActions = actions.length;
			strategy = new double[numOfActions];
			regretSum = new double[numOfActions];
			strategySum = new double[numOfActions];
		}
	}

	public double[] getStrategy(double realizationWeight) {
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
			strategySum[a] += realizationWeight * strategy[a];
		}
		return strategy;
	}

	public double[] getAverageStrategy() {
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

	public String toString() {
		return String.format("%4s: %s", infoSet, Arrays.toString(getAverageStrategy()));
	}

	public String getInfoSet() {
		return infoSet;
	}

	public int getNumOfActions() {
		return numOfActions;
	}

	public String getAction(int a) {
		return actions[a];
	}

	public String[] getActions() {
		return actions;
	}
}