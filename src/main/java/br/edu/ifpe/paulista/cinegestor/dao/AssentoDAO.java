package br.edu.ifpe.paulista.cinegestor.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.paulista.cinegestor.model.Assento;
import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;

public class AssentoDAO {

	// Listar Assentos Ocupados para uma Sessão
	public List<Assento> listarAssentosPorSessao(int idSessao) {
	    String sql = "SELECT * FROM assentos WHERE id_sessao = ? ORDER BY identificador";
	    List<Assento> assentos = new ArrayList<>();

	    try (Connection conn = ConexaoDB.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, idSessao);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            Assento assento = new Assento();
	            assento.setId(rs.getInt("id_assento"));
	            assento.setIdSessao(rs.getInt("id_sessao"));
	            assento.setIdentificador(rs.getString("identificador"));
	            assento.setStatus(rs.getString("status"));

	            assentos.add(assento);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return assentos;
	}

	// Atualizar o Status de um Assento
	public void atualizarStatusAssento(int idAssento, String novoStatus) {
	    String sql = "UPDATE assentos SET status = ? WHERE id_assento = ?";

	    try (Connection conn = ConexaoDB.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, novoStatus);
	        stmt.setInt(2, idAssento);

	        stmt.executeUpdate();
	        System.out.println("Status do assento atualizado com sucesso!");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// Registrar Assentos Escolhidos
	public void registrarAssentos(int idSessao, List<String> assentosEscolhidos) {
	    String sql = "INSERT INTO assentos (id_sessao, identificador, status) VALUES (?, ?, ?)";

	    try (Connection conn = ConexaoDB.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        for (String assento : assentosEscolhidos) {
	            stmt.setInt(1, idSessao);
	            stmt.setString(2, assento);
	            stmt.setString(3, "ocupado");
	            stmt.addBatch();
	        }

	        stmt.executeBatch();
	        System.out.println("Assentos registrados com sucesso!");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	// Para evitar que dois vendedores selecionem o mesmo assento ao mesmo tempo
	public boolean reservarAssento(int idAssento) {
	    String sql = "UPDATE assentos SET status = 'ocupado' WHERE id_assento = ? AND status = 'disponivel'";

	    try (Connection conn = ConexaoDB.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, idAssento);

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; // Retorna true se a reserva foi bem-sucedida

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
}
