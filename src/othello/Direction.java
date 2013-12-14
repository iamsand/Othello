package othello;

/**
 * An instance represents one of the eight cardinal directions. This class is made to allow easy access to all adjacent tiles of a certain tile.
 */
public enum Direction {
	N(-1, +0), NE(-1, +1), E(+0, +1), SE(+1, +1), S(+1, +0), SW(+1, -1), W(+0, -1), NW(-1, -1);

	private final int	dRow;
	private final int	dCol;

	private Direction(int dRow, int dCol) {
		this.dRow = dRow;
		this.dCol = dCol;
	}

	public int getDRow() {
		return dRow;
	}

	public int getDCol() {
		return dCol;
	}
}
