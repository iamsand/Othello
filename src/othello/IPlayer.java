package othello;

/**
 * Represents a player that uses the state information of a grid to take actions and play a game of Othello.
 * 
 * @author sand
 */
public interface IPlayer {

	/**
	 * Allows the player to initialize each new game, providing it with a reference to the board to be used throughout the game. We do this because the size of
	 * the board can change.
	 * 
	 * @param board
	 */
	void startNewGame(Board board);

	/**
	 * Determines the next action the player will take during a game.
	 * 
	 * @return
	 */
	public Coordinate move();

}
