package br.edu.ifpe.paulista.cinegestor.service;

import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    public double calcularTroco(double valorPago, double valorIngresso) {
        if (valorPago < valorIngresso) {
            throw new IllegalArgumentException("Valor pago é menor que o total da compra!");
        }
        return valorPago - valorIngresso;
    }
}
