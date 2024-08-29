package janelas.menu.coordenador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import entidades.Aluno;
import entidades.Disciplina;
import entidades.EditalDeMonitoria;
import entidades.Inscricao;
import janelas.menu.aluno.JanelaEditarInformacoesAluno;
import projeto.central.Persistencia;
import projeto.telasPadroes.TelaPadraoImagem;

/**
 * 
 * @author george
 *
 */

public class JanelaPerfilDoAluno extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aluno aluno;

	public JanelaPerfilDoAluno(Aluno aluno) {
		super("Janela Perfil do Aluno", "Perfil do Aluno");
		this.aluno = aluno;
		adicionarLabel();
		adicionarBotoes();
		adicionarTabela();
		setVisible(true);
	}

	private class OuvinteDosBotoes implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Voltar":
				new JanelaListarTodosAlunos();
				dispose();
				break;
			case "Editar Perfil":
				new JanelaEditarInformacoesAluno(aluno, "Coordenador");
				dispose();
				break;
			}

		}

	}
	//preenchendo as informações do aluno
	public void adicionarLabel() {
		Font font = new Font("Georgia", Font.ITALIC, 15);

		JLabel lbNome = new JLabel("Nome: " + aluno.getNome());
		lbNome.setBounds(190, 80, 300, 30);
		lbNome.setFont(font);
		add(lbNome);

		JLabel lbMatricula = new JLabel("Matricula: " + aluno.getMatricula());
		lbMatricula.setBounds(190, 110, 200, 30);
		lbMatricula.setFont(font);
		add(lbMatricula);

		JLabel lbEmail = new JLabel("E-mail: " + aluno.getEmail());
		lbEmail.setFont(font);
		lbEmail.setBounds(190, 140, 300, 30);
		add(lbEmail);

		JLabel lbSexo = new JLabel("Sexo: " + aluno.getSexo());
		lbSexo.setBounds(190, 170, 150, 30);
		lbSexo.setFont(font);
		add(lbSexo);

		JLabel lbSenha = new JLabel("Senha: " + aluno.getSenha());
		lbSenha.setBounds(360, 170, 150, 30);
		lbSenha.setFont(font);
		add(lbSenha);

	}

	public void adicionarTabela() {

		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("Edital");
		modelo.addColumn("Disciplina");
		modelo.addColumn("Situação");

		//Tabela com as informações de quais editais o aluno passou
		ArrayList<EditalDeMonitoria> editais = Persistencia.getInstance().recuperar().getTodosOsEditais();
		//recuperando a inscrição do aluno
		if (editais != null) {
			for (EditalDeMonitoria edital : editais) {
				for (Disciplina disciplinas : edital.getDisciplinas()) {
					for (Inscricao inscricao : disciplinas.getInscricoes()) {
						if ((inscricao.getResultado().equals("Classificado com Bolsa")
								|| inscricao.getResultado().equals("Classificado como Voluntario"))
								&& inscricao.getAluno().getMatricula().equals(aluno.getMatricula())) {
							String situacao;
							//Determinado a mensagem da situação
							if(inscricao.getResultado().equals("Classificado com Bolsa")){
								situacao = "Bolsista";
							} else {
								situacao = "Voluntario";
							}
							Object[] linha = new Object[3];
							linha[0] = edital.getNumeroDoEdital();
							linha[1] = inscricao.getDisciplina();
							linha[2] = situacao;
							modelo.addRow(linha);
						}
					}
				}

				
			}
		}

		JTable tabela = new JTable(modelo);
		
		JScrollPane jsDisciplinas = new JScrollPane(tabela);
		jsDisciplinas.setBounds(150, 200, 380, 100);
		jsDisciplinas.createVerticalScrollBar();
		add(jsDisciplinas);

	}

	public void adicionarBotoes() {
		// Ouvinte interno
		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		JButton btSalvar = new JButton("Editar Perfil");
		btSalvar.setBounds(390, 350, 115, 30);
		btSalvar.addActionListener(ouvinte);
		add(btSalvar);

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(520, 350, 90, 30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);

	}

}
