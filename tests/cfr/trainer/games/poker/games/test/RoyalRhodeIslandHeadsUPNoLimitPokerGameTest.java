package cfr.trainer.games.poker.games.test;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

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
import cfr.trainer.games.poker.games.TwoPlayerRoyalRhodeIslandGame;
import cfr.trainer.node.InfoSetFactory;
import cfr.trainer.node.Node;

public class RoyalRhodeIslandHeadsUPNoLimitPokerGameTest {

	@Test
	public final void constructorTest() {
		TwoPlayerRoyalRhodeIslandGame twoPlayerRoyalRhodeIslandGame = new TwoPlayerRoyalRhodeIslandGame(BettingLimit.NO_LIMIT, 3);
		Integer[] expectedPlayers = new Integer[] {0,1};

		
		assertEquals(22,twoPlayerRoyalRhodeIslandGame.getPossibleActions().size());
		assertEquals(expectedPlayers,twoPlayerRoyalRhodeIslandGame.getPlayers());
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPlayerToAct());

		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getActionsTaken().size());
		assertEquals(BettingLimit.NO_LIMIT,twoPlayerRoyalRhodeIslandGame.getBettingLimit());
		assertEquals(BetRound.PRETURN,twoPlayerRoyalRhodeIslandGame.getBettingRound());
		assertNull(twoPlayerRoyalRhodeIslandGame.getBoard());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(twoPlayerRoyalRhodeIslandGame instanceof PokerGame);
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getHands().size());
		assertNull(twoPlayerRoyalRhodeIslandGame.getHands().get(0));
		assertNull(twoPlayerRoyalRhodeIslandGame.getHands().get(1));
		assertEquals(PokerGameType.RHODE_ISLAND,twoPlayerRoyalRhodeIslandGame.getPokerGameType());
		assertEquals(116280,twoPlayerRoyalRhodeIslandGame.getListOfValidChanceCombinations().length);
		assertEquals("",twoPlayerRoyalRhodeIslandGame.getNodeIdWithActionMemory());
		
//		TODO handle null pointer thrown here
//		assertEquals("",twoPlayerRoyalRhodeIslandGame.getNodeIdWithGameState());
//		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(1));
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(0));
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(1));
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getRaiseCount());
		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertFalse(twoPlayerRoyalRhodeIslandGame.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(twoPlayerRoyalRhodeIslandGame.raisesAllowed());
	}
	
	@Test
	public final void afterDealTest() {
		TwoPlayerRoyalRhodeIslandGame twoPlayerRoyalRhodeIslandGame = new TwoPlayerRoyalRhodeIslandGame(BettingLimit.NO_LIMIT, 3);
		Integer[] expectedPlayers = new Integer[] {0,1};

		Deck royalDeckUnShuffled = new RoyalDeckShuffled().unShuffleDeck();
		
		twoPlayerRoyalRhodeIslandGame.setDeck(royalDeckUnShuffled);
		assertEquals(royalDeckUnShuffled,twoPlayerRoyalRhodeIslandGame.getDeck());
		twoPlayerRoyalRhodeIslandGame.startGame();
		
		assertEquals(22,twoPlayerRoyalRhodeIslandGame.getPossibleActions().size());
		assertEquals(expectedPlayers,twoPlayerRoyalRhodeIslandGame.getPlayers());
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPlayerToAct());

		assertEquals(1,twoPlayerRoyalRhodeIslandGame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(0));
		assertEquals(BettingLimit.NO_LIMIT,twoPlayerRoyalRhodeIslandGame.getBettingLimit());
		assertEquals(BetRound.PRETURN,twoPlayerRoyalRhodeIslandGame.getBettingRound());
//		Board
		assertTrue(twoPlayerRoyalRhodeIslandGame.getBoard() instanceof Board);
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getBoard().getTurnedCards().size());
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getBoard().getCards().length);
		
		
		assertTrue(twoPlayerRoyalRhodeIslandGame.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(twoPlayerRoyalRhodeIslandGame instanceof PokerGame);
//		Hands
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getHands().size());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(0) instanceof HandSingleCard);
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(1) instanceof HandSingleCard);
		assertEquals(CardHeight.ACE,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardHeight.KING,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getSuit());
		
		assertEquals(PokerGameType.RHODE_ISLAND,twoPlayerRoyalRhodeIslandGame.getPokerGameType());
		assertEquals(116280,twoPlayerRoyalRhodeIslandGame.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,A]D",twoPlayerRoyalRhodeIslandGame.getNodeIdWithActionMemory());
		
//		TODO handle null pointer thrown here
//		assertEquals("",twoPlayerRoyalRhodeIslandGame.getNodeIdWithGameState());
//		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(1));
		assertEquals(1,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(0));
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(1));
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getRaiseCount());
		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertFalse(twoPlayerRoyalRhodeIslandGame.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(twoPlayerRoyalRhodeIslandGame.raisesAllowed());
		
//		Actions available
		Node node = InfoSetFactory.buildInformationSet("nodeId", twoPlayerRoyalRhodeIslandGame);
		assertEquals(20,node.getStrategy().length);
		assertEquals(FoldAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(1));
		
		for (int action = 2; action <node.getStrategy().length; action++) {
		assertTrue(twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
	}
	
	@Test
	public final void afterRaiseTest() {
		TwoPlayerRoyalRhodeIslandGame twoPlayerRoyalRhodeIslandGame = new TwoPlayerRoyalRhodeIslandGame(BettingLimit.NO_LIMIT, 3);
		Integer[] expectedPlayers = new Integer[] {0,1};

		Deck royalDeckUnShuffled = new RoyalDeckShuffled().unShuffleDeck();
		
		twoPlayerRoyalRhodeIslandGame.setDeck(royalDeckUnShuffled);
		assertEquals(royalDeckUnShuffled,twoPlayerRoyalRhodeIslandGame.getDeck());
		twoPlayerRoyalRhodeIslandGame.startGame();
		twoPlayerRoyalRhodeIslandGame.performAction(0, new RaiseAction(5));
		
		assertEquals(22,twoPlayerRoyalRhodeIslandGame.getPossibleActions().size());
		assertEquals(expectedPlayers,twoPlayerRoyalRhodeIslandGame.getPlayers());
		assertEquals(1,twoPlayerRoyalRhodeIslandGame.getPlayerToAct());

		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(0));
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,twoPlayerRoyalRhodeIslandGame.getBettingLimit());
		assertEquals(BetRound.PRETURN,twoPlayerRoyalRhodeIslandGame.getBettingRound());
//		Board
		assertTrue(twoPlayerRoyalRhodeIslandGame.getBoard() instanceof Board);
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getBoard().getTurnedCards().size());
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getBoard().getCards().length);
		
		
		assertTrue(twoPlayerRoyalRhodeIslandGame.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(twoPlayerRoyalRhodeIslandGame instanceof PokerGame);
//		Hands
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getHands().size());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(0) instanceof HandSingleCard);
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(1) instanceof HandSingleCard);
		assertEquals(CardHeight.ACE,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardHeight.KING,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getSuit());
		
		assertEquals(PokerGameType.RHODE_ISLAND,twoPlayerRoyalRhodeIslandGame.getPokerGameType());
		assertEquals(116280,twoPlayerRoyalRhodeIslandGame.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,K]D,5",twoPlayerRoyalRhodeIslandGame.getNodeIdWithActionMemory());
		
//		TODO handle null pointer thrown here
//		assertEquals("",twoPlayerRoyalRhodeIslandGame.getNodeIdWithGameState());
//		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(1));
		assertEquals(7,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(0));
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(1));
		assertEquals(1,twoPlayerRoyalRhodeIslandGame.getRaiseCount());
		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertFalse(twoPlayerRoyalRhodeIslandGame.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(twoPlayerRoyalRhodeIslandGame.raisesAllowed());
		
//		Actions available
		Node node = InfoSetFactory.buildInformationSet("nodeId2", twoPlayerRoyalRhodeIslandGame);
		assertEquals(15,node.getStrategy().length);
		assertEquals(FoldAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(1));
		
		for (int action = 2; action <node.getStrategy().length; action++) {
		assertTrue(twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
	}

	@Test
	public final void afterAllInRaiseTest() {
		TwoPlayerRoyalRhodeIslandGame twoPlayerRoyalRhodeIslandGame = new TwoPlayerRoyalRhodeIslandGame(BettingLimit.NO_LIMIT, 3);
		Integer[] expectedPlayers = new Integer[] {0,1};

		Deck royalDeckUnShuffled = new RoyalDeckShuffled().unShuffleDeck();
		
		twoPlayerRoyalRhodeIslandGame.setDeck(royalDeckUnShuffled);
		assertEquals(royalDeckUnShuffled,twoPlayerRoyalRhodeIslandGame.getDeck());
		twoPlayerRoyalRhodeIslandGame.startGame();
		twoPlayerRoyalRhodeIslandGame.performAction(0, new RaiseAction(5));
		twoPlayerRoyalRhodeIslandGame.performAction(1, new RaiseAction(13));
		
		assertEquals(22,twoPlayerRoyalRhodeIslandGame.getPossibleActions().size());
		assertEquals(expectedPlayers,twoPlayerRoyalRhodeIslandGame.getPlayers());
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPlayerToAct());

		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(0));
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(2) instanceof RaiseAction);
		RaiseAction raiseActionTaken2 = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(2);
		assertEquals(13,raiseActionTaken2.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,twoPlayerRoyalRhodeIslandGame.getBettingLimit());
		assertEquals(BetRound.PRETURN,twoPlayerRoyalRhodeIslandGame.getBettingRound());
//		Board
		assertTrue(twoPlayerRoyalRhodeIslandGame.getBoard() instanceof Board);
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getBoard().getTurnedCards().size());
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getBoard().getCards().length);
		
		
		assertTrue(twoPlayerRoyalRhodeIslandGame.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(twoPlayerRoyalRhodeIslandGame instanceof PokerGame);
//		Hands
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getHands().size());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(0) instanceof HandSingleCard);
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(1) instanceof HandSingleCard);
		assertEquals(CardHeight.ACE,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardHeight.KING,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getSuit());
		
		assertEquals(PokerGameType.RHODE_ISLAND,twoPlayerRoyalRhodeIslandGame.getPokerGameType());
		assertEquals(116280,twoPlayerRoyalRhodeIslandGame.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,A]D,5,13",twoPlayerRoyalRhodeIslandGame.getNodeIdWithActionMemory());
		
//		TODO handle null pointer thrown here
//		assertEquals("",twoPlayerRoyalRhodeIslandGame.getNodeIdWithGameState());
//		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(1));
		assertEquals(7,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(0));
		assertEquals(20,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(1));
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getRaiseCount());
		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertFalse(twoPlayerRoyalRhodeIslandGame.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(twoPlayerRoyalRhodeIslandGame.raisesAllowed());
		
//		Actions available
		Node node = InfoSetFactory.buildInformationSet("nodeId3", twoPlayerRoyalRhodeIslandGame);
		assertEquals(2,node.getStrategy().length);
		assertEquals(FoldAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(1));
		
		for (int action = 2; action <node.getStrategy().length; action++) {
		assertTrue(twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
		
//		Call all in 
		twoPlayerRoyalRhodeIslandGame.performAction(0,CallAction.getInstance());
		assertTrue(twoPlayerRoyalRhodeIslandGame.isAtTerminalNode());
		
//		get payoff
//		get payoff
		int[]payoffs = twoPlayerRoyalRhodeIslandGame.getPayOffs();
		assertEquals(-20,payoffs[0]);
		assertEquals(20,payoffs[1]);
	}
	
	
	@Test
	public final void afterSecondRaiseTest() {
		TwoPlayerRoyalRhodeIslandGame twoPlayerRoyalRhodeIslandGame = new TwoPlayerRoyalRhodeIslandGame(BettingLimit.NO_LIMIT, 3);
		Integer[] expectedPlayers = new Integer[] {0,1};

		Deck royalDeckUnShuffled = new RoyalDeckShuffled().unShuffleDeck();
		
		twoPlayerRoyalRhodeIslandGame.setDeck(royalDeckUnShuffled);
		assertEquals(royalDeckUnShuffled,twoPlayerRoyalRhodeIslandGame.getDeck());
		twoPlayerRoyalRhodeIslandGame.startGame();
		twoPlayerRoyalRhodeIslandGame.performAction(0, new RaiseAction(5));
		twoPlayerRoyalRhodeIslandGame.performAction(1, new RaiseAction(3));
		
		assertEquals(22,twoPlayerRoyalRhodeIslandGame.getPossibleActions().size());
		assertEquals(expectedPlayers,twoPlayerRoyalRhodeIslandGame.getPlayers());
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPlayerToAct());

		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(0));
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(2) instanceof RaiseAction);
		RaiseAction raiseActionTaken2 = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(2);
		assertEquals(3,raiseActionTaken2.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,twoPlayerRoyalRhodeIslandGame.getBettingLimit());
		assertEquals(BetRound.PRETURN,twoPlayerRoyalRhodeIslandGame.getBettingRound());
//		Board
		assertTrue(twoPlayerRoyalRhodeIslandGame.getBoard() instanceof Board);
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getBoard().getTurnedCards().size());
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getBoard().getCards().length);
		
		
		assertTrue(twoPlayerRoyalRhodeIslandGame.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(twoPlayerRoyalRhodeIslandGame instanceof PokerGame);
//		Hands
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getHands().size());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(0) instanceof HandSingleCard);
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(1) instanceof HandSingleCard);
		assertEquals(CardHeight.ACE,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardHeight.KING,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getSuit());
		
		assertEquals(PokerGameType.RHODE_ISLAND,twoPlayerRoyalRhodeIslandGame.getPokerGameType());
		assertEquals(116280,twoPlayerRoyalRhodeIslandGame.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,A]D,5,3",twoPlayerRoyalRhodeIslandGame.getNodeIdWithActionMemory());
		
//		TODO handle null pointer thrown here
//		assertEquals("",twoPlayerRoyalRhodeIslandGame.getNodeIdWithGameState());
//		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(1));
		assertEquals(7,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(0));
		assertEquals(10,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(1));
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getRaiseCount());
		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertFalse(twoPlayerRoyalRhodeIslandGame.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(twoPlayerRoyalRhodeIslandGame.raisesAllowed());
		
//		Actions available
		Node node = InfoSetFactory.buildInformationSet("nodeId3", twoPlayerRoyalRhodeIslandGame);
		assertEquals(12,node.getStrategy().length);
		assertEquals(FoldAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(1));
		
		for (int action = 2; action <node.getStrategy().length; action++) {
		assertTrue(twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
		
		TwoPlayerRoyalRhodeIslandGame twoPlayerRoyalRhodeIslandGameImport = new TwoPlayerRoyalRhodeIslandGame(BettingLimit.NO_LIMIT, 3);
		twoPlayerRoyalRhodeIslandGameImport.importGameProperties(twoPlayerRoyalRhodeIslandGame);
		
		
		assertEquals(22,twoPlayerRoyalRhodeIslandGameImport.getPossibleActions().size());
		assertEquals(expectedPlayers,twoPlayerRoyalRhodeIslandGameImport.getPlayers());
		assertEquals(0,twoPlayerRoyalRhodeIslandGameImport.getPlayerToAct());

		assertEquals(3,twoPlayerRoyalRhodeIslandGameImport.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),twoPlayerRoyalRhodeIslandGameImport.getActionsTaken().get(0));
		assertTrue(twoPlayerRoyalRhodeIslandGameImport.getActionsTaken().get(1) instanceof RaiseAction);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		assertTrue(twoPlayerRoyalRhodeIslandGameImport.getActionsTaken().get(2) instanceof RaiseAction);

		assertEquals(3,raiseActionTaken2.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,twoPlayerRoyalRhodeIslandGameImport.getBettingLimit());
		assertEquals(BetRound.PRETURN,twoPlayerRoyalRhodeIslandGameImport.getBettingRound());
//		Board
		assertTrue(twoPlayerRoyalRhodeIslandGameImport.getBoard() instanceof Board);
		assertEquals(0,twoPlayerRoyalRhodeIslandGameImport.getBoard().getTurnedCards().size());
		assertEquals(2,twoPlayerRoyalRhodeIslandGameImport.getBoard().getCards().length);
		
		
		assertTrue(twoPlayerRoyalRhodeIslandGameImport.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(twoPlayerRoyalRhodeIslandGame instanceof PokerGame);
//		Hands
		assertEquals(2,twoPlayerRoyalRhodeIslandGameImport.getHands().size());
		assertTrue(twoPlayerRoyalRhodeIslandGameImport.getHands().get(0) instanceof HandSingleCard);
		assertTrue(twoPlayerRoyalRhodeIslandGameImport.getHands().get(1) instanceof HandSingleCard);
		assertEquals(CardHeight.ACE,twoPlayerRoyalRhodeIslandGameImport.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGameImport.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardHeight.KING,twoPlayerRoyalRhodeIslandGameImport.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGameImport.getHands().get(1).getCard(0).getSuit());
		
		assertEquals(PokerGameType.RHODE_ISLAND,twoPlayerRoyalRhodeIslandGameImport.getPokerGameType());
		assertEquals(116280,twoPlayerRoyalRhodeIslandGameImport.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,A]D,5,3",twoPlayerRoyalRhodeIslandGameImport.getNodeIdWithActionMemory());
		
//		TODO handle null pointer thrown here
//		assertEquals("",twoPlayerRoyalRhodeIslandGameImport.getNodeIdWithGameState());
//		assertEquals(0,twoPlayerRoyalRhodeIslandGameImport.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGameImport.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGameImport.getPayOffs().get(1));
		assertEquals(7,twoPlayerRoyalRhodeIslandGameImport.getPot().getPlayersContributionToPot(0));
		assertEquals(10,twoPlayerRoyalRhodeIslandGameImport.getPot().getPlayersContributionToPot(1));
		assertEquals(2,twoPlayerRoyalRhodeIslandGameImport.getRaiseCount());
		assertEquals(3,twoPlayerRoyalRhodeIslandGameImport.getRaisesAllowedPerBettingRound());
		assertFalse(twoPlayerRoyalRhodeIslandGameImport.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(twoPlayerRoyalRhodeIslandGameImport.raisesAllowed());
		
//		Actions available
		assertEquals(12,node.getStrategy().length);
		assertEquals(FoldAction.getInstance(),twoPlayerRoyalRhodeIslandGameImport.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),twoPlayerRoyalRhodeIslandGameImport.getPossibleActions().get(1));
		
		for (int action = 2; action <node.getStrategy().length; action++) {
		assertTrue(twoPlayerRoyalRhodeIslandGameImport.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) twoPlayerRoyalRhodeIslandGameImport.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
		
//		stacks
		
		assertEquals(13,twoPlayerRoyalRhodeIslandGameImport.getPlayer(0).getStack());
		assertEquals(10,twoPlayerRoyalRhodeIslandGameImport.getPlayer(1).getStack());

	}
	
	@Test
	public final void afterThirdRaiseTest() {
		TwoPlayerRoyalRhodeIslandGame twoPlayerRoyalRhodeIslandGame = new TwoPlayerRoyalRhodeIslandGame(BettingLimit.NO_LIMIT, 3);
		Integer[] expectedPlayers = new Integer[] {0,1};

		Deck royalDeckUnShuffled = new RoyalDeckShuffled().unShuffleDeck();
		
		twoPlayerRoyalRhodeIslandGame.setDeck(royalDeckUnShuffled);
		assertEquals(royalDeckUnShuffled,twoPlayerRoyalRhodeIslandGame.getDeck());
		twoPlayerRoyalRhodeIslandGame.startGame();
		twoPlayerRoyalRhodeIslandGame.performAction(0, new RaiseAction(5));
		twoPlayerRoyalRhodeIslandGame.performAction(1, new RaiseAction(3));
		twoPlayerRoyalRhodeIslandGame.performAction(0, new RaiseAction(2));
		
		assertEquals(22,twoPlayerRoyalRhodeIslandGame.getPossibleActions().size());
		assertEquals(expectedPlayers,twoPlayerRoyalRhodeIslandGame.getPlayers());
		assertEquals(1,twoPlayerRoyalRhodeIslandGame.getPlayerToAct());

		assertEquals(4,twoPlayerRoyalRhodeIslandGame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(0));
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(2) instanceof RaiseAction);
		RaiseAction raiseActionTaken2 = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(2);
		assertEquals(3,raiseActionTaken2.getRaiseAmount());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(3) instanceof RaiseAction);
		RaiseAction raiseActionTaken3 = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(3);
		assertEquals(2,raiseActionTaken3.getRaiseAmount());
		
		
		assertEquals(BettingLimit.NO_LIMIT,twoPlayerRoyalRhodeIslandGame.getBettingLimit());
		assertEquals(BetRound.PRETURN,twoPlayerRoyalRhodeIslandGame.getBettingRound());
//		Board
		assertTrue(twoPlayerRoyalRhodeIslandGame.getBoard() instanceof Board);
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getBoard().getTurnedCards().size());
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getBoard().getCards().length);
		
		
		assertTrue(twoPlayerRoyalRhodeIslandGame.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(twoPlayerRoyalRhodeIslandGame instanceof PokerGame);
//		Hands
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getHands().size());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(0) instanceof HandSingleCard);
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(1) instanceof HandSingleCard);
		assertEquals(CardHeight.ACE,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardHeight.KING,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getSuit());
		
		assertEquals(PokerGameType.RHODE_ISLAND,twoPlayerRoyalRhodeIslandGame.getPokerGameType());
		assertEquals(116280,twoPlayerRoyalRhodeIslandGame.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,K]D,5,3,2",twoPlayerRoyalRhodeIslandGame.getNodeIdWithActionMemory());
		
//		TODO handle null pointer thrown here
//		assertEquals("",twoPlayerRoyalRhodeIslandGame.getNodeIdWithGameState());
//		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(1));
		assertEquals(12,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(0));
		assertEquals(10,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(1));
		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getRaiseCount());
		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertFalse(twoPlayerRoyalRhodeIslandGame.lastActionIsTerminalCallForTheBettingRound());
		assertFalse(twoPlayerRoyalRhodeIslandGame.raisesAllowed());
		
//		Actions available
		Node node = InfoSetFactory.buildInformationSet("nodeId3", twoPlayerRoyalRhodeIslandGame);
		assertEquals(2,node.getStrategy().length);
		assertEquals(FoldAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(1));

		
	}
	
	
	@Test
	public final void afterSecondDealRoundTest() {
		TwoPlayerRoyalRhodeIslandGame twoPlayerRoyalRhodeIslandGame = new TwoPlayerRoyalRhodeIslandGame(BettingLimit.NO_LIMIT, 3);
		Integer[] expectedPlayers = new Integer[] {0,1};

		Deck royalDeckUnShuffled = new RoyalDeckShuffled().unShuffleDeck();
		
		twoPlayerRoyalRhodeIslandGame.setDeck(royalDeckUnShuffled);
		assertEquals(royalDeckUnShuffled,twoPlayerRoyalRhodeIslandGame.getDeck());
		twoPlayerRoyalRhodeIslandGame.startGame();
		twoPlayerRoyalRhodeIslandGame.performAction(0, new RaiseAction(5));
		twoPlayerRoyalRhodeIslandGame.performAction(1, new RaiseAction(3));
		twoPlayerRoyalRhodeIslandGame.performAction(0, CallAction.getInstance());
		twoPlayerRoyalRhodeIslandGame.performAction(0, DealAction.getInstance());
		
		assertEquals(22,twoPlayerRoyalRhodeIslandGame.getPossibleActions().size());
		assertEquals(expectedPlayers,twoPlayerRoyalRhodeIslandGame.getPlayers());
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPlayerToAct());

		assertEquals(5,twoPlayerRoyalRhodeIslandGame.getActionsTaken().size());
		assertEquals(DealAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(0));
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1) instanceof RaiseAction);
		RaiseAction raiseActionTaken = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(1);
		assertEquals(5,raiseActionTaken.getRaiseAmount());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(2) instanceof RaiseAction);
		RaiseAction raiseActionTaken2 = (RaiseAction)twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(2);
		assertEquals(3,raiseActionTaken2.getRaiseAmount());
		assertEquals(CallAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(3));
		assertEquals(DealAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getActionsTaken().get(4));
		
		assertEquals(BettingLimit.NO_LIMIT,twoPlayerRoyalRhodeIslandGame.getBettingLimit());
		assertEquals(BetRound.TURN,twoPlayerRoyalRhodeIslandGame.getBettingRound());
//		Board
		assertTrue(twoPlayerRoyalRhodeIslandGame.getBoard() instanceof Board);
		assertEquals(1,twoPlayerRoyalRhodeIslandGame.getBoard().getTurnedCards().size());
		assertEquals(CardHeight.QUEEN,twoPlayerRoyalRhodeIslandGame.getBoard().getTurnedCards().get(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getBoard().getTurnedCards().get(0).getSuit());
		
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getBoard().getCards().length);
		
		assertTrue(twoPlayerRoyalRhodeIslandGame.getDeck() instanceof RoyalDeckShuffled);
		assertTrue(twoPlayerRoyalRhodeIslandGame instanceof PokerGame);
//		Hands
		assertEquals(2,twoPlayerRoyalRhodeIslandGame.getHands().size());
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(0) instanceof HandSingleCard);
		assertTrue(twoPlayerRoyalRhodeIslandGame.getHands().get(1) instanceof HandSingleCard);
		assertEquals(CardHeight.ACE,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(0).getCard(0).getSuit());
		assertEquals(CardHeight.KING,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getHeight());
		assertEquals(CardSuit.DIAMONDS,twoPlayerRoyalRhodeIslandGame.getHands().get(1).getCard(0).getSuit());
		
		assertEquals(PokerGameType.RHODE_ISLAND,twoPlayerRoyalRhodeIslandGame.getPokerGameType());
		assertEquals(116280,twoPlayerRoyalRhodeIslandGame.getListOfValidChanceCombinations().length);
		
		assertEquals("[S,S,A,Q]D,5,3,C,D",twoPlayerRoyalRhodeIslandGame.getNodeIdWithActionMemory());
		
//		TODO handle null pointer thrown here
//		assertEquals("",twoPlayerRoyalRhodeIslandGame.getNodeIdWithGameState());
//		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getPayOffs().size());
////	TODO handle null pointer thrown here
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(0));
//		assertEquals(Integer.valueOf(0),twoPlayerRoyalRhodeIslandGame.getPayOffs().get(1));
		assertEquals(10,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(0));
		assertEquals(10,twoPlayerRoyalRhodeIslandGame.getPot().getPlayersContributionToPot(1));
		assertEquals(0,twoPlayerRoyalRhodeIslandGame.getRaiseCount());
		assertEquals(3,twoPlayerRoyalRhodeIslandGame.getRaisesAllowedPerBettingRound());
		assertFalse(twoPlayerRoyalRhodeIslandGame.lastActionIsTerminalCallForTheBettingRound());
		assertTrue(twoPlayerRoyalRhodeIslandGame.raisesAllowed());
		
//		Actions available
		Node node = InfoSetFactory.buildInformationSet("nodeId3", twoPlayerRoyalRhodeIslandGame);
		assertEquals(12,node.getStrategy().length);
		assertEquals(FoldAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(0));
		assertEquals(CallAction.getInstance(),twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(1));
		
		for (int action = 2; action <node.getStrategy().length; action++) {
		assertTrue(twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action) instanceof RaiseAction);
		RaiseAction raiseAction = (RaiseAction) twoPlayerRoyalRhodeIslandGame.getPossibleActions().get(action);
		assertEquals(action-1,raiseAction.getRaiseAmount());
		}
		
	}
	
}
