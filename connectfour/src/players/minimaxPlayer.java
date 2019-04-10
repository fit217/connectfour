package players;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import game.ConnectFourBoard;
import game.GamePiece;
import game.GameRuleViolation;
import game.Move;
import game.Player;
import gui.ConnectFourGUI;
import heuristics.Estimate;
import sun.misc.Queue;

/**
 * @author chase
 */
public class minimaxPlayer extends Player {
	private ConnectFourGUI gui_;// GUI
	private Estimate estimate_;
	private Stack<Integer> stack_;
	private static int maxCalled = 0;
	private static int minCalled = 0;

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
		// TODO determine the move
		int choice = 3;
		//System.out.println(super.getTimeout());
		try {
			choice = miniMax(board,1);
		} catch ( GameRuleViolation e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("MiniMax Player is making a move");
/*		ConnectFourBoard newState = board.copy();
		try {
			newState.drop(piece_,choice);
			System.out.println("Piece dropped at " + new Estimate(this).getTopMostEmpty(board,choice) + ", " + choice);
			System.out.println("The board value for minimax player is no " + new Estimate(this).h(newState,piece_));
		} catch ( GameRuleViolation e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
		//System.out.println("Column " + choice + ", Row" + new Estimate(this).getTopMostEmpty(board,choice));
		//System.out.println("Move is at column " + choice +  ", Value : " + new Estimate(this).getValue(board,this.piece_,choice,new Estimate(this).getTopMostEmpty(board,choice)));
		
		move_ = new Move(piece_,choice,false);

	}

	public int miniMax ( ConnectFourBoard board, int depth )
	    throws GameRuleViolation {

		double best = 3;

		while ( depth != 4 ) {
			if ( !stop_ ) {
				
				best = maxValue(board,0,depth);
				System.out.println(best);
				minCalled = 0;
				maxCalled = 0;
				depth++;
			}
			else
				break;
		}
		return (int) best;

	}

	private double maxValue ( ConnectFourBoard state, int level, int depth ) {
		// System.out.println(level);
		if(stop_) {
			return new Estimate(this).h(state,piece_);
		}
		maxCalled++;
		//System.out.println("max called " + maxCalled);
		if ( level == depth ) {
			// System.out.println(new Estimate(this).h(state,piece_));
			//System.out.println("Terminal state");
			return new Estimate(this).h(state,piece_);
		}
		double v = Double.NEGATIVE_INFINITY;
		double best = 3;
		for ( int i = 0 ; i < 7 ; i++ ) {
			ConnectFourBoard newState = state.copy();
			if ( state.isFull(i) ) {
				// System.out.println("board is full in col " + i);
				continue;
			}
			try {
				newState.drop(piece_,i);
			} catch ( GameRuleViolation e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			double temp = minValue(newState,level+1,depth);
			//System.out.println(temp + " vs " + v);
			if ( v < temp ) {
				v = temp;
				best = i;
			}

			//v = Math.max(v,minValue(newState,level+1,depth));
		}
		if ( level == 0 ) {
			return best;
		} else {
			return v;
		}
		// return v;
	}

	private double minValue ( ConnectFourBoard state, int level, int depth ) {
		if(stop_) {
			return new Estimate(this).h(state,piece_.other());
		}
		minCalled++;
		//System.out.println("min called " + minCalled);
		// System.out.println(level);
		if ( level == depth ) {
			// System.out.println(new Estimate(this).h(state,piece_.other()));
			//System.out.println("Terminal state");
			return new Estimate(this).h(state,piece_.other());
		}
		double best = 3;
		double v = Double.POSITIVE_INFINITY;
		for ( int i = 0 ; i < 7 ; i++ ) {
			ConnectFourBoard newState = state.copy();
			if ( newState.isFull(i) ) {
				// System.out.println("board is full in col " + i);
				continue;
			}
			try {
				newState.drop(piece_.other(),i);
			} catch ( GameRuleViolation e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			double temp = maxValue(newState,level+1,depth);
			//System.out.println(temp + " vs " + v);

			if ( v > temp ) {
				v = temp;
				best = i;
			}

			//v = Math.min(v,maxValue(newState,level+1,depth));
		}
		/*if ( level == 1 ) {
			System.out.println(best +"_____min");
			return best;
		} else {
			return v;
		}*/
		 return v;
	}

}
