package janelas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import janelas.menu.coordenador.JanelaCadastrarEdital;
import janelas.menu.coordenador.JanelaListarEditais;
import janelas.menu.coordenador.JanelaListarTodosAlunos;
import projeto.telasPadroes.TelaPadraoImagem;

/**
 * 
 * @author Manu
 *
 */

public class JanelaMenuCoordenador extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JanelaMenuCoordenador() {
		super("Menu do coordenador", "Menu Coordenador");
		adicionarBotoes();
		setVisible(true);
		repaint();
	}

	private class OuvinteDosBotoes implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			switch (e.getActionCommand()) {
			case "Cadastrar Edital de Monitoria":
				new JanelaCadastrarEdital(null, "Novo edital de Monitoria");
				dispose();
				break;
			case "Listar Editais":
				new JanelaListarEditais();
				dispose();
				break;
			case "Listar Todos os Alunos":
				new JanelaListarTodosAlunos();
				dispose();
				break;
			case "Sair":
				new JanelaLogin();
				dispose();
				break;
			}
		}
	}

	public void adicionarBotoes() {
		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		// Botão de Cadastrar Edital de Monitoria
		JButton btCadastrarEdital = new JButton("Cadastrar Edital de Monitoria");
		btCadastrarEdital.setBounds(190, 120, 250, 30);
		btCadastrarEdital.setFont(new Font("Georgia", Font.ITALIC, 15));
		btCadastrarEdital.addActionListener(ouvinte);
		add(btCadastrarEdital);

		// Botão de Listar Editais
		JButton btListarEditais = new JButton("Listar Editais");
		btListarEditais.setBounds(190, 170, 250, 30);
		btListarEditais.setFont(new Font("Georgia", Font.ITALIC, 15));
		btListarEditais.addActionListener(ouvinte);
		add(btListarEditais);
		
		// Botão de Listar todos os Alunos
		JButton btListarAlunos = new JButton("Listar Todos os Alunos"); 
		btListarAlunos.setBounds(190, 220, 250, 30);
		btListarAlunos.setFont(new Font("Georgia", Font.ITALIC, 15));
		btListarAlunos.addActionListener(ouvinte);
		add(btListarAlunos);

		// Botão de Sair
		JButton btSair = new JButton("Sair");
		btSair.setBounds(500, 350, 90, 30);
		btSair.setFont(new Font("Georgia", Font.ITALIC, 15));
		btSair.addActionListener(ouvinte);
		add(btSair);
	}

}