package com.biblioteca.biblioteca.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.biblioteca.domain.entity.Usuario;

// Repositórios que extendem o JPA permitindo transações no banco através do jpa

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    
}
