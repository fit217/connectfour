package main;

import game.ConnectFourBoard;
import game.ConnectFourDriver;
import game.GamePiece;
import game.GameRuleViolation;
import game.PlayedGame;
import game.Player;
import gui.ConnectFourGUI;
import players.HumanPlayer;
import players.RandomPlayer;
import players.minimaxPlayer;

/**
 * Main program to play a single game of Connect Four.
 * 
 * @author ssb
 */

public class ConnectFour {

	public static void main ( String[] args ) {
		ConnectFourBoard board = new ConnectFourBoard(6,7);
		// comment out the following line if you don't want a gui
		ConnectFourGUI gui = new ConnectFourGUI(board);
		ConnectFourDriver driver = new ConnectFourDriver();

		Player player1 = new RandomPlayer(GamePiece.YELLOW,5000);
		Player player2 = new minimaxPlayer(GamePiece.RED,gui);

		try {
			PlayedGame result = driver.playGame(player1,player2,board);
			Player winner = (result.getWinner() == GamePiece.NONE ? null
			    : (result.getWinner() == player1.getPiece() ? player1 : player2));
			// uncomment the following line if you don't use a gui
			// board.print();
			if ( winner == null ) {
				System.out.println("game is a draw!");
			} else {
				System.out.println("player " + winner.getName() + " wins!");
			}
		} catch ( GameRuleViolation e ) {
			e.printStackTrace();
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}

	}

}
