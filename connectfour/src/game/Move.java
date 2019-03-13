package game;

/**
 * A move - a game piece and the column where it is to be played.
 * 
 * @author ssb
 */
public class Move {

	private GamePiece piece_; // game piece
	private int col_; // column where piece was dropped
	private boolean exploratory_; // if true, this was an exploratory (non-policy)
	                              // move; otherwise move was based on the
	                              // player's policy

	/**
	 * Create a move.
	 * 
	 * @param piece
	 *          the piece played
	 * @param col
	 *          the column the piece was dropped in
	 * @param exploratory
	 *          if true, this was an exploratory (non-policy) move; if false, this
	 *          move was based on the player's policy
	 */
	public Move ( GamePiece piece, int col, boolean exploratory ) {
		super();
		piece_ = piece;
		col_ = col;
		exploratory_ = exploratory;
	}

	/**
	 * Get the column the piece was dropped in.
	 * 
	 * @return the column the piece was dropped in
	 */
	public int getCol () {
		return col_;
	}

	/**
	 * Get the piece played.
	 * 
	 * @return the piece played
	 */
	public GamePiece getPiece () {
		return piece_;
	}

	/**
	 * Was the move exploratory (not on policy) or based on the player's policy?
	 * 
	 * @return true if the move was exploratory (non-policy), false if it was
	 *         based on the player's policy
	 */
	public boolean isExploratory () {
		return exploratory_;
	}

}
