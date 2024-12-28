package br.edu.ifpe.paulista.cinegestor.dao;

import br.edu.ifpe.paulista.cinegestor.model.Sessao;
import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SessaoDAO {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Inserir uma nova sessão
    public void inserirSessao(Sessao sessao) {
        String sql = "INSERT INTO sessao (id_filme, id_sala, horario) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessao.getIdFilme());
            stmt.setInt(2, sessao.getIdSala());
            stmt.setString(3, sessao.getHorario().format(FORMATTER));

            stmt.executeUpdate();
            System.out.println("Sessão inserida com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar todas as sessões
    public List<Sessao> listarSessoes() {
        String sql = "SELECT * FROM sessao";
        List<Sessao> sessoes = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sessao sessao = new Sessao();
                sessao.setId(rs.getInt("id_sessao"));
                sessao.setIdFilme(rs.getInt("id_filme"));
                sessao.setIdSala(rs.getInt("id_sala"));
                sessao.setHorario(LocalDateTime.parse(rs.getString("horario"), FORMATTER));

                sessoes.add(sessao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessoes;
    }

    // Atualizar uma sessão existente
    public void atualizarSessao(Sessao sessao) {
        String sql = "UPDATE sessao SET id_filme = ?, id_sala = ?, horario = ? WHERE id_sessao = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessao.getIdFilme());
            stmt.setInt(2, sessao.getIdSala());
            stmt.setString(3, sessao.getHorario().format(FORMATTER));
            stmt.setInt(4, sessao.getId());

            stmt.executeUpdate();
            System.out.println("Sessão atualizada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remover uma sessão pelo ID
    public void excluirSessao(int idSessao) {
        String sql = "DELETE FROM sessao WHERE id_sessao = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSessao);

            stmt.executeUpdate();
            System.out.println("Sessão removida com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Retorna as sessões de um filme em um dia específico
    public List<Sessao> listarSessoesPorFilmeEData(int idFilme, LocalDateTime data) {
        String sql = "SELECT sess.id_sessao, sess.horario, s.numero_sala " +
                     "FROM sessao sess " +
                     "JOIN sala s ON sess.id_sala = s.id_sala " +
                     "WHERE sess.id_filme = ? AND DATE(sess.horario) = ? " +
                     "ORDER BY sess.horario";

        List<Sessao> sessoes = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFilme);
            stmt.setString(2, data.toLocalDate().toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Sessao sessao = new Sessao();
                sessao.setId(rs.getInt("id_sessao"));
                sessao.setHorario(rs.getTimestamp("horario").toLocalDateTime());
                sessao.setNomeSala(rs.getString("numero_sala"));

                sessoes.add(sessao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sessoes;
    }

    // Retorna uma lista de dias do mês que possuem sessões para um filme
    public List<LocalDateTime> listarDiasComSessoes(int idFilme, int mes, int ano) {
        String sql = "SELECT DISTINCT DATE(sess.horario) AS dia " +
                     "FROM sessao sess " +
                     "WHERE sess.id_filme = ? AND MONTH(sess.horario) = ? AND YEAR(sess.horario) = ? " +
                     "ORDER BY dia";

        List<LocalDateTime> dias = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFilme);
            stmt.setInt(2, mes);
            stmt.setInt(3, ano);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                dias.add(rs.getDate("dia").toLocalDate().atStartOfDay());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dias;
    }

}
