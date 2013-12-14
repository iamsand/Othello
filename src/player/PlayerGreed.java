package player;

import java.util.ArrayList;
import othello.Board;
import othello.Coordinate;
import othello.IPlayer;
import othello.Player;

// This is a testing AI that simply makes the greedy choice for every move.
public class PlayerGreed implements IPlayer {

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

		ArrayList<Coordinate> bestMoves = new ArrayList<Coordinate>();
		int maxNet = Integer.MIN_VALUE;
		for (Coordinate c : allMoves) {
			Board clone = board.clone();
			clone.makeMove(c, p);
			int net = clone.getDiscs(p) - clone.getDiscs(p.switchPlayer());
			if (net > maxNet)
				bestMoves.clear();
			if (net >= maxNet) {
				bestMoves.add(c);
				maxNet = net;
			}
		}

		return bestMoves.get((int)(Math.random() * bestMoves.size()));
	}

	@Override
	public String toString() {
		return "Greed";
	}
}
