package cfr.trainer.games.poker;

public class PokerPlayer {

	String id;
	double stack;
	Hand hand;

	public PokerPlayer(String id, double stack, Hand hand) {
		this.id = id;
		this.stack = stack;
		this.hand = hand;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getStack() {
		return stack;
	}

	public void setStack(double stack) {
		this.stack = stack;
	}

	public void addToStack(double chips) {
		this.stack = this.stack + chips;
	}
	
	public void takeFromStack(double chips) {
		if(stack<chips){
			throw new Error("Cannot take "+chips+" chips away than the only player has "+stack+".");
		}
		this.stack = this.stack - chips;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

}
