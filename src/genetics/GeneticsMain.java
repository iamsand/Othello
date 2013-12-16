package genetics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import othello.*;

public class GeneticsMain {

	// ----------------------------//
	// -- Modify arguments here. --//
	// -------------V--------------//
	static String	bFileName		= "BlackWeights.txt";
	static String	wFileName		= "WhiteWeights.txt";
	// This is the number of players per side. Actually don't change this for now. Certain things are hardcoded for 10.
	static int		numPlayer		= 10;
	// This is the number of generations you want to simulate. Don't make it too high. For PlayerGenetics depth 3, 10 generations runs in a reasonable time.
	static int		numGenerations	= 3;
	// Gives a chance for the child to mutate. Increases diversity.
	static boolean	mutation			= true;
	static double	mutationThresh	= .10;
	// Self explanatory. Keep this true if running for > 1 generation just so you know where the program is during run.
	static boolean	DEBUG				= true;

	// Don't need this for now.
	static String	newbFileName	= "newBlackWeights.txt";
	static String	newwFileName	= "newWhiteWeights.txt";

	static PTag[]	bPlayers;
	static PTag[]	wPlayers;

	public static void main(String[] args) {
		// NOTE: the following two lines only need to be called the first time we run the program to create BlackWeights.txt and WhiteWeights.txt.
		// Only do this if you want to replace the learning we've done in the 2 weight txt files with random weights from scratch.
		// GenTxt.print(bFileName, GenTxt.gen(numPlayer));
		// GenTxt.print(wFileName, GenTxt.gen(numPlayer));

		// Run simulation n times.
		for (int i = 0; i < numGenerations; i++) {
			if (DEBUG)
				System.out.println("Generation " + i);
			initSimulation();
			runForOneGen();
			GenTxt.print(bFileName, GenTxt.normalize(breed(Player.BLACK)));
			GenTxt.print(wFileName, GenTxt.normalize(breed(Player.WHITE)));
		}
	}

	public static void initSimulation() {
		bPlayers = new PTag[numPlayer];
		wPlayers = new PTag[numPlayer];
		initHelper(Player.BLACK);
		initHelper(Player.WHITE);
	}

	public static void initHelper(Player p) {
		Scanner sc = null;
		File fileName = null;
		if (p == Player.BLACK)
			fileName = new File(bFileName);
		else
			fileName = new File(wFileName);

		try {
			sc = new Scanner(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("Failed to read file. Please debug.");
			System.exit(0);
		}

		for (int i = 0; i < numPlayer; i++) {
			sc.nextInt();
			double[][] weights1 = new double[8][8];
			double[][] weights2 = new double[8][8];
			for (int w = 0; w < 2; w++) {
				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 8; c++) {
						if (w == 0)
							weights1[r][c] = sc.nextDouble();
						else
							weights2[r][c] = sc.nextDouble();
					}
				}
			}
			if (p == Player.BLACK) {
				bPlayers[i] = new PTag(new PlayerGenetics(), i);
				bPlayers[i].player.initWeights(weights1, weights2);
			} else {
				wPlayers[i] = new PTag(new PlayerGenetics(), i);
				wPlayers[i].player.initWeights(weights1, weights2);
			}
		}
		sc.close();
	}

	public static void runForOneGen() {
		for (int p1Index = 0; p1Index < numPlayer; p1Index++) {
			for (int p2Index = 0; p2Index < numPlayer; p2Index++) {
				if (DEBUG)
					System.out.println(p1Index + " " + p2Index);
				IPlayer[] players = { bPlayers[p1Index].player, wPlayers[p2Index].player };
				Game g = new Game(8, false, players);
				g.run();
				if (g.getBoard().getWinner() == Player.BLACK)
					bPlayers[p1Index].score++;
				else
					wPlayers[p2Index].score++;
			}
		}

	}

	public static double[][][][] breed(Player p) {
		Arrays.sort(bPlayers);
		Arrays.sort(wPlayers);
		double[][][][] d = new double[numPlayer][2][8][8];
		int counter = 0;
		for (int i = 0; i < 5; i++) {
			for (int j = i + 1; j < 5; j++) {
				double[][] tmp1;
				double[][] tmp2;
				if (p == Player.BLACK) {
					tmp1 = breedTwo(bPlayers[i].player.weights1, bPlayers[j].player.weights1);
					tmp2 = breedTwo(bPlayers[i].player.weights2, bPlayers[j].player.weights2);
				} else {
					tmp1 = breedTwo(wPlayers[i].player.weights1, wPlayers[j].player.weights1);
					tmp2 = breedTwo(wPlayers[i].player.weights2, wPlayers[j].player.weights2);
				}
				d[counter][0] = tmp1;
				d[counter][1] = tmp2;
				counter++;
			}
		}
		return d;
	}

	// Don't worry about normalizing here. Do it later before we print.
	public static double[][] breedTwo(double[][] a, double[][] b) {
		double[][] child = new double[8][8];
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++) {
				child[r][c] = (a[r][c] + b[r][c]) / 2;
				if (mutation && Math.random() < mutationThresh) {
					child[r][c] += Math.random() / 2;
				}
			}
		return child;
	}

	// This provides a wrapper for PlayerGenetics. We do this so that we can add misc information including ID and score.
	// TODO: Is there a better way to do this?
	static class PTag implements Comparable<PTag> {
		public int					score;
		public PlayerGenetics	player;
		public int					ID;

		public PTag(PlayerGenetics player, int ID) {
			this.player = player;
			this.ID = ID;
			score = 0;
		}

		@Override
		public int compareTo(PTag player) {
			if (this.score > player.score)
				return -1;
			if (this.score < player.score)
				return 1;
			return 0;
		}

		@Override
		public String toString() {
			return "[" + ID + ", " + score + "]";
		}
	}
}
