package players;
import java.util.ArrayList;

import game.ConnectFourBoard;
import game.GamePiece;
import game.Player;
import game.GameRuleViolation;
import game.Move;
import gui.ConnectFourGUI;
import heuristics.Estimate;

/**
 * @author Jonko
 *
 */
public class LimitedMM extends Player{
	private ConnectFourGUI gui_;// GUI
	private int depth_;
	/**
	 * @param piece
	 * @param timeout
	 */
	public LimitedMM ( GamePiece piece, ConnectFourGUI gui,long timeout,int depth ) {
		super(piece,Long.MAX_VALUE);
		gui_ = gui;
		depth_ = Integer.max(1,depth);
	}
	@Override
	public void chooseMove ( ConnectFourBoard board) {
		reset();// resets move and stop at the start of the turn
		State best = miniMax(board);
		move_ = new Move(piece_,best.getMove(),false);
	}

	public State miniMax (ConnectFourBoard board ){
		int depth = 1;
		State best = new State(3,board);
		while (!stop_) {
			best = maxValue(best,0,depth);
			if(depth_ == depth) {
				break;
			}else {
			depth++;
			}
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
				temp.drop(piece_.other(),i);
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
	/* (non-Javadoc)
	 * @see game.Player#chooseMove(game.ConnectFourBoard)
	 */


}
