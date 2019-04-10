package main;

import game.ConnectFourBoard;
import game.ConnectFourDriver;
import game.GamePiece;
import game.GameRuleViolation;
import game.PlayedGame;
import game.Player;
import players.ABPwMO;
import players.AlphabetaPrunning;
import players.LimitedABP;
import players.LimitedMM;
import players.RandomPlayer;
import players.minimaxPlayer;

/**
 * Play a tournament (without a GUI) and track win/loss/draw statistics. Each
 * player 1 plays every player 2. Statistics are reported for each player 1
 * against all of the player 2s.
 * 
 * @author ssb
 */

public class ConnectFourTournament {

	/**
	 * @param args
	 */
	public static void main ( String[] args ) {
		ConnectFourBoard board = new ConnectFourBoard(6,7);
		ConnectFourDriver driver = new ConnectFourDriver();

		// time allowed per turn (ms)
		final int TIME_LIMIT = 100;

		// the players - each player 1 plays every player 2
		Player[] player1s = { new RandomPlayer(GamePiece.YELLOW,TIME_LIMIT),new LimitedMM(GamePiece.YELLOW,(long)TIME_LIMIT, 2),new LimitedMM(GamePiece.YELLOW,(long)TIME_LIMIT, 5), new minimaxPlayer(GamePiece.YELLOW,(long)TIME_LIMIT), new LimitedABP(GamePiece.YELLOW,(long)TIME_LIMIT, 2), new LimitedABP(GamePiece.YELLOW,(long)TIME_LIMIT, 5),  new AlphabetaPrunning(GamePiece.YELLOW,(long)TIME_LIMIT),new ABPwMO(GamePiece.YELLOW,(long)TIME_LIMIT) };
		Player[] player2s = { new RandomPlayer(GamePiece.RED,TIME_LIMIT)};

		// number of game to play with each combination
		final int NUMREPS = 10;

		for ( int p1 = 0 ; p1 < player1s.length ; p1++ ) {

			// number of wins for each player, and draws
			int win1 = 0, win2 = 0, draw = 0;

			// number of games played
			int numgames = 0;

			for ( int p2 = 0 ; p2 < player2s.length ; p2++ ) {
				for ( int rep = 0 ; rep < NUMREPS ; rep++ ) {
					try {
						board.clear();
						PlayedGame result =
						    driver.playGame(player1s[p1],player2s[p2],board);
						numgames++;
						Player winner = (result.getWinner() == GamePiece.NONE ? null
						    : (result.getWinner() == player1s[p1].getPiece() ? player1s[p1]
						        : player2s[p2]));
						// board.print();
						if ( winner == null ) {
							draw++;
						} else {
							if ( winner == player1s[p1] ) {
								win1++;
							} else {
								win2++;
							}
						}
					} catch ( GameRuleViolation e ) {
						e.printStackTrace();
					} catch ( InterruptedException e ) {
						e.printStackTrace();
					}
				}
			}
			System.out.println(player1s[p1].getName() + ": " + (100 * win1 / numgames)
			    + " " + (100 * win2 / numgames) + " " + (100 * draw / numgames));
		}
	}
}
