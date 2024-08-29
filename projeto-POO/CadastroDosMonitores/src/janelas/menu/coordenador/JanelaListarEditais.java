package janelas.menu.coordenador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import entidades.Disciplina;
import entidades.EditalDeMonitoria;
import entidades.Inscricao;
import janelas.JanelaMenuCoordenador;
import projeto.central.CentralDeInformacoes;
import projeto.central.Persistencia;
import projeto.telasPadroes.TelaDetalharEditalResultado;
import projeto.telasPadroes.TelaPadraoImagem;

/**
 * 
 * @author Manu george
 *
 */

public class JanelaListarEditais extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CentralDeInformacoes central = Persistencia.getInstance().recuperar();
	private ArrayList<EditalDeMonitoria> editais = central.getTodosOsEditais();
	private JTable tabela;

	public JanelaListarEditais() {
		super("Listar editais", "Listar Editais");
		adicionarBotoes();
		adicionarTabela();
		setVisible(true);
	}

	class OuvinteDosBotoes implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {

			case "Voltar":
				dispose();
				new JanelaMenuCoordenador();
				break;
			// Aqui ele verifica a situação do edital e execulta a determinada
			case "Detalhar":
				int editalSelecionado = tabela.getSelectedRow();
				if (editalSelecionado == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um Edital!");
				} else {
					EditalDeMonitoria edital = editais.get(editalSelecionado);
					if (edital.getSituacaoDoEdital().equals("Resultado Preliminar")) {
						edital = central.recuperarEditalPorID(edital.getId());
						// Calcula o resultado do edital
						for (Disciplina disciplina : edital.getDisciplinas()) {
							disciplina.calcularResultado();
						}
						Persistencia.getInstance().salvar(central);
						// Essa tela mostra o resultado preliminar do edital
						new TelaDetalharEditalResultado("Resultado Preliminar", "Resultado Preliminar", "coordenador",
								edital);
						dispose();
						// reabrir o edital encerrado
					} else if (edital.getSituacaoDoEdital().equals("Encerrado")) {

						int op = JOptionPane.showConfirmDialog(null, "Deseja reabrir o edital?", "Reabrir",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (op == JOptionPane.YES_OPTION) {
							central.recuperarEditalPorID(edital.getId()).setSituacaoDoEdital("Aberto");
							Persistencia.getInstance().salvar(central);
							new JanelaListarEditais();
							dispose();
						}

					} else if (edital.getSituacaoDoEdital().equals("Resultado Final")) {
						// Essa tela mostra o resultado final do edital
						new TelaDetalharEditalResultado("Resultado Final", "Resultado Final", "coordenador", edital);
						dispose();
					} else if (edital.getSituacaoDoEdital().equals("Aberto")
							|| edital.getSituacaoDoEdital().equals("")) {
						if (edital.situacao().equals("Aberto")) {
							dispose();
							// Essa tela mostra o edital aberto
							new JanelaDetalharEditalSemResultado(edital);
						} else {
							String[] dataInicio = edital.getDataInicio().toString().split("-");
							String dataDeInicio = dataInicio[2] + "/" + dataInicio[1] + "/" + dataInicio[0];
							// ele so entra aqui se o edital tiver Fechado
							int op = JOptionPane.showConfirmDialog(null,
									"Edital Fechado so abre dia: " + dataDeInicio + "\n"
											+ "Deseja editar a data do Edital?",
									"Reabrir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
							if (op == JOptionPane.YES_OPTION) {
								// abre a tela de editar para editar a data
								new JanelaCadastrarEdital(edital, "Editar Edital de Monitoria");
								dispose();
								break;
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Selecione um Edital Aberto!");
					}
				}
				break;
			case "Gerar Resultado":
				editalSelecionado = tabela.getSelectedRow();
				if (editalSelecionado == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um Edital!");
				} else {
					EditalDeMonitoria edital = central.recuperarEditalPorID(editais.get(editalSelecionado).getId());
					edital.setSituacaoDoEdital("Resultado Preliminar");
					//Calculando a nota final de cada disciplina
					for (Disciplina disciplina : edital.getDisciplinas()) {
						for (Inscricao i : disciplina.getInscricoes()) {
							i.setNotaFinal(edital.notaFinal(i.getNotaCRE(), i.getNotaDisciplina()));
						}

					}
					//calculando o resultado
					for (Disciplina disciplina : edital.getDisciplinas()) {
						disciplina.calcularResultado();
					}

					Persistencia.getInstance().salvar(central);
					dispose();
					new JanelaListarEditais();
				}
				break;
			}
		}
	}

	// Listar os editais na tabela
	public void adicionarTabela() {

		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("Editais");
		modelo.addColumn("Situação");

		if (editais != null) {
			for (EditalDeMonitoria edital : editais) {
				Object[] linha = new Object[2];
				linha[0] = edital.getNumeroDoEdital();
				if (edital.getSituacaoDoEdital() == "") {
					linha[1] = edital.situacao();
				} else {
					linha[1] = edital.getSituacaoDoEdital();
				}
				modelo.addRow(linha);
			}
		}

		tabela = new JTable(modelo);
		JScrollPane jsDisciplinas = new JScrollPane(tabela);
		jsDisciplinas.setBounds(150, 130, 360, 170);
		jsDisciplinas.createVerticalScrollBar();
		add(jsDisciplinas);

	}

	public void adicionarBotoes() {
		// Ouvinte interno
		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		JButton btSalvar = new JButton("Detalhar");
		btSalvar.setBounds(415, 350, 90, 30);
		btSalvar.addActionListener(ouvinte);
		add(btSalvar);

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(520, 350, 90, 30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);

		JButton btGerarResultado = new JButton("Gerar Resultado");
		btGerarResultado.setBounds(270, 350, 130, 30);
		btGerarResultado.addActionListener(ouvinte);
		add(btGerarResultado);

	}
}
