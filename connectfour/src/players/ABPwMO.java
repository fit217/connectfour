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
public class ABPwMO extends Player{

	ConnectFourGUI gui_;
	ArrayList<Integer> order_;
	/**
	 * @param piece
	 * @param timeout
	 */
	public ABPwMO ( GamePiece piece,ConnectFourGUI gui, long timeout ) {
		super(piece,timeout);
		gui_ = gui;
		// TODO Auto-generated constructor stub
	}



	/* (non-Javadoc)
	 * @see game.Player#chooseMove(game.ConnectFourBoard)
	 */
	@Override
	public void chooseMove ( ConnectFourBoard board ) {
		// TODO Auto-generated method stub
		reset();
		order_ = new ArrayList<Integer>();
		if(board.isEmpty()) {
			move_ = new Move(piece_,3,false);
		}else {
		State best = alphaBeta(board);
		move_ = new Move(piece_,best.getMove(),false);
	  }
	}
	
	private State alphaBeta(ConnectFourBoard board){
		int depth = 1;
		State best = new State(2,board);
		while(!stop_) {
			best = maxValue( best, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,0,depth);
			if(new Estimate(this).hs(board,piece_,best.getMove()) >= 4096) break;
			depth++;
		}
		System.out.println("ABP depth: " + depth);
		return best;
	}
	
	private State maxValue(State state, double alpha, double beta, int level, int depth) {
		if(level == depth || state.getBoard().getWinner()!=GamePiece.NONE) {
			order_.add(state.getMove());
			return state;
		}
		State max = state;
		double v = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < 7;i++) {
			int spot = i;
			if(order_.size() >= 7) {
			 spot = prevOrder(i);
			}
			ConnectFourBoard temp = state.getBoard().copy();
			try {
				temp.drop(piece_.other(),spot);
			} catch ( GameRuleViolation e ) {
				// TODO Auto-generated catch block
				//System.out.println("col " + i + " is full");
				continue;
			}
			State newState = minValue(new State(spot,temp),alpha,beta,level+1,depth);

			double comp = new Estimate(this).hs(newState.getBoard(),piece_,newState.getMove());
			if(v < comp) {
				v= comp;
				max = newState;
			}
			//System.out.println("V is currently " + v);
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
		if(level == depth || state.getBoard().getWinner()!=GamePiece.NONE) {
			order_.add(state.getMove());
			return state;
		}
		State min = state;
		double v = Double.POSITIVE_INFINITY;
		for(int i = 0; i < 7; i++) {
			int spot = i;
			if(order_.size() >= 7) {
			 spot = prevOrder(i);
			}
			ConnectFourBoard temp = state.getBoard().copy();
			try {
				temp.drop(piece_.other(),spot);
			} catch ( GameRuleViolation e ) {
				//System.out.println("col " + i + " is full");
				continue;
			}
			State newState = maxValue(new State(spot,temp),alpha,beta,level+1,depth);
			double comp = new Estimate(this).hs(newState.getBoard(),piece_,newState.getMove());
			if(v > comp) {
				v = comp;
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
	private int prevOrder(int index) {
		switch(index) {
		case 0: return order_.get(0);
		case 1: return order_.get(1);
		case 2: return order_.get(2);
		case 3: return order_.get(3);
		case 4: return order_.get(4);
		case 5: return order_.get(5);
		default: return order_.get(6);
		}
	}

	}
