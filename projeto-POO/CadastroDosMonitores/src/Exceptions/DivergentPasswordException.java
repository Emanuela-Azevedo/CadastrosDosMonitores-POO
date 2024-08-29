package Exceptions;

/**
 * 
 * @author Manu
 *
 */

public class DivergentPasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DivergentPasswordException() {
		super("Senhas divergentes!");
	}

}

