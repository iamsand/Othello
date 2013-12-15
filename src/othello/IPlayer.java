package othello;

/**
 * Represents a player that uses the state information of a grid to take actions and play a game of Othello.
 */
public interface IPlayer {

	/**
	 * A variable p for consistency will store the color of the player using the enum Player.
	 */
	public Player getPlayerColor();

	/**
	 * Allows the player to initialize each new game, providing it with a reference to the board to be used throughout the game. We do this because the size of
	 * the board can change.
	 * 
	 * @param board
	 */
	public void startNewGame(Board board, Player p);

	/**
	 * Determines the next action the player will take during a game.
	 * 
	 * @return
	 */
	public Coordinate move();

	public String toString();

	/**
	 * Allows the player to do something when the game is over. Used for more sophisticated learning AI.
	 */
	public void endOfGameAnal();

}
