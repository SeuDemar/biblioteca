package com.biblioteca.biblioteca.application.service;

import java.time.LocalDate;
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

// Local onde fica a lógica de serviço fica escondida
// Feita de forma simples a fim de melhorar futuramente

@Service
public class EmprestimoService implements IEmprestimoService {

    // injeção de dependências dos repositórios
    @Autowired
    private IEmprestimoRepository emprestimoRepository;

    @Autowired
    private ILivroRepository livroRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private Mappers emprestimoMapper;

    @Override
    public EmprestimoDTO buscarPorId(Long idEmprestimo) {
        Optional<Emprestimo> emprestimo = emprestimoRepository.findById(idEmprestimo);

        if (emprestimo.isEmpty()) {
            throw new CustomException("Livro não encontrado com o ID: " + idEmprestimo);
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
    public List<EmprestimoDTO> buscarPorDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        List<Emprestimo> emprestimos = emprestimoRepository.findByDataDevolucaoPrevista(dataDevolucaoPrevista);
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

        if (!livro.isDisponibilidade() || usuario.getQuantidadeLivrosEmprestados() >= 5 || usuario.isMultado()) {
            return null;
        }

        // Vários seters pra não permitir alterar o objeto dentro do json do empréstimo
        emprestimo.setIdEmprestimo(emprestimo.getIdEmprestimo());
        emprestimo.setDataEmprestimo(emprestimo.getDataEmprestimo());
        emprestimo.setDataDevolucaoPrevista(emprestimo.getDataEmprestimo().plusDays(14));
        emprestimo.setDataDevolucaoReal(null);
        emprestimo.setStatus(StatusEmprestimo.ATIVO.name().toLowerCase());
        emprestimo.setMulta(emprestimo.getMulta());

        livro.setIdLivro(livro.getIdLivro());
        livro.setDisponibilidade(false);
        livro.setAnoPublicacao(livro.getAnoPublicacao());
        livro.setAutor(livro.getAutor());
        livro.setEditora(livro.getEditora());
        livro.setTitulo(livro.getTitulo());

        usuario.setIdUsuario(usuario.getIdUsuario());
        usuario.setQuantidadeLivrosEmprestados(usuario.getQuantidadeLivrosEmprestados() + 1);
        usuario.setDataCadastro(usuario.getDataCadastro());
        usuario.setNome(usuario.getNome());
        usuario.setEmail(usuario.getEmail());
        usuario.setMultado(usuario.isMultado());

        emprestimo = emprestimoRepository.save(emprestimo);
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
        Usuario usuario = emprestimo.getUsuario();
        Livro livro = emprestimo.getLivro();

        emprestimo.setDataDevolucaoReal(LocalDate.now());
        emprestimo.setMulta(emprestimo.getMulta());

        int diasEntrega = (int) ChronoUnit.DAYS.between(emprestimo.getDataEmprestimo(),
                emprestimoAtualizado.getDataDevolucaoReal()); 

        if (diasEntrega > 14) {
            emprestimo.setMulta(1.0f * (diasEntrega - 14));
        }

        // Constantes convertidas em strings minusculas para manter o padrao do Banco de Dados
        // Datas continuam as mesmas, menos a real, pois é registrada no momento do emprestimo
        emprestimo.setIdEmprestimo(emprestimo.getIdEmprestimo());
        emprestimo.setStatus(StatusEmprestimo.CONCLUIDO.name().toLowerCase());
        emprestimo.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista());
        emprestimo.setDataEmprestimo(emprestimo.getDataEmprestimo());

        livro.setIdLivro(livro.getIdLivro());
        livro.setDisponibilidade(true);
        livro.setAnoPublicacao(livro.getAnoPublicacao());
        livro.setAutor(livro.getAutor());
        livro.setEditora(livro.getEditora());
        livro.setTitulo(livro.getTitulo());
        
        usuario.setIdUsuario(usuario.getIdUsuario());
        usuario.setQuantidadeLivrosEmprestados(usuario.getQuantidadeLivrosEmprestados() - 1);
        usuario.setDataCadastro(usuario.getDataCadastro());
        usuario.setNome(usuario.getNome());
        usuario.setEmail(usuario.getEmail());
        usuario.setMultado(false);


        livroRepository.save(livro);
        usuarioRepository.save(usuario);
        emprestimo = emprestimoRepository.save(emprestimo);

        return emprestimoMapper.EmprestimotoDto(emprestimo);
    }

    @Override
    public EmprestimoDTO renovarLivro(Long idEmprestimo, EmprestimoDTO livroRenovado) {
        Optional<Emprestimo> emprestimoExistente = emprestimoRepository.findById(idEmprestimo);

        if (emprestimoExistente.isEmpty()) {
            throw new CustomException("Emprestimo não encontrado com o ID: " + idEmprestimo);
        }

        Emprestimo emprestimo = emprestimoExistente.get();
        Usuario usuario = emprestimo.getUsuario();
        Livro livro = emprestimo.getLivro();

        emprestimo.setDataDevolucaoReal(LocalDate.now());
        emprestimo.setIdEmprestimo(emprestimo.getIdEmprestimo());

        int diasEntrega = (int) ChronoUnit.DAYS.between(emprestimo.getDataEmprestimo(),
                livroRenovado.getDataDevolucaoReal()); 

        if (diasEntrega > 14 || usuario.isMultado()) {
            // entrega em atraso, acertar o status na entrega do livro
            emprestimo.setStatus(StatusEmprestimo.EM_ATRASO.name().toLowerCase());
            // O usuário será multado ainda não esteja com multas, e o preço da multa é acertado na entrega
            // Não poderá renovar livros enquanto tem multas
            usuario.setMultado(true);
            return null;
        }

        // Constantes convertidas em strings minusculas para manter o padrao do Banco de Dados
        // Datas continuam as mesmas, menos a real, pois é registrada no momento do emprestimo
        emprestimo.setStatus(StatusEmprestimo.ATIVO.name().toLowerCase());
        emprestimo.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoReal().plusDays(14));
        emprestimo.setDataEmprestimo(emprestimo.getDataEmprestimo());
        emprestimo.setMulta(emprestimo.getMulta());
        emprestimo.setStatus(StatusEmprestimo.ATIVO.name().toLowerCase());
        emprestimo.setDataDevolucaoReal(null);

        livro.setIdLivro(livro.getIdLivro());
        livro.setDisponibilidade(true);
        livro.setAnoPublicacao(livro.getAnoPublicacao());
        livro.setAutor(livro.getAutor());
        livro.setEditora(livro.getEditora());
        livro.setTitulo(livro.getTitulo());

        usuario.setIdUsuario(usuario.getIdUsuario());
        usuario.setQuantidadeLivrosEmprestados(usuario.getQuantidadeLivrosEmprestados());
        usuario.setDataCadastro(usuario.getDataCadastro());
        usuario.setNome(usuario.getNome());
        usuario.setEmail(usuario.getEmail());
        usuario.setMultado(usuario.isMultado());


        livroRepository.save(livro);
        usuarioRepository.save(usuario);
        emprestimo = emprestimoRepository.save(emprestimo);

        return emprestimoMapper.EmprestimotoDto(emprestimo);
    }

}
