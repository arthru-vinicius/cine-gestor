package br.edu.ifpe.paulista.cinegestor.repository;

import br.edu.ifpe.paulista.cinegestor.model.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngressoRepository extends JpaRepository<Ingresso, Integer> {
}
