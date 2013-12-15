package othello;

import java.awt.Container;
import javax.swing.JFrame;
import player.*;

public class OthelloMain extends JFrame {
	
	// ----------------------------//
	// -- Modify arguments here. --//
	// -------------V--------------//
	private static IPlayer[] players = new IPlayer[2];
	private static final int SIZE = 8;
	private static final boolean PRINT = true;
	private static int DEFAULT_FPS = 80;

	public OthelloMain(int period) {
		super("Othello");
		Container c = getContentPane();
		GamePanel gp = new GamePanel(this, period, players, SIZE);
		c.add(gp, "Center");
		pack();
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		players[1] = new PlayerGreedier();
		players[0] = new PlayerWeight();

		// runOnce();
		
		runMultiple(100);
		// int period = (int) (1000.0 / DEFAULT_FPS);
		// new OthelloMain(period);
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
