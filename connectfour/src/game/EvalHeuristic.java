package game;

import game.ConnectFourBoard;
import game.GamePiece;

/**
 * Board position evaluation (state utility) heuristic.
 * 
 * @author ssb
 */
public interface EvalHeuristic {

	/**
	 * Evaluate the board from the perspective of the specified player.
	 * 
	 * @param board
	 *          the game board
	 * @param player
	 *          the player
	 * @return the quality of the game board from the perspective of the player
	 */
	public double h ( ConnectFourBoard board, GamePiece player );

	/**
	 * Get the maximum value possible with this heuristic.
	 * 
	 * @return max value
	 */
	public double max ();

	/**
	 * Get the minimum value possible with this heuristic.
	 * 
	 * @return min value
	 */
	public double min ();

	
}
