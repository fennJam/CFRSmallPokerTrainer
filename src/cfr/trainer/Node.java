package cfr.trainer;

public interface Node {

	Action getAction(int i);
	
	Action [] getActions();
	
	double[] getAverageStrategy();
	
	String getNodeIdentifier();
	
	int numOfActions();
	
	double[] recalculateStrategy(double realizationWeight);

	double[] getStrategy();
	
}
