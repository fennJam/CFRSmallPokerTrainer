package cfr.poker.actions;

public class BasePokerAction implements PokerAction {

	PokerActionType actionType = PokerActionType.FOLD;
	
	BasePokerAction(PokerActionType pokerActionType){
		actionType = pokerActionType;
	}
	
	@Override
	public PokerActionType getActionType() {
		return actionType;
	}
	
	
	public String toString(){
		return actionType.toString();
	}
	
}
