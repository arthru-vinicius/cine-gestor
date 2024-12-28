package br.edu.ifpe.paulista.cinegestor.dao;

import br.edu.ifpe.paulista.cinegestor.model.Filme;
import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;
import br.edu.ifpe.paulista.cinegestor.util.GerenciadorImagens;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    // Inserir um novo filme
    public void inserirFilme(Filme filme, File arquivoImagem) {
        String sql = "INSERT INTO filme (titulo, sinopse, duracao, classificacao_etaria) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
        	// Salvar a imagem no diretório e obter o caminho relativo
            String caminhoImagem = GerenciadorImagens.salvarImagem(arquivoImagem);
            filme.setImagem(caminhoImagem);

            stmt.setString(1, filme.getTitulo());
            stmt.setString(2, filme.getSinopse());
            stmt.setString(3, filme.getDuracao());
            stmt.setString(4, filme.getClassificacaoEtaria());
            stmt.setString(5, caminhoImagem);

            stmt.executeUpdate();
            System.out.println("Filme inserido com sucesso!");

        }catch (IOException e) {
            System.err.println("Erro ao salvar a imagem: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar todos os filmes
    public List<Filme> listarFilmes() {
        String sql = "SELECT * FROM filme";
        List<Filme> filmes = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Filme filme = new Filme();
                filme.setId(rs.getInt("id_filme"));
                filme.setTitulo(rs.getString("titulo"));
                filme.setSinopse(rs.getString("sinopse"));
                filme.setDuracao(rs.getString("duracao"));
                filme.setClassificacaoEtaria(rs.getString("classificacao_etaria"));
                filme.setImagem(rs.getString("imagem"));

                filmes.add(filme);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filmes;
    }

    // Atualizar um filme existente
    public void atualizarFilme(Filme filme, File novaImagem) {
        String sql = "UPDATE filme SET titulo = ?, sinopse = ?, duracao = ?, classificacao_etaria = ?, imagem = ? WHERE id_filme = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Excluir a imagem antiga se uma nova for fornecida
            if (novaImagem != null) {
                GerenciadorImagens.excluirImagem(filme.getImagem());
                String novoCaminho = GerenciadorImagens.salvarImagem(novaImagem);
                filme.setImagem(novoCaminho);
            }

            // Configurar parâmetros do SQL
            stmt.setString(1, filme.getTitulo());
            stmt.setString(2, filme.getSinopse());
            stmt.setString(3, filme.getDuracao());
            stmt.setString(4, filme.getClassificacaoEtaria());
            stmt.setString(5, filme.getImagem());
            stmt.setInt(6, filme.getId());

            stmt.executeUpdate();
            System.out.println("Filme atualizado com sucesso!");

        } catch (IOException e) {
            System.err.println("Erro ao salvar a nova imagem: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remover um filme pelo ID
    public void excluirFilme(int idFilme) {
        String sql = "DELETE FROM filme WHERE id_filme = ?";

        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFilme);

            stmt.executeUpdate();
            System.out.println("Filme removido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
