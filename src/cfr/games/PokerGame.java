package cfr.games;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import poker.Board;
import poker.Card;
import poker.Deck;
import poker.GameType;
import poker.Hand;
import poker.Pot;

public interface PokerGame {


	Collection<Integer> getPlayers();
	
	Map<Integer, Hand> dealCards(Deck deck);

	Pot postBlinds();

	Pot bet(int player, int bet);

	Pot getPot();

	GameType getGameType();

	Hand getHand(int player);
	
	int getRaisesPerBettingRound();

	int getBettingRounds();

	boolean isAtTerminalBettingRound();
	
	Board turnNextCard();
	
	Board getBoard();
	
	List<Card> getVisibleCards();

	Map<Integer, Hand> getHands();

	Map<Integer, Integer> getPayOffs();

	PokerGame importGameProperties(PokerGame game);

}
