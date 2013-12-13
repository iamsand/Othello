package othello;

/** An instance represents a pair of integer coordinates. */
public class Coordinate {

	private final int	row;
	private final int	col;

	public Coordinate(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Coordinate move(Direction d) {
		return new Coordinate(row + d.getDRow(), col + d.getDCol());
	}
	
	public @Override
	String toString() {
		return "(" + row + ", " + col + ")";
	}

	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
