package cfr.trainer;

import java.util.Map;

public interface Game {

	String getNodeId();

	boolean isAtTerminalNode();

	Map<Integer, Integer> getPayOffs();

	int getPlayerToAct();

	boolean performAction(int player, Action action);

	Game startGame();
}
