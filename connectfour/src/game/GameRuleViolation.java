package game;

/**
 * Exception indicating a violation of game rules.
 * 
 * @author ssb
 */
public class GameRuleViolation extends Exception {

	public GameRuleViolation ( String message ) {
		super(message);
	}

}
