package cfr.trainer.games;

import java.util.List;
import java.util.Map;

import cfr.trainer.action.Action;

public interface Game {

	String getNodeIdWithActionMemory();
	
	String getNodeIdWithGameState();

	boolean isAtTerminalNode();

	Map<Integer, Integer> getPayOffs();

	int getPlayerToAct();

	boolean performAction(int player, Action action);

	Game startGame();
	
	List<Game> getListOfGamesWithAllPossibleChanceNodes();
	
	boolean isAtChanceNode();
	
	boolean performChanceAction();
	
	GameType getGameType();
	
	
}
