package cfr.games;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfr.trainer.PayOffCalculator;
import poker.Board;
import poker.Card;
import poker.Deck;
import poker.GameType;
import poker.Hand;
import poker.HandSingleCard;
import poker.Pot;

public class SingleCardGame implements PokerGame {

	Collection<Integer> players;
	Map<Integer, Hand> hands;
	int raisesPerBettingRound;
	static int bettingRounds =1;
	static GameType gameType = GameType.SINGLE_CARD;
	Board board;
	Pot pot;

	SingleCardGame(Collection<Integer> players, int raisesPerBettingRound) {
		this.players = players;
		board = null;
		hands = null;
		pot = new Pot(players);
		this.raisesPerBettingRound = raisesPerBettingRound;
	}

	SingleCardGame(Integer players, int raisesPerBettingRound) {
		List<Integer> playerList = new ArrayList<Integer>();
		for (int player = 0; player < players; player++) {
			playerList.add(player);
		}
		this.players = playerList;
		board = null;
		hands = null;
		pot = new Pot(playerList);
		this.raisesPerBettingRound = raisesPerBettingRound;
	}

	@Override
	public Map<Integer, Hand> dealCards(Deck deck) {
		int numOfPlayers = players.size();
		hands = new HashMap<Integer, Hand>();
		for (int player = 0; player < numOfPlayers; player++) {
			hands.put(player, new HandSingleCard(deck.nextCard()));
		}
		board = new Board(gameType, deck);
		return hands;
	}

	@Override
	public Pot postBlinds() {
		bet(0, 1);
		return bet(1, 2);
	}

	@Override
	public Pot bet(int player, int bet) {
		pot.addBet(player, bet);
		return pot;
	}

	@Override
	public List<Card> getVisibleCards() {
		return board.getTurnedCards();
	}

	@Override
	public GameType getGameType() {
		return SingleCardGame.gameType;
	}

	@Override
	public Hand getHand(int player) {
		if (hands == null)
			return null;
		return hands.get(player);
	}

	@Override
	public Pot getPot() {
		return this.pot;
	}

	@Override
	public Map<Integer, Integer> getPayOffs() {
		return PayOffCalculator.getPayOffsForTwoPlayerGame(hands, board, pot, gameType);
	}

	@Override
	public boolean isAtTerminalBettingRound() {
		return board.getNoOfUnturnedCards() == 0;
	}

	@Override
	public Board turnNextCard() {
		board.turnNextCard();
		return board;
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Collection<Integer> getPlayers() {
		return players;
	}

	@Override
	public Map<Integer, Hand> getHands() {
		return hands;
	}

	@Override
	public int getRaisesPerBettingRound() {
		return raisesPerBettingRound;
	}

	@Override
	public int getBettingRounds() {
		return SingleCardGame.bettingRounds;
	}

	@Override
	public PokerGame importGameProperties(PokerGame game) {
		
		if(TwoCardGame.bettingRounds != game.getBettingRounds()||TwoCardGame.gameType != game.getGameType()){
			throw new Error("Different game type or number of betting rounds in game you are trying to copy!!");
		}
		
		this.players = game.getPlayers();
		this.hands = game.getHands();
		this.raisesPerBettingRound = game.getRaisesPerBettingRound();
		
		if(game.getBoard() == null){
			this.board = null;
		}else{
			this.board = new Board().importBoardProperties(game.getBoard());
		}
		
		if(game.getPot()==null){
			this.pot=null;
		
		}else{
			this.pot = new Pot(players).importPotProperties(game.getPot());	
		}
		
		return this;
	}
}
