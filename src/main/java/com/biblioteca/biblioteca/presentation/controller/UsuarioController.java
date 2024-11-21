package com.biblioteca.biblioteca.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.biblioteca.biblioteca.domain.dto.UsuarioDTO;
import com.biblioteca.biblioteca.domain.service.IUsuarioService;
import com.biblioteca.biblioteca.shared.CustomException;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("api/usuarios")
@Tag(name = "Usuario", description = "APIs relacionadas a Usuários")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO savedUsuario = usuarioService.cadastrarUsuario(usuarioDTO);
            return ResponseEntity.status(201).body(savedUsuario); // Retorna 201 Created
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{idUsuario}")
    
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long idUsuario, @RequestBody UsuarioDTO usuarioAtualizado) {
        try {
            UsuarioDTO updatedUsuario = usuarioService.atualizarUsuario(idUsuario, usuarioAtualizado);
            return ResponseEntity.ok(updatedUsuario);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{idUsuario}")
   
    public ResponseEntity<Void> removerUsuario(@PathVariable Long idUsuario) {
        try {
            usuarioService.removerUsuario(idUsuario);
            return ResponseEntity.noContent().build();
        } catch (CustomException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping

    public ResponseEntity<List<UsuarioDTO>> listarTodosUsuarios() {
        try {
            List<UsuarioDTO> usuarios = usuarioService.listarTodosUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long idUsuario) {
        try {
            UsuarioDTO usuario = usuarioService.buscarPorId(idUsuario);
            return ResponseEntity.ok(usuario);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@PathVariable String email) {
        try {
            UsuarioDTO usuario = usuarioService.buscarPorEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
