package othello;

import java.util.ArrayList;

/**
 * An instance represents the Othello board.
 */
public class Board {

	private int			boardDim;
	private Disc[][]	board;

	public int getBoardDim() {
		return boardDim;
	}

	public Board(int boardDim) {
		this.boardDim = boardDim;
		this.board = new Disc[boardDim][boardDim];
		for (int r = 0; r < boardDim; r++)
			for (int c = 0; c < boardDim; c++)
				board[r][c] = Disc.EMPTY;
		board[boardDim / 2 - 1][boardDim / 2 - 1] = Disc.WHITE;
		board[boardDim / 2 - 1][boardDim / 2] = Disc.BLACK;
		board[boardDim / 2][boardDim / 2 - 1] = Disc.BLACK;
		board[boardDim / 2][boardDim / 2] = Disc.WHITE;
	}

	/**
	 * Method finds the number of discs of player p that are on the board.
	 * 
	 * @param p
	 * @return the number of discs for p.
	 */
	public int getDiscs(Player p) {
		int count = 0;
		for (int r = 0; r < boardDim; r++)
			for (int c = 0; c < boardDim; c++)
				if (board[r][c] == p.toDisc())
					count++;
		return count;
	}

	/**
	 * Method gives the disc at a certain coordinate.
	 * 
	 * @param c
	 * @return The disc at c.
	 */
	public Disc getDisc(Coordinate c) {
		if (!isLegalCoordinate(c))
			return null;
		return board[c.getRow()][c.getCol()];
	}

	/**
	 * Method that actually places discs on the board.
	 * 
	 * @param c
	 * @param d
	 *           the color we wish to change that disc to.
	 */
	public void setDisc(Coordinate c, Disc d) {
		if (!isLegalCoordinate(c)) {
			System.out.println("Tried to place a disc in an illegal coordinate. Please debug.");
			System.exit(0);
		}
		board[c.getRow()][c.getCol()] = d;
	}

	/**
	 * Method that tells us if player p has any legal moves.
	 * 
	 * @param p
	 * @return true if p has at least 1 move. false otherwise.
	 */
	public boolean canMove(Player p) {
		for (int r = 0; r < boardDim; r++)
			for (int c = 0; c < boardDim; c++)
				if (isLegalMove(new Coordinate(r, c), p))
					return true;
		return false;
	}

	/**
	 * Update the board with moves.
	 * 
	 * @param c
	 * @param p
	 */
	public void makeMove(Coordinate c, Player p) {
		if (!isLegalMove(c, p)) {
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

	/**
	 * Self explanatory.
	 */
	@Override
	public Board clone() {
		Board clone = new Board(boardDim);
		for (int r = 0; r < boardDim; r++)
			for (int c = 0; c < boardDim; c++)
				clone.setDisc(new Coordinate(r, c), board[r][c]);
		return clone;
	}

	/**
	 * Just a bounds check.
	 * 
	 * @param c
	 * @return true if the coordinate is on the board. False otherwise.
	 */
	public boolean isLegalCoordinate(Coordinate c) {
		if (c == null)
			return false;
		return c.getRow() >= 0 && c.getRow() < boardDim && c.getCol() >= 0 && c.getCol() < boardDim;
	}

	/**
	 * Method tells us whether or not c is a legal move for player p.
	 * 
	 * @param c
	 * @param p
	 * @return
	 */
	public boolean isLegalMove(Coordinate c, Player p) {
		if (getDisc(c) != Disc.EMPTY)
			return false;
		for (Direction dir : Direction.values())
			if (isLegalCapture(c, p, dir))
				return true;
		return false;
	}

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

	/**
	 * Method that creates a list of all legal moves for player p.
	 * 
	 * @param p
	 * @return An ArrayList of all legal moves for player p.
	 */
	public ArrayList<Coordinate> allLegalMoves(Player p) {
		ArrayList<Coordinate> al = new ArrayList<Coordinate>();
		for (int r = 0; r < boardDim; r++)
			for (int c = 0; c < boardDim; c++)
				if (isLegalMove(new Coordinate(r, c), p))
					al.add(new Coordinate(r, c));
		return al;
	}

	/**
	 * Method determines whether or not legal moves can still be played.
	 * 
	 * @return True if no more moves can be played. False otherwise.
	 */
	public boolean isGameOver() {
		return !canMove(Player.BLACK) && !canMove(Player.WHITE);
	}

	/**
	 * Method determines who has more discs on the board. <br>
	 * Precondition: Neither players have any more moves. Although the method still works.
	 * 
	 * @return Player.BLACK if black has more discs on the board. Player.White if white. null if there is a tie.
	 */
	public Player getWinner() {
		if (getDiscs(Player.WHITE) < getDiscs(Player.BLACK))
			return Player.BLACK;
		if (getDiscs(Player.WHITE) > getDiscs(Player.BLACK))
			return Player.WHITE;
		return null;
	}

	/**
	 * The method provides a graphical representation of what is going on to the console.
	 */
	public void printBoard() {
		System.out.print("[ ] ");
		for (int c = 0; c < boardDim; c++) {
			System.out.print("[" + c + "]");
			if (c != boardDim - 1)
				System.out.print(" ");
		}
		System.out.println();
		for (int r = 0; r < boardDim; r++) {
			System.out.print("[" + r + "] ");
			for (int c = 0; c < boardDim; c++) {
				switch (board[r][c]) {
					case BLACK:
						System.out.print("[B]");
						break;
					case WHITE:
						System.out.print("[W]");
						break;
					default:
						System.out.print("[ ]");
						break;
				}
				if (c != boardDim - 1)
					System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
