package com.biblioteca.biblioteca.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class LivroDTO {
    private Long idLivro;
    private String titulo;
    private String autor;
    private String editora;
    private int anoPublicacao;
    private boolean disponibilidade;
}
