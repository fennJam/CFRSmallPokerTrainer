package cfr.trainer.games.poker.games;

import java.util.List;
import java.util.Map;

import cfr.trainer.action.Action;
import cfr.trainer.games.Game;
import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.decks.Deck;

public interface PokerGame extends Game {


	Integer[] getPlayers();
	
	Map<Integer, PokerPlayer> dealCards();

	Pot postBlinds();

	Pot bet(int player, int bet);

	Pot getPot();

	PokerGameType getPokerGameType();
	
	int getRaisesAllowedPerBettingRound();
	
	boolean raisesAllowed();

	BetRound getBettingRound();
	
	Board getBoard();

	Map<Integer, Hand> getHands();

	PokerGame importGameProperties(PokerGame game);

	BettingLimit getBettingLimit();

	List<Action> getActionsTaken();
	
	int getRaiseCount();

	boolean lastActionIsTerminalCallForTheBettingRound();

	Deck getDeck();

	void setDeck(Deck deck);

	PokerPlayer getPlayer(Integer player);


}
