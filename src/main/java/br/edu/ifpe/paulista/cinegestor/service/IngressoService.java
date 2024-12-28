package br.edu.ifpe.paulista.cinegestor.service;

import br.edu.ifpe.paulista.cinegestor.dao.PrecoIngressoDAO;
import br.edu.ifpe.paulista.cinegestor.model.Ingresso;

import java.time.LocalDate;

public class IngressoService {

    private PrecoIngressoDAO precoIngressoDAO;

    public IngressoService() {
        this.precoIngressoDAO = new PrecoIngressoDAO();
    }

    public void calcularPrecoIngresso(Ingresso ingresso, LocalDate dia) {
        // Buscar o preço do ingresso para o dia selecionado
        double precoPadrao = precoIngressoDAO.buscarPrecoPorDia(dia);

        // Calcular o preço baseado no tipo
        if ("meia".equalsIgnoreCase(ingresso.getTipo())) {
            ingresso.setPrecoPago(precoPadrao / 2);
        } else {
            ingresso.setPrecoPago(precoPadrao);
        }
    }
}
