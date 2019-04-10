package players;

import game.ConnectFourBoard;
import game.GamePiece;
import game.GameRuleViolation;
import game.Move;
import game.Player;
import gui.ConnectFourGUI;
import heuristics.Estimate;

/**
 * @author Jonko
 *
 */
public class LimitedABP extends Player{
	private ConnectFourGUI gui_;
	private int depth_;
	/**
	 * @param piece
	 * @param timeout
	 */
	public LimitedABP ( GamePiece piece, long timeout,ConnectFourGUI gui, int depth ) {
		super(piece,timeout);
		// TODO Auto-generated constructor stub
		gui_ = gui;
		depth_ = Integer.max(1,depth);
	}
	public LimitedABP ( GamePiece piece, long timeout, int depth ) {
		super(piece,timeout);
		// TODO Auto-generated constructor stub
		depth_ = Integer.max(1,depth);
	}
	@Override
	public void chooseMove ( ConnectFourBoard board ) {
		reset();
		State best = alphaBeta(board);
		move_ = new Move(piece_,best.getMove(),false);
	}
	
	private State alphaBeta(ConnectFourBoard board){
		int depth = 1;
		State best = new State(2,board);
		while(!stop_) {
			best = maxValue( best, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,0,depth);
			if(depth_ == depth) {
				break;
			}else {
			depth++;
			}
		}
		//System.out.println("ABP depth: " + depth);
		return best;
	}
	
	private State maxValue(State state, double alpha, double beta, int level, int depth) {
		if(level == depth || state.getBoard().getWinner()!=GamePiece.NONE) {
			return state;
		}
		State max = state;
		double v = Double.NEGATIVE_INFINITY;
		for(int i = 0; i<7;i++) {
			ConnectFourBoard temp = state.getBoard().copy();
			try {
				temp.drop(piece_.other(),i);
			} catch ( GameRuleViolation e ) {
				// TODO Auto-generated catch block
				//System.out.println("col " + i + " is full");
				continue;
			}
			State newState = minValue(new State(i,temp),alpha,beta,level+1,depth);

			double comp = new Estimate(this).hs(newState.getBoard(),piece_,newState.getMove());
			if(v < comp) {
				v= comp;
				max = newState;
			}
			//System.out.println("V is currently " + v);
			//if(v >= 4096) System.out.println("Winning move on level " + level + " at position " + max.getMove());
			alpha = Math.max(alpha,v);
			if(alpha >= beta) {
				//System.out.println("Prunned at " + level);
				break;
			}
		}
		return max;
	}
	
	private State minValue(State state, double alpha, double beta, int level, int depth) {
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
			State newState = maxValue(new State(i,temp),alpha,beta,level+1,depth);
			double comp = new Estimate(this).hs(newState.getBoard(),piece_,newState.getMove());
			if(v > comp) {
				v= comp;
				min = newState;
			}
			beta = Math.min(beta,v);
			if(alpha >= beta) {
				//System.out.println("Prunned at " + level);
				break;
			}
		}
		return min;
	}

}
