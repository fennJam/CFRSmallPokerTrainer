package cfr.trainer.tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import cfr.trainer.CFRPlusTrainer;
//import cfr.trainer.CFRPlusTrainer;
import cfr.trainer.MonteCarloCFRTrainer;
import cfr.trainer.VanillaCFRTrainer;
import cfr.trainer.games.GameDescription;
import cfr.trainer.node.Node;
import cfr.trainer.node.NodeImpl;

public class PokerTrainerTest {
	
	
	@Test
	public void monteCarloCFRTrainer_test() throws Exception {
		MonteCarloCFRTrainer monteCarloCFRTrainer = new MonteCarloCFRTrainer();
		monteCarloCFRTrainer.train(GameDescription.KUHN_POKER,1000000);

		// Comments taken from wikipdeia page on Kuhn Poker
		// https://en.wikipedia.org/wiki/Kuhn_poker
		//

		// The game has a mixed-strategy Nash equilibrium; when both players
		// play equilibrium strategies,the first player should expect to lose
		// at a rate of -1/18 per hand (as the game is zero-sum,the second
		// player should expect to win at a rate of 1/18). There is no
		// pure-strategy equilibrium.

		double avgGameValue = monteCarloCFRTrainer.getAverageGameValue();
		assertEquals(-0.05555,avgGameValue,0.003);

		Map<String,NodeImpl> nodeMap = monteCarloCFRTrainer.getNodeMap();
		
		Node kingDeal = nodeMap.get("[S,S,K]D");
		Node kingDealCall = nodeMap.get("[S,S,K]D,C");
		Node kingDealCallRaise = nodeMap.get("[S,S,K]D,C,1");
		Node kingDealRaise = nodeMap.get("[S,S,K]D,1");

		Node queenDeal = nodeMap.get("[S,S,Q]D");
		Node queenDealCall = nodeMap.get("[S,S,Q]D,C");
		Node queenDealCallRaise = nodeMap.get("[S,S,Q]D,C,1");
		Node queenDealRaise = nodeMap.get("[S,S,Q]D,1");

		Node jackDeal = nodeMap.get("[S,S,J]D");
		Node jackDealCallRaise = nodeMap.get("[S,S,J]D,C,1");
		Node jackDealCall = nodeMap.get("[S,S,J]D,C");
		Node jackDealRaise = nodeMap.get("[S,S,J]D,1");

		final int CALL_INDEX = 1;
		final int RAISE_INDEX = 2;

		// Kuhn demonstrated there are infinitely many equilibrium strategies
		// for the first player,forming a continuum governed by a single
		// parameter. In one possible formulation,player one freely chooses the
		// probability alpha in the range [0,1/3] alpha with which he will bet
		// when having
		// a Jack.
		double alpha = jackDeal.getAverageStrategy()[RAISE_INDEX];

		assertTrue(alpha > 0);
		assertTrue(alpha < 3.35);
		// Then,when having a King,he should bet with the probability of
		// 3*alpha
		assertEquals(alpha * 3,kingDeal.getAverageStrategy()[RAISE_INDEX],0.01);
		// he should always check when having a Queen,
		assertEquals(.99,queenDeal.getAverageStrategy()[CALL_INDEX],0.01);
		// if the other player bets after this check,he should call with the
		// probability of alpha +1/3
		assertEquals(alpha + 0.333,queenDealCallRaise.getAverageStrategy()[CALL_INDEX],0.01);

		// The second player has a single equilibrium strategy: Always betting
		// or calling when having a King;
		assertEquals(1,kingDealCall.getAverageStrategy()[RAISE_INDEX],0.001);
		assertEquals(1,kingDealRaise.getAverageStrategy()[CALL_INDEX],0.001);
		assertEquals(1,kingDealCallRaise.getAverageStrategy()[CALL_INDEX],0.001);

		// when having a Queen,checking if possible,otherwise calling with the
		// probability of 1/3;

		assertEquals(1,queenDealCall.getAverageStrategy()[CALL_INDEX],0.001);
		assertEquals(0.33,queenDealRaise.getAverageStrategy()[CALL_INDEX],0.01);

		// when having a Jack,never calling and betting with the probability of
		// 1/3.

		assertEquals(0,jackDealCallRaise.getAverageStrategy()[CALL_INDEX],0.001);
		assertEquals(0.33,jackDealCall.getAverageStrategy()[RAISE_INDEX],0.02);
		assertEquals(0,jackDealRaise.getAverageStrategy()[CALL_INDEX],0.001);
	}

	@Test
	public void vannillaCFRTrainer_test() throws Exception {
		VanillaCFRTrainer vanillaCFRTrainer = new VanillaCFRTrainer();
		vanillaCFRTrainer.train(GameDescription.KUHN_POKER,1000000);

		// Comments taken from wikipdeia page on Kuhn Poker
		// https://en.wikipedia.org/wiki/Kuhn_poker
		//

		// The game has a mixed-strategy Nash equilibrium; when both players
		// play equilibrium strategies,the first player should expect to lose
		// at a rate of -1/18 per hand (as the game is zero-sum,the second
		// player should expect to win at a rate of 1/18). There is no
		// pure-strategy equilibrium.

		double avgGameValue = vanillaCFRTrainer.getAverageGameValue();
		assertEquals(-0.05555,avgGameValue,0.003);

		Map<String,Node> nodeMap = vanillaCFRTrainer.getNodeMap();

		Node kingDeal = nodeMap.get("[S,S,K]D");
		Node kingDealCall = nodeMap.get("[S,S,K]D,C");
		Node kingDealCallRaise = nodeMap.get("[S,S,K]D,C,1");
		Node kingDealRaise = nodeMap.get("[S,S,K]D,1");

		Node queenDeal = nodeMap.get("[S,S,Q]D");
		Node queenDealCall = nodeMap.get("[S,S,Q]D,C");
		Node queenDealCallRaise = nodeMap.get("[S,S,Q]D,C,1");
		Node queenDealRaise = nodeMap.get("[S,S,Q]D,1");

		Node jackDeal = nodeMap.get("[S,S,J]D");
		Node jackDealCallRaise = nodeMap.get("[S,S,J]D,C,1");
		Node jackDealCall = nodeMap.get("[S,S,J]D,C");
		Node jackDealRaise = nodeMap.get("[S,S,J]D,1");

		final int CALL_INDEX = 1;
		final int RAISE_INDEX = 2;

		// Kuhn demonstrated there are infinitely many equilibrium strategies
		// for the first player,forming a continuum governed by a single
		// parameter. In one possible formulation,player one freely chooses the
		// probability alpha in the range [0,1/3] alpha with which he will bet
		// when having
		// a Jack.
		double alpha = jackDeal.getAverageStrategy()[RAISE_INDEX];

		assertTrue(alpha > 0);
		assertTrue(alpha < 3.35);
		// Then,when having a King,he should bet with the probability of
		// 3*alpha
		assertEquals(alpha * 3,kingDeal.getAverageStrategy()[RAISE_INDEX],0.01);
		// he should always check when having a Queen,
		assertEquals(.99,queenDeal.getAverageStrategy()[CALL_INDEX],0.01);
		// if the other player bets after this check,he should call with the
		// probability of alpha +1/3
		assertEquals(alpha + 0.333,queenDealCallRaise.getAverageStrategy()[CALL_INDEX],0.01);

		// The second player has a single equilibrium strategy: Always betting
		// or calling when having a King;
		assertEquals(1,kingDealCall.getAverageStrategy()[RAISE_INDEX],0.001);
		assertEquals(1,kingDealRaise.getAverageStrategy()[CALL_INDEX],0.001);
		assertEquals(1,kingDealCallRaise.getAverageStrategy()[CALL_INDEX],0.001);

		// when having a Queen,checking if possible,otherwise calling with the
		// probability of 1/3;

		assertEquals(1,queenDealCall.getAverageStrategy()[CALL_INDEX],0.001);
		assertEquals(0.33,queenDealRaise.getAverageStrategy()[CALL_INDEX],0.01);

		// when having a Jack,never calling and betting with the probability of
		// 1/3.

		assertEquals(0,jackDealCallRaise.getAverageStrategy()[CALL_INDEX],0.001);
		assertEquals(0.33,jackDealCall.getAverageStrategy()[RAISE_INDEX],0.02);
		assertEquals(0,jackDealRaise.getAverageStrategy()[CALL_INDEX],0.001);
	}
	
//@Ignore
//	@Test
//	public void CFRPlusTrainer_test() throws Exception {
//		CFRPlusTrainer cfrPlusTrainer = new CFRPlusTrainer();							
//		cfrPlusTrainer.train(GameDescription.KUHN_POKER,1000000);
//
//		// Comments taken from wikipdeia page on Kuhn Poker
//		// https://en.wikipedia.org/wiki/Kuhn_poker
//		//
//
//		// The game has a mixed-strategy Nash equilibrium; when both players
//		// play equilibrium strategies,the first player should expect to lose
//		// at a rate of -1/18 per hand (as the game is zero-sum,the second
//		// player should expect to win at a rate of 1/18). There is no
//		// pure-strategy equilibrium.
//
//		double avgGameValue = cfrPlusTrainer.getAverageGameValue();
//		assertEquals(-0.05555,avgGameValue,0.003);
//
//		Map<String,int[]> nodeMap = cfrPlusTrainer.getRegretMap();
//
//		Node kingDeal = nodeMap.get("[S,S,K]D");
//		Node kingDealCall = nodeMap.get("[S,S,K]D,C");
//		Node kingDealCallRaise = nodeMap.get("[S,S,K]D,C,1");
//		Node kingDealRaise = nodeMap.get("[S,S,K]D,1");
//
//		Node queenDeal = nodeMap.get("[S,S,Q]D");
//		Node queenDealCall = nodeMap.get("[S,S,Q]D,C");
//		Node queenDealCallRaise = nodeMap.get("[S,S,Q]D,C,1");
//		Node queenDealRaise = nodeMap.get("[S,S,Q]D,1");
//
//		Node jackDeal = nodeMap.get("[S,S,J]D");
//		Node jackDealCallRaise = nodeMap.get("[S,S,J]D,C,1");
//		Node jackDealCall = nodeMap.get("[S,S,J]D,C");
//		Node jackDealRaise = nodeMap.get("[S,S,J]D,1");
//
//
//		final int CALL_INDEX = 1;
//		final int RAISE_INDEX = 2;
//
//		// Kuhn demonstrated there are infinitely many equilibrium strategies
//		// for the first player,forming a continuum governed by a single
//		// parameter. In one possible formulation,player one freely chooses the
//		// probability alpha in the range [0,1/3] alpha with which he will bet
//		// when having
//		// a Jack.
//		double alpha = jackDeal.getAverageStrategy()[RAISE_INDEX];
//
//		assertTrue(alpha > 0);
//		assertTrue(alpha < 3.35);
//		// Then,when having a King,he should bet with the probability of
//		// 3*alpha
//		assertEquals(alpha * 3,kingDeal.getAverageStrategy()[RAISE_INDEX],0.01);
//		// he should always check when having a Queen,
//		assertEquals(.99,queenDeal.getAverageStrategy()[CALL_INDEX],0.01);
//		// if the other player bets after this check,he should call with the
//		// probability of alpha +1/3
//		assertEquals(alpha + 0.333,queenDealCallRaise.getAverageStrategy()[CALL_INDEX],0.01);
//
//		// The second player has a single equilibrium strategy: Always betting
//		// or calling when having a King;
//		assertEquals(1,kingDealCall.getAverageStrategy()[RAISE_INDEX],0.001);
//		assertEquals(1,kingDealRaise.getAverageStrategy()[CALL_INDEX],0.001);
//		assertEquals(1,kingDealCallRaise.getAverageStrategy()[CALL_INDEX],0.001);
//
//		// when having a Queen,checking if possible,otherwise calling with the
//		// probability of 1/3;
//
//		assertEquals(1,queenDealCall.getAverageStrategy()[CALL_INDEX],0.001);
//		assertEquals(0.33,queenDealRaise.getAverageStrategy()[CALL_INDEX],0.01);
//
//		// when having a Jack,never calling and betting with the probability of
//		// 1/3.
//
//		assertEquals(0,jackDealCallRaise.getAverageStrategy()[CALL_INDEX],0.001);
//		assertEquals(0.33,jackDealCall.getAverageStrategy()[RAISE_INDEX],0.02);
//		assertEquals(0,jackDealRaise.getAverageStrategy()[CALL_INDEX],0.001);
//	}
	
}
