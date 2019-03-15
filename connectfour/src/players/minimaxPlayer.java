package players;

import game.ConnectFourBoard;
import game.GamePiece;
import game.Move;
import game.Player;
import gui.ConnectFourGUI;

/**
 * @author chase
 *
 */
public class minimaxPlayer extends Player{
	private ConnectFourGUI gui_;// GUI
	
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
		int choice=miniMax(0,0);
		//TODO make a move
		if(choice==-1) {
			move_ = new Move(piece_,board.getLegalMove(),true);
		}
		move_=new Move(piece_,choice,false);
	}
	public int miniMax(int position, int depth) {
		//if we have timed out
		if(stop_) {
			return -1;
		}
		
	//TODO determine the column to be chosen
		//TODO perform move to alter the state
		//gui.drop()
		//TODO reset the board?
		//gui.undrop()
		return 0;
	}
	
}
