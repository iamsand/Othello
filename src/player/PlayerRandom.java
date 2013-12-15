package player;

import java.util.ArrayList;

import othello.Board;
import othello.Coordinate;
import othello.IPlayer;
import othello.Player;

/**
 * An instance of PlayerRandom simulates a dummy AI that randomly chooses a legal move every turn.
 */
public class PlayerRandom implements IPlayer {

	private Board	board;
	private Player	p;

	@Override
	public void startNewGame(Board board, Player p) {
		this.board = board;
		this.p = p;
	}

	@Override
	public Coordinate move() {
		ArrayList<Coordinate> allMoves = board.allLegalMoves(p);
		return allMoves.get((int) (Math.random() * allMoves.size()));
	}

	@Override
	public String toString() {
		return "Random";
	}

	@Override
	public Player getPlayerColor() {
		return p;
	}

	@Override
	public void endOfGameEval() {
	}
	
	
}
