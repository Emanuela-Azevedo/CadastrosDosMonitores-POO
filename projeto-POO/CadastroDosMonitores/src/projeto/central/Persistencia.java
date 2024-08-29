package projeto.central;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

/**
 * 
 * @author Manu
 *
 */

public class Persistencia {

	private static final String NOME_DO_ARQUIVO = "Central.xml";
	private XStream xstream = new XStream(new DomDriver());

	private static Persistencia instance;

	public static Persistencia getInstance() {
		if (instance == null) {
			instance = new Persistencia();
		}
		return instance;
	}

	private Persistencia() {
		xstream.addPermission(AnyTypePermission.ANY);
	}

	// Metodo para salvar
	public void salvar(CentralDeInformacoes central) {
		String xml = xstream.toXML(central);
		try {
			File arquivo = new File(NOME_DO_ARQUIVO);
			// criando o arquivo
			arquivo.createNewFile();
			PrintWriter pw = new PrintWriter(arquivo);
			pw.print(xml);
			pw.close();
		} catch (IOException e) {
		}
	}

	// Metodo para recuperar a central
	public CentralDeInformacoes recuperar() {
		File arquivo = new File(NOME_DO_ARQUIVO);
		if (arquivo.exists()) {
			CentralDeInformacoes central = (CentralDeInformacoes) xstream.fromXML(arquivo);
			return central;
		} else {
			return new CentralDeInformacoes();
		}

	}

}
