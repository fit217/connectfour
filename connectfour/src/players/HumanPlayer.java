package players;

import game.ConnectFourBoard;
import game.GamePiece;
import game.Move;
import game.Player;
import gui.ConnectFourGUI;
import heuristics.Estimate;

/**
 * Human player. The player's move is specified by clicking on the desired
 * column on the game board.
 */
public class HumanPlayer extends Player {

	private ConnectFourGUI gui_; // GUI
	public Estimate estimate_;

	/**
	 * Create a human player.
	 * 
	 * @param piece
	 *          the game piece the player will use
	 * @param gui
	 *          the GUI for getting the player's move
	 */
	public HumanPlayer ( GamePiece piece, ConnectFourGUI gui ) {
		super(piece,Long.MAX_VALUE);
		gui_ = gui;
		estimate_=new Estimate(this);
	}

	@Override
	public void chooseMove ( ConnectFourBoard board ) {
		System.out.println(getName() + ", it's your turn");
		reset();
		for ( ; move_ == null && !stop_ ; ) {
			int col = gui_.getMove();
			if ( col != -1 && !board.isFull(col) ) {
				move_ = new Move(piece_,col,false);
			} else {
				if ( board.isFull(col) ) {
					System.out.println("column is full; choose another");
				}
			}
		}
		// we were directed to stop before the player made a move
		if ( move_ == null ) {
			System.out.println("no move made; using default move");
			move_ = new Move(piece_,board.getLegalMove(),true);
		}
		
		for(int i=0;i<board.getNumRows();i++) {
			if(board.getPiece(i+1,move_.getCol())==GamePiece.NONE) {
				System.out.println("Move in position "+ i +"," + move_.getCol());
				estimate_.getValue(board,piece_,i,move_.getCol());
				break;
				}
			
		}
		estimate_.h(board,piece_);
	}
}
