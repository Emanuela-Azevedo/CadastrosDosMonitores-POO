package janelas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import entidades.Aluno;
import entidades.Coordenador;
import projeto.central.CentralDeInformacoes;
import projeto.central.Persistencia;
import projeto.telasPadroes.TelaPadraoImagem;

/**
 * 
 * @author george e Manu
 *
 */

public class JanelaLogin extends TelaPadraoImagem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfEmail;
	private JPasswordField pfSenha;

	public JanelaLogin(){
		super("Fazer Login","Fazer Login");
		adicionarLabel();
		adicionarTextFields();
		adicionarBotoes();
		setVisible(true);
	}

	private class OuvinteDosBotoes implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			CentralDeInformacoes central = Persistencia.getInstance().recuperar();

			String email = tfEmail.getText();
			String senha = new String(pfSenha.getPassword());
			switch(e.getActionCommand()) {
			case "Entrar":
				//Faz as verificações e entrar no menu correto 
				Coordenador coordenador = central.getCoordenador();
				Aluno aluno = central.recuperarAlunoPorEmail(email);
				if(aluno != null || coordenador.getEmail().equals(email)) {
					if(aluno != null && aluno.getSenha().equals(senha) && aluno.getEmail().equals(email)) {
						central.setAlunoLogado(aluno); //salva o aluno logado
						Persistencia.getInstance().salvar(central);
						new JanelaMenuAluno(); //Abrir menu do aluno
						dispose();//Fechar Janela
					} else if (coordenador.getEmail().equals(email) && coordenador.getSenha().equals(senha)){
						new JanelaMenuCoordenador();//Abrir menu do coordenador 
						dispose();//fechar janela
					} else {
						JOptionPane.showMessageDialog(null, "Senha errada!");
					}
				} else {
					JOptionPane.showMessageDialog(null,"E-mail inexistente!");
				}
				break;
			//recuperar senha
			case "Recuperar senha":
				new JanelaRecuperarSenha();
				dispose();
				break;
			//nova conta
			case "Nova Conta":
				new JanelaCadastrarAluno();
				dispose();
				break;

			}

		}

	}

	public void adicionarBotoes() {
		//Ouvinte interno
		OuvinteDosBotoes ouvinte = new OuvinteDosBotoes();

		JButton btSalvar = new JButton("Entrar"); 
		btSalvar.setBounds(400,330,100,30);
		btSalvar.addActionListener(ouvinte);
		add(btSalvar);

		JButton btRecuperarSenha = new JButton("Recuperar senha"); 
		btRecuperarSenha.setBounds(220,205,140,20);
		btRecuperarSenha.addActionListener(ouvinte);
		add(btRecuperarSenha);

		JButton btNovaConta = new JButton("Nova Conta"); 
		btNovaConta.setBounds(220,230,100,20);
		btNovaConta.addActionListener(ouvinte);
		add(btNovaConta);

	}

	public void adicionarTextFields() {
		tfEmail = new JTextField();
		tfEmail.setBounds(220, 120 , 270, 25);
		add(tfEmail);

		pfSenha = new JPasswordField();
		pfSenha.setBounds(220, 160 , 270, 25);
		add(pfSenha);

	}

	public void adicionarLabel() {
		Font font = new Font("Georgia", Font.ITALIC, 15);

		JLabel lbEmail = new JLabel("E-mail: ");
		lbEmail.setBounds(160, 120, 100, 30);
		lbEmail.setFont(font);
		add(lbEmail);

		JLabel lbSenha = new JLabel("Senha: ");
		lbSenha.setBounds(158, 160, 100, 30);
		lbSenha.setFont(font);
		add(lbSenha);

	}
}
