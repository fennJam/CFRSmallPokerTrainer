package cfr.trainer.node;

import cfr.trainer.games.Game;
import cfr.trainer.games.GameType;
import cfr.trainer.games.poker.nodes.PokerInfoSetFactory;
import cfr.trainer.node.NodeImpl;

public class InfoSetFactory {

	public static NodeImpl buildInformationSet(String nodeId, Game game) {
		if (game.getGameType() == GameType.POKER) {
			return PokerInfoSetFactory.buildInformationSet(nodeId, game);
		}
		return null;
	}
}
