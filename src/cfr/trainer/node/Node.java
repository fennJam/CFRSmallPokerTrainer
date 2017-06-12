package cfr.trainer.node;

import cfr.trainer.action.Action;

public interface Node {
	
	double[] getAverageStrategy();
	
//	String getNodeIdentifier();
	
	int numOfActions();
	
	double[] recalculateStrategy(double realizationWeight);

	double[] getStrategy();
	
	double[] getRegretSum();
	
	double[] setRegretSum(double[] regretSum);
	
}
