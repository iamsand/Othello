package othello;

/** An instance represents a pair of integer coordinates. */
public class Coordinate {

	public final int	row;
	public final int	col;

	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public @Override
	String toString() {
		return "(" + row + ", " + col + ")";
	}

}
