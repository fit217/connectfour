package players;
import game.ConnectFourBoard;
import game.GamePiece;
import game.GameRuleViolation;
import game.Move;
import game.Player;
import gui.ConnectFourGUI;
import heuristics.Estimate;

class State{
	private int move_;
	private ConnectFourBoard board_;
	public State(int move, ConnectFourBoard board) {
		board_ = board;
		move_ = move;
	}

	public ConnectFourBoard getBoard() {
		return board_;
	}
	public int getMove() {
		return move_;
	}
}
/**
 * @author Jonko
 *
 */
public class AlphabetaPrunning extends Player{
	private ConnectFourGUI gui_;
	/**
	 * @param piece
	 * @param timeout
	 */
	public AlphabetaPrunning ( GamePiece piece,ConnectFourGUI gui, long timeout ) {
		super(piece,timeout);
		// TODO Auto-generated constructor stub
		gui_ = gui;
	}

	/* (non-Javadoc)
	 * @see game.Player#chooseMove(game.ConnectFourBoard)
	 */
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
			if(new Estimate(this).h(board,piece_) >= 4096) break;
			depth++;
		}
		System.out.println(depth);
		return best;
	}
	
	private State maxValue(State state, double alpha, double beta, int level, int depth) {
		if(level == depth) {
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
			double comp = new Estimate(this).h(newState.getBoard(),piece_);
			if(v < comp) {
				v= comp;
				max = newState;
			}
			System.out.println("V is currently " + v);
			if(v >= 4096) System.out.println("Winning move on level " + level + " at position " + max.getMove());
			alpha = Math.max(alpha,v);
			if(alpha >= beta) {
				//System.out.println("Prunned at " + level);
				break;
			}
		}
		return max;
	}
	
	private State minValue(State state, double alpha, double beta, int level, int depth) {
		if(level == depth) {
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
			State newState = maxValue(new State(i,temp),alpha,beta,level+1,depth);
			double comp = new Estimate(this).h(newState.getBoard(),piece_);
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
