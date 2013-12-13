package othello;
public enum Direction {
	N(-1, +0), NE(-1, +1), E(+0, +1), SE(+1, +1), S(+1, +0), SW(+1, -1), W(+0, -1), NW(-1, -1);

	public final int	dRow;
	public final int	dCol;

	Direction(int dRow, int dCol) {
		this.dRow = dRow;
		this.dCol = dCol;
	}
}
