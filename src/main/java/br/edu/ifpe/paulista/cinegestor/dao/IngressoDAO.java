package br.edu.ifpe.paulista.cinegestor.dao;

import br.edu.ifpe.paulista.cinegestor.model.Assento;
import br.edu.ifpe.paulista.cinegestor.model.Ingresso;
import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IngressoDAO {

    // Inserir um novo ingresso
    public void inserirIngresso(Ingresso ingresso) {
        String sql = "INSERT INTO ingresso (id_sessao, id_usuario, id_preco, data_venda, preco_pago, tipo) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ingresso.getIdSessao());
            stmt.setInt(2, ingresso.getIdUsuario());
            stmt.setInt(3, ingresso.getIdPreco());
            stmt.setTimestamp(4, Timestamp.valueOf(ingresso.getDataVenda())); // Converte LocalDateTime para Timestamp
            stmt.setDouble(5, ingresso.getPrecoPago());
            stmt.setString(6, ingresso.getTipo());

            stmt.executeUpdate();
            System.out.println("Ingresso inserido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar todos os ingressos
    public List<Ingresso> listarIngressos() {
        String sql = "SELECT * FROM ingresso ORDER BY data_venda";
        List<Ingresso> ingressos = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Ingresso ingresso = new Ingresso();
                ingresso.setId(rs.getInt("id_ingresso"));
                ingresso.setIdSessao(rs.getInt("id_sessao"));
                ingresso.setIdUsuario(rs.getInt("id_usuario"));
                ingresso.setIdPreco(rs.getInt("id_preco"));
                ingresso.setDataVenda(rs.getTimestamp("data_venda").toLocalDateTime());
                ingresso.setPrecoPago(rs.getDouble("preco_pago"));
                ingresso.setTipo(rs.getString("tipo"));

                ingressos.add(ingresso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingressos;
    }

    // Atualizar um ingresso existente
    public void atualizarIngresso(Ingresso ingresso) {
        String sql = "UPDATE ingresso SET id_sessao = ?, id_usuario = ?, id_preco = ?, data_venda = ?, preco_pago = ?, tipo = ? WHERE id_ingresso = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ingresso.getIdSessao());
            stmt.setInt(2, ingresso.getIdUsuario());
            stmt.setInt(3, ingresso.getIdPreco());
            stmt.setTimestamp(4, Timestamp.valueOf(ingresso.getDataVenda())); // Converte LocalDateTime para Timestamp
            stmt.setDouble(5, ingresso.getPrecoPago());
            stmt.setString(6, ingresso.getTipo());
            stmt.setInt(7, ingresso.getId());

            stmt.executeUpdate();
            System.out.println("Ingresso atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remover um ingresso pelo ID
    public void excluirIngresso(int idIngresso) {
        String sql = "DELETE FROM ingresso WHERE id_ingresso = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idIngresso);

            stmt.executeUpdate();
            System.out.println("Ingresso removido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar preço do ingresso para um dia da semana
    public double buscarPrecoPorDia(String diaSemana) {
        if (diaSemana == null || diaSemana.isEmpty()) {
            throw new IllegalArgumentException("Dia da semana não pode ser nulo ou vazio");
        }

        String sql = "SELECT preco FROM preco_ingresso WHERE dia_semana = ?";
        double preco = 0;

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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
    
    // Buscar Ingresso Detalhado
    public Ingresso buscarIngressoDetalhado(int idIngresso) {
        String sql = "SELECT i.id_ingresso, f.titulo AS nome_filme, s.horario AS horario_sessao, " +
                     "sal.numero_sala AS nome_sala, a.id_assento, a.identificador AS assento_identificador, " +
                     "a.status AS assento_status, i.data_venda, i.preco_pago, i.tipo " +
                     "FROM ingresso i " +
                     "JOIN sessao s ON i.id_sessao = s.id_sessao " +
                     "JOIN filme f ON s.id_filme = f.id_filme " +
                     "JOIN sala sal ON s.id_sala = sal.id_sala " +
                     "JOIN assentos a ON a.id_ingresso = i.id_ingresso " + // Modificação: associação direta pelo id_ingresso
                     "WHERE i.id_ingresso = ?";

        Ingresso ingresso = null;

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idIngresso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Criar objeto Ingresso
                ingresso = new Ingresso();
                ingresso.setId(rs.getInt("id_ingresso"));
                ingresso.setNomeFilme(rs.getString("nome_filme"));
                ingresso.setHorarioSessao(rs.getString("horario_sessao"));
                ingresso.setNomeSala(rs.getString("nome_sala"));
                ingresso.setDataVenda(rs.getTimestamp("data_venda").toLocalDateTime());
                ingresso.setPrecoPago(rs.getDouble("preco_pago"));
                ingresso.setTipo(rs.getString("tipo"));

                // Criar objeto Assento e associar ao ingresso
                Assento assento = new Assento();
                assento.setId(rs.getInt("id_assento"));
                assento.setIdentificador(rs.getString("assento_identificador"));
                assento.setStatus(rs.getString("assento_status"));

                ingresso.setAssento(assento.getIdentificador()); // Associar o objeto Assento ao Ingresso
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingresso;
    }
    
    // atualizar o status do ingresso após o pagamento
    public void confirmarVenda(int idIngresso) throws SQLException {
    	if (!verificarExistenciaIngresso(idIngresso)) {
            throw new SQLException("Ingresso com ID " + idIngresso + " não encontrado.");
        }
    	
        String sql = "UPDATE ingresso SET status = 'confirmado' WHERE id_ingresso = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idIngresso);
            stmt.executeUpdate();

            System.out.println("Venda confirmada para o ingresso #" + idIngresso);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void associarAssentoAoIngresso(int idAssento, int idIngresso) throws SQLException {
    	if (!verificarExistenciaIngresso(idIngresso)) {
            throw new SQLException("Ingresso com ID " + idIngresso + " não encontrado.");
        }
    	
        String sql = "UPDATE assentos SET status = 'ocupado', id_ingresso = ? WHERE id_assento = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idIngresso);
            stmt.setInt(2, idAssento);

            stmt.executeUpdate();
            System.out.println("Assento #" + idAssento + " associado ao ingresso #" + idIngresso);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Assento buscarAssentoPorIngresso(int idIngresso) {
        String sql = "SELECT * FROM assentos WHERE id_ingresso = ?";
        Assento assento = null;

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idIngresso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                assento = new Assento();
                assento.setId(rs.getInt("id_assento"));
                assento.setIdSessao(rs.getInt("id_sessao"));
                assento.setIdentificador(rs.getString("identificador"));
                assento.setStatus(rs.getString("status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assento;
    }
    
    public boolean verificarExistenciaIngresso(int idIngresso) {
        String sql = "SELECT COUNT(*) FROM ingresso WHERE id_ingresso = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idIngresso);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
