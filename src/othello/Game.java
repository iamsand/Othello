package othello;

public class Game {

	public Board		board;
	
	public int			result;
	public boolean		debug;

	public Game(int boardDim, boolean debug) {
		this.board = new Board(boardDim);
		result = 0;
		this.debug = debug;
	}




	public void run(IPlayer[] players, boolean p1Human, boolean p2Human) {
		players[0].startNewGame(board);
		players[1].startNewGame(board);
		while (result == 0) {
			Coordinate p1Move = players[0].move();
			if (board.isLegalMove(p1Move, Disc.BLACK))
				board.addDisc(p1Move, Disc.BLACK);
			else {
				if (p1Human) {
					System.out.println("Move is illegal!");
					continue;
				} else {
					System.out.println("Computer made an illegal move.");
					System.exit(0);
				}

				//If the board is won for a player, break.
				if (board.isGameOver(Disc.WHITE))
					break;
				
				//Otherwise, continue.
				Coordinate p2Move = players[1].move();
				if (board.isLegalMove(p2Move, Disc.WHITE))
					board.addDisc(p2Move, Disc.WHITE);
				else {
					if (p2Human) {
						System.out.println("Move is illegal!");
						continue;
					} else {
						System.out.println("Computer made an illegal move.");
						System.exit(0);
					}
				}

				if (board.isGameOver(Disc.BLACK))
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
