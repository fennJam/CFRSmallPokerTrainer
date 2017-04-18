package cfr.trainer.tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import cfr.trainer.GameType;
import cfr.trainer.MonteCarloCFRTrainer;
import cfr.trainer.Node;
import cfr.trainer.NodeImpl;

public class PokerTrainerTest {

	@Test
	public void monteCarloCFRTrainer_test() {
		MonteCarloCFRTrainer monteCarloCFRTrainer = new MonteCarloCFRTrainer();
		monteCarloCFRTrainer.train(GameType.KUHN_POKER, 1000000);

		// Comments taken from wikipdeia page on Kuhn Poker
		// https://en.wikipedia.org/wiki/Kuhn_poker
//		

		// The game has a mixed-strategy Nash equilibrium; when both players
		// play equilibrium strategies, the first player should expect to lose
		// at a rate of -1/18 per hand (as the game is zero-sum, the second
		// player should expect to win at a rate of 1/18). There is no
		// pure-strategy equilibrium.

		double avgGameValue = monteCarloCFRTrainer.getAverageGameValue();
		assertEquals(-0.05555, avgGameValue, 0.003);

		Map<String, NodeImpl> nodeMap = monteCarloCFRTrainer.getNodeMap();

		Node kingDealRaise = nodeMap.get("[S, S, KING]DEAL RAISE1");
		Node queenDealRaise = nodeMap.get("[S, S, QUEEN]DEAL RAISE1");
		Node jackDealRaise = nodeMap.get("[S, S, JACK]DEAL RAISE1");
		Node jackDealCallRaise = nodeMap.get("[S, S, JACK]DEAL CALL RAISE1");
		Node kingDeal = nodeMap.get("[S, S, KING]DEAL");
		Node queenDealCallRaise = nodeMap.get("[S, S, QUEEN]DEAL CALL RAISE1");
		Node queenDealCall = nodeMap.get("[S, S, QUEEN]DEAL CALL");
		Node kingDealCallRaise = nodeMap.get("[S, S, KING]DEAL CALL RAISE1");
		Node queenDeal = nodeMap.get("[S, S, QUEEN]DEAL");
		Node jackDealCall = nodeMap.get("[S, S, JACK]DEAL CALL");
		Node jackDeal = nodeMap.get("[S, S, JACK]DEAL");
		Node kingDealCall = nodeMap.get("[S, S, KING]DEAL CALL");

		final int FOLD_INDEX = 0;
		final int CALL_INDEX = 1;
		final int RAISE_INDEX = 2;

		// Kuhn demonstrated there are infinitely many equilibrium strategies
		// for the first player, forming a continuum governed by a single
		// parameter. In one possible formulation, player one freely chooses the
		// probability alpha in the range [0,1/3] alpha with which he will bet
		// when having
		// a Jack.
		double alpha = jackDeal.getAverageStrategy()[RAISE_INDEX];

		assertTrue(alpha > 0);
		assertTrue(alpha < 3.35);
		// Then, when having a King, he should bet with the probability of
		// 3*alpha
		assertEquals(alpha * 3, kingDeal.getAverageStrategy()[RAISE_INDEX], 0.01);
		// he should always check when having a Queen,
		assertEquals(.99, queenDeal.getAverageStrategy()[CALL_INDEX], 0.01);
		// if the other player bets after this check, he should call with the
		// probability of alpha +1/3
		assertEquals(alpha + 0.333, queenDealCallRaise.getAverageStrategy()[CALL_INDEX], 0.01);

		// The second player has a single equilibrium strategy: Always betting
		// or calling when having a King; when having a Queen, checking if
		// possible, otherwise calling with the probability of 1/3; when having
		// a Jack, never calling and betting with the probability of 1/3.

	}

}
