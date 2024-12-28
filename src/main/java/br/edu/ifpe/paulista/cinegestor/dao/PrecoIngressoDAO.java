package br.edu.ifpe.paulista.cinegestor.dao;

import br.edu.ifpe.paulista.cinegestor.model.PrecoIngresso;
import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrecoIngressoDAO {

    // Inserir um novo preço para um dia da semana
    public void inserirPrecoIngresso(PrecoIngresso precoIngresso) {
        String sql = "INSERT INTO preco_ingresso (dia_semana, preco) VALUES (?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, precoIngresso.getDiaSemana());
            stmt.setDouble(2, precoIngresso.getPreco());

            stmt.executeUpdate();
            System.out.println("Preço do ingresso inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar todos os preços cadastrados
    public List<PrecoIngresso> listarPrecosIngresso() {
        String sql = "SELECT * FROM preco_ingresso";
        List<PrecoIngresso> precos = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PrecoIngresso precoIngresso = new PrecoIngresso();
                precoIngresso.setId(rs.getInt("id_preco"));
                precoIngresso.setDiaSemana(rs.getString("dia_semana"));
                precoIngresso.setPreco(rs.getDouble("preco"));

                precos.add(precoIngresso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return precos;
    }

    // Atualizar o preço de um dia específico
    public void atualizarPrecoIngresso(PrecoIngresso precoIngresso) {
        String sql = "UPDATE preco_ingresso SET preco = ? WHERE dia_semana = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, precoIngresso.getPreco());
            stmt.setString(2, precoIngresso.getDiaSemana());

            stmt.executeUpdate();
            System.out.println("Preço do ingresso atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar o preço de um dia específico
    public PrecoIngresso buscarPrecoPorDia(String diaSemana) {
        String sql = "SELECT * FROM preco_ingresso WHERE dia_semana = ?";
        PrecoIngresso precoIngresso = null;

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, diaSemana);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                precoIngresso = new PrecoIngresso();
                precoIngresso.setId(rs.getInt("id_preco"));
                precoIngresso.setDiaSemana(rs.getString("dia_semana"));
                precoIngresso.setPreco(rs.getDouble("preco"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return precoIngresso;
    }

    // Remover o preço de um dia específico
    public void excluirPrecoIngresso(String diaSemana) {
        String sql = "DELETE FROM preco_ingresso WHERE dia_semana = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, diaSemana);

            stmt.executeUpdate();
            System.out.println("Preço do ingresso removido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 	// Buscar o preço do ingresso no dia selecionado
    public double buscarPrecoPorDia(LocalDate dia) {
        String sql = "SELECT preco FROM preco_ingresso WHERE dia_semana = ?";

        double preco = 0;

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String diaSemana = dia.getDayOfWeek().toString().toLowerCase(); // Ex.: "monday"
            stmt.setString(1, diaSemana);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                preco = rs.getDouble("preco");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preco;
    }

}
