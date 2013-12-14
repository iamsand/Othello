package player;

import java.util.Scanner;
import java.util.StringTokenizer;
import othello.Board;
import othello.Coordinate;
import othello.IPlayer;
import othello.Player;

// This is a class that allows a human to play.
public class PlayerHuman implements IPlayer {

	Board	board;
	Player 	p;

	@Override
	public void startNewGame(Board board, Player p) {
		this.board = board;
		this.p = p;
	}

	@Override
	public Coordinate move() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter coordinates r c:");
		StringTokenizer st = new StringTokenizer(sc.nextLine());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		sc.close();
		return new Coordinate(r, c);
	}
}
