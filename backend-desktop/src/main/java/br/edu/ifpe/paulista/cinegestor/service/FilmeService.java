package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.model.Filme;
import br.edu.ifpe.paulista.cinegestor.repository.FilmeRepository;
import br.edu.ifpe.paulista.cinegestor.util.GerenciadorArquivos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private GerenciadorArquivos gerenciadorArquivos;

    public List<Filme> listarFilmes() {
        return filmeRepository.findAll();
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
