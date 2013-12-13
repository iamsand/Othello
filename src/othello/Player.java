package othello;

public enum Player {
	WHITE, BLACK;
	
	public Player switchPlayer() {
		if (this == Player.BLACK)
			return Player.WHITE;
		return Player.BLACK;
	}
	
	public int intValue() {
		if (this == Player.BLACK)
			return 0;
		return 1;
	}
	
	public Disc toDisc() {
		if (this == Player.BLACK)
			return Disc.WHITE;
		return Disc.BLACK;
	}
}
