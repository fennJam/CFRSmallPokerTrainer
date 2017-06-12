package cfr.trainer.games.poker.nodes;

import cfr.trainer.games.Game;
import cfr.trainer.games.GameType;
import cfr.trainer.games.poker.BettingLimit;
import cfr.trainer.games.poker.games.PokerGame;
import cfr.trainer.node.NodeImpl;

public class PokerInfoSetFactory {

	public static NodeImpl buildInformationSet(String nodeId, Game game) {
		if (game.getGameType() == GameType.POKER) {
			PokerGame pokerGame = (PokerGame) game;
			if (!pokerGame.raisesAllowed()) {
				return new TerminalInfoSet();
			} else if (pokerGame.getBettingLimit() == BettingLimit.LIMIT) {
				return new LimitPokerInfoSet();
			} else if (pokerGame.getBettingLimit() == BettingLimit.POT_LIMIT) {
				return new PotLimitPokerInfoSet(pokerGame.getPot());
			} else if (pokerGame.getBettingLimit() == BettingLimit.NO_LIMIT) {
				return new NoLimitPokerInfoSet(pokerGame);
			}
		}
		return null;

	}
}
