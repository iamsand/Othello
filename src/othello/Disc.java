package othello;

public enum Disc {
	WHITE(1), BLACK(-1);
	
	public final int value;
	
	Disc(int n){
		this.value = n;
	}
}
