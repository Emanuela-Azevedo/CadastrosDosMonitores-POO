package janelas.menu.aluno;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import Validacoes.Validacao;
import entidades.Aluno;
import entidades.Sexo;
import janelas.JanelaMenuAluno;
import janelas.menu.coordenador.JanelaPerfilDoAluno;
import projeto.central.CentralDeInformacoes;
import projeto.central.Persistencia;
import projeto.telasPadroes.TelaPadraoInformacoesAluno;

/**
 * 
 * @author George
 *
 */

public class JanelaEditarInformacoesAluno extends TelaPadraoInformacoesAluno {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Aluno aluno;
	private String usuario;

	public JanelaEditarInformacoesAluno(Aluno aluno, String usuario) {
		super("Editar Informações Pessoais", "Editar Informações");
		this.aluno = aluno;
		this.usuario = usuario;
		adicionarTextFields(aluno.getNome(), aluno.getMatricula(), aluno.getEmail(), aluno.getSenha());
		adicionarCombo(aluno.getSexo());
		adicionarBotoes();
		setVisible(true);

	}

	private class OuvinteDoBotaoSalvar implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Voltar":
				//verifica quem é o usuario e abre a tela corespondente
				if(usuario.equals("Aluno")) {
					new JanelaMenuAluno();
				} else {
					new JanelaPerfilDoAluno(aluno);
				}
				dispose();
				break;
			case "Salvar":
				try {
					String email = getTfEmail().getText();
					String senha = new String(getPfSenha().getPassword());
					String confirmarSenha = new String(getPfConfirmarSenha().getPassword());
					// validações
					Validacao validacao = new Validacao();
					validacao.validarEmail(email);
					validacao.validarSenha(senha, confirmarSenha);

					CentralDeInformacoes central = Persistencia.getInstance().recuperar();
					Aluno alunoEditar = central.recuperarAlunoPorEmail(aluno.getEmail());
					// fazendo as alterações
					alunoEditar.setNome(getTfNome().getText());
					alunoEditar.setMatricula(getTfMatricula().getText());
					alunoEditar.setEmail(email);
					alunoEditar.setSenha(senha);
					alunoEditar.setSexo((Sexo) getCbSexo().getSelectedItem());

					Persistencia.getInstance().salvar(central);
					JOptionPane.showMessageDialog(null, "As Informações foram Atualizadas!");
					//Vai definir Qual Janela Abrir
					if(usuario.equals("Aluno")) {
						new JanelaMenuAluno();
					} else {
						new JanelaPerfilDoAluno(alunoEditar);
					}
					dispose();
					break;
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, erro.getMessage());

				}
			}
		}
	}

	public void adicionarBotoes() {
		// ouvinte interno
		OuvinteDoBotaoSalvar ouvinte = new OuvinteDoBotaoSalvar();
		setBtSalvar(new JButton("Salvar"));
		getBtSalvar().setBounds(410, 350, 90, 30);
		getBtSalvar().addActionListener(ouvinte);
		getBtSalvar().setEnabled(false);
		add(getBtSalvar());

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(520, 350, 90, 30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);

	}

}
