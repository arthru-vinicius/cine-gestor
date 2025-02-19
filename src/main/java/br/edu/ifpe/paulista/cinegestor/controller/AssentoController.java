package br.edu.ifpe.paulista.cinegestor.controller;

import br.edu.ifpe.paulista.cinegestor.model.Assento;
import br.edu.ifpe.paulista.cinegestor.repository.AssentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assentos")
public class AssentoController {

    @Autowired
    private AssentoRepository assentoRepository;

    @GetMapping("/{salaId}")
    public List<Assento> listarAssentos(@PathVariable Integer salaId) {
        return assentoRepository.findBySalaId(salaId);
    }
}
