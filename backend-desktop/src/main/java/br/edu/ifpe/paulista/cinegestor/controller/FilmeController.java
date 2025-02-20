package br.edu.ifpe.paulista.cinegestor.controller;

import br.edu.ifpe.paulista.cinegestor.model.Filme;
import br.edu.ifpe.paulista.cinegestor.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    // Endpoint para listar todos os filmes
    @GetMapping
    public List<Filme> listarFilmes() {
        return filmeService.listarFilmes();
    }

    // Novo endpoint para listar apenas os filmes com sessões programadas
    @GetMapping("/com-sessoes")
    public List<Filme> listarFilmesComSessoes() {
        return filmeService.listarFilmesComSessoes();
    }

    // Endpoint para cadastrar um novo filme com imagem
    @PostMapping
    public ResponseEntity<Filme> cadastrarFilme(@RequestParam String titulo,
                                                @RequestParam String sinopse,
                                                @RequestParam String duracao,
                                                @RequestParam String classificacaoEtaria,
                                                @RequestParam MultipartFile imagem) throws IOException {
        Filme filme = new Filme();
        filme.setTitulo(titulo);
        filme.setSinopse(sinopse);
        filme.setDuracao(duracao);
        filme.setClassificacaoEtaria(classificacaoEtaria);
        Filme filmeCadastrado = filmeService.cadastrarFilme(filme, imagem);
        return ResponseEntity.ok(filmeCadastrado);
    }

    // Endpoint para atualizar um filme existente
    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(@PathVariable Integer id, @RequestBody Filme filmeAtualizado) {
        Filme filme = filmeService.atualizarFilme(id, filmeAtualizado);
        if (filme != null) {
            return ResponseEntity.ok(filme);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para deletar um filme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable Integer id) {
        filmeService.deletarFilme(id);
        return ResponseEntity.noContent().build();
    }
}
