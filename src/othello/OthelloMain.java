package othello;

import java.awt.Container;
import javax.swing.JFrame;
import player.*;

public class OthelloMain extends JFrame {
	private static final long		serialVersionUID	= 1L;

	// ----------------------------//
	// -- Modify arguments here. --//
	// -------------V--------------//
	private static IPlayer[]		players				= { new PlayerGreedDepth(), new PlayerGreed() };
	private static final int		SIZE					= 8;
	private static final boolean	PRINT					= true;
	private static int				DEFAULT_FPS			= 80;

	public static void main(String[] args) {
		// AITester.test(new PlayerGreedDepth());
		runOnce();
		// runMultiple(10);
		// runWithGUI();
	}

	public static void runWithGUI() {
		int period = (int) (1000.0 / DEFAULT_FPS);
		new OthelloMain(period);
	}

	public OthelloMain(int period) {
		super("Othello");
		Container c = getContentPane();
		GamePanel gp = new GamePanel(this, period, players, SIZE);
		c.add(gp, "Center");
		pack();
		setResizable(false);
		setVisible(true);
	}

	public static void runOnce() {
		Game g = new Game(SIZE, PRINT, players);
		g.run();
		g.printResult();
	}

	public static void runMultiple(int times) {
		int[] score = new int[3];
		for (int i = 0; i < times; i++) {
			System.out.println(i);
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
		System.out.println(String.format("%-10s%-10s%-10s", "Black", "Tie", "White"));
		System.out.println(String.format("%-10s%-10s%-10s", score[0], score[1], score[2]));
	}
}
