package player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import othello.Board;
import othello.Coordinate;
import othello.IPlayer;
import othello.Player;

/**
 * An instance of PlayerWeight basically acts like PlayerGreed, except it gives each tile a weight taken from the txt file PlayerWeightInfo. At the end of each
 * game, it updates these weights to reflect the outcome of the game.
 * 
 * Note: this class is only meant to be run on an 8x8 board.
 */
public class PlayerWeight implements IPlayer {

	private boolean		debug	= false;
	private Board			board;
	private Player			p;
	// There is a problem right now. The sum of the values in weights might drift after a large number of games. Fix this for future.
	private double[][]	weights;
	// This is the number of games the AI has played. Taken from PlayerWeightInfo.txt.
	private int				numGamesPlayed;

	@Override
	public Player getPlayerColor() {
		return p;
	}

	@Override
	public void startNewGame(Board board, Player p) {
		this.board = board;
		this.p = p;

		Scanner sc = null;
		try {
			File fileName = new File("PlayerWeightInfo.txt");
			sc = new Scanner(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("PlayerWeight has thrown an exception while trying to parse input file. Please debug.");
			System.exit(0);
		}
		numGamesPlayed = sc.nextInt();
		weights = new double[board.getDim()][board.getDim()];
		for (int r = 0; r < board.getDim(); r++)
			for (int c = 0; c < board.getDim(); c++) {
				weights[r][c] = sc.nextDouble();
			}
		sc.close();
	}

	@Override
	public Coordinate move() {
		ArrayList<Coordinate> allMoves = board.allLegalMoves(p);
		ArrayList<Coordinate> bestMoves = new ArrayList<Coordinate>();
		double maxNet = Double.NEGATIVE_INFINITY;

		for (Coordinate c : allMoves) {
			Board clone = board.clone();
			clone.makeMove(c, p);
			double net = 0;
			for (int r = 0; r < board.getDim(); r++) {
				for (int col = 0; col < board.getDim(); col++) {
					if (clone.getDisc(new Coordinate(r, col)) == p.toDisc()) {
						net += weights[r][col];
					}
				}
			}
			if (debug)
				System.out.println("Move " + c + " gives net " + net);

			if (net > maxNet)
				bestMoves.clear();
			if (net >= maxNet) {
				bestMoves.add(c);
				maxNet = net;
			}
		}

		return bestMoves.get((int) (Math.random() * bestMoves.size()));
	}

	@Override
	public String toString() {
		return "PlayerWeight";
	}

	public double[][] normalize(double[][] d) {
		DecimalFormat df = new DecimalFormat("#.######");
		double sum = 0;
		for (int r = 0; r < d.length; r++)
			for (int c = 0; c < d.length; c++)
				sum += d[r][c];

		double[][] daa = new double[d.length][d.length];
		for (int r = 0; r < daa.length; r++)
			for (int c = 0; c < daa.length; c++)
				daa[r][c] = Double.parseDouble(df.format(d[r][c] / sum));
		return daa;
	}

	public void debugPrint(double[][] daa) {
		for (int r = 0; r < daa.length; r++) {
			for (int c = 0; c < daa.length; c++) {
				System.out.print(daa[r][c] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	@Override
	public void endOfGameEval() {
		if (debug) {
			System.out.println("Begin Analysis.");
			System.out.println("Old Weights");
			debugPrint(weights);
		}
		boolean win = p == board.getWinner();
		double[][] newWeights = new double[board.getDim()][board.getDim()];

		for (int r = 0; r < board.getDim(); r++) {
			for (int c = 0; c < board.getDim(); c++) {
				// This can obviously be condensed. I will leave it for now for testing.
				newWeights[r][c] = weights[r][c];
				if (win) {
					if (board.getDisc(new Coordinate(r, c)) == p.toDisc()) {
						newWeights[r][c] += 1.0 / (numGamesPlayed + 1000);
					} else {
						// newWeights[r][c] -= .10 / (numGamesPlayed + 10);
					}
				} else {
					if (board.getDisc(new Coordinate(r, c)) == p.toDisc()) {
						// newWeights[r][c] -= .05 / (numGamesPlayed + 10);
					} else {
						newWeights[r][c] += .25 / (numGamesPlayed + 1000);
					}
				}
			}
		}
		newWeights = normalize(newWeights);
		if (debug) {
			System.out.println("New weights");
			debugPrint(newWeights);
		}
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("PlayerWeightInfo.txt")));
		} catch (IOException e) {
			System.out.println("PlayerWeight has thrown an exception while trying to print output file. Please debug.");
			System.exit(0);
		}

		out.println(numGamesPlayed + 1);
		for (int r = 0; r < board.getDim(); r++) {
			for (int c = 0; c < board.getDim(); c++) {
				out.print(newWeights[r][c]);
				if (c != board.getDim() - 1)
					out.print(" ");
			}
			if (r != board.getDim() - 1)
				out.println();
		}
		out.close();
		if (debug)
			System.out.println("End Analysis.");
	}
}
