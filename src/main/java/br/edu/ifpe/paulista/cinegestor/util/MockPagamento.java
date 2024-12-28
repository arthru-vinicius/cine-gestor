package br.edu.ifpe.paulista.cinegestor.util;

import java.util.Random;

public class MockPagamento {

    public static boolean processarPagamento(double valor, String metodo) {
        // Simulação de sucesso ou falha (80% de chance de sucesso)
        Random random = new Random();
        boolean sucesso = random.nextInt(100) < 80;

        if (sucesso) {
            System.out.println("Pagamento aprovado! Método: " + metodo + ", Valor: R$ " + valor);
        } else {
            System.out.println("Pagamento recusado! Método: " + metodo + ", Valor: R$ " + valor);
        }

        return sucesso;
    }
}
