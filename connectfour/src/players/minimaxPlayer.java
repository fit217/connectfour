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
 *
 */
public class minimaxPlayer extends Player{
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
	public minimaxPlayer(GamePiece piece, ConnectFourGUI gui) {
		
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
	 * 					the desired time for a move to be made
	 */
	public minimaxPlayer(GamePiece piece, ConnectFourGUI gui, Long timeOut) {
		super(piece, timeOut);
		gui_=gui;
	
	}
	/* (non-Javadoc)
	 * @see game.Player#chooseMove(game.ConnectFourBoard)
	 */
	@Override
	public void chooseMove ( ConnectFourBoard board ) {
		reset();//resets move and stop at the start of the turn
		//TODO determine the move
		int choice = 3;
		System.out.println(super.getTimeout());
		try {
			choice = miniMax(board,1);
		} catch ( GameRuleViolation e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("Player is making a move");
		move_=new Move(piece_,choice,false);
	}
	public int miniMax(ConnectFourBoard board, int depth) throws GameRuleViolation {
			double best = 3;
			long currentTime = 0;
			while(depth != 2) {
				best = maxValue(board,0,depth);
				System.out.println(best);
				minCalled = 0;
				maxCalled = 0;
				depth++;
			}
			return (int) best;
		}
	
	private double maxValue(ConnectFourBoard state,int level, int depth) {
		//System.out.println(level);
		maxCalled++;
		System.out.println("max called " + maxCalled);
		if(level == depth) {
			//System.out.println(new Estimate(this).h(state,piece_));
			System.out.println("Terminal state");
			return new Estimate(this).h(state,piece_);
		}
		double v = Double.NEGATIVE_INFINITY;
		double best = 3;
		for(int i = 0; i < 7;i++) {
			ConnectFourBoard newState = state.copy();
			if(state.isFull(i)) {
				//System.out.println("board is full in col " + i);
				continue;
			}
			try {
				newState.drop(piece_,i);
			} catch ( GameRuleViolation e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			double temp = minValue(newState,level++,depth);
		  System.out.println(temp + " vs " + v);
			if(v < temp) {
				v = temp;
				best = i;
			}

			v = Math.max(v,minValue(newState,level++,depth));
		}
		if(level == 1) {
			return best;
		}else {
			return v;
		}
//		return v;
	}
		
		private double minValue(ConnectFourBoard state,int level, int depth) {
			minCalled++;
			System.out.println("min called " + minCalled);
			//System.out.println(level);
			if(level == depth) {
				//System.out.println(new Estimate(this).h(state,piece_.other()));
				System.out.println("Terminal state");
				return new Estimate(this).h(state,piece_.other());
			}
			double best = 3;
			double v = Double.POSITIVE_INFINITY;
			for(int i = 0; i < 7;i++) {
				ConnectFourBoard newState = state.copy();
				if(newState.isFull(i)) {
					//System.out.println("board is full in col " + i);
					continue;
				}
				try {
					newState.drop(piece_.other(),i);
				} catch ( GameRuleViolation e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				double temp = maxValue(newState,level++,depth);
			  System.out.println(temp + " vs " + v);
				if(v > temp) {
					v = temp;
					best = i;
				}

				//v = Math.min(v,maxValue(newState,level++,depth));
			}
			if(level == 1) {
				return best;
			}else {
				return v;
			}
			//return v;
		}



		
	

	
}
