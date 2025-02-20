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

    @GetMapping
    public List<Filme> listarFilmes() {
        return filmeService.listarFilmes();
    }

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
        return ResponseEntity.ok(filmeService.cadastrarFilme(filme, imagem));
    }
}
