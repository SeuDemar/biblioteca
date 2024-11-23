package com.biblioteca.biblioteca.domain.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Entidades que serão expostas ao banco de dados, algumas informações alteradas para fazer jus ao banco
// Entidade diferente das outras pois possuí um boolean "multado"

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private long idUsuario;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Column(name = "quantidade_livros_emprestados", nullable = false)
    private int quantidadeLivrosEmprestados;

    @Column(nullable = false)
    private boolean multado;
}
