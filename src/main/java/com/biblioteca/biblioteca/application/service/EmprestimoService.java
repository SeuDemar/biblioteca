package com.biblioteca.biblioteca.application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca.application.Mappers;
import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.biblioteca.domain.repository.IEmprestimoRepository;
import com.biblioteca.biblioteca.domain.service.IEmprestimoService;
import com.biblioteca.biblioteca.shared.CustomException;

@Service
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
    public EmprestimoDTO atualizarEmprestimo(Long id, EmprestimoDTO emprestimoAtualizado) {
        Optional<Emprestimo> emprestimoExistente = emprestimoRepository.findById(id);

        if (emprestimoExistente.isEmpty()) {
            throw new CustomException("Emprestimo não encontrado com o ID: " + id);
        }

        Emprestimo emprestimo = emprestimoExistente.get();
        emprestimo.setDataDevolucaoReal(emprestimoAtualizado.getDataDevolucaoReal());
        emprestimo.setMulta(emprestimoAtualizado.getMulta());
        emprestimo.setStatus(emprestimoAtualizado.getStatus());

        emprestimo = emprestimoRepository.save(emprestimo);

        return emprestimoMapper.EmprestimotoDto(emprestimo);
    }

    @Override
    public EmprestimoDTO buscarPorId(Long id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);

        if (emprestimo.isEmpty()) {
            throw new CustomException("Livro não encontrado com o ID: " + id);
        }

        return emprestimoMapper.EmprestimotoDto(emprestimo.get());
    }

    @Override
    public EmprestimoDTO buscarPorUsuario(Long idUsuario) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(idUsuario);

        if (emprestimo.isEmpty()) {
            throw new CustomException("Emprestimo não encontrado com o ID de usuario: " + idUsuario);
        }

        return emprestimoMapper.EmprestimotoDto(emprestimo.get());
    }

}
