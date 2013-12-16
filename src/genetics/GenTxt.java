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

	// Normalize makes the entries sum to 64.
	public static double[][] normalize(double[][] d) {
		double sum = 0;
		for (int r = 0; r < d.length; r++)
			for (int c = 0; c < d.length; c++)
				sum += d[r][c];

		double[][] daa = new double[d.length][d.length];
		for (int r = 0; r < daa.length; r++)
			for (int c = 0; c < daa.length; c++)
				daa[r][c] = d[r][c] / sum * 64;
		return daa;
	}

	/**
	 * The method prints 2*n 8x8 arrays. The idea is that the first array is the evaluation for your pieces. The second is the evaluation for your opponent's
	 * pieces.
	 * 
	 * @param n
	 */
	public static void gen(String s, int n) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(s)));
		} catch (IOException e) {
			System.out.println("GenWeights has thrown an exception trying to output. Please debug.");
			System.exit(0);
		}

		// This is very ugly. I will fix it later.
		for (int i = 0; i < n; i++) {
			double[][] tmp1 = new double[8][8];
			double[][] tmp2 = new double[8][8];
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					tmp1[r][c] = Math.random();
					tmp2[r][c] = Math.random();
				}
			}
			tmp1 = normalize(tmp1);
			tmp2 = normalize(tmp2);
			out.println(i);
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					out.print(tmp1[r][c]);
					if (c != 7)
						out.print(" ");
				}
				out.println();
			}
			out.println();
			for (int r = 0; r < 8; r++) {
				for (int c = 0; c < 8; c++) {
					out.print(tmp2[r][c]);
					if (c != 7)
						out.print(" ");
				}
				out.println();
			}
			out.println();

		}
		out.close();
	}
}
