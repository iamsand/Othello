package othello;

/*
 * A class to represent the othello board.
 */

// TODO: Add more functionality to the board.
public class Board {

	private Disc[][]	board;
	private int			numWhite;
	private int			numBlack;

	public int getWhite() {
		return numWhite;
	}

	public int getBlack() {
		return numBlack;
	}

	public Board(int boardDim) {
		this.board = new Disc[boardDim][boardDim];
		board[boardDim / 2 - 1][boardDim / 2 - 1] = Disc.WHITE;
		board[boardDim / 2 - 1][boardDim / 2] = Disc.BLACK;
		board[boardDim / 2][boardDim / 2 - 1] = Disc.WHITE;
		board[boardDim / 2][boardDim / 2] = Disc.BLACK;
		numWhite = 2;
		numBlack = 2;
	}

	// Move onto a given square.
	public void makeMove(Coordinate c, Disc d) {

	}

	// Test whether or not a move is legal.
	public boolean isLegalMove(Coordinate c, Disc d) {
		return false;
	}

	// Returns true if the game is over.
	// Instead of using winsfor(), just test for legal moves.
	// TODO: Implement
	public boolean isGameOver(Disc currentPlayer) {
		return false;
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
