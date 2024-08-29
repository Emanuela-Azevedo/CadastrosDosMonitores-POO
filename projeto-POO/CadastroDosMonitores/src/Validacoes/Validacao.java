package Validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Exceptions.DivergentPasswordException;
import Exceptions.InvalidEmailFormatException;
import Exceptions.ShortPasswordException;

/**
 * 
 * @author Manu
 *
 */

public class Validacao {

	//verificas se a a senha e valida
	public void validarSenha(String senha, String confirmarSenha)
			throws ShortPasswordException, DivergentPasswordException {
		if (senha.length() < 4) { // senha
			throw new ShortPasswordException();
		} else if (!senha.equals(confirmarSenha)) {
			throw new DivergentPasswordException();
		}
	}

	public void validarEmail(String email) throws InvalidEmailFormatException {

		// Verifica se o email atende os requisitos para ser valido
		Pattern p = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = p.matcher(email);

		boolean matchFound = m.matches();
		// E-mail válido ou inválido.
		if (!matchFound) {
			throw new InvalidEmailFormatException();

		}
	}

}
