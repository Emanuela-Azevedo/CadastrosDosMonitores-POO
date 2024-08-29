package projeto.central;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entidades.Disciplina;
import entidades.EditalDeMonitoria;
import entidades.Inscricao;

/**
 * 
 * @author Manu
 *
 */

public class GeradorRelatorio {

	public static void gerarRelatorioDeResultado(String titulo, long idEdital, CentralDeInformacoes central) {
		EditalDeMonitoria edital = central.recuperarEditalPorID(idEdital);

		Document doc = new Document(PageSize.A4, 72, 72, 72, 72);

		try {
			PdfWriter.getInstance(doc, new FileOutputStream("Edital "+edital.getNumeroDoEdital()+" Relatório.pdf"));
			doc.open();

			Font f = new Font(FontFamily.COURIER, 20, Font.BOLD);

			Paragraph p = new Paragraph(titulo, f);
			p.setAlignment(Element.ALIGN_CENTER);

			doc.add(p);
			Paragraph linha = new Paragraph(" ");
			doc.add(linha);
			PdfPTable tabela = new PdfPTable(3);
			PdfPCell cabecalho = new PdfPCell();
			cabecalho.setColspan(3);
			cabecalho.setBackgroundColor(BaseColor.GRAY);
			tabela.addCell(cabecalho);
			tabela.addCell("Nome do Aluno");
			tabela.addCell("Disciplina");
			tabela.addCell("Resultado");

			for (Disciplina disciplina : edital.getDisciplinas()) {
				for (Inscricao i : disciplina.getInscricoes()) {
					tabela.addCell(i.getAluno().getNome());
					tabela.addCell(i.getDisciplina().getNomeDaDisciplina());
					tabela.addCell(i.getResultado());
				}
			}
			doc.add(tabela);

			doc.close();

		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		} 
	}

}
