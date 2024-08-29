package entidades;

/**
 * 
 * @author george e Manu
 *
 */

public class Inscricao {

	private Aluno aluno;
	private Disciplina disciplina;
	private float notaCRE;
	private float notaDisciplina;
	private String resultado;
	private float notaFinal;

	public Inscricao(Aluno aluno, Disciplina disciplina, float notaCRE, 
			float notaDisciplina, String resultado, float notaFinal ) {
		this.aluno = aluno;
		this.disciplina = disciplina;
		this.notaCRE = notaCRE;
		this.notaDisciplina = notaDisciplina;
		this.resultado = resultado;
		this.notaFinal = notaFinal;
		
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}


	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public float getNotaDisciplina() {
		return notaDisciplina;
	}

	public void setNotaDisciplina(float notaDisciplina) {
		this.notaDisciplina = notaDisciplina;
	}

	public float getNotaCRE() {
		return notaCRE;
	}

	public void setNotaCRE(float notaCRE) {
		this.notaCRE = notaCRE;
	}

	public float getNotaFinal() {
		return notaFinal;
	}

	public void setNotaFinal(float notaFinal) {
		this.notaFinal = notaFinal;
	}

}
