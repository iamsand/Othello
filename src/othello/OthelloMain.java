package othello;

import player.*;

public class OthelloMain {
	public static void main(String[] args) {
		Game g = new Game(8, true);

		IPlayer[] players = new IPlayer[2];
		// Add the two players here. Player[0] goes first.
<<<<<<< HEAD
		players[0] = new PlayerHuman();
		players[1] = new PlayerHuman();
=======
		players[0] = new PlayerGreed();
		players[1] = new PlayerGreed();
>>>>>>> 7fcf83145c068bbc80a2df55ea9509c664dd107d

		g.run(players);
	}
}
