package Exceptions;

/**
 * 
 * @author Manu
 *
 */

public class InvalidEmailFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEmailFormatException() {
		super("E-mail inválido. Digite o formato correto!");
	}

}
