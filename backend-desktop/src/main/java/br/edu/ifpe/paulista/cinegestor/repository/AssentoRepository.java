package br.edu.ifpe.paulista.cinegestor.repository;

import br.edu.ifpe.paulista.cinegestor.model.Assento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface AssentoRepository extends JpaRepository<Assento, Integer> {
    List<Assento> findBySalaId(Integer salaId);

    @Transactional
    void deleteBySalaId(Integer salaId);
}
