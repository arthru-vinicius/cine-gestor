package br.edu.ifpe.paulista.cinegestor.tests.util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;

public class ConexaoDBTest {

    /**
     * Cenário: Estabelecer conexão com o banco de dados.
     * Descrição: Verifica se o método getConnection() retorna uma conexão não nula e válida.
     * Requisitos: É necessário que a instância do MariaDB esteja rodando e acessível
     * na porta e credenciais definidas.
     */
    @Test
    public void testGetConnection() {
        try {
            Connection conn = ConexaoDB.getConnection();
            assertNotNull(conn, "A conexão não deve ser nula.");
            // isValid verifica se a conexão está ativa; 2 segundos de timeout
            assertTrue(conn.isValid(2), "A conexão deve ser válida.");
            conn.close();
        } catch (SQLException e) {
            fail("Não foi possível estabelecer conexão com o banco de dados: " + e.getMessage());
        }
    }
}
