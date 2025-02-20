package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.model.Ingresso;
import br.edu.ifpe.paulista.cinegestor.model.Sessao;
import br.edu.ifpe.paulista.cinegestor.repository.IngressoRepository;
import br.edu.ifpe.paulista.cinegestor.repository.SessaoRepository;
import br.edu.ifpe.paulista.cinegestor.util.GeradorPDFBox;
import br.edu.ifpe.paulista.cinegestor.util.GerenciadorArquivos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RelatorioService {

    @Autowired
    private IngressoRepository ingressoRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private GeradorPDFBox geradorPDFBox;

    @Autowired
    private GerenciadorArquivos gerenciadorArquivos;

    public byte[] gerarRelatorio(String tipo) throws IOException {
        LocalDateTime inicio;
        if ("diario".equalsIgnoreCase(tipo)) {
            inicio = LocalDateTime.now().minusDays(1);
        } else if ("mensal".equalsIgnoreCase(tipo)) {
            inicio = LocalDateTime.now().minusMonths(1);
        } else {
            inicio = LocalDateTime.of(2000, 1, 1, 0, 0); // "total" pega tudo
        }

        List<Ingresso> ingressos = ingressoRepository.findAll().stream()
                .filter(i -> i.getDataVenda().isAfter(inicio))
                .collect(Collectors.toList());

        BigDecimal totalArrecadado = ingressos.stream()
                .map(i -> i.getPrecoIngresso().getPreco())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Long> salasFilmes = ingressos.stream()
                .collect(Collectors.groupingBy(i -> i.getSessao().getSala().getNumeroSala(), Collectors.counting()));

        StringBuilder markdown = new StringBuilder();
        markdown.append("# Relatório ").append(tipo.toUpperCase()).append("\n\n");
        markdown.append("**Total Arrecadado:** R$ ").append(totalArrecadado).append("\n\n");
        markdown.append("## Salas\n\n");

        salasFilmes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entry -> {
                    markdown.append("### Sala ").append(entry.getKey()).append("\n");
                    markdown.append("- Filmes exibidos: ").append(entry.getValue()).append("\n");
                    markdown.append("- Lugares ocupados: ").append(entry.getValue()).append("\n");
                    markdown.append("- Total arrecadado: R$ ").append(entry.getValue()).append("\n\n");
                });

        byte[] pdf = geradorPDFBox.gerarRelatorioPDF("Relatório " + tipo.toUpperCase(), markdown.toString());
        gerenciadorArquivos.salvarRelatorio("relatorio_" + tipo + ".pdf", pdf);

        return pdf;
    }
}
