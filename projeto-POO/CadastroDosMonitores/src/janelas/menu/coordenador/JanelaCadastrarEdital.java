package janelas.menu.coordenador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import entidades.Disciplina;
import entidades.EditalDeMonitoria;
import entidades.Inscricao;
import janelas.JanelaMenuCoordenador;
import projeto.central.CentralDeInformacoes;
import projeto.central.Persistencia;
import projeto.telasPadroes.TelaPadraoImagem;

/**
 * 
 * @author George e Manu
 *
 */

public class JanelaCadastrarEdital extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfNumeroDoEdital;
	private JComboBox<String> cbQtdInscricao;
	private JFormattedTextField tfDataInicio;
	private JFormattedTextField tfDataLimite;
	private JTextField tfPesoCRE;
	private JTextField tfPesoNota;
	private static ArrayList<Disciplina> disciplinas;
	private long idDeVerificacao;
	private boolean verificaEdicao = false;
	private JTable tabela;
	CentralDeInformacoes central = Persistencia.getInstance().recuperar();

	public JanelaCadastrarEdital(EditalDeMonitoria e, String titulo) { // Se for passado null, será um novo objeto, se
		// não ele clona
		super(titulo, titulo);
		JanelaCadastrarEdital.disciplinas = new ArrayList<Disciplina>();
		adicionarLabel();
		adicionarTextFields();
		adicionarCombo();

		if (e != null) { // Se for null, ele preenche as informações dos campos
			preencherInformacoes(e);
			// se a tela for Editar Edital de Monitoria
			if (titulo.equals("Editar Edital de Monitoria")) {
				tfNumeroDoEdital.setEditable(false);
				idDeVerificacao = e.getId();
				// se a tela for Clonar edital de monitoria
			} else {
				for (Disciplina d : disciplinas) {
					d.setInscricoes(new ArrayList<Inscricao>());
				}
			}
		}

		adicionarJScrollPane();
		adicionarBotoes();
		setVisible(true);
	}
	
	//Prenche as informações
	public void preencherInformacoes(EditalDeMonitoria e) {
		tfNumeroDoEdital.setText(e.getNumeroDoEdital());
		cbQtdInscricao.setSelectedIndex(Integer.parseInt(e.getQtdDeInscricaoPorAluno()) - 1);
		DateTimeFormatter parser = new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy").toFormatter();
		tfDataInicio.setText(e.getDataInicio().format(parser));
		tfDataLimite.setText(e.getDataFim().format(parser));
		tfPesoCRE.setText("" + e.getPesoCRE());
		tfPesoNota.setText("" + e.getPesoNota());
		disciplinas = e.getDisciplinas();
	}

	class OuvinteDosBotoes implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Voltar":
				dispose();
				new JanelaMenuCoordenador();
				break;
			case "Salvar":
				String valorDataInicio = tfDataInicio.getText();
				String valorDataFim = tfDataLimite.getText();
				float pesoCRE = 0;
				float pesoNota = 0;
				boolean dataValida = false;

				LocalDate dataInicio = null;
				LocalDate dataFim = null;

				// Verificando se a data é valida.
				try {
					dataInicio = LocalDate.of(Integer.parseInt(valorDataInicio.split("/")[2]),
							Integer.parseInt(valorDataInicio.split("/")[1]),
							Integer.parseInt(valorDataInicio.split("/")[0]));
					dataFim = LocalDate.of(Integer.parseInt(valorDataFim.split("/")[2]),
							Integer.parseInt(valorDataFim.split("/")[1]), Integer.parseInt(valorDataFim.split("/")[0]));
					dataValida = true;
				} catch (Exception date) {
					JOptionPane.showMessageDialog(null, "A Data é invalida!");
				}

				if (dataValida == true) {
					// verificando se os pesos das notas são validos
					boolean pesoValido = false;

					try {
						pesoCRE = Float.parseFloat(tfPesoCRE.getText());
						pesoNota = Float.parseFloat(tfPesoNota.getText());
						if ((pesoCRE + pesoNota) == 1) {
							pesoValido = true;
						} else {
							JOptionPane.showMessageDialog(null, "A soma do Peso do CRE e da Nota deve ser 1");
						}
					} catch (Exception erro) {
						JOptionPane.showMessageDialog(null,
								"Digite um valor valido. Ex: 0.5 \n" + "Para o peso CRE e peso Nota ");
					}

					if (pesoValido == true) {

						if (disciplinas.size() != 0) {

							if (!tfNumeroDoEdital.getText().equals("")) {

								String numeroDoEdital = tfNumeroDoEdital.getText();
								String QtdMaximaDeInscricao = (String) cbQtdInscricao.getSelectedItem();

								// Criando edital
								EditalDeMonitoria edital = new EditalDeMonitoria(numeroDoEdital, QtdMaximaDeInscricao,
										dataInicio, dataFim, pesoCRE, pesoNota, disciplinas);

								if (idDeVerificacao != 0) { // chama o metodo editar
									edital = validarEdital(idDeVerificacao, dataInicio, dataFim, pesoCRE, pesoNota,
											disciplinas, QtdMaximaDeInscricao);
									if (verificaEdicao) {
										//Editando e salvando
										central.adicionarEdital(edital);
										JOptionPane.showMessageDialog(null, "Edital Editado!");
										Persistencia.getInstance().salvar(central);
										new JanelaMenuCoordenador();
										dispose();
									}
									break;
								}

								else if (central.adicionarEdital(edital)) {
									JOptionPane.showMessageDialog(null, "Edital Adicionado!");
									Persistencia.getInstance().salvar(central);
									new JanelaMenuCoordenador();
									dispose();
								} else {
									JOptionPane.showMessageDialog(null, "O edital já existe no sistema!");
								}
							} else {
								JOptionPane.showMessageDialog(null, "O número do edital não pode ser vazio!");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Adicione pelo menos uma disciplina!");
						}
					}
				}
				break;
			case "Adicionar Disciplina":
				new JanelaAdicionarDisciplinaNoEdital();
				break;

			case "Atualizar Disciplinas":
				atualizarJScrollPane();
				break;
			case "Excluir Disciplina":
				int linhaSelecionada = tabela.getSelectedRow();
				if (linhaSelecionada == -1) {
					JOptionPane.showMessageDialog(null, "Selecione a Disciplina que deseja Excluir!");
				} else {
					int op = JOptionPane.showConfirmDialog(null, "Excluir Disciplina?", "Reabrir",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (op == JOptionPane.YES_OPTION) {
						disciplinas.remove(linhaSelecionada);
						atualizarJScrollPane();
					}
					
				}
				break;
			}

		}

	}

	// metodo para editar edital
	public EditalDeMonitoria validarEdital(long id, LocalDate dataInicio, LocalDate dataFim, float pesoCRE,
			float pesoNota, ArrayList<Disciplina> disciplinas, String qtd) {

		EditalDeMonitoria edital = central.recuperarEditalPorID(id);
		LocalDate data = LocalDate.now();

		if (dataInicio.isAfter(data) || dataInicio.isEqual(data)) {
			if (dataFim.isAfter(data) || dataFim.isEqual(data)) {
				if (Integer.parseInt(edital.getQtdDeInscricaoPorAluno()) <= Integer.parseInt(qtd)) {
					edital.setDataInicio(dataInicio);
					edital.setDataFim(dataFim);
					edital.setPesoCRE(pesoCRE);
					edital.setPesoNota(pesoNota);
					edital.setDisciplinas(disciplinas);
					edital.setQtdDeInscricaoPorAluno(qtd);
					verificaEdicao = true;
				} else {
					JOptionPane.showMessageDialog(null, "A quantidade máxima não pode ser diminuida");
				}

			} else {
				JOptionPane.showMessageDialog(null, "Data final não pode ser anterior da data atual");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Data início não pode ser anterior da data atual");

		}
		return edital;
	}

	public boolean adicionarDiscipina(Disciplina disciplina) {
		disciplinas.add(disciplina);
		return true;
	}

	public void adicionarBotoes() {
		// Ouvinte interno
		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		JButton btSalvar = new JButton("Salvar");
		btSalvar.setBounds(415, 350, 90, 30);
		btSalvar.addActionListener(ouvinte);
		add(btSalvar);

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(520, 350, 90, 30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);

		JButton btAdicionarDiscipliba = new JButton("Adicionar Disciplina");
		btAdicionarDiscipliba.setBounds(200, 350, 200, 30);
		btAdicionarDiscipliba.addActionListener(ouvinte);
		add(btAdicionarDiscipliba);

		JButton btAtualizarDisciplinas = new JButton("Atualizar Disciplinas");
		btAtualizarDisciplinas.setBounds(450, 240, 150, 30);
		btAtualizarDisciplinas.addActionListener(ouvinte);
		add(btAtualizarDisciplinas);

		JButton btExcluirDisciplina = new JButton("Excluir Disciplina");
		btExcluirDisciplina.setBounds(450, 280, 150, 30);
		btExcluirDisciplina.addActionListener(ouvinte);
		add(btExcluirDisciplina);

	}

	public void adicionarLabel() {
		Font font = new Font("Georgia", Font.ITALIC, 15);
		
		JLabel lbDataInicio = new JLabel("Data Inicio:");
		lbDataInicio.setBounds(200, 90, 100, 20);
		lbDataInicio.setFont(font);
		add(lbDataInicio);

		JLabel lbDataLimite = new JLabel("Data Limite:");
		lbDataLimite.setBounds(380, 90, 100, 20);
		lbDataLimite.setFont(font);
		add(lbDataLimite);

		JLabel lbNumeroDoEdital = new JLabel("Número do Edital:");
		lbNumeroDoEdital.setBounds(100, 150, 150, 20);
		lbNumeroDoEdital.setFont(font);
		add(lbNumeroDoEdital);

		JLabel lbQtdVagasPorAluno = new JLabel("Limite de inscrições por aluno:");
		lbQtdVagasPorAluno.setBounds(100, 180, 260, 20);
		lbQtdVagasPorAluno.setFont(font);
		add(lbQtdVagasPorAluno);

		JLabel lbPesoDoCRE = new JLabel("Peso do CRE:");
		lbPesoDoCRE.setBounds(390, 150, 100, 20);
		lbPesoDoCRE.setFont(font);
		add(lbPesoDoCRE);

		JLabel lbPesoDaNota = new JLabel("Peso da Nota:");
		lbPesoDaNota.setBounds(390, 180, 100, 20);
		lbPesoDaNota.setFont(font);
		add(lbPesoDaNota);

	}

	public void adicionarTextFields() {

		tfNumeroDoEdital = new JTextField();
		tfNumeroDoEdital.setBounds(230, 150, 100, 20);
		add(tfNumeroDoEdital);

		tfPesoCRE = new JTextField();
		tfPesoCRE.setText("0.5");
		tfPesoCRE.setBounds(485, 150, 40, 20);
		add(tfPesoCRE);

		tfPesoNota = new JTextField();
		tfPesoNota.setText("0.5");
		tfPesoNota.setBounds(485, 180, 40, 20);
		add(tfPesoNota);

		try {
			MaskFormatter mascaraDeData = new MaskFormatter("##/##/####");

			tfDataInicio = new JFormattedTextField(mascaraDeData);
			tfDataInicio.setBounds(200, 110, 80, 25);
			tfDataInicio.setHorizontalAlignment(JLabel.CENTER);
			add(tfDataInicio);

			tfDataLimite = new JFormattedTextField(mascaraDeData);
			tfDataLimite.setBounds(380, 110, 80, 25);
			tfDataLimite.setHorizontalAlignment(JLabel.CENTER);
			add(tfDataLimite);

		} catch (ParseException e) {
		}

	}

	public void atualizarJScrollPane() {

		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("Disciplinas adicionadas no edital");

		if (disciplinas != null) {
			for (Disciplina disciplina : disciplinas) {
				Object[] linha = new Object[1];
				linha[0] = disciplina.getNomeDaDisciplina();
				modelo.addRow(linha);
			}
		}

		tabela.setModel(modelo);
		tabela.repaint();
	}

	public void adicionarJScrollPane() {

		DefaultTableModel modelo = new DefaultTableModel();

		modelo.addColumn("Disciplinas adicionadas no edital");

		if (disciplinas != null) {
			for (Disciplina disciplina : disciplinas) {
				Object[] linha = new Object[1];
				linha[0] = disciplina.getNomeDaDisciplina();
				modelo.addRow(linha);
			}
		}
		tabela = new JTable(modelo);
		JScrollPane jsDisciplinas = new JScrollPane(tabela);
		jsDisciplinas.setBounds(200, 215, 240, 100);
		jsDisciplinas.createVerticalScrollBar();
		add(jsDisciplinas);
	}

	public void adicionarCombo() {
		String[] num = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
		cbQtdInscricao = new JComboBox<String>(num);
		cbQtdInscricao.setBounds(320, 180, 45, 20);
		add(cbQtdInscricao);

	}

	public static ArrayList<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	
}