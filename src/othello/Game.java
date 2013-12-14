package othello;

/**
 * An instance represents one game of Othello.
 */
public class Game {

	private Board		board;
	private boolean		PRINT;
	private IPlayer[]	players;

	public Game(int boardDim, boolean print, IPlayer[] players) {
		this.players = players;
		this.board = new Board(boardDim);
		this.PRINT = print;
	}
	
	public Board getBoard() {
		return board;
	}

	public void run() {
		players[0].startNewGame(board, Player.BLACK);
		players[1].startNewGame(board, Player.WHITE);
		Player p = Player.BLACK;

		if (PRINT) {
			System.out.println("A friendly game of Othello on a " + board.getBoardDim() + "x" + board.getBoardDim() + " board.");
			System.out.println("Player 1: " + players[0]);
			System.out.println("Player 2: " + players[1]);
			System.out.println();
			board.printBoard();
		}

		while (!board.isGameOver()) {
			if (!board.canMove(p)) {
				p = p.switchPlayer();
				continue;
			}
			if (PRINT) {
				System.out.println("Player " + p + " to move.");
			}
			Coordinate move = players[p.intValue()].move();
			while (!board.isLegalMove(move, p)) {
				System.out.println("Move is illegal!");
				move = players[p.intValue()].move();
			}

			board.makeMove(move, p);

			if (PRINT) {
				System.out.println("Move choosen is: " + move);
				System.out.println();
				board.printBoard();
			}
			p = p.switchPlayer();
		}
	}

	public void printResult() {
		if (!board.isGameOver()) {
			System.out.println("Game is not yet over! Please debug me.");
			return;
		}
		if (board.getWinner() == null)
			System.out.println("Tie game");
		else {
			System.out.println((players[0].getPlayerColor() == board.getWinner() ? players[0].toString() : players[1].toString()) + " playing "
					+ board.getWinner() + " wins");
		}
		System.out.println("Score: " + Math.max(board.getDiscs(Player.WHITE), board.getDiscs(Player.BLACK)) + " - "
				+ Math.min(board.getDiscs(Player.WHITE), board.getDiscs(Player.BLACK)));
	}
}
