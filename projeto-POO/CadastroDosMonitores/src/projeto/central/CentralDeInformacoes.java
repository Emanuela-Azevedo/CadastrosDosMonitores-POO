package projeto.central;

import java.util.ArrayList;

import entidades.Aluno;
import entidades.Coordenador;
import entidades.EditalDeMonitoria;

/**
 * 
 * @author George
 *
 */

public class CentralDeInformacoes {

	private Coordenador coordenador;
	private Aluno alunoLogado;
	private ArrayList<Aluno> alunos = new ArrayList<Aluno>();
	private ArrayList<EditalDeMonitoria> todosOsEditais = new ArrayList<EditalDeMonitoria>();

	// Adicionar aluno
	public boolean adicionarAluno(Aluno aluno) {
		for (Aluno a : this.alunos) {
			if (a.getMatricula().equals(aluno.getMatricula())) {
				return false;
			}
		}
		this.alunos.add(aluno);
		return true;
	}

	// Recuperar aluno com email
	public Aluno recuperarAlunoPorEmail(String email) {
		for (Aluno aluno : alunos) {
			if (aluno.getEmail().equals(email)) {
				return aluno;
			}
		}
		return null;
	}

	// Recuperar senha com email
	public String recuperarSenhaPorEmail(String email) {
		if (coordenador.getEmail().equals(email)) {
			return coordenador.getSenha();
		} else {
			for (Aluno aluno : alunos) {
				if (aluno.getEmail().equals(email)) {
					return aluno.getSenha();
				}
			}
		}
		return null;
	}

	// Recuperar edital por id
	public EditalDeMonitoria recuperarEditalPorID(long id) {
		for (EditalDeMonitoria edital : todosOsEditais) {
			if (edital.getId() == id) {
				return edital;
			}
		}
		return null;
	}

	// adicionar edital
	public boolean adicionarEdital(EditalDeMonitoria edital) {
		if (this.todosOsEditais == null) {
			this.todosOsEditais = new ArrayList<EditalDeMonitoria>();
		}
		for (EditalDeMonitoria ed : this.todosOsEditais) {
			if (ed.getId() == edital.getId()) {
				return false;
			}
			if (ed.getNumeroDoEdital().equals(edital.getNumeroDoEdital())) {
				return false;
			}
		}

		this.todosOsEditais.add(edital);
		return true;
	}

	public Coordenador getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Coordenador coordenador) {
		this.coordenador = coordenador;
	}

	public Aluno getAlunoLogado() {
		return alunoLogado;
	}

	public void setAlunoLogado(Aluno alunoLogado) {
		this.alunoLogado = alunoLogado;
	}

	public ArrayList<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(ArrayList<Aluno> alunos) {
		this.alunos = alunos;
	}

	public ArrayList<EditalDeMonitoria> getTodosOsEditais() {
		return todosOsEditais;
	}

	public void setTodosOsEditais(ArrayList<EditalDeMonitoria> todosOsEditais) {
		this.todosOsEditais = todosOsEditais;
	}

}
