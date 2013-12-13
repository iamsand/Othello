package player;

import java.util.Scanner;

import othello.*;

// This is a class that allows a human to play.
public class PlayerHuman implements IPlayer {

	Board	board;

	@Override
	public void startNewGame(Board board) {
		this.board = board;
	}

	@Override
	public Coordinate move() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter coordinates r c:");
		int r = sc.nextInt();
		int c = sc.nextInt();
		sc.close();
		return new Coordinate(r, c);
	}
}
