package othello;

import player.*;

public class OthelloMain {
	
	// ----------------------------//
	// -- Modify arguments here. --//
	// -------------V--------------//
	public static IPlayer[] players = new IPlayer[2];
	public static final int SIZE = 8;
	public static final boolean PRINT = true;

	public static void main(String[] args) {

		players[0] = new PlayerGreed();
		players[1] = new PlayerGreedier();

		runMultiple(1000);
	}
	
	public static void runOnce() {
		Game g = new Game(SIZE, PRINT, players);
		g.run();
		g.printResult();
	}
	
	public static void runMultiple(int times) {
		int[] score = new int[3];
		for (int i = 0; i < times; i++) {
			Game g = new Game(SIZE, false, players);
			g.run();
			Player winner = g.getBoard().getWinner();
			if (winner == Player.BLACK)
				score[0]++;
			if (winner == null)
				score[1]++;
			if (winner == Player.WHITE)
				score[2]++;
		}
		int space = 10;
		System.out.println(fillR("Black",space)+fillR("Tie",space)+fillR("White",space));
		System.out.println(fillR(""+score[0],space)+fillR(""+score[1],space)+fillR(""+score[2],space));
	}
	
	public static String fillR(String str, int len) {
		while (str.length() < len)
			str += " ";
		return str;
	}
}
