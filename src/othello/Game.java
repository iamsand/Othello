package othello;

public class Game {

	public int			boardDim;
	public short[][]	board;
	// -1 black win, 1 white win, 0 game not over!
	public int			result;
	public boolean		debug;

	public Game(int boardDim, boolean debug) {
		this.boardDim = boardDim;
		this.board = new short[boardDim][boardDim];
		result = 0;
		this.debug = debug;
	}

	// 1 will represent white. -1 will represent black. Black goes first.
	public void init() {
		board[boardDim / 2 - 1][boardDim / 2 - 1] = 1;
		board[boardDim / 2 - 1][boardDim / 2] = -1;
		board[boardDim / 2][boardDim / 2 - 1] = -1;
		board[boardDim / 2][boardDim / 2] = 1;
	}

	// This method actually updates the grid.
	public void move(Coordinate c, boolean black) {

	}

	public void printBoard() {
		for (int r = 0; r < boardDim; r++) {
			for (int c = 0; c < boardDim; c++) {
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

	public void run(IPlayer[] players, boolean p1Human, boolean p2Human) {
		players[0].startNewGame(board);
		players[1].startNewGame(board);
		while (result == 0) {
			Coordinate p1Move = players[0].move();
			if (Board.isLegalMove(board, p1Move, true))
				move(p1Move, true);
			else {
				if (p1Human) {
					System.out.println("Move is illegal!");
					continue;
				} else {
					System.out.println("Computer made an illegal move.");
					System.exit(0);
				}

				result = Board.isGameOver(false);
				if (result != 0)
					break;

				Coordinate p2Move = players[1].move();
				if (Board.isLegalMove(board, p2Move, false))
					move(p2Move, false);
				else {
					if (p2Human) {
						System.out.println("Move is illegal!");
						continue;
					} else {
						System.out.println("Computer made an illegal move.");
						System.exit(0);
					}
				}

				result = Board.isGameOver(false);
				if (result != 0)
					break;

			}
			printResult();
		}
	}

	public void printResult() {
		switch (result) {
			case -1:
				System.out.println("Black Wins.");
				break;
			case 0:
				System.out.println("Game is not yet over! Please debug me.");
				break;
			case 1:
				System.out.println("White Wins.");
				break;
		}
	}

}
