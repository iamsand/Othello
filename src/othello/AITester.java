package othello;

import java.util.ArrayList;

/**
 * This is a testing class which allows us to set up arbitrary arrangements of 8x8 boards and make the AI play a move.
 */
public class AITester {

	// I did it like this so that it's easy to view. Using a 2D array will screw up formatting.
	// The convention is 0 for black, 1 for white, and -1 for empty.
	static Board		board;
	static Integer[]	l0		= { 1, 1, 1, 1, 1, 1, -1, -1 };
	static Integer[]	l1		= { 1, 1, 0, 1, 1, 1, -1, 0 };
	static Integer[]	l2		= { 0, 0, 0, 1, 1, 1, 1, 0 };
	static Integer[]	l3		= { 0, 0, 0, 0, 0, 0, 0, 0 };
	static Integer[]	l4		= { 0, 0, 1, 0, 0, 1, 0, 0 };
	static Integer[]	l5		= { 0, 1, 0, 0, 1, 0, 0, 0 };
	static Integer[]	l6		= { 0, 0, 0, 1, 1, 1, 0, 0 };
	static Integer[]	l7		= { 0, 0, 0, 0, 0, 0, 0, 0 };
	static Player		color	= Player.BLACK;

	public static void init() {
		board = new Board(8);
		ArrayList<Integer[]> tst = new ArrayList<Integer[]>();
		tst.add(l0);
		tst.add(l1);
		tst.add(l2);
		tst.add(l3);
		tst.add(l4);
		tst.add(l5);
		tst.add(l6);
		tst.add(l7);
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				Disc d = null;
				switch (tst.get(r)[c]) {
					case -1:
						d = Disc.EMPTY;
						break;
					case 0:
						d = Disc.BLACK;
						break;
					case 1:
						d = Disc.WHITE;
						break;
				}
				board.setDisc(new Coordinate(r, c), d);
			}
		}
	}

	// Call this method and pass it the AI you wish to test.
	public static void test(IPlayer player) {
		init();
		board.printBoard();

		player.startNewGame(board, color);
		Coordinate move = player.move();

		if (!board.isLegalMove(move, color)) {
			System.out.println("Illegal Move " + move);
			System.exit(0);
		}

		board.makeMove(move, color);
		board.printBoard();
	}

}
