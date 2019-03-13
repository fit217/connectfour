package game;

/**
 * Indicates an action made at a time when it isn't applicable.
 * 
 * @author ssb
 */
public class InvalidStateException extends RuntimeException {

	/**
	 * @param message
	 */
	public InvalidStateException ( String message ) {
		super(message);
	}

}
