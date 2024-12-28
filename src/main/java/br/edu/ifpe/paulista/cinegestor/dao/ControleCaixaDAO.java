package br.edu.ifpe.paulista.cinegestor.dao;

import java.sql.*;

import br.edu.ifpe.paulista.cinegestor.model.ControleCaixa;
import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;

public class ControleCaixaDAO {
    public void registrarTransacao(ControleCaixa transacao) {
        String sql = "INSERT INTO ControleCaixa (tipo_transacao, valor, data_transacao, descricao) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, transacao.getTipoTransacao());
            stmt.setDouble(2, transacao.getValor());
            stmt.setString(3, transacao.getDataTransacao().toString());
            stmt.setString(4, transacao.getDescricao());

            stmt.executeUpdate();
            System.out.println("Transação registrada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
