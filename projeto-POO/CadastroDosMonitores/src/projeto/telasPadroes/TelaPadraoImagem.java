package projeto.telasPadroes;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pacote.imagens.Imagens;

/**
 * 
 * @author Manu
 *
 */

public class TelaPadraoImagem extends TelaPadrao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelaPadraoImagem(String titulo,String tituloPadrao) {
		super(titulo);

		//Titulo
		JLabel lbTitulo = new JLabel(tituloPadrao);
		lbTitulo.setBounds(180, 25, 300, 30);
		lbTitulo.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 20));
		lbTitulo.setHorizontalAlignment(JLabel.CENTER);
		add(lbTitulo);

		// simbolo do inicio
		JLabel icone1 = new JLabel("");
		icone1.setBounds(50, -100, 500, 300);
		ImageIcon imagem1 = Imagens.ICONE_PAPEL_RASGADO;
		Image img1 = imagem1.getImage().getScaledInstance(icone1.getWidth(), icone1.getHeight(), Image.SCALE_SMOOTH);
		icone1.setIcon(new ImageIcon(img1));
		getContentPane().add(icone1);
		// simbolo do livro
		JLabel icone2 = new JLabel("");
		icone2.setBounds(10, 240, 200, 200);
		ImageIcon imagem2 = Imagens.ICONE_LIVRO;
		Image img2 = imagem2.getImage().getScaledInstance(icone2.getWidth(), icone2.getHeight(), Image.SCALE_SMOOTH);
		icone2.setIcon(new ImageIcon(img2));
		getContentPane().add(icone2);

	}

}
