package br.edu.ifpe.paulista.cinegestor.tests.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.edu.ifpe.paulista.cinegestor.util.MockPagamento;

public class MockPagamentoTest {

    /**
     * Cenário 1: Verificar que o método retorna um valor booleano.
     * Descrição: Executa processarPagamento uma vez e assegura que o resultado é booleano (true ou false).
     */
    @Test
    public void testPagamentoReturnsBoolean() {
        boolean resultado = MockPagamento.processarPagamento(100.0, "Cartao");
        // Apenas garantimos que o método retorna um booleano (true ou false)
        assertTrue(resultado == true || resultado == false, "O método deve retornar um valor booleano.");
    }

    /**
     * Cenário 2: Verificar que ambos os resultados (sucesso e falha) ocorrem.
     * Descrição: Executa o método várias vezes e assegura que, em 100 tentativas, há ao menos uma ocorrência de sucesso e uma de falha.
     */
    @Test
    public void testBothOutcomesOccur() {
        boolean encontrouSucesso = false;
        boolean encontrouFalha = false;
        for (int i = 0; i < 100; i++) {
            boolean resultado = MockPagamento.processarPagamento(50.0, "PIX");
            if (resultado) {
                encontrouSucesso = true;
            } else {
                encontrouFalha = true;
            }
            if (encontrouSucesso && encontrouFalha) {
                break;
            }
        }
        assertTrue(encontrouSucesso, "Deve ocorrer ao menos um pagamento aprovado em 100 tentativas.");
        assertTrue(encontrouFalha, "Deve ocorrer ao menos um pagamento recusado em 100 tentativas.");
    }

    /**
     * Cenário 3: Verificar a distribuição dos resultados.
     * Descrição: Executa processarPagamento 1000 vezes e calcula a taxa de sucesso, 
     * esperando que ela esteja aproximadamente em 80% (com tolerância de 70% a 90%).
     */
    @Test
    public void testDistribution() {
        int total = 1000;
        int successCount = 0;
        for (int i = 0; i < total; i++) {
            if (MockPagamento.processarPagamento(20.0, "Cartao")) {
                successCount++;
            }
        }
        double sucessoPercent = (double) successCount / total;
        System.out.println("Taxa de sucesso: " + sucessoPercent);
        // Aceitamos uma margem de erro de ±10%
        assertTrue(sucessoPercent >= 0.70 && sucessoPercent <= 0.90, "A taxa de sucesso deve estar entre 70% e 90%.");
    }
}