package cfr.trainer.games.poker.nodes.test;

import org.junit.Test;
import org.mockito.Mockito;

import cfr.trainer.action.Action;
import cfr.trainer.games.GameType;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.Pot;
import cfr.trainer.games.poker.actions.CallAction;
import cfr.trainer.games.poker.actions.DealAction;
import cfr.trainer.games.poker.actions.FoldAction;
import cfr.trainer.games.poker.actions.PokerAction;
import cfr.trainer.games.poker.actions.RaiseAction;
import cfr.trainer.games.poker.games.PokerGame;
import cfr.trainer.games.poker.nodes.LimitPokerInfoSet;
import cfr.trainer.games.poker.nodes.NoLimitPokerInfoSet;
import cfr.trainer.games.poker.nodes.PokerInfoSetFactory;
import cfr.trainer.games.poker.nodes.PotLimitPokerInfoSet;
import cfr.trainer.games.poker.nodes.TerminalInfoSet;
import cfr.trainer.node.Node;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PokerInfoSetFactoryTest {

	PokerGame mockedPokerGame = Mockito.mock(PokerGame.class, Mockito.RETURNS_DEEP_STUBS);
	Pot mockedPot = Mockito.mock(Pot.class);

	@Test
	public final void nodeFactoryReturnsTerminalNode_test() {
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(false);
		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);

		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID", mockedPokerGame);

		assertTrue(infoSet instanceof TerminalInfoSet);

	}

	@Test
	public final void nodeFactoryReturnsLimitPokerInfoSet_test() {
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(true);
		Mockito.when(mockedPokerGame.getBettingLimit()).thenReturn(BettingLimit.LIMIT);
		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);

		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID", mockedPokerGame);

		assertTrue(infoSet instanceof LimitPokerInfoSet);

	}

	@Test
	public final void nodeFactoryReturnsPotLimitPokerInfoSet_test() {
		List<Action> possibleActions = new ArrayList<Action>();
		possibleActions.add(FoldAction.getInstance());
		possibleActions.add(CallAction.getInstance());

		for (int raise = 1; raise < 20 + 1; raise++) {
			possibleActions.add(new RaiseAction(raise));
		}
		Mockito.when(mockedPokerGame.getPossibleActions()).thenReturn(possibleActions);
		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(true);
		Mockito.when(mockedPokerGame.getBettingLimit()).thenReturn(BettingLimit.POT_LIMIT);
		Mockito.when(mockedPokerGame.getPot()).thenReturn(mockedPot);
		Mockito.when(mockedPot.getLastBetValue()).thenReturn(2);
		Mockito.when(mockedPot.getTotalPotSize()).thenReturn(5);

		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID", mockedPokerGame);
		assertTrue(infoSet instanceof PotLimitPokerInfoSet);
		assertEquals(10, infoSet.numOfActions());

	}

	@Test
	public final void nodeFactoryReturnsNoLimitPokerInfoSet_test() {
		Integer one = Integer.valueOf(1);
		List<Action> possibleActions = new ArrayList<Action>();
		possibleActions.add(FoldAction.getInstance());
		possibleActions.add(CallAction.getInstance());

		for (int raise = 1; raise < 20 + 1; raise++) {
			possibleActions.add(new RaiseAction(raise));
		}
		Mockito.when(mockedPokerGame.getPossibleActions()).thenReturn(possibleActions);
		
		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(true);
		Mockito.when(mockedPokerGame.getBettingLimit()).thenReturn(BettingLimit.NO_LIMIT);
		Mockito.when(mockedPokerGame.getPlayerToAct()).thenReturn(one);
		Mockito.when(mockedPokerGame.getPlayer(one).getStack()).thenReturn(12);

		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID", mockedPokerGame);
		NoLimitPokerInfoSet noLimitPokerInfoSet = (NoLimitPokerInfoSet) infoSet;
		assertEquals(14, infoSet.numOfActions());

		List<Action> actionsList = Arrays.asList(noLimitPokerInfoSet.constructActionArray(mockedPokerGame));

		assertTrue(actionsList.contains(FoldAction.getInstance()));
		assertTrue(actionsList.contains(CallAction.getInstance()));

		String raisesExpected = "";
		String raisesFound = "";

		for (int raise = 1; raise < 13; raise++) {
			raisesExpected += raise;
		}

		for (Action action : actionsList) {
			if (action instanceof RaiseAction) {
				RaiseAction raiseAction = (RaiseAction) action;
				raisesFound += raiseAction.getRaiseAmount();
			}

		}

		assertEquals(raisesExpected, raisesFound);

	}

	@Test
	public final void nodeFactoryReturnsNoLimitPokerInfoSet_test2() {
		Integer one = Integer.valueOf(1);
		Integer zero = Integer.valueOf(0);
		List<Action> possibleActions = new ArrayList<Action>();
		possibleActions.add(FoldAction.getInstance());
		possibleActions.add(CallAction.getInstance());

		for (int raise = 1; raise < 20 + 1; raise++) {
			possibleActions.add(new RaiseAction(raise));
		}
		Mockito.when(mockedPokerGame.getPossibleActions()).thenReturn(possibleActions);
		
		
		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(true);
		Mockito.when(mockedPokerGame.getBettingLimit()).thenReturn(BettingLimit.NO_LIMIT);
		Mockito.when(mockedPokerGame.getPlayerToAct()).thenReturn(one);
		Mockito.when(mockedPokerGame.getPlayer(one).getStack()).thenReturn(12);

		Mockito.when(mockedPokerGame.getPot().getPlayersContributionToPot(one)).thenReturn(6);
		Mockito.when(mockedPokerGame.getPot().getPlayersContributionToPot(zero)).thenReturn(10);

		
		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID", mockedPokerGame);
		NoLimitPokerInfoSet noLimitPokerInfoSet = (NoLimitPokerInfoSet) infoSet;
		assertEquals(10, infoSet.numOfActions());

		List<Action> actionsList = Arrays.asList(noLimitPokerInfoSet.constructActionArray(mockedPokerGame));

		assertTrue(actionsList.contains(FoldAction.getInstance()));
		assertTrue(actionsList.contains(CallAction.getInstance()));

		String raisesExpected = "";
		String raisesFound = "";

		for (int raise = 1; raise < 9; raise++) {
			raisesExpected += raise;
		}

		for (Action action : actionsList) {
			if (action instanceof RaiseAction) {
				RaiseAction raiseAction = (RaiseAction) action;
				raisesFound += raiseAction.getRaiseAmount();
			}

		}
		assertEquals(raisesExpected, raisesFound);
	}

	@Test
	public final void nodeFactoryReturnsNoLimitPokerInfoSet_test_no_chips_left() {
		Integer one = Integer.valueOf(1);
		Integer zero = Integer.valueOf(0);
		List<Action> possibleActions = new ArrayList<Action>();
		possibleActions.add(FoldAction.getInstance());
		possibleActions.add(CallAction.getInstance());

		// for (int raise = 1; raise < 20 + 1; raise++) {
		// possibleActions.add(new RaiseAction(raise));
		// }

		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(true);
		Mockito.when(mockedPokerGame.getBettingLimit()).thenReturn(BettingLimit.NO_LIMIT);
		Mockito.when(mockedPokerGame.getPlayerToAct()).thenReturn(one);
		Mockito.when(mockedPokerGame.getPlayer(one).getStack()).thenReturn(0);
		Mockito.when(mockedPokerGame.getPlayer(one).getStack()).thenReturn(0);

		Mockito.when(mockedPokerGame.getPot().getPlayersContributionToPot(one)).thenReturn(6);
		Mockito.when(mockedPokerGame.getPot().getPlayersContributionToPot(zero)).thenReturn(10);
		Mockito.when(mockedPokerGame.getPossibleActions()).thenReturn(possibleActions);

		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID", mockedPokerGame);
		NoLimitPokerInfoSet noLimitPokerInfoSet = (NoLimitPokerInfoSet) infoSet;
		assertEquals(2, infoSet.numOfActions());

		List<Action> actionsList = Arrays.asList(noLimitPokerInfoSet.constructActionArray(mockedPokerGame));

		assertTrue(actionsList.contains(FoldAction.getInstance()));
		assertTrue(actionsList.contains(CallAction.getInstance()));

		List<Action> expectedActions = new ArrayList<>();
		expectedActions.add(FoldAction.getInstance());
		expectedActions.add(CallAction.getInstance());

		assertEquals(expectedActions, actionsList);
	}

	@Test
	public final void nodeFactoryReturnsNoLimitPokerInfoSet_test_not_enough_chips_left() {
		Integer one = Integer.valueOf(1);
		Integer zero = Integer.valueOf(0);
		List<Action> possibleActions = new ArrayList<Action>();
		possibleActions.add(FoldAction.getInstance());
		possibleActions.add(CallAction.getInstance());

		for (int raise = 1; raise < 20 + 1; raise++) {
			possibleActions.add(new RaiseAction(raise));
		}

		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(true);
		Mockito.when(mockedPokerGame.getBettingLimit()).thenReturn(BettingLimit.NO_LIMIT);
		Mockito.when(mockedPokerGame.getPlayerToAct()).thenReturn(one);
		Mockito.when(mockedPokerGame.getPlayer(one).getStack()).thenReturn(4);

		Mockito.when(mockedPokerGame.getPot().getPlayersContributionToPot(one)).thenReturn(6);
		Mockito.when(mockedPokerGame.getPot().getPlayersContributionToPot(zero)).thenReturn(10);
		Mockito.when(mockedPokerGame.getPossibleActions()).thenReturn(possibleActions);

		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID", mockedPokerGame);
		NoLimitPokerInfoSet noLimitPokerInfoSet = (NoLimitPokerInfoSet) infoSet;
		assertEquals(2, infoSet.numOfActions());

		List<Action> actionsList = Arrays.asList(noLimitPokerInfoSet.constructActionArray(mockedPokerGame));

		assertTrue(actionsList.contains(FoldAction.getInstance()));
		assertTrue(actionsList.contains(CallAction.getInstance()));

		List<Action> expectedActions = new ArrayList<>();
		expectedActions.add(FoldAction.getInstance());
		expectedActions.add(CallAction.getInstance());

		assertEquals(expectedActions, actionsList);
	}
}
