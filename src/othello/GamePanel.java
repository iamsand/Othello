package othello;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import player.PlayerHuman;

public class GamePanel extends JPanel implements Runnable, MouseListener {
	private static final long	serialVersionUID		= 1L;

	private static final int	PWIDTH					= 800;
	private static final int	PHEIGHT					= 800;
	private static final int	BORDER					= 100;
	private volatile boolean	running					= false;
	private volatile boolean	gameOver					= false;
	private Graphics				g;
	private Image					dbImage					= null;
	private static final int	NO_DELAYS_PER_YIELD	= 16;
	private static int			MAX_FRAME_SKIPS		= 5;
	private long					gameStartTime;
	private Thread					animator;
	private int						period;

	private IPlayer[]				players					= new IPlayer[2];
	private int						SIZE;
	private Board					board;
	private Player					p;
	private Coordinate			playerMove;

	public GamePanel(OthelloMain om, int period, IPlayer[] players, int SIZE) {
		this.period = period;
		this.players = players;
		this.SIZE = SIZE;
		board = new Board(SIZE);
		p = Player.BLACK;
		players[0].startNewGame(board, Player.BLACK);
		players[1].startNewGame(board, Player.WHITE);

		setBackground(Color.white);
		setPreferredSize(new Dimension(PWIDTH, PHEIGHT));

		setFocusable(true);
		requestFocus();
		readyForTermination();

		addMouseListener(this);
	}

	private void gameUpdate() {
		if (board.isGameOver())
			gameOver = true;
		if (!gameOver) { // if game is still playing
			if (!board.canMove(p))
				p = p.switchPlayer();
			if (players[p.intValue()] instanceof PlayerHuman) {
				if (!board.isLegalMove(playerMove, p))
					return;
				board.makeMove(playerMove, p);
				playerMove = null;
			} else
				board.makeMove(players[p.intValue()].move(), p);

			p = p.switchPlayer();
		}
	}

	private void gameRender() {
		if (dbImage == null) {
			dbImage = createImage(PWIDTH, PHEIGHT);
			g = dbImage.getGraphics();
		}

		// clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, PWIDTH, PHEIGHT);

		// green board
		g.setColor(new Color(62, 156, 76));
		g.fillRect(BORDER, BORDER, PWIDTH - 2 * BORDER, PHEIGHT - 2 * BORDER);

		// draw grid lines
		g.setColor(Color.BLACK);
		for (int i = 0; i <= SIZE; i++)
			g.drawLine(BORDER, BORDER + (PHEIGHT - 2 * BORDER) * i / SIZE, PWIDTH - BORDER, BORDER + (PHEIGHT - 2 * BORDER) * i / SIZE);
		for (int i = 0; i <= SIZE; i++)
			g.drawLine(BORDER + (PWIDTH - 2 * BORDER) * i / SIZE, BORDER, BORDER + (PWIDTH - 2 * BORDER) * i / SIZE, PHEIGHT - BORDER);

		// draw discs
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++) {
				Disc d = board.getDisc(new Coordinate(i, j));
				if (d != Disc.EMPTY) {

					int centerx = (int) (BORDER + (i + 0.5) * (PWIDTH - 2 * BORDER) / SIZE);
					int centery = (int) (BORDER + (j + 0.5) * (PWIDTH - 2 * BORDER) / SIZE);

					if (d == Disc.BLACK)
						g.setColor(Color.BLACK);
					else
						g.setColor(Color.WHITE);
					int r = (PWIDTH - 2 * BORDER) / SIZE / 3;
					g.fillOval(centerx - r, centery - r, 2 * r, 2 * r);

				}
			}

		// draw score
		g.setColor(Color.BLACK);
		g.drawString("Black: " + board.getDiscs(Player.BLACK), 10, 20);
		g.drawString("White: " + board.getDiscs(Player.WHITE), 10, 40);

		if (gameOver)
			gameOverMessage(g);
	}

	private void readyForTermination() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_Q)
					running = false;
			}
		});
	}

	public void addNotify() {
		super.addNotify();
		startGame();
	}

	private void startGame() {
		if (animator == null || !running) {
			animator = new Thread(this);
			animator.start();
		}
	}

	private void gameOverMessage(Graphics g) {
		String msg = "GAME OVER";

		Font font = new Font("SansSerif", Font.BOLD, 100);
		FontMetrics metrics = this.getFontMetrics(font);
		int x = (PWIDTH - metrics.stringWidth(msg)) / 2;
		int y = (PHEIGHT + metrics.getHeight()) / 2;

		g.setFont(font);
		g.setColor(Color.red);
		g.drawString(msg, x, y);

		g.setFont(new JLabel().getFont());
	}

	public void run() {
		long beforeTime, afterTime, timeDiff, sleepTime;
		int overSleepTime = 0;
		int noDelays = 0;
		int excess = 0;

		gameStartTime = System.currentTimeMillis();
		beforeTime = gameStartTime;

		running = true;

		while (running) {
			gameUpdate();
			gameRender();
			paintScreen();

			afterTime = System.currentTimeMillis();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			if (sleepTime > 0) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException ex) {
				}
				overSleepTime = (int) ((System.currentTimeMillis() - afterTime) - sleepTime);
			} else {
				excess -= sleepTime;
				overSleepTime = 0;

				if (++noDelays >= NO_DELAYS_PER_YIELD) {
					Thread.yield();
					noDelays = 0;
				}
			}

			beforeTime = System.currentTimeMillis();

			int skips = 0;
			while ((excess > period) && (skips < MAX_FRAME_SKIPS)) {
				excess -= period;
				gameUpdate();
				skips++;
			}
		}
		System.exit(0);
	}

	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();

			g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics error: " + e);
		}
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (x > BORDER && x < PWIDTH - BORDER && y > BORDER && y < PHEIGHT - BORDER) {
			int i = (x - BORDER) * SIZE / (PWIDTH - 2 * BORDER);
			int j = (y - BORDER) * SIZE / (PHEIGHT - 2 * BORDER);
			playerMove = new Coordinate(i, j);
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
