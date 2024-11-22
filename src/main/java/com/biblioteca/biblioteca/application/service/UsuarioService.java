package com.biblioteca.biblioteca.application.service;

import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca.application.Mappers;
import com.biblioteca.biblioteca.domain.dto.UsuarioDTO;
import com.biblioteca.biblioteca.domain.entity.Usuario;
import com.biblioteca.biblioteca.domain.repository.IUsuarioRepository;
import com.biblioteca.biblioteca.domain.service.IUsuarioService;
import com.biblioteca.biblioteca.shared.CustomException;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private Mappers usuarioMapper;

    @Override
    public UsuarioDTO buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            throw new CustomException("Usuário não encontrado com o ID: " + id);
        }


        return usuarioMapper.UsuariotoDto(usuario.get());
    }

    @Override
    public List<UsuarioDTO> listarTodosUsuarios() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuarioMapper::UsuariotoDto) 
                .collect(Collectors.toList()); 
    }

    @Override
    public UsuarioDTO buscarPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isEmpty()) {
            throw new CustomException("Usuário não encontrado com o email: " + email);
        }

        return usuarioMapper.UsuariotoDto(usuario.get());
    }

    @Override
    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
    
        Usuario usuario = usuarioMapper.UsuarioDTOtoEntity(usuarioDTO);

        // ID definido como zero para não causar insconsistência, já que o ID é gerado pelo hibernates
        // Data de cadastro é feita no dia de hoje independento do caso, pois cadastro é cadastro é não deve ter inconsistência
        // Quantidade de livros emprestados inicia em zero, pois o emprestimo administra esse valor
        usuario.setIdUsuario(0);
        usuario.setDataCadastro(LocalDate.now());
        usuario.setQuantidadeLivrosEmprestados(0);
        usuario.setMultado(false);

        usuario = usuarioRepository.save(usuario);

        return usuarioMapper.UsuariotoDto(usuario);
    }

 
    @Override
    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioAtualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isEmpty()) {
            throw new CustomException("Usuário não encontrado com o ID: " + id);
        }

        Usuario usuario = usuarioExistente.get();

        // Permitido a alteração apenas do nome e email, o resto deve ser consistente e imutavel
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());

        usuario.setDataCadastro(usuario.getDataCadastro());
        usuario.setIdUsuario(usuario.getIdUsuario());
        usuario.setQuantidadeLivrosEmprestados(usuario.getQuantidadeLivrosEmprestados());
        usuario.setMultado(usuario.isMultado());

        usuario = usuarioRepository.save(usuario);


        return usuarioMapper.UsuariotoDto(usuario);
    }

  
    @Override
    public void removerUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (usuario.isEmpty()) {
            throw new CustomException("Usuário não encontrado com o ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }

}


