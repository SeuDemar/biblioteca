package com.biblioteca.biblioteca.domain.service;

import com.biblioteca.biblioteca.domain.dto.LivroDTO;

public interface ILivroService {
    
    LivroDTO cadastrarLivro(LivroDTO livroDTO);

    LivroDTO atualizarLivro(LivroDTO livroDTO);

    void removerLivro(LivroDTO livroDTO);

    
}
