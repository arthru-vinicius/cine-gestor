package br.edu.ifpe.paulista.cinegestor.controller;

import br.edu.ifpe.paulista.cinegestor.model.Sala;
import br.edu.ifpe.paulista.cinegestor.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping
    public List<Sala> listarSalas() {
        return salaService.listarSalas();
    }

    @PostMapping
    public ResponseEntity<Sala> cadastrarSala(@RequestBody Sala sala) {
        return ResponseEntity.ok(salaService.cadastrarSala(sala));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarSala(@PathVariable Integer id, @RequestBody Sala sala) {
        try {
            Sala atualizada = salaService.atualizarSala(id, sala);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSala(@PathVariable Integer id) {
        try {
            salaService.deletarSala(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
