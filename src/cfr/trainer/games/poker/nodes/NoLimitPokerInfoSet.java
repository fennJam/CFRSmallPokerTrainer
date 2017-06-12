package cfr.trainer.games.poker.nodes;

import java.util.ArrayList;
import java.util.List;

import cfr.trainer.action.Action;
import cfr.trainer.games.poker.actions.PokerAction;
import cfr.trainer.games.poker.games.PokerGame;
import cfr.trainer.node.NodeImpl;

public class NoLimitPokerInfoSet extends NodeImpl {

	static final int BIG_BLIND = 2;

	NoLimitPokerInfoSet(String nodeId, PokerGame game) {
		super(constructActionArray(game));
//		nodeIdentifier = nodeId;
	}
	
	NoLimitPokerInfoSet(PokerGame game) {
		super(constructActionArray(game));
	}

	public static Action[] constructActionArray(PokerGame game) {

		List<Action> infoSetActionsList = new ArrayList<Action>();

		int player = game.getPlayerToAct();
		int playerStack = game.getPlayer(player).getStack();
		int opponent = player == 1 ? 0 : 1;
		int playersPotContribution = game.getPot().getPlayersContributionToPot(player);
		int opponentsPotContribution = game.getPot().getPlayersContributionToPot(opponent);
		
		if (playersPotContribution <0 || opponentsPotContribution<0) {
			throw new Error ("Players contribution cannot be less than zero!");
		}
		
		int betToMatch = opponentsPotContribution - playersPotContribution;

		if (playerStack > betToMatch) {
			infoSetActionsList =  game.getPossibleActions().subList(0,(playerStack - betToMatch)+2);
		}else{
			infoSetActionsList =  game.getPossibleActions().subList(0, 2);	
		}
		
		PokerAction[] infoSetActionsArray = infoSetActionsList.toArray(new PokerAction[infoSetActionsList.size()]);
		return infoSetActionsArray;

	}

}
