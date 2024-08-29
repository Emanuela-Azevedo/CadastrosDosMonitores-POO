package janelas.menu.coordenador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import entidades.Disciplina;
import entidades.EditalDeMonitoria;
import projeto.central.CentralDeInformacoes;
import projeto.central.Persistencia;
import projeto.telasPadroes.TelaPadraoImagem;

/**
 * 
 * @author Manu
 *
 */

public class JanelaDetalharEditalSemResultado extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CentralDeInformacoes central = Persistencia.getInstance().recuperar();
	private EditalDeMonitoria edital;
	private JTable tabela;

	public JanelaDetalharEditalSemResultado(EditalDeMonitoria edital) {
		super("Detalhar Edital", "Detalhar Edital");
		this.edital = edital;

		adicionarTabela();
		adicionarBotoes();
		adicionarLabel();
		setVisible(true);
	}

	private class OuvinteDosBotoes implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Voltar":
				new JanelaListarEditais();
				dispose();
				break;
			// Encerrando o edital manualmente
			case "Encerrar":
				for (EditalDeMonitoria ep : central.getTodosOsEditais()) {
					if (ep.getId() == edital.getId()) {
						ep.setSituacaoDoEdital("Encerrado");
						// ep.setDataFim(LocalDate.now());
					}
				}
				Persistencia.getInstance().salvar(central);
				new JanelaListarEditais();
				dispose();
				break;
			case "Clonar":
				//clona o edital
				new JanelaCadastrarEdital(edital, "Clonar Edital de Monitoria");
				dispose();
				break;
			//Mostra todas as inscrições do edital
			case "Inscrições":
				int cond = 0;
				if (edital.getDisciplinas() != null) {
					for (Disciplina disciplina : edital.getDisciplinas()) {
						if (!disciplina.getInscricoes().isEmpty()) {
							cond = 1;
							new JanelaListarInscricao(edital);
							dispose();
							break;
						}
					}

				}
				if (cond == 0) {
					JOptionPane.showMessageDialog(null, "Esse edital não possui inscrições!");
				}
				break;
			case "Editar":
				new JanelaCadastrarEdital(edital, "Editar Edital de Monitoria");
				dispose();
				break;

			}
		}
	}

	private void adicionarTabela() {
		// adicionando tabela
		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("Disciplinas / Vagas:");
		modelo.addColumn("Remuneradas");
		modelo.addColumn("Voluntários");

		if (edital.getDisciplinas() != null) {
			for (Disciplina disciplina : edital.getDisciplinas()) {
				Object[] linha = new Object[4];
				linha[0] = disciplina.getNomeDaDisciplina();
				linha[1] = disciplina.getQtdVagasRemuneradas();
				linha[2] = disciplina.getQtdVagasVoluntario();
				modelo.addRow(linha);
			}
		}

		tabela = new JTable(modelo);
		JScrollPane jsDisciplinas = new JScrollPane(tabela);
		jsDisciplinas.setBounds(110, 210, 380, 100);
		jsDisciplinas.createVerticalScrollBar();
		add(jsDisciplinas);
	}

	public void adicionarLabel() {
		Font font = new Font("Georgia", Font.ITALIC, 15);

		JLabel lbNumeroDoEdital = new JLabel("Edital: " + edital.getNumeroDoEdital());
		lbNumeroDoEdital.setBounds(280, 80, 300, 30);
		lbNumeroDoEdital.setFont(font);
		add(lbNumeroDoEdital);

		String[] dataInicio = edital.getDataInicio().toString().split("-");
		String dataDeInicio = dataInicio[2] + "/" + dataInicio[1] + "/" + dataInicio[0];
		JLabel lbDataDeInicio = new JLabel("Data de Inicio: " + dataDeInicio);
		lbDataDeInicio.setBounds(130, 120, 210, 30);
		lbDataDeInicio.setFont(font);
		add(lbDataDeInicio);

		String[] dataFim = edital.getDataFim().toString().split("-");
		String dataDoFim = dataFim[2] + "/" + dataFim[1] + "/" + dataFim[0];
		JLabel lbDataFim = new JLabel("Data de Término: " + dataDoFim);
		lbDataFim.setBounds(350, 120, 210, 30);
		lbDataFim.setFont(font);
		add(lbDataFim);

		JLabel lbPesoCre = new JLabel("Peso do CRE: " + edital.getPesoCRE());
		lbPesoCre.setBounds(175, 145, 200, 30);
		lbPesoCre.setFont(font);
		add(lbPesoCre);

		JLabel lbPesoNota = new JLabel("Peso da Nota: " + edital.getPesoNota());
		lbPesoNota.setBounds(400, 145, 200, 30);
		lbPesoNota.setFont(font);
		add(lbPesoNota);

		JLabel lbQtdInscricao = new JLabel("Quantidade de Inscrição por Aluno: " + edital.getQtdDeInscricaoPorAluno());
		lbQtdInscricao.setBounds(200, 170, 300, 30);
		lbQtdInscricao.setFont(font);
		add(lbQtdInscricao);
	}

	public void adicionarBotoes() {
		// Ouvintes interno
		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		JButton btInscricao = new JButton("Inscrições");
		btInscricao.setBounds(499, 250, 95, 30);
		btInscricao.addActionListener(ouvinte);
		add(btInscricao);

		JButton btEditar = new JButton("Editar");
		btEditar.setBounds(200, 350, 90, 30);
		btEditar.addActionListener(ouvinte);
		add(btEditar);

		JButton btClonar = new JButton("Clonar");
		btClonar.setBounds(300, 350, 90, 30);
		btClonar.addActionListener(ouvinte);
		add(btClonar);

		JButton btEncerrar = new JButton("Encerrar");
		btEncerrar.setBounds(400, 350, 90, 30);
		btEncerrar.addActionListener(ouvinte);
		add(btEncerrar);

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(500, 350, 90, 30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);
	}

}
