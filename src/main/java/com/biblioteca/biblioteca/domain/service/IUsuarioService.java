package com.biblioteca.biblioteca.domain.service;

import java.util.List;

import com.biblioteca.biblioteca.domain.dto.UsuarioDTO;

public interface IUsuarioService {
    
    UsuarioDTO buscarPorId(Long idEmprestimo);

    UsuarioDTO buscarPorEmail(String email);

    List<UsuarioDTO> listarTodosUsuarios();
   
    UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO);

    UsuarioDTO atualizarUsuario(Long idEmprestimo, UsuarioDTO usuarioAtualizado);

    void removerUsuario(Long id);
}

