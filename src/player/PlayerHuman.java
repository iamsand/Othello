package player;

import java.util.Scanner;
import othello.Board;
import othello.Coordinate;
import othello.IPlayer;
import othello.Player;

// This is a class that allows a human to play.
public class PlayerHuman implements IPlayer {

	Board		board;
	Player	p;
	Scanner	sc;

	@Override
	public void startNewGame(Board board, Player p) {
		this.sc = new Scanner(System.in);
		this.board = board;
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
}
