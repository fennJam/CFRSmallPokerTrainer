package cfr.trainer.games.poker.games.xlite;

public interface PokerGameXLite {
	
	String setActionsTaken(String actionsTaken, int[][] cardsDealt);
	
	int getPlayerToAct();
	
	char[] getAvailableActions();
	
	int[] getPayoffs();
	
	boolean isAtTerminalNode();
	
	boolean isAtChanceNode();

	int[][] getListOfValidChanceCombinations();
	
	String getNodeId();
}
