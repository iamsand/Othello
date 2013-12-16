package genetics;

import java.util.ArrayList;

import othello.Board;
import othello.Coordinate;
import othello.IPlayer;
import othello.Player;

/**
 * An instance of PlayerGenetics plays using a tree, but uses weights to evaluate the board.
 */
// Right now this is a copy of PlayerGreedDepth with very minor changes. Will modify to make it work.
public class PlayerGenetics implements IPlayer {

	private Board		board;
	private Player		p;
	private boolean	DEBUG	= false;
	// Modify this to change performance. I'm not sure if it matters, but use an odd depth for now.
	int					depth	= 3;
	double[][]			weights1;
	double[][]			weights2;

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

	public void initWeights(double[][] weights1, double[][] weights2) {
		this.weights1 = weights1;
		this.weights2 = weights2;
	}

	treeNode	origin;

	@Override
	public Coordinate move() {
		origin = new treeNode(0, null, board.clone(), p.switchPlayer());
		tree(origin);
		if (DEBUG)
			tstPrint(origin);

		ArrayList<Coordinate> bestMoves = new ArrayList<Coordinate>();
		
		// System.out.println("Begin move() debug");

		// System.out.println(origin.maxWeightForBranch);
		// System.out.println("num child " + origin.children.size());
		
		for (treeNode tn : origin.children) {
			// System.out.println(tn.maxWeightForBranch);
			if (origin.maxWeightForBranch == tn.maxWeightForBranch) {
				bestMoves.add(tn.coordinate);
			}
		}

		// System.out.println("End move() debug");
		return bestMoves.get((int) (Math.random() * bestMoves.size()));
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
			double ourWeight = 0;
			double oppWeight = 0;
			for (int r = 0; r < 8; r++)
				for (int c = 0; c < 8; c++) {
					if (t.board.getDisc(new Coordinate(r, c)) == p.toDisc())
						ourWeight += weights1[r][c];
					else if (t.board.getDisc(new Coordinate(r, c)) == p.switchPlayer().toDisc())
						oppWeight += weights2[r][c];
				}
			t.maxWeightForBranch = ourWeight-oppWeight;
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
				treeNode child = new treeNode(t.depth + 1, c, b.clone(), nextPlayer);
				t.children.add(child);
				tree(child);
			}

			// This takes the min/ max value of the children treenode depending on whose turn it is.
			if (nextPlayer != p) {
				t.maxWeightForBranch = Integer.MAX_VALUE;
				for (treeNode tn : t.children)
					t.maxWeightForBranch = Math.min(t.maxWeightForBranch, tn.maxWeightForBranch);
			} else {
				t.maxWeightForBranch = Integer.MIN_VALUE;
				for (treeNode tn : t.children)
					t.maxWeightForBranch = Math.max(t.maxWeightForBranch, tn.maxWeightForBranch);
			}
		}
	}

	// A treeNode represents a board in the tree with information attached to it.
	public class treeNode {

		public int						depth;
		// coordinate is the Coordinate of the move which led to this position. player is the color of the player whose move led to this position.
		public Coordinate				coordinate;
		public Player					player;
		public Board					board;
		public ArrayList<treeNode>	children;

		// This is what we're trying to figure out.
		public double					maxWeightForBranch;

		public treeNode(int depth, Coordinate value, Board board, Player player) {
			this.depth = depth;
			this.coordinate = value;
			this.board = board;
			this.player = player;
			children = new ArrayList<treeNode>();
		}

		public void addChildren(treeNode child) {
			children.add(child);
		}

		public String toString() {
			String s = String.format("%-7s%-3s%-6s%-10s%-8s%-7s%-13s%-3s%-9s%-10s", "depth:", depth + ",", "coord", coordinate + ",", "Player:", player + ",",
					"numChildren:", children.size()+",", "maxDiscs", maxWeightForBranch);
			return "[" + s + "]";
		}
	}
}
