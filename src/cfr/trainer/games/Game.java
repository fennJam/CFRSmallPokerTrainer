package cfr.trainer.games;

import java.util.List;
import java.util.Map;

import cfr.trainer.action.Action;

public interface Game {

	String getNodeIdWithActionMemory();
	
	String getNodeIdWithSummaryState();

	boolean isAtTerminalNode();

	int[] getPayOffs() throws Exception;

	int getPlayerToAct();

	boolean performAction(int player, Action action) throws Exception;

	Game startGame() throws Exception;
	
	int[][] getListOfValidChanceCombinations();
	
	Game setValidChanceCombinations(int[] listOfChanceCombinations);
	
	boolean isAtChanceNode();
	
	boolean performChanceAction();

	List<Action> getPossibleActions();

	Action[] constructActionArray() throws Exception;
	
	
}
