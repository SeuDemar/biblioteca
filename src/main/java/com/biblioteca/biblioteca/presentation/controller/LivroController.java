package com.biblioteca.biblioteca.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.biblioteca.domain.dto.LivroDTO;
import com.biblioteca.biblioteca.domain.service.ILivroService;
import com.biblioteca.biblioteca.shared.CustomException;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/livro")
@Tag(name = "Livros", description = "APIs relacionadas a Livros")
public class LivroController {
    
    @Autowired
    private ILivroService livroService;

    @PostMapping
    public ResponseEntity<LivroDTO> cadastrarLivro(@RequestBody LivroDTO livroDTO) {
        try {
            LivroDTO savedLivro = livroService.cadastrarLivro(livroDTO);
            return ResponseEntity.status(201).body(savedLivro);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Long id, @RequestBody LivroDTO livroAtualizado) {
        try {
            LivroDTO updatedLivro = livroService.atualizarLivro(id, livroAtualizado);
            return ResponseEntity.ok(updatedLivro);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerLivro(@PathVariable Long id) {
        try {
            livroService.removerLivro(id);
            return ResponseEntity.noContent().build();
        } catch (CustomException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarTodosLivros() {
        try {
            List<LivroDTO> livros = livroService.listarTodosLivros();
            return ResponseEntity.ok(livros);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarPorId(@PathVariable Long id) {
        try {
            LivroDTO livro = livroService.buscarPorId(id);
            return ResponseEntity.ok(livro);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/titulo/{livro}")
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

}
