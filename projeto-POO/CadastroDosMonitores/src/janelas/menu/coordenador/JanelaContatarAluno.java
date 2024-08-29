package janelas.menu.coordenador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import entidades.Aluno;
import entidades.EditalDeMonitoria;
import projeto.central.Mensageiro;
import projeto.telasPadroes.TelaDetalharEditalResultado;
import projeto.telasPadroes.TelaPadraoImagem;

/**
 * 
 * @author Manu
 *
 */

public class JanelaContatarAluno extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aluno aluno;
	private EditalDeMonitoria edital;
	private JTextArea tAMensagem;

	public JanelaContatarAluno(Aluno aluno, EditalDeMonitoria edital) {
		super("Contatar Aluno", "Contatar Aluno");
		this.aluno = aluno;
		this.edital = edital;

		adicionarTextArea();
		adicionarBotoes();
		setVisible(true);
	}

	private class OuvinteDosBotoes implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Voltar":
				dispose();
				if(edital.getSituacaoDoEdital().equals("Resultado Final")) {
					new TelaDetalharEditalResultado("Resultado Final", "Resultado Final", "coordenador", edital);
				} else {
					new TelaDetalharEditalResultado("Resultado Preliminar", "Resultado Preliminar", "coordenador", edital);
				}
				break;
			case "Enviar":
				//verificndo e enviando a mensagem
				String mensagem = tAMensagem.getText();
				if(!mensagem.equals("")) {
					Mensageiro.enviarEmail(aluno.getEmail(), mensagem, "Contatação do monitor");
					JOptionPane.showMessageDialog(null, "E-mail enviado para " + aluno.getNome());
					dispose();
					new TelaDetalharEditalResultado("Resultado final", "Resultado final", "coordenador", edital);
				} else {
					JOptionPane.showMessageDialog(null, "A mensagem está vazia");
				}
				break;	
			}
		}
	}

	public void adicionarTextArea() {

		tAMensagem = new JTextArea();
		tAMensagem.setBounds(150, 130, 360, 170);
		tAMensagem.setLineWrap(true);
		tAMensagem.setWrapStyleWord(true);
		add(tAMensagem);
	}

	public void adicionarBotoes() {

		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		JButton btEnviar = new JButton("Enviar");
		btEnviar.setBounds(400, 350, 100, 30);
		btEnviar.addActionListener(ouvinte);
		add(btEnviar);

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(520, 350, 100, 30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);

	}
}