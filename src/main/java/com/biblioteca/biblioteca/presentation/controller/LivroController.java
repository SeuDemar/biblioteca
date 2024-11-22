package com.biblioteca.biblioteca.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.biblioteca.domain.dto.LivroDTO;
import com.biblioteca.biblioteca.domain.service.ILivroService;
import com.biblioteca.biblioteca.shared.CustomException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/livro")
@Tag(name = "Livros", description = "APIs relacionadas a Livros")
public class LivroController {
    
    @Autowired
    private ILivroService livroService;

    @GetMapping
    @Operation(
        summary = "Listar Livros",
        description = "Endpoint que lista todos os livros cadastrados."
    )
    public ResponseEntity<List<LivroDTO>> listarTodosLivros() {
        try {
            List<LivroDTO> livros = livroService.listarTodosLivros();
            return ResponseEntity.ok(livros);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{idLivro}")
    @Operation(
        summary = "Buscar Livro por ID",
        description = "Endpoint que busca um livro pelo ID como parâmetro."
    )
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long idLivro) {
        try {
            LivroDTO livro = livroService.buscarPorId(idLivro);
            return ResponseEntity.ok(livro);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/titulo/{titulo}")
    @Operation(
        summary = "Buscar Livro por Título",
        description = "Endpoint que busca um livro pelo título como parâmetro."
    )
    public ResponseEntity<LivroDTO> buscarPorTitulo(@PathVariable String titulo) {
        try {
            LivroDTO livro = livroService.buscarPorTitulo(titulo);
            return ResponseEntity.ok(livro);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/LivrosDisponiveis")
    @Operation(
        summary = "Listar Livros Disponiveis",
        description = "Endpoint que lista os livros disponiveis."
    )
    public ResponseEntity<List<LivroDTO>> listarLivrosDisponiveis() {
        List<LivroDTO> livrosDisponiveis = livroService.listarLivrosDisponiveis();
        return ResponseEntity.ok(livrosDisponiveis);
    }

    @GetMapping("/LivrosEmprestados")
    @Operation(
        summary = "Listar Livros Emprestados",
        description = "Endpoint que lista os livros emprestados."
    )
    public ResponseEntity<List<LivroDTO>> listarLivrosEmprestados() {
        List<LivroDTO> livrosDisponiveis = livroService.listarLivrosEmprestados();
        return ResponseEntity.ok(livrosDisponiveis);
    }

    @PostMapping
    @Operation(
        summary = "Cadastrar um livro",
        description = "Endpoint que cadastra um novo livro."
    )
    public ResponseEntity<LivroDTO> cadastrarLivro(@RequestBody LivroDTO livroDTO) {
        try {
            LivroDTO savedLivro = livroService.cadastrarLivro(livroDTO);
            return ResponseEntity.status(201).body(savedLivro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{idLivro}")
    @Operation(
        summary = "Deletar um livro",
        description = "Endpoint que deleta os dados de um livro passando o ID fornecido como parâmetro."
    )
    public ResponseEntity<Void> removerLivro(@PathVariable Long idLivro) {
        try {
            livroService.removerLivro(idLivro);
            return ResponseEntity.noContent().build();
        } catch (CustomException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/livro/{idLivro}")
    @Operation(
        summary = "Atualizar um livro",
        description = "Endpoint que atualiza os dados de um livro passando o ID fornecido como parâmetro."
    )
    public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Long idLivro, @RequestBody LivroDTO livroAtualizado) {
        try {
            LivroDTO updatedLivro = livroService.atualizarLivro(idLivro, livroAtualizado);
            return ResponseEntity.ok(updatedLivro);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
