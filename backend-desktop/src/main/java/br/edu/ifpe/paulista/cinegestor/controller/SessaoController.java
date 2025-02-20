package br.edu.ifpe.paulista.cinegestor.controller;

import br.edu.ifpe.paulista.cinegestor.model.Sessao;
import br.edu.ifpe.paulista.cinegestor.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {

    @Autowired
    private SessaoService sessaoService;

    @GetMapping
    public List<Sessao> listarSessoes() {
        return sessaoService.listarSessoes();
    }

    // Opcional: endpoint para listar apenas sessões agendadas
    @GetMapping("/agendadas")
    public List<Sessao> listarSessoesAgendadas() {
        return sessaoService.listarSessoesAgendadas();
    }

    @PostMapping
    public ResponseEntity<Sessao> cadastrarSessao(@RequestBody Sessao sessao) {
        return ResponseEntity.ok(sessaoService.cadastrarSessao(sessao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sessao> atualizarSessao(@PathVariable Integer id, @RequestBody Sessao sessao) {
        Sessao atualizada = sessaoService.atualizarSessao(id, sessao);
        if (atualizada != null) {
            return ResponseEntity.ok(atualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Em vez de excluir, este endpoint encerra a sessão
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSessao(@PathVariable Integer id) {
        sessaoService.deletarSessao(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ocupar")
    public ResponseEntity<Void> ocuparAssento(@PathVariable Integer id, @RequestParam String assento) {
        sessaoService.ocuparAssento(id, assento);
        return ResponseEntity.ok().build();
    }
}
