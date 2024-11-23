package com.biblioteca.biblioteca.domain.service;

import java.time.LocalDate;
import java.util.List;

import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;

// Service que possuí os métodos que vão ser expostos nas controllers

public interface IEmprestimoService {

    EmprestimoDTO buscarPorId(Long idEmprestimo);

    List<EmprestimoDTO> listarPorIdUsuario(Long idUsuario);

    List<EmprestimoDTO> listarPorIdLivro(Long idLivro);

    List<EmprestimoDTO> listarTodosEmprestimos();

    EmprestimoDTO registrarEmprestimo(EmprestimoDTO emprestimoDTO);

    EmprestimoDTO entregarLivro(Long id, EmprestimoDTO livroEntregue);

    EmprestimoDTO renovarLivro(Long id, EmprestimoDTO livroRenovado);

    List<EmprestimoDTO> buscarPorDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista);

}
