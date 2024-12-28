package br.edu.ifpe.paulista.cinegestor.dao;

import br.edu.ifpe.paulista.cinegestor.model.Sala;
import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO {

    // Inserir uma nova sala
    public void inserirSala(Sala sala) {
        String sql = "INSERT INTO sala (numero_sala, qtd_cadeiras, colunas, linhas, lado_entrada) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getNumeroSala());
            stmt.setInt(2, sala.getQtdCadeiras());
            stmt.setInt(3, sala.getColunas());
            stmt.setInt(4, sala.getLinhas());
            stmt.setString(5, sala.getLadoEntrada());

            stmt.executeUpdate();
            System.out.println("Sala inserida com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar todas as salas
    public List<Sala> listarSalas() {
        String sql = "SELECT * FROM sala";
        List<Sala> salas = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sala sala = new Sala();
                sala.setId(rs.getInt("id_sala"));
                sala.setNumeroSala(rs.getString("numero_sala"));
                sala.setQtdCadeiras(rs.getInt("qtd_cadeiras"));
                sala.setColunas(rs.getInt("colunas"));
                sala.setLinhas(rs.getInt("linhas"));
                sala.setLadoEntrada(rs.getString("lado_entrada"));

                salas.add(sala);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salas;
    }

    // Atualizar uma sala existente
    public void atualizarSala(Sala sala) {
        String sql = "UPDATE sala SET qtd_cadeiras = ?, colunas = ?, linhas = ?, lado_entrada = ? WHERE numero_sala = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sala.getQtdCadeiras());
            stmt.setInt(2, sala.getColunas());
            stmt.setInt(3, sala.getLinhas());
            stmt.setString(4, sala.getLadoEntrada());
            stmt.setString(5, sala.getNumeroSala());

            stmt.executeUpdate();
            System.out.println("Sala atualizada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remover uma sala pelo número da sala
    public void excluirSala(String numeroSala) {
        String sql = "DELETE FROM sala WHERE numero_sala = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroSala);

            stmt.executeUpdate();
            System.out.println("Sala removida com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
