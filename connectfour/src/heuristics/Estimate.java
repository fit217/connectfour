package heuristics;

import game.ConnectFourBoard;
import game.EvalHeuristic;
import game.GamePiece;

/**
 * @author chase
 */
public class Estimate implements EvalHeuristic{

	/* (non-Javadoc)
	 * @see game.EvalHeuristic#h(game.ConnectFourBoard, game.GamePiece)
	 */
	@Override
	public double h ( ConnectFourBoard board, GamePiece player ) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see game.EvalHeuristic#max()
	 */
	@Override
	public double max () {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see game.EvalHeuristic#min()
	 */
	@Override
	public double min () {
		// TODO Auto-generated method stub
		return 0;
	}
	// One possibility for a heuristic is to check every group of four consecutive
	// squares (rows, columns, and diagonals). A group scores 0 if it contains at
	// least one of the other player's pieces, 1 if it contains one of the
	// player's pieces, 4 if it contains two, 32 if it contains three, and 4096 if
	// it contains four (i.e. a victory for the current player). The total score
	// of the board is the sum of the group scores.

	// A second possibilty for a heuristic is to evaluate the advantage that one
	// player has over the other on a given board. For this, use a heuristic such
	// as the one described in the previous paragraph and compute
	// hplayer(n)-hother(n) i.e. the difference between the quality of the board
	// for the player and the quality of the board for the other player.
}
