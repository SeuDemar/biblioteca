package com.biblioteca.biblioteca.application;

import org.mapstruct.Mapper;

import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.domain.dto.LivroDTO;
import com.biblioteca.biblioteca.domain.dto.UsuarioDTO;
import com.biblioteca.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.biblioteca.domain.entity.Livro;
import com.biblioteca.biblioteca.domain.entity.Usuario;

@Mapper(componentModel = "spring")
public interface Mappers {
    
    UsuarioDTO UsuariotoDto(Usuario usuario);
    Usuario UsuarioDTOtoEntity(UsuarioDTO usuarioDTO);

    LivroDTO LivrotoDto(Livro livro);
    Livro LivroDTOtoEntity(LivroDTO livroDTO);
    
    EmprestimoDTO EmprestimotoDto(Emprestimo emprestimo);
    Emprestimo EmprestimoDTOtoEntity(EmprestimoDTO emprestimoDTO);
}
