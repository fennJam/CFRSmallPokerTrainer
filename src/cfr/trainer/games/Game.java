package cfr.trainer.games;

import java.util.Map;

import cfr.trainer.action.Action;

public interface Game {

	String getNodeId();

	boolean isAtTerminalNode();

	Map<Integer, Integer> getPayOffs();

	int getPlayerToAct();

	boolean performAction(int player, Action action);

	Game startGame();
}
