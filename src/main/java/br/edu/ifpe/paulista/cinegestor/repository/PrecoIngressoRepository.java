package br.edu.ifpe.paulista.cinegestor.repository;

import br.edu.ifpe.paulista.cinegestor.model.PrecoIngresso;
import br.edu.ifpe.paulista.cinegestor.model.PrecoIngresso.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrecoIngressoRepository extends JpaRepository<PrecoIngresso, Integer> {
    Optional<PrecoIngresso> findByDiaSemana(DiaSemana diaSemana);
}
