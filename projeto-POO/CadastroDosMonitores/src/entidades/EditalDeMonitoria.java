package entidades;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 
 * @author George
 *
 */

public class EditalDeMonitoria {

	private long id = System.currentTimeMillis();
	private String numeroDoEdital;
	private String qtdDeInscricaoPorAluno;
	private String situacaoDoEdital;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private float pesoCRE;
	private float pesoNota;
	private ArrayList<Disciplina> disciplinas;

	public EditalDeMonitoria(String numeroDoEdital, String qtdDeInscricaoPorAluno, LocalDate dataInicio,
			LocalDate dataFim, float pesoCRE, float pesoNota, ArrayList<Disciplina> disciplinas) {
		this.numeroDoEdital = numeroDoEdital;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.qtdDeInscricaoPorAluno = qtdDeInscricaoPorAluno;
		this.pesoCRE = pesoCRE;
		this.pesoNota = pesoNota;
		this.disciplinas = disciplinas;
		this.situacaoDoEdital = "";
	}

	// Recuperar disciplina pelo nome
	public Disciplina recuperarDiscipinaPorNome(String nome) {
		for (Disciplina disciplina : disciplinas) {
			if (disciplina.getNomeDaDisciplina().equals(nome)) {
				return disciplina;
			}
		}
		return null;
	}

	// Verificar se ja atingiu a quantidade maxima de inscrições
	public boolean jaAtingiuQuantidadeInscricao(Aluno aluno) {
		int cont = 0;
		for (Disciplina disciplina : disciplinas) {
			if (disciplina.jaEstaInscrito(aluno)) {
				cont++;
			}
		}
		int qtd = Integer.parseInt(qtdDeInscricaoPorAluno);
		if (qtd == cont) {
			return true;
		}
		return false;
	}

	
	// Calucular a media da nota do aluno
	public float notaFinal(float notaCRE, float nota) {
		float notaFinal = notaCRE * pesoCRE + nota * pesoNota;
		return notaFinal;

	}

	public String toString() {
		return numeroDoEdital;
	}

	// Verificando se o edital esta aberto ou fechado
	public String situacao() {
		LocalDate data = LocalDate.now();
		if (dataInicio.isAfter(data)) {
			return "Fechado";
		}
		if ((dataInicio.isBefore(data)) && dataFim.isAfter(data)
				|| (dataInicio.isEqual(data) || dataFim.isEqual(data))) {
			return "Aberto";
		}
		return "Encerrado";
	}

	public String getSituacaoDoEdital() {
		return situacaoDoEdital;
	}

	public void setSituacaoDoEdital(String situacaoDoEdital) {
		this.situacaoDoEdital = situacaoDoEdital;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNumeroDoEdital() {
		return numeroDoEdital;
	}

	public void setNumeroDoEdital(String numeroDoEdital) {
		this.numeroDoEdital = numeroDoEdital;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public ArrayList<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(ArrayList<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public String getQtdDeInscricaoPorAluno() {
		return qtdDeInscricaoPorAluno;
	}

	public void setQtdDeInscricaoPorAluno(String qtdDeInscricaoPorAluno) {
		this.qtdDeInscricaoPorAluno = qtdDeInscricaoPorAluno;
	}

	public float getPesoCRE() {
		return pesoCRE;
	}

	public void setPesoCRE(float pesoCRE) {
		this.pesoCRE = pesoCRE;
	}

	public float getPesoNota() {
		return pesoNota;
	}

	public void setPesoNota(float pesoNota) {
		this.pesoNota = pesoNota;
	}

}
