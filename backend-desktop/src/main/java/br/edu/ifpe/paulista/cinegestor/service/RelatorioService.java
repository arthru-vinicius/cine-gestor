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
import java.util.HashMap;
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
        // Define o período de início com base no tipo do relatório
        LocalDateTime inicio;
        if ("diario".equalsIgnoreCase(tipo)) {
            inicio = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        } else if ("mensal".equalsIgnoreCase(tipo)) {
            inicio = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        } else { // "total" ou outro valor
            inicio = LocalDateTime.of(2000, 1, 1, 0, 0);
        }

        // Busca todas as sessões encerradas com horário após o início
        List<Sessao> sessoesEncerradas = sessaoRepository.findAll().stream()
                .filter(sessao -> sessao.getStatus() == Sessao.StatusSessao.encerrada
                        && sessao.getHorario().isAfter(inicio)
                        && sessao.getSala() != null)
                .collect(Collectors.toList());

        // Cria um mapa para agrupar os dados por sala (chave: número da sala)
        Map<String, RoomReport> reportMap = new HashMap<>();

        // Para cada sessão encerrada, acumula os dados
        for (Sessao sessao : sessoesEncerradas) {
            String salaNumero = sessao.getSala().getNumeroSala();
            RoomReport report = reportMap.getOrDefault(salaNumero, new RoomReport(salaNumero));
            report.incrementSessaoCount();

            // Obter os ingressos da sessão
            List<Ingresso> ingressosSessao = ingressoRepository.findAll().stream()
                    .filter(i -> i.getSessao() != null && i.getSessao().getId().equals(sessao.getId()))
                    .collect(Collectors.toList());
            report.addIngressos(ingressosSessao.size());
            BigDecimal soma = ingressosSessao.stream()
                    .map(Ingresso::getPrecoPago)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            report.addArrecadado(soma);

            reportMap.put(salaNumero, report);
        }

        // Gerar o markdown para o relatório
        StringBuilder markdown = new StringBuilder();
        markdown.append("# Relatório ").append(tipo.toUpperCase()).append("\n\n");
        markdown.append("## Relatório por Sala\n\n");

        for (RoomReport report : reportMap.values()) {
            markdown.append("### Sala ").append(report.getSalaNumero()).append("\n");
            markdown.append("- Sessões encerradas: ").append(report.getSessaoCount()).append("\n");
            markdown.append("- Ingressos vendidos: ").append(report.getIngressosCount()).append("\n");
            markdown.append("- Total arrecadado: R$ ").append(report.getTotalArrecadado()).append("\n\n");
        }

        byte[] pdf = geradorPDFBox.gerarRelatorioPDF("Relatório " + tipo.toUpperCase(), markdown.toString());
        gerenciadorArquivos.salvarRelatorio("relatorio_" + tipo + ".pdf", pdf);
        return pdf;
    }

    // DTO auxiliar para acumular os dados do relatório por sala
    private static class RoomReport {
        private String salaNumero;
        private int sessaoCount;
        private int ingressosCount;
        private BigDecimal totalArrecadado;

        public RoomReport(String salaNumero) {
            this.salaNumero = salaNumero;
            this.sessaoCount = 0;
            this.ingressosCount = 0;
            this.totalArrecadado = BigDecimal.ZERO;
        }

        public void incrementSessaoCount() {
            this.sessaoCount++;
        }

        public void addIngressos(int quantidade) {
            this.ingressosCount += quantidade;
        }

        public void addArrecadado(BigDecimal valor) {
            this.totalArrecadado = this.totalArrecadado.add(valor);
        }

        public String getSalaNumero() {
            return salaNumero;
        }

        public int getSessaoCount() {
            return sessaoCount;
        }

        public int getIngressosCount() {
            return ingressosCount;
        }

        public BigDecimal getTotalArrecadado() {
            return totalArrecadado;
        }
    }
}
