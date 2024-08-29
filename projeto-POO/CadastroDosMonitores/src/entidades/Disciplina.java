package entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * 
 * @author Manu
 *
 */

public class Disciplina {
	private String nomeDaDisciplina;
	private String qtdVagasVoluntario;
	private String qtdVagasRemunerada;

	private ArrayList<Inscricao> inscricoes;

	public Disciplina(String nomeDaDisciplina, String qtdVagasVoluntario, String qtdVagasRemunerada) {
		this.nomeDaDisciplina = nomeDaDisciplina;
		this.setQtdVagasVoluntario(qtdVagasVoluntario);
		this.qtdVagasRemunerada = qtdVagasRemunerada;
		this.setInscricoes(new ArrayList<>());
	}

	public String toString() {
		return nomeDaDisciplina;
	}

	// Calculando o resultado do edital
	public void calcularResultado() {
		Collections.sort(inscricoes, Comparator.comparingDouble(inscricoes -> inscricoes.getNotaFinal()));
		Collections.reverse(inscricoes);
		int voluntario = Integer.parseInt(qtdVagasVoluntario);
		int remunerado = Integer.parseInt(qtdVagasRemunerada);
		for (Inscricao i : inscricoes) {
			if (!i.getResultado().equals("Desistiu")) {
				if (remunerado > 0) {
					i.setResultado("Classificado com Bolsa");
					remunerado--;
				} else if (voluntario > 0) {
					i.setResultado("Classificado como Voluntario");
					voluntario--;
				} else {
					i.setResultado("Lista de Espera");
				}
			}
		}
	}

	// Verificar se ja esta inscrito na disciplina
	public boolean jaEstaInscrito(Aluno aluno) {
		for (Inscricao inscrito : inscricoes) {
			if (inscrito.getAluno().getMatricula().equals(aluno.getMatricula())) {
				return true;
			}
		}
		return false;
	}

	// adicionar inscrição
	public void addIncricao(Inscricao i) {
		inscricoes.add(i);
	}

	public String getNomeDaDisciplina() {
		return nomeDaDisciplina;
	}

	public void setNomeDaDisciplina(String nomeDaDisciplina) {
		this.nomeDaDisciplina = nomeDaDisciplina;
	}

	public String getQtdVagasRemuneradas() {
		return qtdVagasRemunerada;
	}

	public void setQtdVagasRemuneradas(String qtdVagasRemuneradas) {
		this.qtdVagasRemunerada = qtdVagasRemuneradas;
	}

	public String getQtdVagasVoluntario() {
		return qtdVagasVoluntario;
	}

	public void setQtdVagasVoluntario(String qtdVagasVoluntario) {
		this.qtdVagasVoluntario = qtdVagasVoluntario;
	}

	public ArrayList<Inscricao> getInscricoes() {
		return inscricoes;
	}

	public void setInscricoes(ArrayList<Inscricao> inscricoes) {
		this.inscricoes = inscricoes;
	}

}
