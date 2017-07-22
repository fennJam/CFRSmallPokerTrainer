package cfr.trainer.games.poker.games.xlite;

import cfr.trainer.games.poker.hand_evaluators.SmallHandEvaluator;

public abstract class PokerXLiteBase implements PokerGameXLite {

	char[] actionsTaken;
	int lastDealIndex = 0;
	int lastCallIndex = 0;
	int raiseCount = 0;// number of raises so far in current betting round
	int dealCount = 0;// Number of deal actions in the game so far.
	boolean folded = false;// has there been a fold action in the game
	char[] possibleActions = { 'F', 'C', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	char[] terminalActions = { 'F', 'C' };
	char[] limitActions = { 'F', 'C', '1' };
	int[] pot = new int[2];

	int[] hands = new int[2];

	int[] board = new int[0];
	int raisesAllowed = 3;// raises allowed in each betting round
	int betRounds = 1;
	int startingStacks = 0;
	boolean noLimitGame = true;// limit = 0 no-limit=1

	public PokerXLiteBase(int boardSize, int raisesAllowed, int bettingRounds, int startingStacks,
			boolean noLimitGame) {
		this.board = new int[boardSize];
		this.raisesAllowed = raisesAllowed;
		this.betRounds = bettingRounds;
		this.startingStacks = startingStacks;
		this.noLimitGame = noLimitGame;
	}

	@Override
	public String setActionsTaken(String actionsTakenString, int[][] cardsDealt) {
		if (cardsDealt.length != 2) {
			System.out.println("cardsDealt.length = " + cardsDealt.length);
		}
		if (cardsDealt[0].length != 2) {
			System.out.println("Cards[0] length = " + cardsDealt[0].length);
		} else {
			hands = cardsDealt[0];
		}
		board = cardsDealt[1];

		this.actionsTaken = actionsTakenString.toCharArray();
		this.lastDealIndex = 0;
		this.dealCount = 0;
		this.raiseCount = 0;
		this.folded = false;

		int index = 0;
		pot = new int[2];

		for (char action : actionsTaken) {
			int playerWhoActed = (index - this.lastDealIndex + 1) % 2;
			int opponent = playerWhoActed == 1 ? 0 : 1;
			if (action == 'D') {
				this.lastDealIndex = index;
				this.lastCallIndex = index;
				this.dealCount++;
				this.raiseCount = 0;
				if (index == 0) {
					postBlinds();
				}
			} else if (action == 'C') {
				this.lastCallIndex = index;
				pot[playerWhoActed] = pot[opponent];
			} else if (action == 'F') {
				this.folded = true;
			} else if (Character.isDigit(action)) {
				raiseCount++;
				int raise = Character.getNumericValue(action);
				pot[playerWhoActed] = pot[opponent];
				pot[playerWhoActed] = pot[playerWhoActed] + raise;
			}
			index++;
		}
		return "";
	}

	@Override
	public int getPlayerToAct() {
		return (actionsTaken.length - lastDealIndex - 1) % 2;
	}

	@Override
	public char[] getAvailableActions() {

		if (!raisesAllowed()) {
			return terminalActions;
		} else if (noLimitGame) {

			int player = getPlayerToAct();
			int opponent = player == 1 ? 0 : 1;
			int playersPotContribution = this.pot[player];
			int opponentsPotContribution = this.pot[opponent];
			int playersStack = startingStacks - playersPotContribution;

			if (playersPotContribution < 0 || opponentsPotContribution < 0) {
				throw new Error("Players contribution cannot be less than zero!");
			}

			int betToMatch = opponentsPotContribution - playersPotContribution;

			if (playersStack > betToMatch) {
				char[] infoSetActionsList = new char[(playersStack - betToMatch) + 2];
				System.arraycopy(possibleActions, 0, infoSetActionsList, 0, (playersStack - betToMatch) + 2);
				return infoSetActionsList;
			} else {
				return terminalActions;
			}

		} else {

			return limitActions;
		}
	}

	@Override
	public int[] getPayoffs() {
		if (folded) {
			Integer player = getPlayerToAct();
			Integer opponent = 1 - player;
			Integer winnings = pot[opponent];

			int[] payOffs = new int[2];
			// System.out.println("winnings:"+winnings);
			payOffs[player] = winnings;
			payOffs[opponent] = -winnings;

			return payOffs;
		}
		try {
			return getPayOffsForTwoPlayerLiteGame(hands, board, pot);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isAtTerminalNode() {
		if (folded) {
			return true;
		} else if (this.betRounds == this.dealCount && lastActionIsTerminalCallForTheBettingRound()) {
			return true;
		}else if(pot[0]==this.startingStacks&&pot[1]==this.startingStacks){
			return true;
		}
		return false;
	}

	@Override
	public boolean isAtChanceNode() {
		return (lastActionIsTerminalCallForTheBettingRound() && (this.betRounds > this.dealCount));
	}

	@Override
	public String getNodeId() {

		StringBuffer cardHistory = getThreeCardHistory(hands[getPlayerToAct()], getTurnedCards());

		return cardHistory.append(this.actionsTaken).toString();

	}

	private boolean lastActionIsTerminalCallForTheBettingRound() {
		return (this.lastCallIndex - this.lastDealIndex > 1);
	}

	private boolean raisesAllowed() {
		return raiseCount < raisesAllowed;
	}

	// return hand strengths for all hands passed in through the constructor
	private static int[] getHandStrengths(int[] playerCards, int[] board) throws Exception {
		int[] handStrengths = new int[2];
		// store the hand and table cards together
		for (int player = 0; player < playerCards.length; player++) {
			handStrengths[player] = getHandStrength(playerCards[player], board);
		}
		return handStrengths;

	}

	private static int getHandStrength(int playerCard, int[] board) throws Exception {
		if (board.length == 2) {
			// store the hand and table cards together
			long combinationCode = 0l;
			combinationCode = combinationCode | getCardNumber(playerCard);
			for (int boardCard : board)
				combinationCode = combinationCode | getCardNumber(boardCard);

			return SmallHandEvaluator.evaluateRhodeIslandHand(combinationCode);

		} else if (board.length == 0) {
			// store the hand and table cards together
			long combinationCode = 0l;
			combinationCode = combinationCode | getCardNumber(playerCard);

			return SmallHandEvaluator.evaluateSingleCardPokerHand(combinationCode);

		} else if (board.length == 1) {

			// store the hand and table cards together
			long combinationCode = 0l;
			combinationCode = combinationCode | getCardNumber(playerCard);
			for (int boardCard : board)
				combinationCode = combinationCode | getCardNumber(boardCard);
			;

			return SmallHandEvaluator.evaluateTwoCardPokerHand(combinationCode);

		}
		throw new Exception("PokerGame with board length : " + board.length + " not recognised");
	}

	/**
	 * Returns the number of the card as a long.
	 */
	private static long getCardNumber(int card) {
		int suitShift = card / 13;
		int heightShift = card % 13;
		return (1l << (16 * suitShift + heightShift));
	}

	private static int[] getPayOffsForTwoPlayerLiteGame(int[] playerCards, int[] board, int[] pot) throws Exception {
		int[] payoffs = new int[playerCards.length];
		int[] handStrengths = getHandStrengths(playerCards, board);
		Integer player0Strength = handStrengths[0];
		Integer player1Strength = handStrengths[1];
		if (player0Strength > player1Strength) {
			Integer player1Loss = pot[1];
			payoffs[0] = player1Loss;
			payoffs[1] = -player1Loss;
		} else if (player0Strength < player1Strength) {
			Integer player0Loss = pot[0];
			payoffs[0] = -player0Loss;
			payoffs[1] = player0Loss;
		} else {
			// payoffs already set to zero
		}

		return payoffs;
	}

	/**
	 * Returns a string representation of the card history
	 */
	private StringBuffer getThreeCardHistory(int singleCardHand, int[] turnedCards) {

		boolean playerSuitedWithTurn = true;
		boolean riverSuitedWithTurn = true;
		int turnedCount = 0;

		if (turnedCards != null) {
			turnedCount = turnedCards.length;
		}
		int[] handSuit = new int[turnedCount + 1];
		handSuit[0] = singleCardHand / 13;
		int[] handRanks = new int[turnedCount + 1];
		handRanks[0] = singleCardHand % 13;

		for (int boardIndex=0;boardIndex<turnedCount; boardIndex++) {
			handSuit[boardIndex+1]=this.board[boardIndex] / 13;
			handRanks[boardIndex+1]=this.board[boardIndex] % 13;
		}

		if (turnedCount > 0 && handSuit[0] != handSuit[1]) {
			playerSuitedWithTurn = false;
		}

		if (turnedCount == 2) {
			riverSuitedWithTurn = (handSuit[1] == handSuit[2]);
		}

		StringBuffer str = new StringBuffer();
		str.append("[");
		str.append(playerSuitedWithTurn ? "S" : "O");
		str.append(",");
		str.append(riverSuitedWithTurn ? "S" : "O");
		str.append(",");

		for (int i = 0; i < handRanks.length - 1; i++) {
			str.append(handRanks[i]);
			str.append(",");
		}
		str.append(handRanks[handRanks.length - 1]);
		str.append("]");

		return str;
	}

	public int[] getTurnedCards() {
		if (dealCount > 1) {
			int[] turnedCards = new int[dealCount - 1];
			System.arraycopy(board, 0, turnedCards, 0, turnedCards.length);
			return turnedCards;
		}
		return new int[0];
	}

	abstract void postBlinds();

}
