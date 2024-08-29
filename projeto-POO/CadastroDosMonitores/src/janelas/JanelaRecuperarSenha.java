package janelas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import projeto.central.CentralDeInformacoes;
import projeto.central.Mensageiro;
import projeto.central.Persistencia;
import projeto.telasPadroes.TelaPadraoImagem;

public class JanelaRecuperarSenha extends TelaPadraoImagem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField tfEmail;

	public JanelaRecuperarSenha() {
		super("Recuperar Senha","Recuperar Senha");
		adicionarLabel();
		adicionarBotoes();
		adicionarTextFields();
		setVisible(true);
	}

	private class OuvinteDoBotaoRecuperar implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Voltar")){
				dispose();
				new JanelaLogin();
			}else {
				CentralDeInformacoes central = Persistencia.getInstance().recuperar();
				String email = tfEmail.getText();
				//recuperara a senha
				String senhaRecuperada = central.recuperarSenhaPorEmail(email);
				if(senhaRecuperada != null) {
					//Eviar a senha para o email
					Mensageiro.enviarEmail(email, "A sua Senha é: "+ senhaRecuperada, "Recuperação de Senha");
					JOptionPane.showMessageDialog(null, "Sua senha foi enviada para o E-mail!");
					dispose();
					new JanelaLogin();
				}else {
					JOptionPane.showMessageDialog(null, "O Email não existe!");
				}
			}
		}

	}

	public void adicionarBotoes() {
		//ouvinte interno
		OuvinteDoBotaoRecuperar ouvinte = new OuvinteDoBotaoRecuperar();

		JButton btSalvar = new JButton("Recuperar");
		btSalvar.setBounds(270,200,100,30);
		btSalvar.addActionListener(ouvinte);
		add(btSalvar);

		JButton btVoltar = new JButton("Voltar");
		btVoltar.setBounds(450,350,100,30);
		btVoltar.addActionListener(ouvinte);
		add(btVoltar);
	}

	public void adicionarLabel() {
		JLabel lbEmail = new JLabel("E-mail:");
		lbEmail.setBounds(170, 150, 45, 25);
		add(lbEmail);
	}

	public void adicionarTextFields() {

		tfEmail = new JTextField();
		tfEmail.setBounds(215, 150 , 260, 25);
		add(tfEmail);
	}

}
