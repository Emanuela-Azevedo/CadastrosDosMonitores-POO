package projeto.central;

import janelas.JanelaCadastrarCoordenador;
import janelas.JanelaLogin;

/**
 * 
 * @author george e Manu
 *
 */

public class Programa {

	public static void main(String[] args) {

		// Recuperando a Central na persistencia
		CentralDeInformacoes central = Persistencia.getInstance().recuperar();

		if (central.getCoordenador() == null) {
			new JanelaCadastrarCoordenador();
		} else {
			new JanelaLogin();
		}
	}
}