package Exceptions;

/**
 * 
 * @author Manu
 *
 */

public class ShortPasswordException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShortPasswordException() {
		super("Sua senha deve conter no mínimo 4 caracteres.");
	}

}
