package cfr.trainer.games.poker.games.lite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import cfr.trainer.action.Action;
import cfr.trainer.games.Game;
import cfr.trainer.games.GameType;
import cfr.trainer.games.poker.*;
import cfr.trainer.games.poker.actions.*;
import cfr.trainer.games.poker.decks.Deck;
import cfr.trainer.games.poker.nodes.CardHistoryBuilder;

public abstract class BaseTwoPlayerPokerLiteGame implements PokerGameLite {

	PokerGameType pokerGameType;

	int[] playerHands;
	int[] board;
	boolean[] visibleBoardCards;
	int[] playerStack;
	int[] pot;

	List<Action> possibleActions;
	List<Action> actionsTaken = new ArrayList<>();
	BetRound betRound;
	int raiseCount;
	int raisesPerBettingRound;

	BettingLimit bettingLimit;

	BaseTwoPlayerPokerLiteGame(BettingLimit bettingLimit, int raisesPerBettingRound) {
		int stack = 10;
		// players.put(0, new PokerPlayer("0", stack, null));
		// players.put(1, new PokerPlayer("0", stack, null));
		// board = null;
		// pot = new Pot(players.keySet().toArray(new Integer[players.size()]));
		// this.bettingLimit = bettingLimit;
		// this.raisesPerBettingRound = raisesPerBettingRound;
		this.bettingLimit = bettingLimit;
		this.raisesPerBettingRound = raisesPerBettingRound;

		playerHands = new int[2];
		playerStack = new int[2];
		pot = new int[2];

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
	public Game startGame() throws Exception {
		dealCards();
		actionsTaken.add(DealAction.getInstance());
		postBlinds();
		return this;
	}

	@Override
	public int[] postBlinds() throws Exception {
		bet(0, 1);
		return bet(1, 2);
	}

	@Override
	public int[] bet(int player, int bet) throws Exception {
		if (playerStack[player] >= bet) {
			pot[player] += bet;
			playerStack[player] -= bet;
			return pot;
		} else {
			throw new Exception("Cannot bet more than the player has in their stack");
		}
	}

	@Override
	public PokerGameType getPokerGameType() {
		return pokerGameType;
	}

	@Override
	public int[] getPot() {
		return this.pot;
	}

	@Override
	public int[] getPayOffs() throws Exception {
		if (actionsTaken.get(actionsTaken.size() - 1).equals(FoldAction.getInstance())) {
			Integer player = getPlayerToAct();
			Integer opponent = 1 - player;
			Integer winnings = pot[opponent];

			int[] payOffs = new int[2];
			// System.out.println("winnings:"+winnings);
			payOffs[player] = winnings;
			payOffs[opponent] = -winnings;

			return payOffs;
		}
		return PayOffCalculator.getPayOffsForTwoPlayerLiteGame(getHands(), board, pot, pokerGameType);
	}

	@Override
	public boolean isAtTerminalNode() {
		if (actionsTaken.contains(FoldAction.getInstance())) {
			return true;
		} else if (this.betRound != null && this.betRound.equals(BetRound.RIVER)
				&& lastActionIsTerminalCallForTheBettingRound()) {
			return true;
		} else if (playerStack[0] == 0 && playerStack[1] == 0) {
			// both players all in
			turnAllBoardCards();
			return true;
		}
		return false;
	}

	@Override
	public int[] getBoard() {
		return board;
	}

	@Override
	public int[] getHands() {
		return playerHands;
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

	public BaseTwoPlayerPokerLiteGame importGameProperties(PokerGameLite pokerLiteGame) {

		for (Action action : pokerLiteGame.getActionsTaken()) {
			this.actionsTaken.add(action);
		}

		this.betRound = pokerLiteGame.getBetRound();
		this.board = pokerLiteGame.getBoard().clone();
		this.playerHands = pokerLiteGame.getPlayerHands().clone();
		this.playerStack = pokerLiteGame.getPlayerStack().clone();
		this.possibleActions = pokerLiteGame.getPossibleActions();
		this.pot = pokerLiteGame.getPot().clone().clone();
		this.raiseCount = pokerLiteGame.getRaiseCount();
		this.raisesPerBettingRound = pokerLiteGame.getRaisesPerBettingRound();
		this.visibleBoardCards = pokerLiteGame.getVisibleBoardCards().clone();

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
	public abstract int[] dealCards();

	// For pokerGames this will return a list of valid card combinations which
	// will be allocated, in order to players1,2 and then the board positions
	// 1,2,3...
	@Override
	public abstract int[][] getListOfValidChanceCombinations();

	@Override
	public boolean performAction(int player, Action action) throws Exception {
		PokerAction pokerAction = (PokerAction) action;
		PokerActionType currentAction = pokerAction.getActionType();
		if (currentAction.equals(PokerActionType.CALL)) {
			performCallAction(player);
		} else if (currentAction.equals(PokerActionType.RAISE)) {
			RaiseAction raiseAction = (RaiseAction) action;
			raiseCount++;
			performRaiseAction(player, raiseAction.getRaiseAmount());
		} else if (currentAction.equals(PokerActionType.DEAL)) {
			turnNextBoardCard();
			betRound = betRound.next();
			action = DealAction.getInstance();
			raiseCount = 0;
		}
		return actionsTaken.add(pokerAction);

	}

	private int[] performCallAction(int player) throws Exception {
		int opponent = 1 - player;
		int betToCall = pot[opponent] - pot[player];

		return bet(player, betToCall);
	}

	private int[] performRaiseAction(int player, int raiseSize) throws Exception {
		performCallAction(player);
		return bet(player, raiseSize);
	}

	@Override
	public String getNodeIdWithActionMemory() {
		String cardHistory = "";
		if (board != null) {
			cardHistory = getThreeCardHistory(playerHands[getPlayerToAct()], getTurnedCards());
		} else {

		}
		return cardHistory + getActionsTakenString();

	}

	@Override
	public String getNodeIdWithSummaryState() {
		String cardHistory = getThreeCardHistory(playerHands[getPlayerToAct()], getTurnedCards());
		return cardHistory + (pot[0] + pot[1]) + " raisesAllowed : " + raisesAllowed();
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
			actionString += action.toString() + ",";
		}
		if (actionString != null && actionString.length() > 0
				&& actionString.charAt(actionString.length() - 1) == ',') {
			actionString = actionString.substring(0, actionString.length() - 1);
		}
		return actionString;
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
		try {
			return performAction(0, DealAction.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Action> getPossibleActions() {
		return possibleActions;
	}

	public void setPossibleActions(List<Action> possibleActions) {
		this.possibleActions = possibleActions;
	}

	@Override
	public Action[] constructActionArray() throws Exception {

		if (!raisesAllowed()) {
			Action[] terminalActions = { FoldAction.getInstance(), CallAction.getInstance() };
			return terminalActions;
		} else if (bettingLimit.equals(BettingLimit.NO_LIMIT)) {

			List<Action> infoSetActionsList = new ArrayList<Action>();

			int player = getPlayerToAct();
			int opponent = player == 1 ? 0 : 1;
			int playersPotContribution = getPot()[player];
			int opponentsPotContribution = getPot()[opponent];

			if (playersPotContribution < 0 || opponentsPotContribution < 0) {
				throw new Error("Players contribution cannot be less than zero!");
			}

			int betToMatch = opponentsPotContribution - playersPotContribution;

			if (playerStack[player] > betToMatch) {
				infoSetActionsList = getPossibleActions().subList(0, (playerStack[player] - betToMatch) + 2);
			} else {
				infoSetActionsList = getPossibleActions().subList(0, 2);
			}

			PokerAction[] infoSetActionsArray = infoSetActionsList.toArray(new PokerAction[infoSetActionsList.size()]);
			return infoSetActionsArray;
		} else if (bettingLimit.equals(BettingLimit.LIMIT)) {
			return possibleActions.toArray(new PokerAction[possibleActions.size()]);
		} else {
			throw new Exception("Betting limit : " + bettingLimit + " not recognised");
		}
	}

	private boolean[] turnAllBoardCards() {
		for (int card = 0; card < visibleBoardCards.length; card++) {
			visibleBoardCards[card] = true;
		}
		return visibleBoardCards;
	}

	/**
	 * Turn the next unturned card
	 */
	private boolean turnNextBoardCard() {
		for (int i = 0; i < visibleBoardCards.length; i++) {
			if (visibleBoardCards[i] == false) {
				visibleBoardCards[i] = true;
				return true;
			}
		}
		return false;
	}

	private IntArrayList getTurnedCards() {
		IntArrayList turnedCards = new IntArrayList();
		for (int card = 0; card < board.length; card++) {
			if (visibleBoardCards[card]) {
				turnedCards.add(board[card]);
			}
		}
		return turnedCards;
	}

	/**
	 * Returns a string representation of the card history
	 */
	public String getThreeCardHistory(int singleCardHand, IntArrayList turnedCards) {

		boolean playerSuitedWithTurn = true;
		boolean riverSuitedWithTurn = true;

		int turnedCount = turnedCards.size();

		IntArrayList handSuit = new IntArrayList();
		handSuit.add(singleCardHand / 13);
		IntArrayList handRanks = new IntArrayList();
		handRanks.add(singleCardHand % 13);

		for (int boardCard : turnedCards.toArray()) {
			handSuit.add(boardCard / 13);
			handRanks.add(boardCard % 13);
		}

		if (turnedCount > 0 && handSuit.get(0) != handSuit.get(1)) {
			playerSuitedWithTurn = false;
		}

		if (turnedCount == 2) {
			riverSuitedWithTurn = (handSuit.get(1) == handSuit.get(2));
		}

		String str = "[" + (playerSuitedWithTurn ? "S" : "O") + "," + (riverSuitedWithTurn ? "S" : "O") + ",";
		for (int i = 0; i < handRanks.size() - 1; i++)
			str += handRanks.get(i) + ",";

		str += handRanks.get(handRanks.size() - 1) + "]";
		return str;
	}

	@Override
	public int[] getPlayerHands() {
		return playerHands;
	}

	@Override
	public boolean[] getVisibleBoardCards() {
		return visibleBoardCards;
	}

	@Override
	public int[] getPlayerStack() {
		return playerStack;
	}

	@Override
	public BetRound getBetRound() {
		return betRound;
	}

	@Override
	public int getRaisesPerBettingRound() {
		return raisesPerBettingRound;
	}

}
