package com.biblioteca.biblioteca.domain.service;

import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;

public interface IEmprestimoService {
    
    EmprestimoDTO registrarEmprestimo(EmprestimoDTO emprestimoDTO);

    EmprestimoDTO atualizarEmprestimo(Long id, EmprestimoDTO dataPrevistaAtualizado);

    EmprestimoDTO buscarPorId(Long id);

    EmprestimoDTO buscarPorUsuario(Long idUsuario);
}
