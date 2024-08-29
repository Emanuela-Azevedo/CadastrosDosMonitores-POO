package projeto.central;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * 
 * @author Manu
 *
 */
public class Mensageiro {
	public static void enviarEmail(String destinatario, String mensagem, String titulo) {
		SimpleEmail email = new SimpleEmail();
		
		try {
			email.setHostName("smtp.gmail.com");
			
			email.setAuthentication("jaine.ifpb2023@gmail.com", "mgnqnygtaroabqve");
			email.setSSL(true);
			email.addTo(destinatario);// pode ser qualquer email
			email.setFrom(destinatario);// será passado o email que você fará a autenticação
			email.setSubject(titulo);
			email.setMsg(mensagem);
			email.send();
		} catch (EmailException e) {
			System.out.println(e.getMessage());
		}
	}
}
