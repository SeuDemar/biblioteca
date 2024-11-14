package com.biblioteca.biblioteca.domain.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EmprestimoDTO {
    private long id;
    private long idUsuario;
    private long idLivro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
    private String status;
    private float multa;
}
