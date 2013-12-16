package player;

import java.util.Scanner;
import othello.Board;
import othello.Coordinate;
import othello.IPlayer;
import othello.Player;

/**
 * An instance of PlayerHuman allows a human player for the game. It utilizes scanner to take inputs.
 * 
 * Note: make sure PRINT is true when using the PlayerHuman class!
 */
public class PlayerHuman implements IPlayer {

	// private Board		board;
	private Player		p;
	private Scanner	sc;

	@Override
	public void startNewGame(Board board, Player p) {
		this.sc = new Scanner(System.in);
		// this.board = board;
		this.p = p;
	}

	@Override
	public Coordinate move() {
		System.out.println("Enter coordinates r c:");
		int r = sc.nextInt();
		int c = sc.nextInt();
		return new Coordinate(r, c);
	}

	@Override
	public String toString() {
		return "Human";
	}

	@Override
	public Player getPlayerColor() {
		return p;
	}

	@Override
	public void endOfGameEval() {
	}
}
