package othello;

import player.*;

public class OthelloMain {
	public static void main(String[] args) {
		Game g = new Game(8, true);

		IPlayer[] players = new IPlayer[2];
		// Add the two players here. Player[0] goes first.
		players[0] = new PlayerGreed();
		players[1] = new PlayerGreed();

		g.run(players);
	}
}
