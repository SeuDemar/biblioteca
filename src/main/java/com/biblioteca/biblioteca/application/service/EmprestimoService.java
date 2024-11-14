package com.biblioteca.biblioteca.application.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.biblioteca.biblioteca.application.Mappers;
import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.biblioteca.domain.repository.IEmprestimoRepository;
import com.biblioteca.biblioteca.domain.service.IEmprestimoService;

public class EmprestimoService implements IEmprestimoService{
    
    @Autowired
    private IEmprestimoRepository emprestimoRepository;

    @Autowired
    private Mappers emprestimoMapper;
    
    // registrarEmprestimo
    // atualizarEmprestimo
    // listarTodosEmprestimos

    @Override
    public EmprestimoDTO registrarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Emprestimo emprestimo = emprestimoMapper.EmprestimoDTOtoEntity(emprestimoDTO);
        emprestimo = emprestimoRepository.save(emprestimo);

        return emprestimoMapper.EmprestimotoDto(emprestimo);
    }

    @Override
    public 

}
