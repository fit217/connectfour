package heuristics;

import java.util.ArrayList;

import game.ConnectFourBoard;
import game.EvalHeuristic;
import game.GamePiece;
import game.Move;
import game.Player;

/**
 * @author chase
 */
public class Estimate implements EvalHeuristic {

	public Estimate () {

	}

	/*
	 * (non-Javadoc)
	 * @see game.EvalHeuristic#h(game.ConnectFourBoard, game.GamePiece)
	 */
	@Override
	public double h ( ConnectFourBoard board, GamePiece player ) {
		// TODO Auto-generated method stub
		if ( board.isEmpty() ) {
			return Integer.MAX_VALUE;
		}
		int playerVal = 0;
		int opponentVal = 0;
		for ( int col = 0 ; col < board.getNumCols() ; col++ ) {

			int row;
			if ( (row = getTopMostEmpty(board,col)) != -1 ) {
				playerVal += getValue(board,player,row,col);
			}
		}
		return playerVal - opponentVal;
	}

	/**
	 * (non-Javadoc) Evaluates the effect of a piece dropped on a board
	 * 
	 * @param board
	 *          The board before the move has been made
	 * @param colMove
	 *          The column the piece will be dropped on
	 **/
	public int getValue ( ConnectFourBoard board, GamePiece player, int row,
	                      int col ) {

		ArrayList<Integer> group = new ArrayList<Integer>(7);// rowBelow,colL,colR,
		                                                     // DiagRU,DiagRD,DiagLU,DiagLD
		for ( int i = 0 ; i < 7 ; i++ ) {
			group.add(0);
		}
		int rowVal=0;
		int colValR=0;
		int colValL=0;
		int diagVal=0;
		/*
		 * for ( int i = 0 ; i < 4 ; i++ ) { if ( inHeight(board,row - i) &&
		 * board.getPiece(row - i,col) != GamePiece.NONE && group.get(0) != -1 ) {
		 * if ( isEqual(player,board.getPiece(row - i,col)) ) {
		 * group.set(0,group.get(0) + 1); } else { group.set(0,-1); } } if (
		 * inWidth(board,col + i) && board.getPiece(row,col + i) != GamePiece.NONE
		 * && group.get(1) != -1 ) { if ( isEqual(player,board.getPiece(row,col +
		 * i)) ) group.set(1,group.get(1) + 1); else group.set(1,-1); } if (
		 * inWidth(board,col - i) && board.getPiece(row,col - i) != GamePiece.NONE
		 * && group.get(2) != -1 ) { if ( isEqual(player,board.getPiece(row,col -
		 * i)) ) group.set(2,group.get(2) + 1); else group.set(2,-1); } if (
		 * inWidth(board,col + i) && inHeight(board,row + i) && board.getPiece(row +
		 * i,col + i) != GamePiece.NONE && group.get(3) != -1 ) { if (
		 * isEqual(player,board.getPiece(row + i,col)) ) group.set(3,group.get(3) +
		 * 1); else group.set(3,-1); } if ( inWidth(board,col + i) &&
		 * inHeight(board,row - i) && board.getPiece(row - i,col + i) !=
		 * GamePiece.NONE && group.get(4) != -1 ) { if (
		 * isEqual(player,board.getPiece(row - i,col + i)) )
		 * group.set(4,group.get(4) + 1); else group.set(4,-1); } if (
		 * inWidth(board,col - i) && inHeight(board,row + i) && board.getPiece(row +
		 * i,col - i) != GamePiece.NONE && group.get(5) != -1 ) { if (
		 * isEqual(player,board.getPiece(row + i,col - i)) )
		 * group.set(5,group.get(5) + 1); else group.set(5,-1); } if (
		 * inWidth(board,col - i) && inHeight(board,row - i) && board.getPiece(row -
		 * i,col - i) != GamePiece.NONE && group.get(6) != -1 ) { if (
		 * isEqual(player,board.getPiece(row - i,col - i)) )
		 * group.set(6,group.get(6) + 1); else group.set(6,-1); } }
		 */

		for ( int i = -3 ; i <= 0 ; i++ ) {
			if ( validSpotCheck(board, row+i,col) || rowVal==-1) {
				continue;
			}
			if ( isEqual(player,board.getPiece(row + i,col)) ) rowVal++;
			else rowVal = -1;
			for ( int j = -3 ; j < 4 ; j++ ) {
				if (validSpotCheck(board, row,col+j) ) {
					continue;
				}
				if ( isEqual(player,board.getPiece(row,col + j)) ) {
					if ( j < 0 && colValL >= 0 ) {
						colValL++;
					} else if ( j > 0 && colValR >= 0 ) {
						colValR++;
					}
				} else {
					if ( j < 0 ) {
						colValL = -1;
					} else {
						colValR = -1;
					}
				}
				if ( i == j ) {
					if ( isEqual(player,board.getPiece(row + i,col + j)) ) diagVal++;
				}
			}
		}

		/*System.out.println("In Row Below: " + group.get(0));
		System.out.println("In Column to the left: " + group.get(1));
		System.out.println("In Column to the right: " + group.get(2));
		System.out.println("In diagonal to the Upper right: " + group.get(3));
		System.out.println("In diagonal to the Lower right: " + group.get(4));
		System.out.println("In diagonal to the Upper left: " + group.get(5));
		System.out.println("In diagonal to the Lower left: " + group.get(6));
		return (groupValue(group.get(0)) + groupValue(group.get(1))
		    + groupValue(group.get(2)) + groupValue(group.get(3))
		    + groupValue(group.get(4)) + groupValue(group.get(5))
		    + groupValue(group.get(6)));*/
		if((groupValue(colValL)+groupValue(colValR)+groupValue(rowVal))>0) {
			System.out.println("Position : " +row+", "+col+ " has values:");
			System.out.println("In Column left: "+ colValL);
			System.out.println("In Column right: " + colValR);
			System.out.println("In Row Below: "+ rowVal);
		}
		return groupValue(colValL)+groupValue(colValR)+groupValue(rowVal);

	}
	private boolean validSpotCheck(ConnectFourBoard board, int row, int column) {
		if(!inHeight(board,row) || !(inWidth(board,column))) {
			return true;
		}
		if(board.getPiece(row,column) == GamePiece.NONE) {
			return true;
		}
		return false;
	}

	private boolean inWidth ( ConnectFourBoard board, int col ) {
		if ( col < 0 || col >= board.getNumCols() ) {
			return false;
		}
		return true;
	}

	private boolean inHeight ( ConnectFourBoard board, int row ) {
		if ( row < 0 || row >= board.getNumRows() ) {
			return false;
		}
		return true;
	}

	private boolean isEqual ( GamePiece player, GamePiece other ) {
		if ( player.getColor() == other.getColor() ) {
			return true;
		}
		return false;
	}

	private int groupValue ( int x ) {
		switch ( x ) {
		case -1:
			return 0;
		case 1:
			return 1;
		case 2:
			return 4;
		case 3:
			return 32;
		case 4:
			return 4096;
		default:
			return 0;
		}
	}

	private int getTopMostEmpty ( ConnectFourBoard board, int col ) {
		if ( board.isFull(col) ) {
			return -1;
		}
		for ( int row = 0 ; row < board.getNumRows() ; row++ ) {
			if ( board.getPiece(row,col) == GamePiece.NONE ) {
				return row;
			}
		}
		return -1;
	}

	/*
	 * public ArrayList<Move> minimax (Player player, Long time, ConnectFourBoard
	 * board, ArrayList<Move> moves, GamePiece piece) {
	 * if(player.getTimeout()<time) { return moves; } for(int
	 * col=0;col<board.getNumCols();col++) { } }
	 */

	@Override
	public double max () {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see game.EvalHeuristic#min()
	 */
	@Override
	public double min () {
		// TODO Auto-generated method stub
		return 0;
	}
	// One possibility for a heuristic is to check every group of four consecutive
	// squares (rows, columns, and diagonals). A group scores 0 if it contains at
	// least one of the other player's pieces, 1 if it contains one of the
	// player's pieces, 4 if it contains two, 32 if it contains three, and 4096 if
	// it contains four (i.e. a victory for the current player). The total score
	// of the board is the sum of the group scores.

	// A second possibilty for a heuristic is to evaluate the advantage that one
	// player has over the other on a given board. For this, use a heuristic such
	// as the one described in the previous paragraph and compute
	// hplayer(n)-hother(n) i.e. the difference between the quality of the board
	// for the player and the quality of the board for the other player.
}
