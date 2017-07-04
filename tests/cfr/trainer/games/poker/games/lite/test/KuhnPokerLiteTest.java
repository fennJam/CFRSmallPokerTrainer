package cfr.trainer.games.poker.games.lite.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cfr.trainer.games.Game;
import cfr.trainer.games.GameFactory;
import cfr.trainer.games.poker.BetRound;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.actions.CallAction;
import cfr.trainer.games.poker.actions.DealAction;
import cfr.trainer.games.poker.actions.FoldAction;
import cfr.trainer.games.poker.actions.RaiseAction;
import cfr.trainer.games.poker.games.lite.KuhnPokerLite;

public class KuhnPokerLiteTest {
	@Test
	public void getListOfGamesWithAllPossibleChanceNodesTest() {
		KuhnPokerLite gameTemplate = new KuhnPokerLite();

		List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

		assertEquals(6, chanceComboList.size());

		KuhnPokerLite game0 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(0));
		KuhnPokerLite game1 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(1));
		KuhnPokerLite game2 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(2));
		KuhnPokerLite game3 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(3));
		KuhnPokerLite game4 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(4));
		KuhnPokerLite game5 = new KuhnPokerLite().setValidChanceCombinations(chanceComboList.get(5));

		assertEquals(9, game0.getHands()[0] % 13);
		assertEquals(10, game0.getHands()[1] % 13);

		assertEquals(9, game1.getHands()[0] % 13);
		assertEquals(11, game1.getHands()[1] % 13);

		assertEquals(10, game2.getHands()[0] % 13);
		assertEquals(9, game2.getHands()[1] % 13);

		assertEquals(10, game3.getHands()[0] % 13);
		assertEquals(11, game3.getHands()[1] % 13);

		assertEquals(11, game4.getHands()[0] % 13);
		assertEquals(9, game4.getHands()[1] % 13);

		assertEquals(11, game5.getHands()[0] % 13);
		assertEquals(10, game5.getHands()[1] % 13);

	}

	@Test
	public void afterDealTest() throws Exception {
		KuhnPokerLite gameTemplate = new KuhnPokerLite();

		List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

		assertEquals(6, chanceComboList.size());

		KuhnPokerLite kuhnPokerLitegame = new KuhnPokerLite();

		kuhnPokerLitegame.startGame();

		kuhnPokerLitegame.setValidChanceCombinations(chanceComboList.get(0));

		assertEquals(0, kuhnPokerLitegame.getPlayerToAct());


		// available actions
		assertEquals(3, kuhnPokerLitegame.constructActionArray().length);
		assertEquals(FoldAction.getInstance(), kuhnPokerLitegame.constructActionArray()[0]);
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.constructActionArray()[1]);
		assertTrue(kuhnPokerLitegame.constructActionArray()[2] instanceof RaiseAction);
		RaiseAction actualRaiseAction = (RaiseAction) kuhnPokerLitegame.constructActionArray()[2];
		assertEquals(1, actualRaiseAction.getRaiseAmount());
		// actions taken
		assertEquals(1, kuhnPokerLitegame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(0));
		assertEquals(0, kuhnPokerLitegame.getRaiseCount());
		// Betround - Limit
		assertEquals(BetRound.RIVER, kuhnPokerLitegame.getBetRound());
		assertEquals(BettingLimit.LIMIT, kuhnPokerLitegame.getBettingLimit());

		// Hands + Board
		assertEquals(0, kuhnPokerLitegame.getBoard().length);
		assertEquals(0, kuhnPokerLitegame.getVisibleBoardCards().length);
		assertEquals(2, kuhnPokerLitegame.getHands().length);
		assertEquals(9, kuhnPokerLitegame.getHands()[0]);
		assertEquals(10, kuhnPokerLitegame.getHands()[1]);
		// Pot
		assertEquals(1, kuhnPokerLitegame.getPot()[0]);
		assertEquals(1, kuhnPokerLitegame.getPot()[1]);
		// Node ID
		assertEquals("[S,S,9]D", kuhnPokerLitegame.getNodeIdWithActionMemory());
		assertEquals("[S,S,9]2 raisesAllowed : true", kuhnPokerLitegame.getNodeIdWithSummaryState());
		assertFalse(kuhnPokerLitegame.isAtChanceNode());
		assertFalse(kuhnPokerLitegame.isAtTerminalNode());
		assertTrue(kuhnPokerLitegame.raisesAllowed());
		// Stacks
		assertEquals(1, kuhnPokerLitegame.getPlayerStack()[0]);
		assertEquals(1, kuhnPokerLitegame.getPlayerStack()[1]);
		//

		assertFalse(kuhnPokerLitegame.lastActionIsTerminalCallForTheBettingRound());

	}
	
	
	@Test
	public void afterCallTest() throws Exception {
		KuhnPokerLite gameTemplate = new KuhnPokerLite();

		List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

		assertEquals(6, chanceComboList.size());

		KuhnPokerLite kuhnPokerLitegame = new KuhnPokerLite();

		kuhnPokerLitegame.startGame();

		kuhnPokerLitegame.setValidChanceCombinations(chanceComboList.get(0));

		assertEquals(0, kuhnPokerLitegame.getPlayerToAct());

		kuhnPokerLitegame.performAction(0, CallAction.getInstance());

		assertEquals(1, kuhnPokerLitegame.getPlayerToAct());

		// available actions
		assertEquals(3, kuhnPokerLitegame.constructActionArray().length);
		assertEquals(FoldAction.getInstance(), kuhnPokerLitegame.constructActionArray()[0]);
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.constructActionArray()[1]);
		assertTrue(kuhnPokerLitegame.constructActionArray()[2] instanceof RaiseAction);
		RaiseAction actualRaiseAction = (RaiseAction) kuhnPokerLitegame.constructActionArray()[2];
		assertEquals(1, actualRaiseAction.getRaiseAmount());
		// actions taken
		assertEquals(2, kuhnPokerLitegame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(0));
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(1));
		assertEquals(0, kuhnPokerLitegame.getRaiseCount());
		// Betround - Limit
		assertEquals(BetRound.RIVER, kuhnPokerLitegame.getBetRound());
		assertEquals(BettingLimit.LIMIT, kuhnPokerLitegame.getBettingLimit());

		// Hands + Board
		assertEquals(0, kuhnPokerLitegame.getBoard().length);
		assertEquals(0, kuhnPokerLitegame.getVisibleBoardCards().length);
		assertEquals(2, kuhnPokerLitegame.getHands().length);
		assertEquals(9, kuhnPokerLitegame.getHands()[0]);
		assertEquals(10, kuhnPokerLitegame.getHands()[1]);
		// Pot
		assertEquals(1, kuhnPokerLitegame.getPot()[0]);
		assertEquals(1, kuhnPokerLitegame.getPot()[1]);
		// Node ID
		assertEquals("[S,S,10]D,C", kuhnPokerLitegame.getNodeIdWithActionMemory());
		assertEquals("[S,S,10]2 raisesAllowed : true", kuhnPokerLitegame.getNodeIdWithSummaryState());
		assertFalse(kuhnPokerLitegame.isAtChanceNode());
		assertFalse(kuhnPokerLitegame.isAtTerminalNode());
		assertTrue(kuhnPokerLitegame.raisesAllowed());
		// Stacks
		assertEquals(1, kuhnPokerLitegame.getPlayerStack()[0]);
		assertEquals(1, kuhnPokerLitegame.getPlayerStack()[1]);
		//

		assertFalse(kuhnPokerLitegame.lastActionIsTerminalCallForTheBettingRound());

	}

	@Test
	public void afterCallCallTest() throws Exception {
		KuhnPokerLite gameTemplate = new KuhnPokerLite();
		List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

		assertEquals(6, chanceComboList.size());

		KuhnPokerLite kuhnPokerLitegame = new KuhnPokerLite();

		kuhnPokerLitegame.startGame();

		kuhnPokerLitegame.setValidChanceCombinations(chanceComboList.get(0));

		assertEquals(0, kuhnPokerLitegame.getPlayerToAct());

		kuhnPokerLitegame.performAction(0, CallAction.getInstance());
		kuhnPokerLitegame.performAction(0, CallAction.getInstance());

		assertEquals(0, kuhnPokerLitegame.getPlayerToAct());

		// available actions
		// assertEquals(3,kuhnPokerLitegame.constructActionArray().length);
		// assertEquals(FoldAction.getInstance(),kuhnPokerLitegame.constructActionArray()[0]);
		// assertEquals(CallAction.getInstance(),kuhnPokerLitegame.constructActionArray()[1]);
		// assertTrue(kuhnPokerLitegame.constructActionArray()[2] instanceof
		// RaiseAction);
		// RaiseAction actualRaiseAction =
		// (RaiseAction)kuhnPokerLitegame.constructActionArray()[2];
		// assertEquals(1,actualRaiseAction.getRaiseAmount());
		// actions taken
		assertEquals(3, kuhnPokerLitegame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(0));
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(1));
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(2));
		assertEquals(0, kuhnPokerLitegame.getRaiseCount());
		// Betround - Limit
		assertEquals(BetRound.RIVER, kuhnPokerLitegame.getBetRound());
		assertEquals(BettingLimit.LIMIT, kuhnPokerLitegame.getBettingLimit());

		// Hands + Board
		assertEquals(0, kuhnPokerLitegame.getBoard().length);
		assertEquals(0, kuhnPokerLitegame.getVisibleBoardCards().length);
		assertEquals(2, kuhnPokerLitegame.getHands().length);
		assertEquals(9, kuhnPokerLitegame.getHands()[0]);
		assertEquals(10, kuhnPokerLitegame.getHands()[1]);
		// Pot
		assertEquals(1, kuhnPokerLitegame.getPot()[0]);
		assertEquals(1, kuhnPokerLitegame.getPot()[1]);
		// Node ID
		assertEquals("[S,S,9]D,C,C", kuhnPokerLitegame.getNodeIdWithActionMemory());
		assertEquals("[S,S,9]2 raisesAllowed : true", kuhnPokerLitegame.getNodeIdWithSummaryState());
		assertTrue(kuhnPokerLitegame.isAtChanceNode());
		assertTrue(kuhnPokerLitegame.isAtTerminalNode());
		// assertFalse(kuhnPokerLitegame.raisesAllowed());
		// Stacks
		assertEquals(1, kuhnPokerLitegame.getPlayerStack()[0]);
		assertEquals(1, kuhnPokerLitegame.getPlayerStack()[1]);
		//

		assertTrue(kuhnPokerLitegame.lastActionIsTerminalCallForTheBettingRound());
		assertEquals(new Integer(-1), kuhnPokerLitegame.getPayOffs().get(0));
		assertEquals(new Integer(1), kuhnPokerLitegame.getPayOffs().get(1));

	}

	@Test
	public void afterCallRaiseTest() throws Exception {
		KuhnPokerLite gameTemplate = new KuhnPokerLite();
		List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

		assertEquals(6, chanceComboList.size());

		KuhnPokerLite kuhnPokerLitegame = new KuhnPokerLite();

		kuhnPokerLitegame.startGame();

		kuhnPokerLitegame.setValidChanceCombinations(chanceComboList.get(0));

		assertEquals(0, kuhnPokerLitegame.getPlayerToAct());

		kuhnPokerLitegame.performAction(0, CallAction.getInstance());
		kuhnPokerLitegame.performAction(1, new RaiseAction(1));

		assertEquals(0, kuhnPokerLitegame.getPlayerToAct());

		// available actions
		assertFalse(kuhnPokerLitegame.raisesAllowed());
		assertEquals(2, kuhnPokerLitegame.constructActionArray().length);
		assertEquals(FoldAction.getInstance(), kuhnPokerLitegame.constructActionArray()[0]);
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.constructActionArray()[1]);
		// TODO delete
		// assertTrue(kuhnPokerLitegame.constructActionArray()[2] instanceof
		// RaiseAction);
		// RaiseAction actualRaiseAction =
		// (RaiseAction)kuhnPokerLitegame.constructActionArray()[2];
		// assertEquals(1,actualRaiseAction.getRaiseAmount());
		// actions taken
		assertEquals(3, kuhnPokerLitegame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(0));
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(1));
		assertTrue(kuhnPokerLitegame.getActionsTaken().get(2) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction) kuhnPokerLitegame.getActionsTaken().get(2);
		assertEquals(1, raiseActionTaken.getRaiseAmount());
		assertEquals(1, kuhnPokerLitegame.getRaiseCount());
		// Betround - Limit
		assertEquals(BetRound.RIVER, kuhnPokerLitegame.getBetRound());
		assertEquals(BettingLimit.LIMIT, kuhnPokerLitegame.getBettingLimit());

		// Hands + Board
		assertEquals(0, kuhnPokerLitegame.getBoard().length);
		assertEquals(0, kuhnPokerLitegame.getVisibleBoardCards().length);
		assertEquals(2, kuhnPokerLitegame.getHands().length);
		assertEquals(9, kuhnPokerLitegame.getHands()[0]);
		assertEquals(10, kuhnPokerLitegame.getHands()[1]);
		// Pot
		assertEquals(1, kuhnPokerLitegame.getPot()[0]);
		assertEquals(2, kuhnPokerLitegame.getPot()[1]);
		// Node ID
		assertEquals("[S,S,9]D,C,1", kuhnPokerLitegame.getNodeIdWithActionMemory());
		assertEquals("[S,S,9]3 raisesAllowed : false", kuhnPokerLitegame.getNodeIdWithSummaryState());
		assertFalse(kuhnPokerLitegame.isAtChanceNode());
		assertFalse(kuhnPokerLitegame.isAtTerminalNode());
		assertFalse(kuhnPokerLitegame.raisesAllowed());
		// Stacks
		assertEquals(1, kuhnPokerLitegame.getPlayerStack()[0]);
		assertEquals(0, kuhnPokerLitegame.getPlayerStack()[1]);
		//

		assertFalse(kuhnPokerLitegame.lastActionIsTerminalCallForTheBettingRound());
		assertEquals(new Integer(-1), kuhnPokerLitegame.getPayOffs().get(0));
		assertEquals(new Integer(1), kuhnPokerLitegame.getPayOffs().get(1));

	}
@Test
	public void afterRaiseTest() throws Exception {
		KuhnPokerLite gameTemplate = new KuhnPokerLite();
		List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

		assertEquals(6, chanceComboList.size());

		KuhnPokerLite kuhnPokerLitegame = new KuhnPokerLite();

		kuhnPokerLitegame.startGame();

		kuhnPokerLitegame.setValidChanceCombinations(chanceComboList.get(0));

		assertEquals(0, kuhnPokerLitegame.getPlayerToAct());

		kuhnPokerLitegame.performAction(0, new RaiseAction(1));


		assertEquals(1, kuhnPokerLitegame.getPlayerToAct());

		// available actions
		assertFalse(kuhnPokerLitegame.raisesAllowed());
		assertEquals(2, kuhnPokerLitegame.constructActionArray().length);
		assertEquals(FoldAction.getInstance(), kuhnPokerLitegame.constructActionArray()[0]);
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.constructActionArray()[1]);
		// TODO delete
		// assertTrue(kuhnPokerLitegame.constructActionArray()[2] instanceof
		// RaiseAction);
		// RaiseAction actualRaiseAction =
		// (RaiseAction)kuhnPokerLitegame.constructActionArray()[2];
		// assertEquals(1,actualRaiseAction.getRaiseAmount());
		// actions taken
		assertEquals(2, kuhnPokerLitegame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(0));
		assertTrue(kuhnPokerLitegame.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction) kuhnPokerLitegame.getActionsTaken().get(1);
		assertEquals(1, raiseActionTaken.getRaiseAmount());
		assertEquals(1, kuhnPokerLitegame.getRaiseCount());
		// Betround - Limit
		assertEquals(BetRound.RIVER, kuhnPokerLitegame.getBetRound());
		assertEquals(BettingLimit.LIMIT, kuhnPokerLitegame.getBettingLimit());

		// Hands + Board
		assertEquals(0, kuhnPokerLitegame.getBoard().length);
		assertEquals(0, kuhnPokerLitegame.getVisibleBoardCards().length);
		assertEquals(2, kuhnPokerLitegame.getHands().length);
		assertEquals(9, kuhnPokerLitegame.getHands()[0]);
		assertEquals(10, kuhnPokerLitegame.getHands()[1]);
		// Pot
		assertEquals(2, kuhnPokerLitegame.getPot()[0]);
		assertEquals(1, kuhnPokerLitegame.getPot()[1]);
		// Node ID
		assertEquals("[S,S,10]D,1", kuhnPokerLitegame.getNodeIdWithActionMemory());
		assertEquals("[S,S,10]3 raisesAllowed : false", kuhnPokerLitegame.getNodeIdWithSummaryState());
		assertFalse(kuhnPokerLitegame.isAtChanceNode());
		assertFalse(kuhnPokerLitegame.isAtTerminalNode());
		assertFalse(kuhnPokerLitegame.raisesAllowed());
		// Stacks
		assertEquals(0, kuhnPokerLitegame.getPlayerStack()[0]);
		assertEquals(1, kuhnPokerLitegame.getPlayerStack()[1]);
		//

		assertFalse(kuhnPokerLitegame.lastActionIsTerminalCallForTheBettingRound());
		assertEquals(new Integer(-2), kuhnPokerLitegame.getPayOffs().get(0));
		assertEquals(new Integer(2), kuhnPokerLitegame.getPayOffs().get(1));
	}


@Test
public void afterCopyGameRaiseTest() throws Exception {
	KuhnPokerLite gameTemplate = new KuhnPokerLite();
	List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

	assertEquals(6, chanceComboList.size());

	KuhnPokerLite kuhnPokerLitegame = new KuhnPokerLite();

	kuhnPokerLitegame.startGame();

	kuhnPokerLitegame.setValidChanceCombinations(chanceComboList.get(0));

	assertEquals(0, kuhnPokerLitegame.getPlayerToAct());

	kuhnPokerLitegame.performAction(0, new RaiseAction(1));

	
	Game gameCopy = GameFactory.copyGame(kuhnPokerLitegame);
	
	assertTrue(gameCopy instanceof KuhnPokerLite);
	KuhnPokerLite kuhnPokerLiteGameCopy = (KuhnPokerLite)gameCopy;

	assertEquals(1, kuhnPokerLiteGameCopy.getPlayerToAct());

	// available actions
	assertFalse(kuhnPokerLiteGameCopy.raisesAllowed());
	assertEquals(2, kuhnPokerLiteGameCopy.constructActionArray().length);
	assertEquals(FoldAction.getInstance(), kuhnPokerLiteGameCopy.constructActionArray()[0]);
	assertEquals(CallAction.getInstance(), kuhnPokerLiteGameCopy.constructActionArray()[1]);
	// TODO delete
	// assertTrue(kuhnPokerLiteGameCopy.constructActionArray()[2] instanceof
	// RaiseAction);
	// RaiseAction actualRaiseAction =
	// (RaiseAction)kuhnPokerLiteGameCopy.constructActionArray()[2];
	// assertEquals(1,actualRaiseAction.getRaiseAmount());
	// actions taken
	assertEquals(2, kuhnPokerLiteGameCopy.getActionsTaken().size());
	assertEquals(DealAction.getInstance(), kuhnPokerLiteGameCopy.getActionsTaken().get(0));
	assertTrue(kuhnPokerLiteGameCopy.getActionsTaken().get(1) instanceof RaiseAction);
	RaiseAction raiseActionTaken = (RaiseAction) kuhnPokerLiteGameCopy.getActionsTaken().get(1);
	assertEquals(1, raiseActionTaken.getRaiseAmount());
	assertEquals(1, kuhnPokerLiteGameCopy.getRaiseCount());
	// Betround - Limit
	assertEquals(BetRound.RIVER, kuhnPokerLiteGameCopy.getBetRound());
	assertEquals(BettingLimit.LIMIT, kuhnPokerLiteGameCopy.getBettingLimit());

	// Hands + Board
	assertEquals(0, kuhnPokerLiteGameCopy.getBoard().length);
	assertEquals(0, kuhnPokerLiteGameCopy.getVisibleBoardCards().length);
	assertEquals(2, kuhnPokerLiteGameCopy.getHands().length);
	assertEquals(9, kuhnPokerLiteGameCopy.getHands()[0]);
	assertEquals(10, kuhnPokerLiteGameCopy.getHands()[1]);
	// Pot
	assertEquals(2, kuhnPokerLiteGameCopy.getPot()[0]);
	assertEquals(1, kuhnPokerLiteGameCopy.getPot()[1]);
	// Node ID
	assertEquals("[S,S,10]D,1", kuhnPokerLiteGameCopy.getNodeIdWithActionMemory());
	assertEquals("[S,S,10]3 raisesAllowed : false", kuhnPokerLiteGameCopy.getNodeIdWithSummaryState());
	assertFalse(kuhnPokerLiteGameCopy.isAtChanceNode());
	assertFalse(kuhnPokerLiteGameCopy.isAtTerminalNode());
	assertFalse(kuhnPokerLiteGameCopy.raisesAllowed());
	// Stacks
	assertEquals(0, kuhnPokerLiteGameCopy.getPlayerStack()[0]);
	assertEquals(1, kuhnPokerLiteGameCopy.getPlayerStack()[1]);
	//

	assertFalse(kuhnPokerLiteGameCopy.lastActionIsTerminalCallForTheBettingRound());
	assertEquals(new Integer(-2), kuhnPokerLiteGameCopy.getPayOffs().get(0));
	assertEquals(new Integer(2), kuhnPokerLiteGameCopy.getPayOffs().get(1));
}


@Test
	public void afterRaiseCallTest() throws Exception {
		KuhnPokerLite gameTemplate = new KuhnPokerLite();
		List<List<Integer>> chanceComboList = gameTemplate.getListOfValidChanceCombinations();

		assertEquals(6, chanceComboList.size());

		KuhnPokerLite kuhnPokerLitegame = new KuhnPokerLite();

		kuhnPokerLitegame.startGame();

		kuhnPokerLitegame.setValidChanceCombinations(chanceComboList.get(0));

		assertEquals(0, kuhnPokerLitegame.getPlayerToAct());

		kuhnPokerLitegame.performAction(0, new RaiseAction(1));
		assertEquals(1, kuhnPokerLitegame.getPlayerToAct());

		kuhnPokerLitegame.performAction(1, CallAction.getInstance());


		assertEquals(0, kuhnPokerLitegame.getPlayerToAct());

		// available actions
		assertFalse(kuhnPokerLitegame.raisesAllowed());
		assertEquals(2, kuhnPokerLitegame.constructActionArray().length);
		assertEquals(FoldAction.getInstance(), kuhnPokerLitegame.constructActionArray()[0]);
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.constructActionArray()[1]);
		// TODO delete
		// assertTrue(kuhnPokerLitegame.constructActionArray()[2] instanceof
		// RaiseAction);
		// RaiseAction actualRaiseAction =
		// (RaiseAction)kuhnPokerLitegame.constructActionArray()[2];
		// assertEquals(1,actualRaiseAction.getRaiseAmount());
		// actions taken
		assertEquals(3, kuhnPokerLitegame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(0));
		assertTrue(kuhnPokerLitegame.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction) kuhnPokerLitegame.getActionsTaken().get(1);
		assertEquals(1, raiseActionTaken.getRaiseAmount());
		assertEquals(CallAction.getInstance(), kuhnPokerLitegame.getActionsTaken().get(2));
		assertEquals(1, kuhnPokerLitegame.getRaiseCount());
		// Betround - Limit
		assertEquals(BetRound.RIVER, kuhnPokerLitegame.getBetRound());
		assertEquals(BettingLimit.LIMIT, kuhnPokerLitegame.getBettingLimit());

		// Hands + Board
		assertEquals(0, kuhnPokerLitegame.getBoard().length);
		assertEquals(0, kuhnPokerLitegame.getVisibleBoardCards().length);
		assertEquals(2, kuhnPokerLitegame.getHands().length);
		assertEquals(9, kuhnPokerLitegame.getHands()[0]);
		assertEquals(10, kuhnPokerLitegame.getHands()[1]);
		// Pot
		assertEquals(2, kuhnPokerLitegame.getPot()[0]);
		assertEquals(2, kuhnPokerLitegame.getPot()[1]);
		// Node ID
		assertEquals("[S,S,9]D,1,C", kuhnPokerLitegame.getNodeIdWithActionMemory());
		assertEquals("[S,S,9]4 raisesAllowed : false", kuhnPokerLitegame.getNodeIdWithSummaryState());
		assertTrue(kuhnPokerLitegame.isAtChanceNode());
		assertTrue(kuhnPokerLitegame.isAtTerminalNode());
		assertFalse(kuhnPokerLitegame.raisesAllowed());
		// Stacks
		assertEquals(0, kuhnPokerLitegame.getPlayerStack()[0]);
		assertEquals(0, kuhnPokerLitegame.getPlayerStack()[1]);
		//

		assertTrue(kuhnPokerLitegame.lastActionIsTerminalCallForTheBettingRound());
		assertEquals(new Integer(-2), kuhnPokerLitegame.getPayOffs().get(0));
		assertEquals(new Integer(2), kuhnPokerLitegame.getPayOffs().get(1));
	}

}
