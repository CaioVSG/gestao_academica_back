package br.edu.ufape.sgi.servicos.interfaces;

import br.edu.ufape.sgi.exceptions.aluno.AlunoNotFoundException;
import br.edu.ufape.sgi.models.Aluno;

import java.util.List;

public interface AlunoService {
    Aluno salvar(Aluno aluno);

    Aluno buscarAluno(Long id) throws AlunoNotFoundException;

    List<Aluno> listarAlunos();

    void deletarAluno(Long id) throws AlunoNotFoundException;
}
