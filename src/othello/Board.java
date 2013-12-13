package othello;

/*
 * A class to represent the othello board.
 */

// TODO: Add more functionality to the board.
public class Board {
	private short[][] board;

	
	
	public Board(int boardDim){
		this.board = new short[boardDim][boardDim];
		this.addDisc(new Coordinate(boardDim / 2 - 1, boardDim / 2 - 1), Disc.WHITE);
		this.addDisc(new Coordinate(boardDim / 2 - 1, boardDim / 2), Disc.BLACK);
		this.addDisc(new Coordinate(boardDim / 2, boardDim / 2 - 1), Disc.WHITE);
		this.addDisc(new Coordinate(boardDim / 2, boardDim /2 ), Disc.BLACK);
	}
	
	// Move onto a given square.
	public void addDisc(Coordinate c, Disc d){
		
	}
	
	// Test whether or not a move is legal.
	public boolean isLegalMove(Coordinate c, Disc d) {
		return false;
	}

	// Returns true if the game is over.
	// TODO: Implement
	public boolean isGameOver(Disc currentPlayer) {
		return false;
	}
	
	// if the current state of the board wins for the inputted disc, outputs true. false otherwise.
	// May not be neccesary -- not sure if it's helpful yet. TODO: Determine if helpful.
	public boolean winsFor(Disc d){
		return false;
	}
	
	// Prints the board to the console.
	// TODO: make method less ugly, make board printout less ugly (graphical interface eventually?)
	public void printBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board.length; c++) {
				switch (board[r][c]) {
					case -1:
						System.out.print("B");
						break;
					case 0:
						System.out.print(" ");
						break;
					case 1:
						System.out.print("W");
						break;
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
}
