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


class TreeNode{
	 static int nodeCount = 0;
	 private ArrayList<TreeNode> children_;
	 private double val_;
	 private TreeNode parent_;
	 private int generation_;
	 
	 public TreeNode(double val) {
		 val_ = val; 
		 parent_ = null;
		 generation_ = 0;
	 }
	 public TreeNode(double val, TreeNode parent) {
		 val_ = val; 
		 parent_ = parent;
		 generation_ = this.getParent().getGen() + 1;
	 }
	 public TreeNode(TreeNode parent) {
		 val_ = -1;
		 parent_ = parent;
		 generation_ = this.getParent().getGen() + 1;
	 }
	 
	 public TreeNode getParent() {
		 return parent_;
	 }
	 
	 public ArrayList<TreeNode> getChildren(){
		 return children_;
	 }
	 
	 public double getVal() {
		 return val_;
	 }
	 
	 public void addChild(TreeNode child) {
		 children_.add(child);
	 }
	 public static int getNodeCount() {
		 return nodeCount;
	 }
	 public int getGen() {
		 return generation_;
	 }
	
}

/**
 * @author chase
 *
 */
public class minimaxPlayer extends Player{
	private ConnectFourGUI gui_;// GUI
	private Estimate estimate_;
	private Stack<TreeNode> stack_;

	
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
		try {
			choice = miniMax(board,0,1);
		} catch ( GameRuleViolation e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		move_=new Move(piece_,choice,false);
	}
	public int miniMax(ConnectFourBoard board, int depth) throws GameRuleViolation {
			int level = 1;
			double best = Double.NEGATIVE_INFINITY;
			while(!super.stop_) {
				best = Math.max(best,maxValue(board,0,level));
				level++;
				
			}
		}
	
	private double maxValue(ConnectFourBoard state,int level, int depth) {
		if(level == depth) {
			return  new Estimate().h(state,getPiece());
		}
		double v = Double.NEGATIVE_INFINITY;
		for(int i = 0; i < 7;i++) {
			ConnectFourBoard newState = state.copy();
			try {
				newState.drop(getPiece(),i);
			} catch ( GameRuleViolation e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v = Math.max(v,minValue(newState,level++,depth));
		}
		return v;
	}
		
		private double minValue(ConnectFourBoard state,int level, int depth) {
			if(level == depth) {
				return new Estimate().h(state,getPiece());
			}
			double v = Double.POSITIVE_INFINITY;
			for(int i = 0; i < 7;i++) {
				ConnectFourBoard newState = state.copy();
				try {
					newState.drop(getPiece(),i);
				} catch ( GameRuleViolation e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				v = Math.min(v,maxValue(newState,level++,depth));
			}
			return v;
		}



		
	

	
}
