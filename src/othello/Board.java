package othello;

import java.util.ArrayList;

/*
 * A class to represent the othello board.
 */

// TODO: Add more functionality to the board.
public class Board {

	private int			boardDim;
	private Disc[][]	board;

	public Board(int boardDim) {
		this.boardDim = boardDim;
		this.board = new Disc[boardDim][boardDim];
		for (int i = 0; i < boardDim; i++)
			for (int j = 0; j < boardDim; j++)
				board[i][j] = Disc.EMPTY;
		board[boardDim / 2 - 1][boardDim / 2 - 1] = Disc.WHITE;
		board[boardDim / 2 - 1][boardDim / 2] = Disc.BLACK;
		board[boardDim / 2][boardDim / 2 - 1] = Disc.WHITE;
		board[boardDim / 2][boardDim / 2] = Disc.BLACK;
	}
	
	// returns the number of pieces a player has
	public int getDiscs(Player p) {
		int count = 0;
		for (int i = 0; i < boardDim; i++)
			for (int j = 0; j < boardDim; j++)
				if (board[i][j] == p.toDisc())
					count++;
		return count;
	}
	
	// Returns the disc at a given coordinate
	public Disc getDisc(Coordinate c) {
		if (!isLegalCoordinate(c))
			return null;
		return board[c.getRow()][c.getCol()];
	}
	
	// Changes the disc at a given coordinate
	public void setDisc(Coordinate c, Disc d) {
		if (!isLegalCoordinate(c)) {
			System.out.println("Tried to place a disc in an illegal coordinate. Please debug.");
			System.exit(0);
		}
		board[c.getRow()][c.getCol()] = d;
	}
	
	// Tests whether a certain player can make a move
	public boolean canMove(Player p) {
		for (int i = 0; i < boardDim; i++)
			for (int j = 0; j < boardDim; j++)
				if (isLegalMove(new Coordinate(i,j), p))
					return true;
		return false;
	}

	// Move onto a given square.
	public void makeMove(Coordinate c, Player p) {
		if (!isLegalMove(c,p)) {
			System.out.println("Player made an illegal move. Please debug.");
			System.exit(0);
		}
		setDisc(c, p.toDisc());
		for (Direction dir : Direction.values())
			if (isLegalCapture(c, p, dir)) {
				Coordinate adj = c.move(dir);
				while (getDisc(adj) == p.switchPlayer().toDisc()) {
					setDisc(adj, p.toDisc());
					adj = adj.move(dir);
				}
			}
	}
	
	// Returns a copied board
	public Board clone() {
		Board clone = new Board(boardDim);
		for (int i = 0; i < boardDim; i++)
			for (int j = 0; j < boardDim; j++)
				clone.setDisc(new Coordinate(i, j), board[i][j]);
		return clone;
	}
	
	// Tests if a give coordinate is on the board
	public boolean isLegalCoordinate(Coordinate c) {
		if (c == null)
			return false;
		return c.getRow() >= 0 && c.getRow() < boardDim && c.getCol() >= 0 && c.getCol() < boardDim;
	}

	// Test whether or not a move is legal.
	public boolean isLegalMove(Coordinate c, Player p) {
		if (getDisc(c) != Disc.EMPTY)
			return false;
		for (Direction dir : Direction.values()) 
			if (isLegalCapture(c, p, dir))
				return true;
		return false;
	}
	
	// Helper Method
	// Checks if playing a disc at the given coordinate results in a capture in a certain direction
	public boolean isLegalCapture(Coordinate c, Player p, Direction dir) {
		Coordinate adj = c.move(dir);
		if (getDisc(adj) == p.switchPlayer().toDisc()) {
			while (getDisc(adj) == p.switchPlayer().toDisc())
				adj = adj.move(dir);
			if (getDisc(adj) == p.toDisc())
				return true;
		}
		return false;
	}
	
	// Returns an arraylist of all legal moves for a player
	public ArrayList<Coordinate> allLegalMoves(Player p) {
		ArrayList<Coordinate> al = new ArrayList<Coordinate>();
		for (int i = 0; i < boardDim; i++)
			for (int j = 0; j < boardDim; j++)
				if (isLegalMove(new Coordinate(i,j), p))
					al.add(new Coordinate(i,j));
		return al;
	}

	// Returns true if the game is over.
	public boolean isGameOver() {
		return !canMove(Player.BLACK) && !canMove(Player.WHITE);
	}
	
	// Returns the winner of the game, or null if it's a tie
	public Player getWinner() {
		if (getDiscs(Player.WHITE) == getDiscs(Player.BLACK))
			return null;
		if (getDiscs(Player.WHITE) > getDiscs(Player.BLACK))
			return Player.WHITE;
		return Player.BLACK;
	}

	// Prints the board to the console.
	// TODO: make method less ugly, make board printout less ugly (graphical interface eventually?)
	public void printBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				switch (board[r][c]) {
					case BLACK:
						System.out.print("B");
						break;
					case WHITE:
						System.out.print("W");
						break;
					default:
						System.out.print(" ");
						break;
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
