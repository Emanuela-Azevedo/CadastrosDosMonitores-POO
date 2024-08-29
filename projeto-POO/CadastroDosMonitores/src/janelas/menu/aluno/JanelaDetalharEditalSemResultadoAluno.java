package janelas.menu.aluno;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entidades.Aluno;
import entidades.Disciplina;
import entidades.EditalDeMonitoria;
import projeto.central.Persistencia;
import projeto.telasPadroes.TelaPadraoImagem;

/**
 * 
 * @author George
 *
 */

public class JanelaDetalharEditalSemResultadoAluno extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EditalDeMonitoria edital;
	private JTable tabela;

	public JanelaDetalharEditalSemResultadoAluno(EditalDeMonitoria edital) {
		super("Detalhar Edital", "Detalhar Edital");
		this.edital = edital;
		adicionarLabel();
		adicionarTabela();
		adicionarBotoes();
		setVisible(true);

	}

	private class OuvinteDosBotoes implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "Voltar":
				dispose();
				new JanelaListarEditaisAlunos();
				break;
			case "Inscrever":
				int linhaSelecionada = tabela.getSelectedRow();
				if (linhaSelecionada == -1) {
					JOptionPane.showMessageDialog(null, "Selecione uma Disciplina!");
				} else {
					Disciplina disciplina = edital.getDisciplinas().get(linhaSelecionada);

					//Verifica se é possivel se inscrever
					Aluno aluno = Persistencia.getInstance().recuperar().getAlunoLogado();
					if(edital.jaAtingiuQuantidadeInscricao(aluno)) {
						JOptionPane.showMessageDialog(null, "Você já atingiu a Quantidade Máxima\n"
								+ "de Inscrições nesse edital!");
					} else {
						//verifica se ja esta inscrito na disciplina
						if(disciplina.jaEstaInscrito(aluno)) {
							JOptionPane.showMessageDialog(null, "Você já se inscreveu nesta Disciplina!");
						} else {
							//Abre a tela Para fazer a incrição
							new JanelaInscricaoNaDisciplinaDoEdital( edital, disciplina );
							dispose();
						}
					}	
				}
				break;
			}
		}
	}

	private void adicionarTabela() {

		DefaultTableModel modelo = new DefaultTableModel();
		//definir as colunas
		modelo.addColumn("Disciplina /  Vagas:");
		modelo.addColumn("remuneradas");
		modelo.addColumn("voluntario");
		//preencer linhas
		if(edital.getDisciplinas()!= null) {
			for(Disciplina disciplina : edital.getDisciplinas()) {
				Object[] linha = new Object[3];
				linha[0] = disciplina.getNomeDaDisciplina();
				linha[1] = disciplina.getQtdVagasRemuneradas();
				linha[2] = disciplina.getQtdVagasVoluntario();
				modelo.addRow(linha);
			}
		}

		tabela = new JTable(modelo);
		JScrollPane jsDisciplinas = new JScrollPane(tabela);
		jsDisciplinas.setBounds(180, 210, 350, 110);
		jsDisciplinas.createVerticalScrollBar();
		add(jsDisciplinas);

	}

	public void adicionarBotoes() {
		//Ouvinte interno
		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		JButton btInscrever = new JButton("Inscrever"); 
		btInscrever.setBounds(380,350,120,30);
		btInscrever.addActionListener(ouvinte);
		add(btInscrever);

		JButton btVoltar = new JButton("Voltar"); 
		btVoltar.setBounds(520,350,90,30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);

	}

	//exibir as informações do edital
	public void adicionarLabel() {
		Font font = new Font("Georgia", Font.ITALIC, 15);

		JLabel lbPesoCre = new JLabel("Peso do CRE: "+ edital.getPesoCRE());
		lbPesoCre.setBounds(410, 110, 200, 30);
		lbPesoCre.setFont(font);
		add(lbPesoCre);

		JLabel lbPesoNota = new JLabel("Peso da Nota: "+ edital.getPesoNota());
		lbPesoNota.setBounds(410, 140, 200, 30);
		lbPesoNota.setFont(font);
		add(lbPesoNota);

		JLabel lbNumeroDoEdital = new JLabel("Numero do Edital: "+ edital.getNumeroDoEdital());
		lbNumeroDoEdital.setBounds(190, 80, 310, 30);
		lbNumeroDoEdital.setFont(font);
		add(lbNumeroDoEdital);

		String[] dataInicio = edital.getDataInicio().toString().split("-");
		String dataDeInicio = dataInicio[2]+"/"+dataInicio[1]+"/"+dataInicio[0];
		JLabel lbDataDeInicio = new JLabel("Data de Inicio: "+ dataDeInicio);
		lbDataDeInicio.setBounds(190, 110, 210, 30);
		lbDataDeInicio.setFont(font);
		add(lbDataDeInicio);

		String[] dataFim = edital.getDataFim().toString().split("-");
		String dataDoFim = dataFim[2]+"/"+dataFim[1]+"/"+dataFim[0];
		JLabel lbDataFim = new JLabel("Data de Término: "+ dataDoFim);
		lbDataFim.setBounds(190, 140, 210, 30);
		lbDataFim.setFont(font);
		add(lbDataFim);

		JLabel lbQtdInscricao = new JLabel("Quantidade de Inscriçãao por Aluno: "+ edital.getQtdDeInscricaoPorAluno());
		lbQtdInscricao.setBounds(190, 170, 300, 30);
		lbQtdInscricao.setFont(font);
		add(lbQtdInscricao);

	}

}
