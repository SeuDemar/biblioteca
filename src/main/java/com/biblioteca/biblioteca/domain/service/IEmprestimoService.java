package com.biblioteca.biblioteca.domain.service;

import java.util.List;

import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;

public interface IEmprestimoService {

    EmprestimoDTO buscarPorId(Long id);

    List<EmprestimoDTO> listarPorIdUsuario(Long idUsuario);

    List<EmprestimoDTO> listarTodosEmprestimos();

    EmprestimoDTO registrarEmprestimo(EmprestimoDTO emprestimoDTO);

    EmprestimoDTO atualizarEmprestimo(Long id, EmprestimoDTO dataPrevistaAtualizado);

}
