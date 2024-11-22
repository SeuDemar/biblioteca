package com.biblioteca.biblioteca.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca.application.Mappers;
import com.biblioteca.biblioteca.domain.dto.LivroDTO;
import com.biblioteca.biblioteca.domain.entity.Livro;
import com.biblioteca.biblioteca.domain.repository.ILivroRepository;
import com.biblioteca.biblioteca.domain.service.ILivroService;
import com.biblioteca.biblioteca.shared.CustomException;

@Service
public class LivroService implements ILivroService {
    
    @Autowired
    private ILivroRepository livroRepository;

    @Autowired
    private Mappers livroMapper;

    @Override
    public LivroDTO buscarPorId(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);

        if (livro.isEmpty()) {
            throw new CustomException("Livro não encontrado com o ID: " + id);
        }

        return livroMapper.LivrotoDto(livro.get());
    }

    @Override
    public List<LivroDTO> listarTodosLivros() {
        List<Livro> livros = livroRepository.findAll();
        return livros.stream()
                .map(livroMapper::LivrotoDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LivroDTO> listarLivrosDisponiveis() {

        List<Livro> livrosDisponiveis = livroRepository.findByDisponibilidadeTrue();
        
        return livrosDisponiveis.stream()
                .map(livroMapper::LivrotoDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LivroDTO> listarLivrosEmprestados() {

        List<Livro> livrosDisponiveis = livroRepository.findByDisponibilidadeFalse();
        
        return livrosDisponiveis.stream()
                .map(livroMapper::LivrotoDto)
                .collect(Collectors.toList());
    }

    @Override
    public LivroDTO buscarPorTitulo(String titulo) {
        Optional<Livro> livro = livroRepository.findByTitulo(titulo);

        if (livro.isEmpty()) {
            throw new CustomException("Livro nao encontrado com o titulo: " + titulo);
        }

        return livroMapper.LivrotoDto(livro.get());
    }

    @Override
    public LivroDTO cadastrarLivro(LivroDTO livroDTO) {
   
        Livro livro = livroMapper.LivroDTOtoEntity(livroDTO);

        // ID definido como zero para não causar insconsistência, já que o ID é gerado pelo hibernates
        livro.setIdLivro(0);
        livro.setDisponibilidade(true);

        livro = livroRepository.save(livro);

        return livroMapper.LivrotoDto(livro);
    }


    @Override
    public LivroDTO atualizarLivro(Long id, LivroDTO livroAtualizado) {
        Optional<Livro> livroExistente = livroRepository.findById(id);

        if (livroExistente.isEmpty()) {
            throw new CustomException("Livro não encontrado com o ID: " + id);
        }

        // Deixei alterações apenas da editora e do ano, pois geralmente titulo e autor não são alterados, pelo menos não nesse contexto.
        // Claro q isso depende de casos e casos, mas esse é o mais pertinento por enquanto
        Livro livro = livroExistente.get();
        livro.setTitulo(livro.getTitulo());
        livro.setAutor(livro.getAutor());
        livro.setDisponibilidade(livro.isDisponibilidade());
        
        livro.setEditora(livroAtualizado.getEditora());
        livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());

        livro = livroRepository.save(livro);

        return livroMapper.LivrotoDto(livro);
    }
    
    @Override
    public void removerLivro(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);

        if (livro.isEmpty()) {
            throw new CustomException("Livro não encontrado com o ID: " + id);
        }

        livroRepository.deleteById(id);
    }

    

}



