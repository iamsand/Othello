package othello;

import player.*;

public class OthelloMain {

	public static void main(String[] args) {

		IPlayer[] players = new IPlayer[2];

		// ----------------------------//
		// -- Modify arguments here. --//
		// -------------V--------------//
		final int SIZE = 8;
		final boolean PRINT = true;
		players[0] = new PlayerGreed();
		players[1] = new PlayerRandom();

		Game g = new Game(SIZE, PRINT, players);
		g.run();
		g.printResult();
	}
}
