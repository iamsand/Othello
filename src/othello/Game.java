package othello;

public class Game {

	private Board	board;
	private boolean	debug;

	public Game(int boardDim, boolean debug) {
		this.board = new Board(boardDim);
		this.debug = debug;
	}

	public void run(IPlayer[] players) {
		players[0].startNewGame(board);
		players[1].startNewGame(board);
		
		Player p = Player.BLACK;
		while (!board.isGameOver()) {
			if (!board.canMove(p)) {
				p = p.switchPlayer();
				continue;
			}
			Coordinate move = players[p.intValue()].move();
			while (!board.isLegalMove(move, p)) {
				System.out.println("Move is illegal!");
				move = players[p.intValue()].move();
			}
			board.makeMove(move, p);
			p = p.switchPlayer();
		}
		
		printResult();
	}

	public void printResult() {
		if (!board.isGameOver()) {
			System.out.println("Game is not yet over! Please debug me.");
			return;
		}
		if (board.getWinner() == null)
			System.out.println("Tie game");
		else
			System.out.println(board.getWinner() + " wins");
	}

}
