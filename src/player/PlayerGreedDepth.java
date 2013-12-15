package player;

import java.util.ArrayList;

import othello.Board;
import othello.Coordinate;
import othello.IPlayer;
import othello.Player;

/**
 * An instance of PlayerGreedDepth simulates an AI that generates a tree and plays the greedy move at some depth.
 */
public class PlayerGreedDepth implements IPlayer {

	private Board		board;
	private Player		p;
	private boolean	DEBUG	= false;
	// Modify this to change performance. I'm not sure if it matters, but use an odd depth for now.
	int					depth	= 5;

	@Override
	public String toString() {
		return "GreedDepth";
	}

	@Override
	public void endOfGameEval() {
	}

	@Override
	public Player getPlayerColor() {
		return p;
	}

	@Override
	public void startNewGame(Board board, Player p) {
		this.board = board;
		this.p = p;
	}

	treeNode	origin;

	@Override
	public Coordinate move() {
		origin = new treeNode(0, null, null, board.clone(), p.switchPlayer());
		tree(origin);
		if (DEBUG)
			tstPrint(origin);

		for (treeNode tn : origin.children) {
			if (origin.maxDiscsForBranch == tn.maxDiscsForBranch) {
				return tn.coordinate;
			}
		}
		System.out.println("I returned null. Oops.");
		return null;
	}

	public void tstPrint(treeNode t) {
		System.out.println(t);
		for (treeNode tn : t.children) {
			tstPrint(tn);
		}
	}

	public void tree(treeNode t) {
		// Once we hit a leaf. Determine the number of discs for our player.
		if (t.depth >= depth || t.board.isGameOver()) {
			t.maxDiscsForBranch = t.board.getDiscs(p);
			return;
		}

		else {
			// We need the next 3 lines because a player may go twice.
			Player nextPlayer = t.player.switchPlayer();
			if (t.board.allLegalMoves(nextPlayer).size() == 0) {
				nextPlayer = nextPlayer.switchPlayer();
			}
			for (Coordinate c : t.board.allLegalMoves(nextPlayer)) {
				Board b = t.board.clone();
				b.makeMove(c, nextPlayer);
				treeNode child = new treeNode(t.depth + 1, t, c, b.clone(), nextPlayer);
				t.children.add(child);
				tree(child);
			}

			// This takes the min/ max value of the children treenode depending on whose turn it is.
			if (nextPlayer != p) {
				t.maxDiscsForBranch = Integer.MAX_VALUE;
				for (treeNode tn : t.children)
					t.maxDiscsForBranch = Math.min(t.maxDiscsForBranch, tn.maxDiscsForBranch);
			} else {
				for (treeNode tn : t.children)
					t.maxDiscsForBranch = Math.max(t.maxDiscsForBranch, tn.maxDiscsForBranch);
			}
		}
	}

	// A treeNode represents a board in the tree with information attached to it.
	public class treeNode {

		public int						depth;
		// Parent isn't used, but left for now.
		public treeNode				parent;
		// coordinate is the Coordinate of the move which led to this position. player is the color of the player whose move led to this position.
		public Coordinate				coordinate;
		public Player					player;
		public Board					board;
		public ArrayList<treeNode>	children;

		// This is what we're trying to figure out.
		public int						maxDiscsForBranch;

		public treeNode(int depth, treeNode parent, Coordinate value, Board board, Player player) {
			this.depth = depth;
			this.parent = parent;
			this.coordinate = value;
			this.board = board;
			this.player = player;
			children = new ArrayList<treeNode>();
		}

		public void addChildren(treeNode child) {
			children.add(child);
		}

		public String toString() {
			String s = String.format("%-7s%-3s%-6s%-10s%-8s%-7s%-13s%-2s", "depth:", depth + ",", "coord", coordinate + ",", "Player:", player + ",",
					"numChildren:", children.size(), "maxDiscs", maxDiscsForBranch);
			return "[" + s + "]";
		}
	}
}
