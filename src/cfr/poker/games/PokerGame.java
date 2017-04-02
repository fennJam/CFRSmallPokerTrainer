package cfr.poker.games;

import java.util.List;
import java.util.Map;

import cfr.poker.*;
import cfr.poker.actions.PokerAction;
import cfr.trainer.Action;
import cfr.trainer.Game;

public interface PokerGame extends Game {


	int[] getPlayers();
	
	Map<Integer, Hand> dealCards(Deck deck);

	Pot postBlinds();

	Pot bet(int player, int bet);

	Pot getPot();

	PokerGameType getGameType();
	
	Hand getHand(int player);
	
	int getRaisesAllowedPerBettingRound();
	
	boolean raisesAllowed();

	BetRound getBettingRound();
	
	Board turnNextCard();
	
	Board getBoard();
	
	List<Card> getVisibleCards();

	Map<Integer, Hand> getHands();

	PokerGame importGameProperties(PokerGame game);

	BettingLimit getBettingLimit();

	List<PokerAction> getActions();


}
