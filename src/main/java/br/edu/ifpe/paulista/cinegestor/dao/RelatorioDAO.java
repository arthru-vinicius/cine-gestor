package br.edu.ifpe.paulista.cinegestor.dao;

import br.edu.ifpe.paulista.cinegestor.model.Relatorio;

import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Relatório de Uso das Salas
    public List<Relatorio> gerarRelatorioUsoSalas(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT s.numero_sala AS descricao, COUNT(sess.id_sessao) AS total " +
                     "FROM sala s " +
                     "JOIN sessao sess ON s.id_sala = sess.id_sala " +
                     "WHERE sess.horario BETWEEN ? AND ? " +
                     "GROUP BY s.numero_sala " +
                     "ORDER BY total DESC";

        List<Relatorio> relatorios = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inicio.format(FORMATTER));
            stmt.setString(2, fim.format(FORMATTER));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setDescricao(rs.getString("descricao"));
                relatorio.setTotal(rs.getInt("total"));
                relatorios.add(relatorio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return relatorios;
    }

    // Relatório de Desempenho dos Filmes
    public List<Relatorio> gerarRelatorioDesempenhoFilmes(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT f.titulo AS descricao, COUNT(i.id_ingresso) AS total " +
                     "FROM filme f " +
                     "JOIN sessao sess ON f.id_filme = sess.id_filme " +
                     "JOIN ingresso i ON sess.id_sessao = i.id_sessao " +
                     "WHERE i.data_venda BETWEEN ? AND ? " +
                     "GROUP BY f.titulo " +
                     "ORDER BY total DESC";

        List<Relatorio> relatorios = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inicio.format(FORMATTER));
            stmt.setString(2, fim.format(FORMATTER));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setDescricao(rs.getString("descricao"));
                relatorio.setTotal(rs.getInt("total"));
                relatorios.add(relatorio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return relatorios;
    }

    // Relatório de Vendas por Vendedor
    public List<Relatorio> gerarRelatorioVendasPorVendedor(LocalDateTime inicio, LocalDateTime fim) {
        String sql = "SELECT u.nome_completo AS descricao, COUNT(i.id_ingresso) AS total " +
                     "FROM usuario u " +
                     "JOIN ingresso i ON u.id_usuario = i.id_usuario " +
                     "WHERE i.data_venda BETWEEN ? AND ? " +
                     "GROUP BY u.nome_completo " +
                     "ORDER BY total DESC";

        List<Relatorio> relatorios = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, inicio.format(FORMATTER));
            stmt.setString(2, fim.format(FORMATTER));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Relatorio relatorio = new Relatorio();
                relatorio.setDescricao(rs.getString("descricao"));
                relatorio.setTotal(rs.getInt("total"));
                relatorios.add(relatorio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return relatorios;
    }
}
