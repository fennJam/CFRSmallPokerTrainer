package cfr.trainer.games.poker.games;

import java.util.List;
import java.util.Map;

import cfr.trainer.games.Game;
import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.actions.PokerAction;
import cfr.trainer.games.poker.decks.Deck;

public interface PokerGame extends Game {


	int[] getPlayers();
	
	Map<Integer, Hand> dealCards(Deck deck);

	Pot postBlinds();

	Pot bet(int player, int bet);

	Pot getPot();

	PokerGameType getGameType();
	
	int getRaisesAllowedPerBettingRound();
	
	boolean raisesAllowed();

	BetRound getBettingRound();
	
	Board getBoard();

	Map<Integer, Hand> getHands();

	PokerGame importGameProperties(PokerGame game);

	BettingLimit getBettingLimit();

	List<PokerAction> getActions();
	
	int getRaiseCount();


}
