package br.edu.ifpe.paulista.cinegestor.repository;

import br.edu.ifpe.paulista.cinegestor.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface SessaoRepository extends JpaRepository<Sessao, Integer> {
    List<Sessao> findByFilmeId(Integer filmeId);
    List<Sessao> findBySalaId(Integer salaId);
    List<Sessao> findByHorarioBetween(LocalDateTime inicio, LocalDateTime fim);
}
