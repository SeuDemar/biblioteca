package com.biblioteca.biblioteca.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.domain.service.IEmprestimoService;
import com.biblioteca.biblioteca.shared.CustomException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/emprestimo")
@Tag(name = "Emprestimos", description = "APIs relacionadas aos empréstimos")
public class EmprestimoController {
    
    @Autowired
    private IEmprestimoService emprestimoService;

    @Operation(summary = "Buscar empréstimo por ID", description = "Retorna os detalhes de um empréstimo baseado no ID fornecido.")
    @GetMapping("/{idEmprestimo}")
    public ResponseEntity<EmprestimoDTO> buscarPorId(@PathVariable Long id) {
        try {
            EmprestimoDTO emprestimo = emprestimoService.buscarPorId(id);
            return ResponseEntity.ok(emprestimo);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarTodosLivros() {
        try {
            List<EmprestimoDTO> emprestimo = emprestimoService.listarTodosEmprestimos();
            return ResponseEntity.ok(emprestimo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/livro/{idLivro}")
    public ResponseEntity<List<EmprestimoDTO>> listarPorIdLivro(@PathVariable Long idLivro) {
        try {
            List<EmprestimoDTO> emprestimos = emprestimoService.listarPorIdLivro(idLivro);
            return ResponseEntity.ok(emprestimos);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EmprestimoDTO>> listarPorIdUsuario(@PathVariable Long idUsuario) {
        try {
            List<EmprestimoDTO> emprestimos = emprestimoService.listarPorIdUsuario(idUsuario);
            return ResponseEntity.ok(emprestimos);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<EmprestimoDTO> registrarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) {
        try {
            EmprestimoDTO savedEmprestimo = emprestimoService.registrarEmprestimo(emprestimoDTO);
            return ResponseEntity.status(201).body(savedEmprestimo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{idEmprestimo}")
    public ResponseEntity<EmprestimoDTO> entregarLivro(@PathVariable Long id, @RequestBody EmprestimoDTO livroEntregue) {
        try {
            EmprestimoDTO updatedEmprestimo = emprestimoService.entregarLivro(id, livroEntregue);
            return ResponseEntity.ok(updatedEmprestimo);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
