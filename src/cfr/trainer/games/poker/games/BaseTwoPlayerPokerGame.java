package cfr.trainer.games.poker.games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cfr.trainer.action.Action;
import cfr.trainer.games.Game;
import cfr.trainer.games.GameType;
import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.actions.*;
import cfr.trainer.games.poker.decks.Deck;
import cfr.trainer.games.poker.nodes.CardHistoryBuilder;

public abstract class BaseTwoPlayerPokerGame implements PokerGame {

	BetRound betRound;
	GameType gameType = GameType.POKER;
	PokerGameType pokerGameType;

	List<Action> possibleActions;
	List<Action> actionsTaken;
	Map<Integer, PokerPlayer> players = new HashMap<>();
	int raisesPerBettingRound;
	int raiseCount;
	BettingLimit bettingLimit;
	Board board;
	Pot pot;
	Deck deck;

	BaseTwoPlayerPokerGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		int stack = 20;
		players.put(0, new PokerPlayer("0", stack, null));
		players.put(1, new PokerPlayer("0", stack, null));
		board = null;
		pot = new Pot(players.keySet().toArray(new Integer[players.size()]));
		this.bettingLimit = bettingLimit;
		this.raisesPerBettingRound = raisesPerBettingRound;
		actionsTaken = new ArrayList<Action>();
		if (bettingLimit == BettingLimit.LIMIT) {
			possibleActions = new ArrayList<Action>();
			possibleActions.add(FoldAction.getInstance());
			possibleActions.add(CallAction.getInstance());
			possibleActions.add(new RaiseAction(1));
		} else {
			possibleActions = new ArrayList<Action>();
			possibleActions.add(FoldAction.getInstance());
			possibleActions.add(CallAction.getInstance());
			for (int raise = 1; raise < stack + 1; raise++) {
				possibleActions.add(new RaiseAction(raise));
			}
		}

	}

	@Override
	public Game startGame() {
		dealCards();
		actionsTaken.add(DealAction.getInstance());
		postBlinds();
		return this;
	}

	@Override
	public Pot postBlinds() {
		bet(0, 1);
		return bet(1, 2);
	}

	@Override
	public Pot bet(int player, int bet) {
		pot.addBet(player, bet);
		players.get(player).takeFromStack(bet);
		return pot;
	}

	@Override
	public PokerGameType getPokerGameType() {
		return pokerGameType;
	}

	@Override
	public Pot getPot() {
		return this.pot;
	}

	@Override
	public Map<Integer, Integer> getPayOffs() {
		if (actionsTaken.get(actionsTaken.size() - 1).equals(FoldAction.getInstance())) {
			Integer player = getPlayerToAct();
			Integer opponent = 1 - player;
			Integer winnings = pot.getPlayersContributionToPot(opponent);

			Map<Integer, Integer> payOffs = new HashMap<Integer, Integer>();
			payOffs.put(player, winnings);
			payOffs.put(opponent, -winnings);

			return payOffs;
		}
		return PayOffCalculator.getPayOffsForTwoPlayerGame(getHands(), board, pot, pokerGameType);
	}

	@Override
	public boolean isAtTerminalNode() {
		if (actionsTaken.contains(FoldAction.getInstance())) {
			return true;
		} else if (this.betRound != null && this.betRound.equals(BetRound.RIVER)
				&& lastActionIsTerminalCallForTheBettingRound()) {
			return true;
		}else if(players.get(0).getStack()==0 &&players.get(1).getStack()==0){
//			both players all in
			board.turnAllCards();
			return true;
		}
		return false;
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Integer[] getPlayers() {
		return players.keySet().toArray(new Integer[players.size()]);
	}

	@Override
	public Map<Integer, Hand> getHands() {
		Map<Integer, Hand> hands = new HashMap<>();
		for (Entry<Integer, PokerPlayer> player : players.entrySet()) {
			hands.put(player.getKey(), player.getValue().getHand());
		}
		return hands;
	}

	public void setHands(Map<Integer, Hand> newHands) {
		for (Entry<Integer, Hand> hand : newHands.entrySet()) {
			players.get(hand.getKey()).setHand(hand.getValue());
		}
	}

	@Override
	public int getRaisesAllowedPerBettingRound() {
		return raisesPerBettingRound;
	}

	@Override
	public BetRound getBettingRound() {
		return betRound;
	}

	@Override
	public BettingLimit getBettingLimit() {
		return this.bettingLimit;
	}

	@Override
	public int getRaiseCount() {
		return this.raiseCount;
	}

	@Override
	public PokerGame importGameProperties(PokerGame game) {

		if (this.pokerGameType != game.getPokerGameType()) {
			throw new Error("Different game type or number of betting rounds in game you are trying to copy!!");
		}
		this.bettingLimit = game.getBettingLimit();
		setHands(game.getHands());
		this.raisesPerBettingRound = game.getRaisesAllowedPerBettingRound();
		this.raiseCount = game.getRaiseCount();
		this.betRound = game.getBettingRound();

		if (game.getBoard() == null) {
			this.board = null;
		} else {
			this.board = new Board().importBoardProperties(game.getBoard());
		}

		if (game.getPot() == null) {
			this.pot = null;

		} else {
			this.pot = new Pot(getPlayers()).importPotProperties(game.getPot());
		}
		this.actionsTaken.addAll(game.getActionsTaken());

		return this;
	}

	@Override
	public int getPlayerToAct() {
		// get number of actions since last instance of deal action
		int noOfActions = 0;
		actionsTaken.lastIndexOf(DealAction.getInstance());
		for (int action = actionsTaken.lastIndexOf(DealAction.getInstance()) + 1; action < actionsTaken
				.size(); action++) {
			noOfActions++;
		}
		return noOfActions % 2;
	}

	@Override
	public List<Action> getActionsTaken() {
		return actionsTaken;
	}

	@Override
	public abstract Map<Integer, PokerPlayer> dealCards();

	// For pokerGames this will return a list of valid card combinations which
	// will be allocated, in order to players1,2 and then the board positions
	// 1,2,3...
	@Override
	public abstract List<List<Integer>> getListOfValidChanceCombinations();

	@Override
	public boolean performAction(int player, Action action) {
		PokerAction pokerAction = (PokerAction) action;
		PokerActionType currentAction = pokerAction.getActionType();
		if (currentAction.equals(PokerActionType.CALL)) {
			performCallAction(player);
		} else if (currentAction.equals(PokerActionType.RAISE)) {
			RaiseAction raiseAction = (RaiseAction) action;
			raiseCount++;
			performRaiseAction(player, raiseAction.getRaiseAmount());
		} else if (currentAction.equals(PokerActionType.DEAL)) {
			board.turnNextCard();
			betRound = betRound.next();
			action = DealAction.getInstance();
			raiseCount = 0;
		}
		return actionsTaken.add(pokerAction);

	}

	private Pot performCallAction(int player) {
		int opponent = 1 - player;
		int betToCall = pot.getPlayersContributionToPot(opponent) - pot.getPlayersContributionToPot(player);

		return bet(player, betToCall);
	}

	private Pot performRaiseAction(int player, int raiseSize) {
		performCallAction(player);
		return bet(player, raiseSize);
	}

	@Override
	public String getNodeIdWithActionMemory() {
		String cardHistory = "";
		if (board != null) {
			cardHistory = new CardHistoryBuilder(players.get(getPlayerToAct()).getHand(), board).build();
		} else {

		}
		return cardHistory + getActionsTakenString();

	}

	@Override
	public String getNodeIdWithGameState() {
		String cardHistory = new CardHistoryBuilder(players.get(getPlayerToAct()).getHand(), board).toString();
		return cardHistory + pot.getTotalPotSize() + " raisesAllowed : " + raisesAllowed();
	}

	@Override
	public boolean lastActionIsTerminalCallForTheBettingRound() {
		int dealIndex = actionsTaken.lastIndexOf(DealAction.getInstance());
		int callIndex = actionsTaken.lastIndexOf(CallAction.getInstance());
		// any call that is not a "check" (a call after a deal action) is a
		// terminal call for the betting round
		return (callIndex - dealIndex > 1);

	}

	@Override
	public boolean raisesAllowed() {
		if (raiseCount < raisesPerBettingRound) {
			return true;
		}
		return false;
	}

	private String getActionsTakenString() {
		String actionString = "";
		for (Action action : actionsTaken) {
			actionString += action.toString() + " ";
		}
		return actionString.trim();
	}

	public String toString() {

		return "Game - BetRound " + betRound + " PokerGameType " + pokerGameType + " actions " + actionsTaken
				+ " actingPlayer " + getPlayerToAct() + " raisesPerBettingRound " + raisesPerBettingRound
				+ " raiseCount " + raiseCount + " bettingLimit " + bettingLimit + "hands" + getHands() + " board "
				+ board + " pot " + pot;
	}

	@Override
	public boolean isAtChanceNode() {
		return lastActionIsTerminalCallForTheBettingRound();
	}

	@Override
	public boolean performChanceAction() {
		return performAction(0, DealAction.getInstance());
	}

	@Override
	public GameType getGameType() {
		return gameType;
	}

	@Override
	public Deck getDeck() {
		return deck;
	}

	@Override
	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	@Override
	public PokerPlayer getPlayer(Integer player) {
		return this.players.get(player);
	}

	@Override
	public List<Action> getPossibleActions() {
		return possibleActions;
	}

	public void setPossibleActions(List<Action> possibleActions) {
		this.possibleActions = possibleActions;
	}

}
