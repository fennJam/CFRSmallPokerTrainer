package cfr.trainer.games;

import java.util.List;
import java.util.Map;

import cfr.trainer.action.Action;

public interface Game {

	String getNodeIdWithActionMemory();
	
	String getNodeIdWithSummaryState();

	boolean isAtTerminalNode();

	Map<Integer, Integer> getPayOffs();

	int getPlayerToAct();

	boolean performAction(int player, Action action) throws Exception;

	Game startGame() throws Exception;
	
	List<List<Integer>> getListOfValidChanceCombinations();
	
	Game setValidChanceCombinations(List<Integer> listOfChanceCombinations);
	
	boolean isAtChanceNode();
	
	boolean performChanceAction();

	List<Action> getPossibleActions();

	Action[] constructActionArray() throws Exception;
	
	
}
