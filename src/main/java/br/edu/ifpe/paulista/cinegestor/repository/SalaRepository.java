package br.edu.ifpe.paulista.cinegestor.repository;

import br.edu.ifpe.paulista.cinegestor.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
    Optional<Sala> findByNumeroSala(String numeroSala);
}
