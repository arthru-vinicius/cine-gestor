package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.model.Filme;
import br.edu.ifpe.paulista.cinegestor.model.Sessao;
import br.edu.ifpe.paulista.cinegestor.repository.FilmeRepository;
import br.edu.ifpe.paulista.cinegestor.repository.SessaoRepository;
import br.edu.ifpe.paulista.cinegestor.util.GerenciadorArquivos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private GerenciadorArquivos gerenciadorArquivos;

    // Injeção do repositório de sessões para buscar as sessões cadastradas
    @Autowired
    private SessaoRepository sessaoRepository;

    public List<Filme> listarFilmes() {
        return filmeRepository.findAll();
    }

    // Mét0do que retorna os filmes com sessões programadas
    public List<Filme> listarFilmesComSessoes() {
        List<Sessao> sessoes = sessaoRepository.findAll();
        Set<Filme> filmesComSessoes = new HashSet<>();

        for (Sessao sessao : sessoes) {
            // Verifica se a sessão está agendada e se há um filme associado
            if (sessao.getStatus() == Sessao.StatusSessao.AGENDADA && sessao.getFilme() != null) {
                Filme f = sessao.getFilme();
                if (f.getImagemCaminho() != null && !f.getImagemCaminho().isEmpty()) {
                    String nomeArquivo = f.getImagemCaminho().replace("img/", "");
                    String fullUrl = "http://localhost:8080/imagens/" + nomeArquivo;
                    f.setImagemCaminho(fullUrl);
                }
                filmesComSessoes.add(f);
            }
        }
        return new ArrayList<>(filmesComSessoes);
    }

    public Filme cadastrarFilme(Filme filme, MultipartFile imagem) throws IOException {
        if (imagem != null && !imagem.isEmpty()) {
            String caminhoImagem = gerenciadorArquivos.salvarFilme(imagem);
            filme.setImagemCaminho(caminhoImagem);
        }
        return filmeRepository.save(filme);
    }

    public Filme atualizarFilme(Integer id, Filme filmeAtualizado) {
        return filmeRepository.findById(id).map(filme -> {
            filme.setTitulo(filmeAtualizado.getTitulo());
            filme.setSinopse(filmeAtualizado.getSinopse());
            filme.setDuracao(filmeAtualizado.getDuracao());
            filme.setClassificacaoEtaria(filmeAtualizado.getClassificacaoEtaria());
            return filmeRepository.save(filme);
        }).orElse(null);
    }

    public void deletarFilme(Integer id) {
        filmeRepository.findById(id).ifPresent(filme -> {
            gerenciadorArquivos.excluirArquivo(filme.getImagemCaminho());
            filmeRepository.deleteById(id);
        });
    }
}
