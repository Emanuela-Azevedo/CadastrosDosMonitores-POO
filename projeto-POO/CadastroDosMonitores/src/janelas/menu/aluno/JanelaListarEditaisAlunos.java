package janelas.menu.aluno;

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
import janelas.JanelaMenuAluno;
import projeto.central.CentralDeInformacoes;
import projeto.central.Persistencia;
import projeto.telasPadroes.TelaDetalharEditalResultado;
import projeto.telasPadroes.TelaPadraoImagem;

/**
 * 
 * @author George
 *
 */

public class JanelaListarEditaisAlunos extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CentralDeInformacoes central = Persistencia.getInstance().recuperar();

	private ArrayList<EditalDeMonitoria> editais = central.getTodosOsEditais();
	private JTable tabela;

	public JanelaListarEditaisAlunos() {
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
				new JanelaMenuAluno();
				break;
			case "Detalhar Edital":
				int linhaSelecionada = tabela.getSelectedRow();
				if (linhaSelecionada == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um Edital!");
				} else {
					EditalDeMonitoria edital = editais.get(linhaSelecionada);
					//verifica a situaç~co do edital
					if (edital.getSituacaoDoEdital().equals("Resultado Preliminar")) {
						edital = central.recuperarEditalPorID(edital.getId());
						for (Disciplina disciplina : edital.getDisciplinas()) {
							disciplina.calcularResultado();
						}
						Persistencia.getInstance().salvar(central);
						// Essa tela mostra o resultado preliminar do edital
						new TelaDetalharEditalResultado("Resultado Preliminar", "Resultado Preliminar", "aluno",
								edital);
						dispose();

					} else if (edital.getSituacaoDoEdital().equals("Resultado Final")) {
						// Essa tela mostra o resultado final do edital
						new TelaDetalharEditalResultado("Resultado Final", "Resultado Final", "aluno", edital);
						dispose();
					} else if (edital.getSituacaoDoEdital().equals("Aberto")
							|| edital.getSituacaoDoEdital().equals("")) {
						if (edital.situacao().equals("Aberto")) {
							dispose();
							// Essa tela mostra o edital em aberto
							new JanelaDetalharEditalSemResultadoAluno(edital);
						} else {
							JOptionPane.showMessageDialog(null, "Selecione um Edital Aberto!");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Selecione um Edital Aberto!");
					}
				}
				break;
			}
		}
	}

	private void adicionarTabela() {

		DefaultTableModel modelo = new DefaultTableModel();
		// definir as colunas
		modelo.addColumn("Numero do Edital");
		modelo.addColumn("Situação");
		// preencher lihas colunas
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

		JButton btSalvar = new JButton("Detalhar Edital");
		btSalvar.setBounds(380, 350, 120, 30);
		btSalvar.addActionListener(ouvinte);
		add(btSalvar);

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(520, 350, 90, 30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);

	}
}