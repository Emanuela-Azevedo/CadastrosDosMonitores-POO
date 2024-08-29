package janelas;

import projeto.telasPadroes.TelaPadraoInformacoesAluno;

/**
 * 
 * @author George
 *
 */

public class JanelaCadastrarAluno extends TelaPadraoInformacoesAluno{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JanelaCadastrarAluno() {
		super("Cadastrar Aluno","Cadastrar Aluno");
		adicionarTextFields(null, null, null, null);
		adicionarCombo(null);
		adicionarBotoes();
		setVisible(true);
	}
	
}