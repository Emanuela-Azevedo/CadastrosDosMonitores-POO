package entidades;

/**
 * 
 * @author George
 *
 */

public class Aluno extends Pessoa {

	private String matricula;
	private Sexo sexo;

	public Aluno(String nome, String email, String senha, String matricula, Sexo sexo) {
		super(nome, email, senha);
		this.matricula = matricula;
		this.sexo = sexo;
	}

	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
}