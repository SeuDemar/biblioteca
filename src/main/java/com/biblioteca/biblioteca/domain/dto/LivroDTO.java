package com.biblioteca.biblioteca.domain.dto;

import lombok.Data;

// DTO que irá manipular os dados recebidos a fim de não deixar exposto as nossas entidades


@Data
public class LivroDTO {
    private Long idLivro;
    private String titulo;
    private String autor;
    private String editora;
    private int anoPublicacao;
    private boolean disponibilidade;
}
