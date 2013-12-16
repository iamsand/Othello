package genetics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import othello.*;

public class GeneticsMain {

	// ----------------------------//
	// -- Modify arguments here. --//
	// -------------V--------------//
	static String				bFileName	= "BlackWeights.txt";
	static String				wFileName	= "WhiteWeights.txt";
	// This is the number of players per side.
	static int					numPlayer	= 20;

	static PlayerGenetics[]	bPlayers;
	static int[]				bscore;
	static PlayerGenetics[]	wPlayers;
	static int[]				wscore;

	public static void main(String[] args) {
		GenTxt.gen(bFileName, 20);
		GenTxt.gen(wFileName, 20);
		initSimulation();
		runForOneGen();
		breed();
		printNewWeights();
	}

	// This code is really bad because it duplicates like everything. I will fix it.
	public static void initSimulation() {
		bPlayers = new PlayerGenetics[numPlayer];
		wPlayers = new PlayerGenetics[numPlayer];

		// Initializing for black.
		Scanner sc = null;
		try {
			File fileName = new File(bFileName);
			sc = new Scanner(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("initSimulation has thrown an exception while trying to parse black input file. Please debug.");
			System.exit(0);
		}

		for (int i = 0; i < numPlayer; i++) {
			sc.nextInt();
			bPlayers[i] = new PlayerGenetics();
			double[][] weights1 = new double[8][8];
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					weights1[r][c] = sc.nextDouble();
				}
			}
			double[][] weights2 = new double[8][8];
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					weights2[r][c] = sc.nextDouble();
				}
			}
			bPlayers[i].initWeights(weights1, weights2);
		}
		sc.close();

		// Initializing for white.
		Scanner sc2 = null;
		try {
			File fileName = new File(wFileName);
			sc2 = new Scanner(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("initSimulation has thrown an exception while trying to parse white input file. Please debug.");
			System.exit(0);
		}

		for (int i = 0; i < numPlayer; i++) {
			sc2.nextInt();
			wPlayers[i] = new PlayerGenetics();
			double[][] weights1 = new double[8][8];
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					weights1[r][c] = sc2.nextDouble();
				}
			}
			double[][] weights2 = new double[8][8];
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					weights2[r][c] = sc2.nextDouble();
				}
			}
			wPlayers[i].initWeights(weights1, weights2);
		}
		sc2.close();
	}

	public static void runForOneGen() {
		for (int p1Index = 0; p1Index < numPlayer; p1Index++) {
			for (int p2Index = 0; p2Index < numPlayer; p2Index++) {
				IPlayer[] players = { bPlayers[p1Index], wPlayers[p2Index] };
				Game g = new Game(8, false, players);
				g.run();
				if (g.getBoard().getWinner() == Player.BLACK)
					bscore[p1Index]++;
				else
					wscore[p2Index]++;
			}
		}
	}

	public static void breed() {

	}

	public static void printNewWeights() {

	}

	public static class PlayerScore implements Comparable<PlayerScore> {
		public int		score;
		public IPlayer	player;

		public PlayerScore(int score, IPlayer player) {
			this.score = score;
			this.player = player;
		}

		// Do I have this backwards? Need to check. 
		@Override
		public int compareTo(PlayerScore player) {
			if (this.score > player.score)
				return -1;
			if (this.score < player.score)
				return 1;
			return 0;
		}
	}
}
