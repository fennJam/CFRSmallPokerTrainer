package cfr.trainer.games.poker.games.lite;

import java.util.List;
import java.util.Map;

import cfr.trainer.action.Action;
import cfr.trainer.games.Game;
import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.decks.Deck;

public interface PokerGameLite extends Game {
	
	int[] dealCards();

	int[] postBlinds() throws Exception;

	int[] bet(int player, int bet) throws Exception;

	int[] getPot();

	PokerGameType getPokerGameType();
	
	int getRaisesAllowedPerBettingRound();
	
	boolean raisesAllowed();

	BetRound getBettingRound();
	
	int[] getBoard();

	int[] getHands();

	PokerGameLite importGameProperties(PokerGameLite game);

	BettingLimit getBettingLimit();

	List<Action> getActionsTaken();
	
	int getRaiseCount();

	boolean lastActionIsTerminalCallForTheBettingRound();

	public int[] getPlayerHands();

	public boolean[] getVisibleBoardCards();

	public int[] getPlayerStack();

	public BetRound getBetRound();

	public int getRaisesPerBettingRound();

}
