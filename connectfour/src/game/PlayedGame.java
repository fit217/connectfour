package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores a complete history of a played game.
 * 
 * @author ssb
 */
public class PlayedGame {

	private List<Move> moves_; // the moves, in order from the beginning of the
	                           // game
	private ConnectFourBoard end_; // the terminal state of the game

	/**
	 * Load the game from the specified stream.
	 * 
	 * @param in
	 *          stream to read from
	 * @throws GameRuleViolation
	 *           if there is a violation of the game rules when applying the moves
	 * @throws IOException
	 *           if there is a problem reading
	 */
	public PlayedGame ( InputStream in ) throws IOException, GameRuleViolation {
		moves_ = new ArrayList<Move>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String[] line = reader.readLine().split(" ");
		end_ = new ConnectFourBoard(Integer.parseInt(line[0]),
		                            Integer.parseInt(line[1]));

		int size = Integer.parseInt(reader.readLine());
		for ( int ctr = 0 ; ctr < size ; ctr++ ) {
			line = reader.readLine().split(" ");
			GamePiece piece = GamePiece.getInstance(line[0]);
			int col = Integer.parseInt(line[1]);
			boolean exploratory = Boolean.parseBoolean(line[2]);
			moves_.add(new Move(piece,col,exploratory));
			end_.drop(piece,col);
		}
	}

	/**
	 * Create a new played game.
	 * 
	 * @param moves
	 *          the moves made, in order from the beginning of the game
	 * @param end
	 *          the terminal state of the game
	 */
	public PlayedGame ( List<Move> moves, ConnectFourBoard end ) {
		super();
		moves_ = moves;
		end_ = end;
	}

	/**
	 * Get the terminal state of the game.
	 * 
	 * @return the terminal state of the game
	 */
	public ConnectFourBoard getEnd () {
		return end_;
	}

	/**
	 * Get the moves made.
	 * 
	 * @return the moves made, in order from the beginning of the game
	 */
	public List<Move> getMoves () {
		return moves_;
	}

	/**
	 * Get the winner of the game.
	 * 
	 * @return game winner
	 */
	public GamePiece getWinner () {
		return end_.getWinner();
	}

	/**
	 * Save the game to the specified stream.
	 * 
	 * @param out
	 *          stream to write game to
	 */
	public void save ( OutputStream out ) {
		// just save the moves - rest can be figured out
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
		writer.println(end_.getNumRows() + " " + end_.getNumCols());
		writer.println(moves_.size());
		for ( Move move : moves_ ) {
			writer.println(move.getPiece() + " " + move.getCol() + " "
			    + move.isExploratory());
		}
		writer.flush();
	}
}
