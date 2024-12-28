package br.edu.ifpe.paulista.cinegestor.dao;

import br.edu.ifpe.paulista.cinegestor.model.Usuario;
import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para inserir um usuário no banco
	public void inserirUsuario(Usuario usuario) {
	    String sql = "INSERT INTO usuario (nome_completo, cpf, contato, login, senha, tipo) VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conn = ConexaoDB.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, usuario.getNomeCompleto());
	        stmt.setString(2, usuario.getCpf());
	        stmt.setString(3, usuario.getContato());
	        stmt.setString(4, usuario.getLogin());
	        stmt.setString(5, usuario.getSenha());
	        stmt.setString(6, usuario.getTipo().name()); // Converte o enum para String

	        stmt.executeUpdate();
	        System.out.println("Usuário inserido com sucesso!");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    // Método para buscar todos os usuários
	public List<Usuario> listarUsuarios() {
	    String sql = "SELECT * FROM usuario";
	    List<Usuario> usuarios = new ArrayList<>();

	    try (Connection conn = ConexaoDB.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        while (rs.next()) {
	            Usuario usuario = new Usuario();
	            usuario.setId(rs.getInt("id_usuario"));
	            usuario.setNomeCompleto(rs.getString("nome_completo"));
	            usuario.setCpf(rs.getString("cpf"));
	            usuario.setContato(rs.getString("contato"));
	            usuario.setLogin(rs.getString("login"));
	            usuario.setSenha(rs.getString("senha"));
	            usuario.setTipo(Usuario.TipoUsuario.valueOf(rs.getString("tipo").toUpperCase())); // Converte String para enum

	            usuarios.add(usuario);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return usuarios;
	}

    // Método para atualizar um usuário
	public void atualizarUsuario(Usuario usuario) {
	    String sql = "UPDATE usuario SET nome_completo = ?, contato = ?, login = ?, senha = ?, tipo = ? WHERE cpf = ?";

	    try (Connection conn = ConexaoDB.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, usuario.getNomeCompleto());
	        stmt.setString(2, usuario.getContato());
	        stmt.setString(3, usuario.getLogin());
	        stmt.setString(4, usuario.getSenha());
	        stmt.setString(5, usuario.getTipo().name()); // Converte o enum para String
	        stmt.setString(6, usuario.getCpf());

	        stmt.executeUpdate();
	        System.out.println("Usuário atualizado com sucesso!");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    // Método para excluir um usuário pelo CPF
    public void excluirUsuario(String cpf) {
        String sql = "DELETE FROM usuario WHERE cpf = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            stmt.executeUpdate();
            System.out.println("Usuário excluído com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método para autenticar o usuário
    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";
        Usuario usuario = null;

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNomeCompleto(rs.getString("nome_completo"));
                usuario.setCpf(rs.getString("cpf"));
                usuario.setContato(rs.getString("contato"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setTipo(Usuario.TipoUsuario.valueOf(rs.getString("tipo").toUpperCase())); // Converte String para enum
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario; // Retorna null se o login falhar
    }
}
