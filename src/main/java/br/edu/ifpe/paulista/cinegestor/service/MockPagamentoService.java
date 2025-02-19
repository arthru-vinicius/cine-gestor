package br.edu.ifpe.paulista.cinegestor.service;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class MockPagamentoService {

    private final Random random = new Random();

    public boolean processarPagamento(double valor, String metodo) {
        // Simulação de sucesso ou falha (80% de chance de sucesso)
        boolean sucesso = random.nextInt(100) < 80;

        if (sucesso) {
            System.out.println("Pagamento aprovado! Método: " + metodo + ", Valor: R$ " + valor);
        } else {
            System.out.println("Pagamento recusado! Método: " + metodo + ", Valor: R$ " + valor);
        }

        return sucesso;
    }
}
