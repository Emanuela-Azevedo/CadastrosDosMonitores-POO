package projeto.telasPadroes;

import java.awt.Color;

import javax.swing.JFrame;

/**
 * 
 * @author Manu
 *
 */

public class TelaPadrao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Tela padrão para todas as telas
	public TelaPadrao(String titulo) {
		setTitle(titulo);
		setSize(650, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		Color minhaCor = new Color(229, 229, 229);
		getContentPane().setBackground(minhaCor);
	}
	
}
