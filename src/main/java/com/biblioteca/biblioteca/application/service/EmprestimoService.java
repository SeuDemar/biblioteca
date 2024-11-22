package com.biblioteca.biblioteca.application.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca.application.Mappers;
import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.biblioteca.domain.entity.Livro;
import com.biblioteca.biblioteca.domain.entity.Usuario;
import com.biblioteca.biblioteca.domain.repository.IEmprestimoRepository;
import com.biblioteca.biblioteca.domain.repository.ILivroRepository;
import com.biblioteca.biblioteca.domain.repository.IUsuarioRepository;
import com.biblioteca.biblioteca.domain.service.IEmprestimoService;
import com.biblioteca.biblioteca.shared.CustomException;
import com.biblioteca.biblioteca.shared.StatusEmprestimo;

@Service
public class EmprestimoService implements IEmprestimoService{
    
    @Autowired
    private IEmprestimoRepository emprestimoRepository;

    @Autowired
    private ILivroRepository livroRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private Mappers emprestimoMapper;
    
    @Override
    public EmprestimoDTO buscarPorId(Long id) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(id);

        if (emprestimo.isEmpty()) {
            throw new CustomException("Livro não encontrado com o ID: " + id);
        }

        return emprestimoMapper.EmprestimotoDto(emprestimo.get());
    }

    @Override
    public List<EmprestimoDTO> listarTodosEmprestimos() {
        // Utiliza o repositório para pegar livros com disponibilidade true
        List<Emprestimo> emprestimoDisponiveis = emprestimoRepository.findAll();
        
        // Converte os livros disponíveis para DTO
        return emprestimoDisponiveis.stream()
                .map(emprestimoMapper::EmprestimotoDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<EmprestimoDTO> listarPorIdUsuario(Long idUsuario) {
        List<Emprestimo> emprestimos = emprestimoRepository.findByUsuarioIdUsuario(idUsuario);

        return emprestimos.stream()
                .map(emprestimoMapper::EmprestimotoDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmprestimoDTO> listarPorIdLivro(Long idLivro) {
        List<Emprestimo> emprestimos = emprestimoRepository.findByLivroIdLivro(idLivro);

        return emprestimos.stream()
                .map(emprestimoMapper::EmprestimotoDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public EmprestimoDTO registrarEmprestimo(EmprestimoDTO emprestimoDTO) {

        Emprestimo emprestimo = emprestimoMapper.EmprestimoDTOtoEntity(emprestimoDTO);
        
        Long livroId = emprestimo.getLivro().getIdLivro();
        Long usuarioId = emprestimo.getUsuario().getIdUsuario();

        Livro livro = livroRepository.findById(livroId)
            .orElseThrow(() -> new CustomException("Livro não encontrado com o ID: " + livroId));
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new CustomException("Usuário não encontrado com o ID: " + usuarioId));

        if (!livro.isDisponibilidade() || usuario.getQuantidadeLivrosEmprestados() >= 5) {
            return null;
        }

        emprestimo.setDataEmprestimo(emprestimo.getDataEmprestimo());
        emprestimo.setDataDevolucaoPrevista(emprestimo.getDataEmprestimo().plusDays(14));
        emprestimo.setDataDevolucaoReal(null);
        emprestimo.setStatus(StatusEmprestimo.ATIVO.name().toLowerCase());

        emprestimo = emprestimoRepository.save(emprestimo);

        
        livro.setDisponibilidade(false);
        livro.setAnoPublicacao(livro.getAnoPublicacao());
        livro.setAutor(livro.getAutor());
        livro.setEditora(livro.getEditora());
        livro.setTitulo(livro.getTitulo());
        
        usuario.setQuantidadeLivrosEmprestados(usuario.getQuantidadeLivrosEmprestados() + 1);
        usuario.setDataCadastro(usuario.getDataCadastro());
        usuario.setNome(usuario.getNome());
        usuario.setEmail(usuario.getEmail());

        livroRepository.save(livro);
        usuarioRepository.save(usuario);

        return emprestimoMapper.EmprestimotoDto(emprestimo);
    }

    @Override
    public EmprestimoDTO entregarLivro(Long idEmprestimo, EmprestimoDTO emprestimoAtualizado) {
        Optional<Emprestimo> emprestimoExistente = emprestimoRepository.findById(idEmprestimo);

        if (emprestimoExistente.isEmpty()) {
            throw new CustomException("Emprestimo não encontrado com o ID: " + idEmprestimo);
        }

        Emprestimo emprestimo = emprestimoExistente.get();
        int diasAtraso = (int) ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), emprestimoAtualizado.getDataDevolucaoReal());
        if (diasAtraso > 14) {
            emprestimo.setMulta(emprestimo.getMulta() + (1 *diasAtraso));
        }
        emprestimo.setDataDevolucaoReal(emprestimoAtualizado.getDataDevolucaoReal());
        emprestimo.setStatus(StatusEmprestimo.CONCLUIDO.name());  

        Livro livro = emprestimo.getLivro();
        livro.setDisponibilidade(true);
        livroRepository.save(livro);

        // Diminui a quantidade de livros do usuario
        Usuario usuario = emprestimo.getUsuario();
        usuario.setQuantidadeLivrosEmprestados(usuario.getQuantidadeLivrosEmprestados() - 1);
        usuario.setDataCadastro(usuario.getDataCadastro());
        usuario.setNome(usuario.getNome());
        usuario.setEmail(usuario.getEmail());
        usuarioRepository.save(usuario);

        emprestimo = emprestimoRepository.save(emprestimo);

        return emprestimoMapper.EmprestimotoDto(emprestimo);
    }
}
