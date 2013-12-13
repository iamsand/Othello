package player;

import othello.*;

// This is a testing AI that simply makes the greedy choice for every move.
public class PlayerGreed implements IPlayer {

	Board	board;

	@Override
	public void startNewGame(Board board) {
		this.board = board;

	}

	@Override
	public Coordinate move() {
		// TODO Auto-generated method stub
		return null;
	}
}
