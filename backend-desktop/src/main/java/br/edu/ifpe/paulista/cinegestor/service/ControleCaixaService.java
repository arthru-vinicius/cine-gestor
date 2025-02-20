package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.model.Ingresso;
import br.edu.ifpe.paulista.cinegestor.repository.IngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ControleCaixaService {

    @Autowired
    private IngressoRepository ingressoRepository;

    public BigDecimal calcularTotalDiario() {
        List<Ingresso> ingressos = ingressoRepository.findAll();
        return ingressos.stream()
                .filter(i -> i.getDataVenda().toLocalDate().equals(LocalDate.now()))
                .map(i -> i.getPrecoIngresso().getPreco())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
