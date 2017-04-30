package cfr.trainer.games.poker.nodes.test;

import org.junit.Test;
import org.mockito.Mockito;

import cfr.trainer.games.GameType;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.Pot;
import cfr.trainer.games.poker.games.PokerGame;
import cfr.trainer.games.poker.nodes.LimitPokerInfoSet;
import cfr.trainer.games.poker.nodes.PokerInfoSetFactory;
import cfr.trainer.games.poker.nodes.PotLimitPokerInfoSet;
import cfr.trainer.games.poker.nodes.TerminalInfoSet;
import cfr.trainer.node.Node;

import static org.junit.Assert.*;

public class PokerInfoSetFactoryTest {

	PokerGame mockedPokerGame = Mockito.mock(PokerGame.class);
	Pot mockedPot = Mockito.mock(Pot.class);
	
	@Test
	public final void nodeFactoryReturnsTerminalNode_test() {
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(false);
		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);
		
		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID",mockedPokerGame);
		
		assertTrue(infoSet instanceof TerminalInfoSet);

	}
	
	@Test
	public final void nodeFactoryReturnsLimitPokerInfoSet_test() {
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(true);
		Mockito.when(mockedPokerGame.getBettingLimit()).thenReturn(BettingLimit.LIMIT);
		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);
		
		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID",mockedPokerGame);
		
		assertTrue(infoSet instanceof LimitPokerInfoSet);

	}
	
	@Test
	public final void nodeFactoryReturnsPotLimitPokerInfoSet_test() {
		Mockito.when(mockedPokerGame.getGameType()).thenReturn(GameType.POKER);
		Mockito.when(mockedPokerGame.raisesAllowed()).thenReturn(true);
		Mockito.when(mockedPokerGame.getBettingLimit()).thenReturn(BettingLimit.POT_LIMIT);
		Mockito.when(mockedPokerGame.getPot()).thenReturn(mockedPot);
		Mockito.when(mockedPot.getLastBetValue()).thenReturn(2);
		Mockito.when(mockedPot.getTotalPotSize()).thenReturn(5);
		
		
		Node infoSet = PokerInfoSetFactory.buildInformationSet("nodeID",mockedPokerGame);
		
		assertTrue(infoSet instanceof PotLimitPokerInfoSet);
		assertEquals(10,infoSet.getActions().length);

	}
}
