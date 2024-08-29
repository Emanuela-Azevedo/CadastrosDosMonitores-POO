package janelas.menu.coordenador;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import entidades.Disciplina;
import projeto.telasPadroes.TelaPadraoImagem;
/** 
 * @author Manu
 */

public class JanelaAdicionarDisciplinaNoEdital extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private ArrayList<Disciplina> disciplinas;
	private JTextField tfNomeDaDisciplina;
	private JComboBox<String> cbQtdVagasRemuneradas;
	private JComboBox<String> cbQtdVagasVoluntarias;

	public JanelaAdicionarDisciplinaNoEdital() {
		super("Nova Discilpina", "Adicionar nova Disciplina");
		//this.disciplinas = disciplinas;
		adicionarCombo();
		adicionarBotoes();
		adicionarTextFields();
		adicionarLabel();
		setVisible(true);
	}

	private class OuvinteDosBotoes implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Salvar")) {
				if (!tfNomeDaDisciplina.getText().equals("")) { // verificar se há um nome na disciplina
					int vagasR = Integer.parseInt(String.valueOf(cbQtdVagasRemuneradas.getSelectedItem()));
					int vagasV = Integer.parseInt(String.valueOf(cbQtdVagasVoluntarias.getSelectedItem()));
					if (vagasR + vagasV != 0) { //Verificar se há pelo menos uma vaga disponível
						String nomeDaDisciplina = tfNomeDaDisciplina.getText();
						String QtdVagasRemuneradas = (String) cbQtdVagasRemuneradas.getSelectedItem();
						String QtdVagasVoluntarias = (String) cbQtdVagasVoluntarias.getSelectedItem();
						//Criando disciplina
						Disciplina disciplina = new Disciplina(nomeDaDisciplina, QtdVagasVoluntarias,
								QtdVagasRemuneradas);

						JanelaCadastrarEdital.getDisciplinas().add(disciplina);
						JOptionPane.showMessageDialog(null, "Disciplina Adicionada!");

						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Adicione pelo menos uma vaga!");		
					}
				} else {
					JOptionPane.showMessageDialog(null, "Adicione o nome da Disciplina!");
				}

			} else {
				dispose();
			}
		}

	}

	public void adicionarBotoes() {
		// Ouvinte interno
		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		JButton btSalvar = new JButton("Salvar");
		btSalvar.setBounds(400, 350, 90, 30);
		btSalvar.addActionListener(ouvinte);
		add(btSalvar);

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(510, 350, 90, 30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);

	}

	public void adicionarLabel() {
		Font font = new Font("Georgia", Font.ITALIC, 15);

		JLabel lbNomeDaDisciplina = new JLabel("Nome da disciplina:");
		lbNomeDaDisciplina.setBounds(150, 120, 250, 30);
		lbNomeDaDisciplina.setFont(font);
		add(lbNomeDaDisciplina);

		JLabel lbQtdVagasRemunerads = new JLabel("Quantidade de vagas remuneradas:");
		lbQtdVagasRemunerads.setBounds(150, 150, 250, 30);
		lbQtdVagasRemunerads.setFont(font);
		add(lbQtdVagasRemunerads);

		JLabel lbQtdVagasVoluntarias = new JLabel("Quantidade de vagas voluntárias:");
		lbQtdVagasVoluntarias.setBounds(150, 180, 250, 30);
		lbQtdVagasVoluntarias.setFont(font);
		add(lbQtdVagasVoluntarias);

	}

	public void adicionarTextFields() {
		tfNomeDaDisciplina = new JTextField();
		tfNomeDaDisciplina.setBounds(300, 124, 180, 20);
		add(tfNomeDaDisciplina);

	}

	public void adicionarCombo() {
		String[] num = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

		cbQtdVagasRemuneradas = new JComboBox<String>(num);
		cbQtdVagasRemuneradas.setBounds(410, 153, 45, 20);
		add(cbQtdVagasRemuneradas);

		cbQtdVagasVoluntarias = new JComboBox<String>(num);
		cbQtdVagasVoluntarias.setBounds(410, 183, 45, 20);
		add(cbQtdVagasVoluntarias);
	}

}
