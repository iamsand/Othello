package player;

import java.util.ArrayList;
import othello.Board;
import othello.Coordinate;
import othello.IPlayer;
import othello.Player;

/**
 * An instance of PlayerGreedier simulates a dummy AI that looks one move ahead and picks the greediest move. If multiple moves and equal greed value, a random one is
 * chosen.
 */
public class PlayerGreedier implements IPlayer {

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
			int net = Integer.MAX_VALUE;
			ArrayList<Coordinate> oppMoves = clone.allLegalMoves(p.switchPlayer());
			for (Coordinate move : oppMoves) {
				Board clone2 = clone.clone();
				clone2.makeMove(move, p.switchPlayer());
				if (clone2.getDiscs(p) < net)
					net = clone2.getDiscs(p);
			}
			if (net > maxNet)
				bestMoves.clear();
			if (net >= maxNet) {
				bestMoves.add(c);
				maxNet = net;
			}
		}

		return bestMoves.get((int) (Math.random() * bestMoves.size()));
	}

	@Override
	public String toString() {
		return "Greedier";
	}

	@Override
	public Player getPlayerColor() {
		return p;
	}

	@Override
	public void endOfGameEval() {
	}
}
