package cfr.trainer.games.poker.games.lite.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import cfr.trainer.action.Action;
import cfr.trainer.games.GameType;
import cfr.trainer.games.poker.BetRound;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.Board;
import cfr.trainer.games.poker.CardHeight;
import cfr.trainer.games.poker.CardSuit;
import cfr.trainer.games.poker.HandSingleCard;
import cfr.trainer.games.poker.PokerGameType;
import cfr.trainer.games.poker.actions.CallAction;
import cfr.trainer.games.poker.actions.DealAction;
import cfr.trainer.games.poker.actions.FoldAction;
import cfr.trainer.games.poker.actions.RaiseAction;
import cfr.trainer.games.poker.decks.Deck;
import cfr.trainer.games.poker.decks.RoyalDeckShuffled;
import cfr.trainer.games.poker.games.PokerGame;
import cfr.trainer.games.poker.games.lite.PokerGameLite;
import cfr.trainer.games.poker.games.lite.RoyalRhodeIsland10ChipNoLimitLite;
import cfr.trainer.node.InfoSetFactory;
import cfr.trainer.node.Node;

public class RoyalRhodeIslandHeadsUPNoLimitPokerGameLiteTest {

	@Test
	public final void constructorTest() {
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLite = new RoyalRhodeIsland10ChipNoLimitLite(3);
		

		
		assertEquals(12,royalRhodeIsland10ChipNoLimitLite.getPossibleActions().size());
//	
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getPlayerToAct());

		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getActionsTaken().size());
		assertEquals(BettingLimit.NO_LIMIT,royalRhodeIsland10ChipNoLimitLite.getBettingLimit());
		assertEquals(BetRound.PRETURN,royalRhodeIsland10ChipNoLimitLite.getBettingRound());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getBoard() instanceof int[]);
//		assertTrue(RoyalRhodeIsland10ChipNoLimitLite.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof PokerGameLite);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
//		assertNull(RoyalRhodeIsland10ChipNoLimitLite.getHands().get(0));
//		assertNull(RoyalRhodeIsland10ChipNoLimitLite.getHands().get(1));
		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof PokerGameLite);
		assertEquals(116280,royalRhodeIsland10ChipNoLimitLite.getListOfValidChanceCombinations().length);
		assertEquals("[S,S,0]",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithActionMemory());
		assertEquals("[S,S,0]",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithSummaryState());
		
//		TODO handle null pointer thrown here
//		assertEquals("",RoyalRhodeIsland10ChipNoLimitLite.getNodeIdWithGameState());
//		assertEquals(0,RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(1));
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getPot()[0]);
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getPot()[1]);
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getRaiseCount());
		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getRaisesAllowedPerBettingRound());
		assertFalse(royalRhodeIsland10ChipNoLimitLite.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.raisesAllowed());
	}
	
	@Test
	public final void afterDealTest() throws Exception {
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLite = new RoyalRhodeIsland10ChipNoLimitLite(3);
		

		
		
//		
//		
		royalRhodeIsland10ChipNoLimitLite.startGame();
		int[] validChanceCombo = new int[4];
//		add Ad,Kd,Qd,JD
		validChanceCombo[0]=51;
		validChanceCombo[1]=50;
		validChanceCombo[2]=49;
		validChanceCombo[3]=48;
		
		royalRhodeIsland10ChipNoLimitLite.setValidChanceCombinations(validChanceCombo);
		
		assertEquals(12,royalRhodeIsland10ChipNoLimitLite.getPossibleActions().size());
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getPlayerHands().length);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getPot().length);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getPlayerStack().length);
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getPlayerToAct());

		assertEquals(1,royalRhodeIsland10ChipNoLimitLite.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(0));
		assertEquals(BettingLimit.NO_LIMIT,royalRhodeIsland10ChipNoLimitLite.getBettingLimit());
		assertEquals(BetRound.PRETURN,royalRhodeIsland10ChipNoLimitLite.getBettingRound());
//		Board
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getBoard() instanceof int[]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[0]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getBoard().length);
		
		
//		assertTrue(RoyalRhodeIsland10ChipNoLimitLite.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof PokerGameLite);
//		Hands
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);
		assertEquals(49,royalRhodeIsland10ChipNoLimitLite.getHands()[0]);
		assertEquals(48,royalRhodeIsland10ChipNoLimitLite.getHands()[1]);
		
		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof RoyalRhodeIsland10ChipNoLimitLite);
		assertEquals(116280,royalRhodeIsland10ChipNoLimitLite.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,10]D",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithActionMemory());
		assertEquals("[S,S,10]D",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithSummaryState());
		
//		TODO handle null pointer thrown here
//		assertEquals("",RoyalRhodeIsland10ChipNoLimitLite.getNodeIdWithGameState());
//		assertEquals(0,RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(1));
		assertEquals(1,royalRhodeIsland10ChipNoLimitLite.getPot()[0]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getPot()[1]);
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getRaiseCount());
		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getRaisesAllowedPerBettingRound());
		assertFalse(royalRhodeIsland10ChipNoLimitLite.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.raisesAllowed());
		
//		Actions available
		Action[] actionArray = royalRhodeIsland10ChipNoLimitLite.constructActionArray();
		assertEquals(10,actionArray.length);
		assertEquals(FoldAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(1));
		
		for (int action = 2; action <actionArray.length; action++) {
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
	}
	
	@Test
	public final void afterRaiseTest() throws Exception {
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLite = new RoyalRhodeIsland10ChipNoLimitLite( 3);

		royalRhodeIsland10ChipNoLimitLite.startGame();
		int[] validChanceCombo = new int[4];
//		add Ad,Kd,Qd,JD
		validChanceCombo[0]=51;
		validChanceCombo[1]=50;
		validChanceCombo[2]=49;
		validChanceCombo[3]=48;
		
		royalRhodeIsland10ChipNoLimitLite.setValidChanceCombinations(validChanceCombo);
		
		royalRhodeIsland10ChipNoLimitLite.performAction(0, new RaiseAction(5));
		
		assertEquals(12,royalRhodeIsland10ChipNoLimitLite.getPossibleActions().size());
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getPlayerHands().length);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getPot().length);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getPlayerStack().length);
		assertEquals(1,royalRhodeIsland10ChipNoLimitLite.getPlayerToAct());

		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(0));
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,royalRhodeIsland10ChipNoLimitLite.getBettingLimit());
		assertEquals(BetRound.PRETURN,royalRhodeIsland10ChipNoLimitLite.getBettingRound());
//		Board
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getBoard() instanceof int[]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[0]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getBoard().length);
		

		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof PokerGameLite);
//		Hands
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);

		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);
		assertEquals(49,royalRhodeIsland10ChipNoLimitLite.getHands()[0]);
		assertEquals(48,royalRhodeIsland10ChipNoLimitLite.getHands()[1]);
		
		assertEquals(116280,royalRhodeIsland10ChipNoLimitLite.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,9]D,5",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithActionMemory());
		assertEquals("[S,S,9]D5",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithSummaryState());
		
//		TODO handle null pointer thrown here
//		assertEquals("",RoyalRhodeIsland10ChipNoLimitLite.getNodeIdWithGameState());
//		assertEquals(0,RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(1));
		assertEquals(7,royalRhodeIsland10ChipNoLimitLite.getPot()[0]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getPot()[1]);
		assertEquals(1,royalRhodeIsland10ChipNoLimitLite.getRaiseCount());
		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getRaisesAllowedPerBettingRound());
		assertFalse(royalRhodeIsland10ChipNoLimitLite.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.raisesAllowed());
		
//		Actions available
		Action[] actionArray = royalRhodeIsland10ChipNoLimitLite.constructActionArray();
		assertEquals(5,actionArray.length);
		assertEquals(FoldAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(1));
		
		for (int action = 2; action <actionArray.length; action++) {
		assertTrue(actionArray[action] instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) actionArray[action];
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
	}

	@Test
	public final void afterAllInRaiseTest() throws Exception {
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLite = new RoyalRhodeIsland10ChipNoLimitLite(3);
		

		
		
		
		
		royalRhodeIsland10ChipNoLimitLite.startGame();
		int[] validChanceCombo = new int[4];
//		add Ad,Kd,Qd,JD
		validChanceCombo[0]=51;
		validChanceCombo[1]=50;
		validChanceCombo[2]=49;
		validChanceCombo[3]=48;
		
		royalRhodeIsland10ChipNoLimitLite.setValidChanceCombinations(validChanceCombo);
		
		royalRhodeIsland10ChipNoLimitLite.performAction(0, new RaiseAction(5));
		royalRhodeIsland10ChipNoLimitLite.performAction(1, new RaiseAction(3));
		
		assertEquals(12,royalRhodeIsland10ChipNoLimitLite.getPossibleActions().size());
	
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getPlayerToAct());

		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(0));
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(2) instanceof RaiseAction);
		RaiseAction raiseActionTaken2 = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(2);
		assertEquals(3,raiseActionTaken2.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,royalRhodeIsland10ChipNoLimitLite.getBettingLimit());
		assertEquals(BetRound.PRETURN,royalRhodeIsland10ChipNoLimitLite.getBettingRound());
//		Board
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getBoard() instanceof int[]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[0]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getBoard().length);
		
		assertEquals(51,royalRhodeIsland10ChipNoLimitLite.getBoard()[0]);
		assertEquals(50,royalRhodeIsland10ChipNoLimitLite.getBoard()[1]);
		

		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof PokerGameLite);
//		Hands
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);

		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);
		assertEquals(49,royalRhodeIsland10ChipNoLimitLite.getHands()[0]);
		assertEquals(48,royalRhodeIsland10ChipNoLimitLite.getHands()[1]);
		
		assertEquals(116280,royalRhodeIsland10ChipNoLimitLite.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,10]D,5,3",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithActionMemory());
		assertEquals("[S,S,10]D53",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithSummaryState());
		
//		TODO handle null pointer thrown here
//		assertEquals("",RoyalRhodeIsland10ChipNoLimitLite.getNodeIdWithGameState());
//		assertEquals(0,RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(1));
		assertEquals(7,royalRhodeIsland10ChipNoLimitLite.getPot()[0]);
		assertEquals(10,royalRhodeIsland10ChipNoLimitLite.getPot()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getRaiseCount());
		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getRaisesAllowedPerBettingRound());
		assertFalse(royalRhodeIsland10ChipNoLimitLite.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.raisesAllowed());
		
//		Actions available
		Action[] actionArray = royalRhodeIsland10ChipNoLimitLite.constructActionArray();
		assertEquals(2,actionArray.length);
		assertEquals(FoldAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(1));
		
		for (int action = 2; action <actionArray.length; action++) {
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
		
//		Call all in 
		royalRhodeIsland10ChipNoLimitLite.performAction(0,CallAction.getInstance());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.isAtTerminalNode());
		
		
//		get payoff
		int[]payoffs = royalRhodeIsland10ChipNoLimitLite.getPayOffs();
		assertEquals(10,payoffs[0]);
		assertEquals(-10,payoffs[1]);
	}
	
	
	@Test
	public final void afterSecondRaiseTest() throws Exception {
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLite = new RoyalRhodeIsland10ChipNoLimitLite(3);
		
		royalRhodeIsland10ChipNoLimitLite.startGame();
		int[] validChanceCombo = new int[4];
//		add Ad,Kd,Qd,JD
		validChanceCombo[0]=51;
		validChanceCombo[1]=50;
		validChanceCombo[2]=49;
		validChanceCombo[3]=48;
		
		royalRhodeIsland10ChipNoLimitLite.setValidChanceCombinations(validChanceCombo);
		
		royalRhodeIsland10ChipNoLimitLite.performAction(0, new RaiseAction(5));
		royalRhodeIsland10ChipNoLimitLite.performAction(1, new RaiseAction(1));
		
		assertEquals(12,royalRhodeIsland10ChipNoLimitLite.getPossibleActions().size());
	
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getPlayerToAct());

		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(0));
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(2) instanceof RaiseAction);
		RaiseAction raiseActionTaken2 = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(2);
		assertEquals(1,raiseActionTaken2.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,royalRhodeIsland10ChipNoLimitLite.getBettingLimit());
		assertEquals(BetRound.PRETURN,royalRhodeIsland10ChipNoLimitLite.getBettingRound());
//		Board
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getBoard() instanceof int[]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[0]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getBoard().length);
		

		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof PokerGameLite);
//		Hands
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);

		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);
		assertEquals(49,royalRhodeIsland10ChipNoLimitLite.getHands()[0]);
		assertEquals(48,royalRhodeIsland10ChipNoLimitLite.getHands()[1]);
		
		assertEquals(116280,royalRhodeIsland10ChipNoLimitLite.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,10]D,5,1",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithActionMemory());
		assertEquals("[S,S,10]D51",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithSummaryState());
		
//		TODO handle null pointer thrown here
//		assertEquals("",RoyalRhodeIsland10ChipNoLimitLite.getNodeIdWithGameState());
//		assertEquals(0,RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(1));
		assertEquals(7,royalRhodeIsland10ChipNoLimitLite.getPot()[0]);
		assertEquals(8,royalRhodeIsland10ChipNoLimitLite.getPot()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getRaiseCount());
		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getRaisesAllowedPerBettingRound());
		assertFalse(royalRhodeIsland10ChipNoLimitLite.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.raisesAllowed());
		
//		Actions available
		Action[] actionArray = royalRhodeIsland10ChipNoLimitLite.constructActionArray();
		assertEquals(4,actionArray.length);
		assertEquals(FoldAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(1));
		
		for (int action = 2; action <actionArray.length; action++) {
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
		
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLiteImport = new RoyalRhodeIsland10ChipNoLimitLite(3);
		royalRhodeIsland10ChipNoLimitLiteImport.importGameProperties(royalRhodeIsland10ChipNoLimitLite);
		
		
		assertEquals(12,royalRhodeIsland10ChipNoLimitLiteImport.getPossibleActions().size());

		assertEquals(0,royalRhodeIsland10ChipNoLimitLiteImport.getPlayerToAct());

		assertEquals(3,royalRhodeIsland10ChipNoLimitLiteImport.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),royalRhodeIsland10ChipNoLimitLiteImport.getActionsTaken().get(0));
		assertTrue(royalRhodeIsland10ChipNoLimitLiteImport.getActionsTaken().get(1) instanceof RaiseAction);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		assertTrue(royalRhodeIsland10ChipNoLimitLiteImport.getActionsTaken().get(2) instanceof RaiseAction);
		assertEquals(1,raiseActionTaken2.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,royalRhodeIsland10ChipNoLimitLiteImport.getBettingLimit());
		assertEquals(BetRound.PRETURN,royalRhodeIsland10ChipNoLimitLiteImport.getBettingRound());
//		Board
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getBoard() instanceof int[]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[0]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getBoard().length);
		

		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof PokerGameLite);
//		Hands
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);

		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);
		assertEquals(49,royalRhodeIsland10ChipNoLimitLite.getHands()[0]);
		assertEquals(48,royalRhodeIsland10ChipNoLimitLite.getHands()[1]);
		
		assertEquals(116280,royalRhodeIsland10ChipNoLimitLite.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,10]D,5,1",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithActionMemory());
		assertEquals("[S,S,10]D51",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithSummaryState());
		
//		TODO handle null pointer thrown here
//		assertEquals("",RoyalRhodeIsland10ChipNoLimitLiteImport.getNodeIdWithGameState());
//		assertEquals(0,RoyalRhodeIsland10ChipNoLimitLiteImport.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLiteImport.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLiteImport.getPayOffs().get(1));
		assertEquals(7,royalRhodeIsland10ChipNoLimitLiteImport.getPot()[0]);
		assertEquals(8,royalRhodeIsland10ChipNoLimitLiteImport.getPot()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLiteImport.getRaiseCount());
		assertEquals(3,royalRhodeIsland10ChipNoLimitLiteImport.getRaisesAllowedPerBettingRound());
		assertFalse(royalRhodeIsland10ChipNoLimitLiteImport.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(royalRhodeIsland10ChipNoLimitLiteImport.raisesAllowed());
		
//		Actions available
		assertEquals(4,actionArray.length);
		assertEquals(FoldAction.getInstance(),royalRhodeIsland10ChipNoLimitLiteImport.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),royalRhodeIsland10ChipNoLimitLiteImport.getPossibleActions().get(1));
		
		for (int action = 2; action <actionArray.length; action++) {
		assertTrue(royalRhodeIsland10ChipNoLimitLiteImport.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) royalRhodeIsland10ChipNoLimitLiteImport.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
		
//		stacks
		
		assertEquals(3,royalRhodeIsland10ChipNoLimitLiteImport.getPlayerStack()[0]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLiteImport.getPlayerStack()[1]);

	}
	
	@Test
	public final void afterThirdRaiseTest() throws Exception {
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLite = new RoyalRhodeIsland10ChipNoLimitLite(3);
		
		royalRhodeIsland10ChipNoLimitLite.startGame();
		int[] validChanceCombo = new int[4];
//		add Ad,Kd,Qd,JD
		validChanceCombo[0]=51;
		validChanceCombo[1]=50;
		validChanceCombo[2]=49;
		validChanceCombo[3]=48;
		
		royalRhodeIsland10ChipNoLimitLite.setValidChanceCombinations(validChanceCombo);
		
		royalRhodeIsland10ChipNoLimitLite.performAction(0, new RaiseAction(3));
		royalRhodeIsland10ChipNoLimitLite.performAction(1, new RaiseAction(2));
		royalRhodeIsland10ChipNoLimitLite.performAction(0, new RaiseAction(1));
		
		assertEquals(12,royalRhodeIsland10ChipNoLimitLite.getPossibleActions().size());
	
		assertEquals(1,royalRhodeIsland10ChipNoLimitLite.getPlayerToAct());

		assertEquals(4,royalRhodeIsland10ChipNoLimitLite.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(0));
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1);
		assertEquals(3,raiseActionTaken.getRaiseAmount());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(2) instanceof RaiseAction);
		RaiseAction raiseActionTaken2 = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(2);
		assertEquals(2,raiseActionTaken2.getRaiseAmount());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(3) instanceof RaiseAction);
		RaiseAction raiseActionTaken3 = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(3);
		assertEquals(1,raiseActionTaken3.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,royalRhodeIsland10ChipNoLimitLite.getBettingLimit());
		assertEquals(BetRound.PRETURN,royalRhodeIsland10ChipNoLimitLite.getBettingRound());
//		Board
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getBoard() instanceof int[]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[0]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getBoard().length);
		

		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof PokerGameLite);
//		Hands
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);

		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);
		assertEquals(49,royalRhodeIsland10ChipNoLimitLite.getHands()[0]);
		assertEquals(48,royalRhodeIsland10ChipNoLimitLite.getHands()[1]);
		
		assertEquals(116280,royalRhodeIsland10ChipNoLimitLite.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,9]D,3,2,1",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithActionMemory());
		assertEquals("[S,S,9]D321",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithSummaryState());
		
//		TODO handle null pointer thrown here
//		assertEquals("",RoyalRhodeIsland10ChipNoLimitLite.getNodeIdWithGameState());
//		assertEquals(0,RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(1));
		assertEquals(8,royalRhodeIsland10ChipNoLimitLite.getPot()[0]);
		assertEquals(7,royalRhodeIsland10ChipNoLimitLite.getPot()[1]);
		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getRaiseCount());
		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getRaisesAllowedPerBettingRound());
		assertFalse(royalRhodeIsland10ChipNoLimitLite.lastActionIsTerminalCallForTheBettingRound());
		assertFalse(royalRhodeIsland10ChipNoLimitLite.raisesAllowed());
		
//		Actions available
		Action[] actionArray = royalRhodeIsland10ChipNoLimitLite.constructActionArray();
		assertEquals(2,actionArray.length);
		assertEquals(FoldAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(1));

	}
	
	
	@Test
	public final void afterSecondDealRoundTest() throws Exception {
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLite = new RoyalRhodeIsland10ChipNoLimitLite(3);
		
		royalRhodeIsland10ChipNoLimitLite.startGame();
		int[] validChanceCombo = new int[4];
//		add Ad,Kd,Qd,JD
		validChanceCombo[0]=51;
		validChanceCombo[1]=50;
		validChanceCombo[2]=49;
		validChanceCombo[3]=48;
		
		royalRhodeIsland10ChipNoLimitLite.setValidChanceCombinations(validChanceCombo);
		
		royalRhodeIsland10ChipNoLimitLite.performAction(0, new RaiseAction(5));
		royalRhodeIsland10ChipNoLimitLite.performAction(1, new RaiseAction(1));
		royalRhodeIsland10ChipNoLimitLite.performAction(0, CallAction.getInstance());
		royalRhodeIsland10ChipNoLimitLite.performAction(0, DealAction.getInstance());
		
		assertEquals(12,royalRhodeIsland10ChipNoLimitLite.getPossibleActions().size());
	
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getPlayerToAct());

		assertEquals(5,royalRhodeIsland10ChipNoLimitLite.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(0));
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(1);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(2) instanceof RaiseAction);
		RaiseAction raiseActionTaken2 = (RaiseAction)royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(2);
		assertEquals(1,raiseActionTaken2.getRaiseAmount());
		assertEquals(CallAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(3));
		assertEquals(DealAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getActionsTaken().get(4));
		
		assertEquals(BettingLimit.NO_LIMIT,royalRhodeIsland10ChipNoLimitLite.getBettingLimit());
		assertEquals(BetRound.TURN,royalRhodeIsland10ChipNoLimitLite.getBettingRound());
//		Board
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getBoard() instanceof int[]);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[0]);
		assertFalse(royalRhodeIsland10ChipNoLimitLite.getVisibleBoardCards()[1]);
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getBoard().length);
		

		assertTrue(royalRhodeIsland10ChipNoLimitLite instanceof PokerGameLite);
//		Hands
		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);

		assertEquals(2,royalRhodeIsland10ChipNoLimitLite.getHands().length);
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getHands() instanceof int[]);
		assertEquals(49,royalRhodeIsland10ChipNoLimitLite.getHands()[0]);
		assertEquals(48,royalRhodeIsland10ChipNoLimitLite.getHands()[1]);
		
		assertEquals(116280,royalRhodeIsland10ChipNoLimitLite.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,10,12]D,5,1,C,D",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithActionMemory());
		assertEquals("[S,S,10,12]D[5, 1]D",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithSummaryState());
//		TODO handle null pointer thrown here
//		assertEquals("",RoyalRhodeIsland10ChipNoLimitLite.getNodeIdWithGameState());
//		assertEquals(0,RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),RoyalRhodeIsland10ChipNoLimitLite.getPayOffs().get(1));
		assertEquals(8,royalRhodeIsland10ChipNoLimitLite.getPot()[0]);
		assertEquals(8,royalRhodeIsland10ChipNoLimitLite.getPot()[1]);
		assertEquals(0,royalRhodeIsland10ChipNoLimitLite.getRaiseCount());
		assertEquals(3,royalRhodeIsland10ChipNoLimitLite.getRaisesAllowedPerBettingRound());
		assertFalse(royalRhodeIsland10ChipNoLimitLite.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(royalRhodeIsland10ChipNoLimitLite.raisesAllowed());
		
//		Actions available
		Action[] actionArray = royalRhodeIsland10ChipNoLimitLite.constructActionArray();
		assertEquals(4,actionArray.length);
		assertEquals(FoldAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(1));
		
		for (int action = 2; action <actionArray.length; action++) {
		assertTrue(royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) royalRhodeIsland10ChipNoLimitLite.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
		
	}
	
	@Ignore
	@Test
	public final void summaryStateTests() throws Exception{
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLite = new RoyalRhodeIsland10ChipNoLimitLite(3);
		
		royalRhodeIsland10ChipNoLimitLite.startGame();
		int[] validChanceCombo = new int[4];
//		add Ad,Kd,Qd,JD
		validChanceCombo[0]=51;
		validChanceCombo[1]=50;
		validChanceCombo[2]=49;
		validChanceCombo[3]=48;
		
		royalRhodeIsland10ChipNoLimitLite.setValidChanceCombinations(validChanceCombo);
		
		royalRhodeIsland10ChipNoLimitLite.performAction(0, new RaiseAction(5));
		royalRhodeIsland10ChipNoLimitLite.performAction(1, new RaiseAction(1));
		royalRhodeIsland10ChipNoLimitLite.performAction(0, CallAction.getInstance());
		royalRhodeIsland10ChipNoLimitLite.performAction(0, DealAction.getInstance());
		royalRhodeIsland10ChipNoLimitLite.performAction(0, new RaiseAction(2));
		
		assertEquals("[S,S,9,12]D,5,1,C,D,2",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithActionMemory());
		assertEquals("[S,S,9,12]D[5, 1]D2",royalRhodeIsland10ChipNoLimitLite.getNodeIdWithSummaryState());
		
		RoyalRhodeIsland10ChipNoLimitLite royalRhodeIsland10ChipNoLimitLite2 = new RoyalRhodeIsland10ChipNoLimitLite(3);
		
		royalRhodeIsland10ChipNoLimitLite2.startGame();
		
		royalRhodeIsland10ChipNoLimitLite2.setValidChanceCombinations(validChanceCombo);
		
		royalRhodeIsland10ChipNoLimitLite2.performAction(0, new RaiseAction(3));
		royalRhodeIsland10ChipNoLimitLite2.performAction(1, new RaiseAction(1));
		royalRhodeIsland10ChipNoLimitLite2.performAction(1, new RaiseAction(1));
		royalRhodeIsland10ChipNoLimitLite2.performAction(0, CallAction.getInstance());
		royalRhodeIsland10ChipNoLimitLite2.performAction(0, DealAction.getInstance());
		royalRhodeIsland10ChipNoLimitLite2.performAction(0, new RaiseAction(2));
		royalRhodeIsland10ChipNoLimitLite2.performAction(0, new RaiseAction(1));
		
		assertEquals("[S,S,10,12]D,3,1,1,C,D,2,1",royalRhodeIsland10ChipNoLimitLite2.getNodeIdWithActionMemory());
		assertEquals("[S,S,10,12]D[4, 1]D21",royalRhodeIsland10ChipNoLimitLite2.getNodeIdWithSummaryState());
		
	}
	
}
