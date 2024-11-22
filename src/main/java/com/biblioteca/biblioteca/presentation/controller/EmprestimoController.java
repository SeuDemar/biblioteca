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

    @GetMapping("/{idEmprestimo}")
    @Operation(
        summary = "Listar um empréstimo",
        description = "Endpoint que busca um empréstimo com o id informado."
    )
    public ResponseEntity<EmprestimoDTO> buscarPorId(@PathVariable Long idEmprestimo) {
        try {
            EmprestimoDTO emprestimo = emprestimoService.buscarPorId(idEmprestimo);
            return ResponseEntity.ok(emprestimo);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping
    @Operation(
        summary = "Listar todos os empréstimos",
        description = "Endpoint que lista todos os empréstimos."
    )
    public ResponseEntity<List<EmprestimoDTO>> listarTodosLivros() {
        try {
            List<EmprestimoDTO> emprestimo = emprestimoService.listarTodosEmprestimos();
            return ResponseEntity.ok(emprestimo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/livro/{idLivro}")
    @Operation(
        summary = "Listar empréstimos de um livro",
        description = "Endpoint que lista os empréstimos com o id de um livro."
    )
    public ResponseEntity<List<EmprestimoDTO>> listarPorIdLivro(@PathVariable Long idLivro) {
        try {
            List<EmprestimoDTO> emprestimos = emprestimoService.listarPorIdLivro(idLivro);
            return ResponseEntity.ok(emprestimos);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    

    @GetMapping("/usuario/{idUsuario}")
    @Operation(
        summary = "Listar empréstimos de um usuário",
        description = "Endpoint que lista os empréstimos com o id de um usuário."
    )
    public ResponseEntity<List<EmprestimoDTO>> listarPorIdUsuario(@PathVariable Long idUsuario) {
        try {
            List<EmprestimoDTO> emprestimos = emprestimoService.listarPorIdUsuario(idUsuario);
            return ResponseEntity.ok(emprestimos);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    @Operation(
        summary = "Registrar um empréstimo",
        description = "Endpoint que cadastra um novo empréstimo."
    )
    public ResponseEntity<EmprestimoDTO> registrarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) {
        try {
            EmprestimoDTO savedEmprestimo = emprestimoService.registrarEmprestimo(emprestimoDTO);
            return ResponseEntity.status(201).body(savedEmprestimo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{idEmprestimo}")
    @Operation(
        summary = "Entregar um livro",
        description = "Endpoint que atualiza a data de entrega e calcual multas, passando o ID do livro fornecido como parâmetro."
    )
    public ResponseEntity<EmprestimoDTO> entregarLivro(@PathVariable Long idEmprestimo, @RequestBody EmprestimoDTO livroEntregue) {
        try {
            EmprestimoDTO updatedEmprestimo = emprestimoService.entregarLivro(idEmprestimo, livroEntregue);
            return ResponseEntity.ok(updatedEmprestimo);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{idEmprestimo}/renovado")
    @Operation(
        summary = "Renovar um livro",
        description = "Endpoint que atualiza renova a data prevista de um livro passando o ID fornecido como parâmetro."
    )
    public ResponseEntity<EmprestimoDTO> renovarLivro(@PathVariable Long idEmprestimo, @RequestBody EmprestimoDTO livroRenovado) {
        try {
            EmprestimoDTO updatedEmprestimo = emprestimoService.renovarLivro(idEmprestimo, livroRenovado);
            return ResponseEntity.ok(updatedEmprestimo);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
