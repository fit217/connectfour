package players;
import game.ConnectFourBoard;
import game.GamePiece;
import game.GameRuleViolation;
import game.Move;
import game.Player;
import gui.ConnectFourGUI;
import heuristics.Estimate;





/**
 * @author chase
 */
public class minimaxPlayer extends Player {
	private ConnectFourGUI gui_;// GUI


	/**
	 * Create a default minimax player.
	 * 
	 * @param piece
	 *          the game piece the player will use
	 * @param gui
	 *          the GUI for getting the player's move
	 */
	public minimaxPlayer ( GamePiece piece, ConnectFourGUI gui ) {

		super(piece,Long.MAX_VALUE);
		gui_ = gui;

	}

	/**
	 * Create a minimax player with a specific timeout.
	 * 
	 * @param piece
	 *          the game piece the player will use
	 * @param gui
	 *          the GUI for getting the player's move
	 * @param timeOut
	 *          the desired time for a move to be made
	 */
	public minimaxPlayer ( GamePiece piece, ConnectFourGUI gui, Long timeOut ) {
		super(piece,timeOut);
		gui_ = gui;

	}

	/*
	 * (non-Javadoc)
	 * @see game.Player#chooseMove(game.ConnectFourBoard)
	 */
	@Override
	public void chooseMove ( ConnectFourBoard board ) {
		reset();// resets move and stop at the start of the turn
		State best = miniMax(board);
		move_ = new Move(piece_,best.getMove(),false);

	}

	public State miniMax (ConnectFourBoard board ){
		int depth = 1;
		State best = new State(3,board);
		while (!stop_ ) {
			best = maxValue(best,0,depth);
			if(new Estimate(this).hs(board,piece_,best.getMove()) >= 4096) break;
			depth++;
		}
		System.out.println("miniMax depth: " + depth);
		return  best;

	}

	private State maxValue ( State state, int level, int depth ) {
		if(level == depth || state.getBoard().getWinner()!=GamePiece.NONE) {
			return state;
		}
		State max = state;
		double v = Double.NEGATIVE_INFINITY;

		for ( int i = 0 ; i < 7 ; i++ ) {
			ConnectFourBoard temp = state.getBoard().copy();
			try {
				temp.drop(piece_.other(),i);
			} catch ( GameRuleViolation e ) {
				// TODO Auto-generated catch block
				//System.out.println("col " + i + " is full");
				continue;
			}
			State newState = minValue(new State(i,temp),level+1,depth);

			double comp = new Estimate(this).hs(newState.getBoard(),piece_,newState.getMove());
			if(v < comp) {
				v= comp;
				max = newState;
			}
			//System.out.println("V is currently " + v);
			if(v >= 4096) System.out.println("Winning move on level " + level + " at position " + max.getMove());
			
		}

		 return max;
	}

	private State minValue (State  state, int level, int depth ) {
		if(level == depth || state.getBoard().getWinner()!=GamePiece.NONE) {
			return state;
		}
		State min = state;
		double v = Double.POSITIVE_INFINITY;
		for(int i = 0; i < 7; i++) {
			ConnectFourBoard temp = state.getBoard().copy();
			try {
				temp.drop(piece_,i);
			} catch ( GameRuleViolation e ) {
				//System.out.println("col " + i + " is full");
				continue;
			}
			State newState = maxValue(new State(i,temp),level+1,depth);
			double comp = new Estimate(this).hs(newState.getBoard(),piece_,newState.getMove());
			if(v > comp) {
				v= comp;
				min = newState;
			}	
		}
		return min;
	}

}
