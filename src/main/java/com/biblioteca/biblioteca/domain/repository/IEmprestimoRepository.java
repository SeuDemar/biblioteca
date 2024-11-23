package com.biblioteca.biblioteca.domain.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.biblioteca.domain.entity.Emprestimo;

// Repositórios que extendem o JPA permitindo transações no banco através do jpa

public interface IEmprestimoRepository extends JpaRepository<Emprestimo, Long>{

    List<Emprestimo> findByDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista);
    
    List<Emprestimo> findByUsuarioIdUsuario(Long idUsuario);

    List<Emprestimo> findByLivroIdLivro(Long idLivro);

}
