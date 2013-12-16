package genetics;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This is a class which sets up our simulation. It creates 2*n random board where every set of 2 boards is to be used by 1 AI.
 * 
 * @author sand
 * 
 */
public class GenTxt {

	// Note that for the arrays: dim 1 represents player, dim 2 represents first array or second, dim 3 and 4 are dimensions.

	// Normalize() makes the entries sum to 64.
	public static double[][][][] normalize(double[][][][] d) {
		for (int i = 0; i < d.length; i++)
			for (int w = 0; w < 2; w++) {
				double sum = 0;
				for (int r = 0; r < 8; r++)
					for (int c = 0; c < 8; c++) {
						sum += d[i][w][r][c];
					}
				for (int r = 0; r < 8; r++)
					for (int c = 0; c < 8; c++) {
						d[i][w][r][c] = d[i][w][r][c] / sum * 64;
					}
			}

		return d;
	}

	/**
	 * The method prints our weights in d to a file with title s.
	 * 
	 * @param s
	 * @param d
	 */
	public static void print(String s, double[][][][] d) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(s)));
		} catch (IOException e) {
			System.out.println("GenWeights has thrown an exception trying to initialize. Please debug.");
			System.exit(0);
		}

		for (int i = 0; i < d.length; i++) {
			out.println(i);
			for (int w = 0; w < 2; w++) {
				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 8; c++) {
						out.print(d[i][w][r][c]);
						if (c != 7)
							out.print(" ");
					}
					out.println();
				}
				out.println();
			}
		}
		out.close();
	}

	/**
	 * The method generates 2*n sets of random weights for the n players.
	 * 
	 * @param n
	 * @return
	 */
	public static double[][][][] gen(int n) {
		double[][][][] d = new double[n][2][8][8];
		for (int i = 0; i < n; i++) {
			for (int w = 0; w < 2; w++) {
				for (int r = 0; r < 8; r++) {
					for (int c = 0; c < 8; c++) {
						d[i][w][r][c] = Math.random();
					}
				}
			}
		}
		d = normalize(d);
		return d;
	}
}
