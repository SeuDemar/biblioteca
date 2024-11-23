package com.biblioteca.biblioteca.domain.dto;

import java.time.LocalDate;

import lombok.Data;

// DTO que irá manipular os dados recebidos a fim de não deixar exposto as nossas entidades


@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String nome;
    private String email;
    private LocalDate dataCadastro;
    private int quantidadeLivrosEmprestados;
    private boolean multado;
}
