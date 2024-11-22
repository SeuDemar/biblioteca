package com.biblioteca.biblioteca.domain.service;

import java.util.List;

import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;

public interface IEmprestimoService {

    EmprestimoDTO buscarPorId(Long idEmprestimo);

    List<EmprestimoDTO> listarPorIdUsuario(Long idUsuario);

    List<EmprestimoDTO> listarPorIdLivro(Long idLivro);

    List<EmprestimoDTO> listarTodosEmprestimos();

    EmprestimoDTO registrarEmprestimo(EmprestimoDTO emprestimoDTO);

    EmprestimoDTO entregarLivro(Long id, EmprestimoDTO livroEntregue);

}
