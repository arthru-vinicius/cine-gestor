package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.dao.ControleCaixaDAO;
import br.edu.ifpe.paulista.cinegestor.dao.IngressoDAO;
import br.edu.ifpe.paulista.cinegestor.model.ControleCaixa;
import br.edu.ifpe.paulista.cinegestor.util.MockPagamento;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class VendaService {

    private final IngressoDAO ingressoDAO;
    private final ControleCaixaDAO controleCaixaDAO;

    public VendaService() {
        this.ingressoDAO = new IngressoDAO();
        this.controleCaixaDAO = new ControleCaixaDAO();
    }

    public boolean processarPagamento(int idIngresso, double valor, String metodoPagamento) throws SQLException {
        // Simular o pagamento com Mock
        boolean pagamentoAprovado = MockPagamento.processarPagamento(valor, metodoPagamento);

        if (pagamentoAprovado) {
            // Atualizar status do ingresso para "confirmado"
            ingressoDAO.confirmarVenda(idIngresso);

            // Registrar a transação no ControleCaixa
            ControleCaixa transacao = new ControleCaixa();
            transacao.setTipoTransacao("entrada");
            transacao.setValor(valor);
            transacao.setDataTransacao(LocalDateTime.now());
            transacao.setDescricao("Venda de ingresso #" + idIngresso);
            controleCaixaDAO.registrarTransacao(transacao);

            return true; // Pagamento aprovado
        } else {
            System.out.println("Pagamento recusado. O ingresso permanece como temporário.");
            return false; // Pagamento recusado
        }
    }
}
