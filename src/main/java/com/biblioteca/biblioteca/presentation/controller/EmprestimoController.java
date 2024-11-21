package com.biblioteca.biblioteca.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.domain.service.IEmprestimoService;
import com.biblioteca.biblioteca.shared.CustomException;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("api/emprestimo")
@Tag(name = "Emprestimos", description = "APIs relacionadas aos empréstimos")
public class EmprestimoController {
    
    @Autowired
    private IEmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<EmprestimoDTO> registrarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) {
        try {
            EmprestimoDTO savedEmprestimo = emprestimoService.registrarEmprestimo(emprestimoDTO);
            return ResponseEntity.status(201).body(savedEmprestimo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoDTO> atualizarEmprestimo(@PathVariable Long id, @RequestBody EmprestimoDTO dataPrevistaAtualizado) {
        try {
            EmprestimoDTO updatedEmprestimo = emprestimoService.atualizarEmprestimo(id, dataPrevistaAtualizado);
            return ResponseEntity.ok(updatedEmprestimo);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EmprestimoDTO>> buscarPorUsuario(@PathVariable Long idUsuario) {
        try {
            List<EmprestimoDTO> emprestimos = emprestimoService.listarPorIdUsuario(idUsuario);
            return ResponseEntity.ok(emprestimos);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDTO> buscarPorId(@PathVariable Long id) {
        try {
            EmprestimoDTO emprestimo = emprestimoService.buscarPorId(id);
            return ResponseEntity.ok(emprestimo);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
