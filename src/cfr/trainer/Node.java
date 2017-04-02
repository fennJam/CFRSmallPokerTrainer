package cfr.trainer;

import cfr.poker.actions.PokerAction;

public interface Node {

	Action getAction(int i);
	
	Action [] getActions();
	
	double[] getAverageStrategy();
	
	String getNodeIdentifier();
	
	int numOfActions();
	
	double[] recalculateStrategy(double realizationWeight);

	double[] getStrategy();
	
}
