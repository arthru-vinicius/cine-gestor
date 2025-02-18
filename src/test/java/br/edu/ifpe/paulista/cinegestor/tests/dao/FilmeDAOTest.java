package br.edu.ifpe.paulista.cinegestor.tests.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import br.edu.ifpe.paulista.cinegestor.dao.FilmeDAO;
import br.edu.ifpe.paulista.cinegestor.model.Filme;
import br.edu.ifpe.paulista.cinegestor.util.ConexaoDB;
import br.edu.ifpe.paulista.cinegestor.util.GerenciadorArquivos;

public class FilmeDAOTest {

    private FilmeDAO filmeDAO = new FilmeDAO();

    // Cria um arquivo temporário simulando uma imagem (pode ser um arquivo de texto com extensão .jpg)
    private File criarArquivoImagemTemp(String conteudo, String extensao) throws IOException {
        File tempFile = File.createTempFile("imagemTeste_", extensao);
        Files.write(tempFile.toPath(), conteudo.getBytes());
        return tempFile;
    }

    // Remove o registro inserido diretamente no banco de dados (para limpeza)
    private void removerFilmeDoBanco(int idFilme) {
        String deleteSql = "DELETE FROM filme WHERE id_filme = ?";
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
            stmt.setInt(1, idFilme);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retorna o ID do filme a partir do título (assumindo que o título seja único para o teste)
    private Optional<Integer> buscarFilmeIdPorTitulo(String titulo) {
        String sql = "SELECT id_filme FROM filme WHERE titulo = ?";
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getInt("id_filme"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @AfterEach
    public void tearDown() {
        // Limpeza dos arquivos salvos pela GerenciadorArquivos (imagens de filmes)
        // É importante apagar os arquivos salvos em src/main/resources/img/
        File dirImg = new File("src" + File.separator + "main" + File.separator + "resources" 
                + File.separator + "img" + File.separator);
        if (dirImg.exists() && dirImg.isDirectory()) {
            for (File file : dirImg.listFiles()) {
                file.delete();
            }
        }
    }

    /**
     * Cenário: Inserir um novo filme.
     * Descrição: Cria um objeto Filme com dados únicos e uma imagem temporária,
     * insere o filme, e depois verifica se o filme aparece na listagem.
     */
    @Test
    public void testInserirFilme() throws IOException {
        // Arrange
        String tituloUnico = "Filme Teste " + System.currentTimeMillis();
        Filme filme = new Filme();
        filme.setTitulo(tituloUnico);
        filme.setSinopse("Sinopse do filme de teste");
        // Para simplificar, usamos LocalTime como String (assumindo que getDuracao espera um formato "HH:mm:ss")
        filme.setDuracao(LocalTime.of(1, 30).toString());
        filme.setClassificacaoEtaria("12");
        
        File imagemTemp = criarArquivoImagemTemp("conteudo imagem filme", ".jpg");
        
        // Act
        filmeDAO.inserirFilme(filme, imagemTemp);
        
        // Assert
        List<Filme> filmes = filmeDAO.listarFilmes();
        Optional<Filme> filmeInserido = filmes.stream()
                .filter(f -> f.getTitulo().equals(tituloUnico))
                .findFirst();
        assertTrue(filmeInserido.isPresent(), "O filme deve estar presente na listagem após inserção.");
        // Verifica se o caminho da imagem foi definido
        assertNotNull(filmeInserido.get().getImagem(), "O caminho da imagem não deve ser nulo.");
        
        // Limpeza: Remove o filme inserido do banco
        buscarFilmeIdPorTitulo(tituloUnico).ifPresent(this::removerFilmeDoBanco);
        imagemTemp.delete();
    }

    /**
     * Cenário: Atualizar um filme existente.
     * Descrição: Insere um filme, atualiza alguns campos e a imagem, e verifica se as alterações foram persistidas.
     */
    @Test
    public void testAtualizarFilme() throws IOException {
        // Arrange: Insere um filme primeiro
        String tituloOriginal = "Filme Atualizar " + System.currentTimeMillis();
        Filme filme = new Filme();
        filme.setTitulo(tituloOriginal);
        filme.setSinopse("Sinopse original");
        filme.setDuracao("01:30:00");
        filme.setClassificacaoEtaria("12");
        
        File imagemTemp = criarArquivoImagemTemp("imagem original", ".jpg");
        filmeDAO.inserirFilme(filme, imagemTemp);
        
        // Recupera o filme inserido para obter o ID
        int idFilme = buscarFilmeIdPorTitulo(tituloOriginal)
                .orElseThrow(() -> new RuntimeException("Filme não inserido."));
        filme.setId(idFilme);
        
        // Act: Atualiza o filme
        String novoTitulo = "Filme Atualizado " + System.currentTimeMillis();
        filme.setTitulo(novoTitulo);
        filme.setSinopse("Sinopse atualizada");
        File novaImagemTemp = criarArquivoImagemTemp("imagem atualizada", ".jpg");
        filmeDAO.atualizarFilme(filme, novaImagemTemp);
        
        // Assert: Recupera o filme e verifica os dados atualizados
        List<Filme> filmes = filmeDAO.listarFilmes();
        Optional<Filme> filmeAtualizadoOpt = filmes.stream()
                .filter(f -> f.getId() == idFilme)
                .findFirst();
        assertTrue(filmeAtualizadoOpt.isPresent(), "O filme atualizado deve estar presente na listagem.");
        Filme filmeAtualizado = filmeAtualizadoOpt.get();
        assertEquals(novoTitulo, filmeAtualizado.getTitulo(), "O título do filme deve estar atualizado.");
        assertEquals("Sinopse atualizada", filmeAtualizado.getSinopse(), "A sinopse deve estar atualizada.");
        assertNotNull(filmeAtualizado.getImagem(), "O caminho da nova imagem não deve ser nulo.");
        
        // Limpeza: Remove o filme inserido do banco
        removerFilmeDoBanco(idFilme);
        imagemTemp.delete();
        novaImagemTemp.delete();
    }

    /**
     * Cenário: Excluir um filme.
     * Descrição: Insere um filme, em seguida, o remove, e verifica que o registro não está mais presente
     * na listagem e que o arquivo de imagem associado foi excluído do sistema.
     */
    @Test
    public void testExcluirFilme() throws IOException {
        // Arrange: Insere um filme
        String tituloExcluir = "Filme Excluir " + System.currentTimeMillis();
        Filme filme = new Filme();
        filme.setTitulo(tituloExcluir);
        filme.setSinopse("Sinopse para exclusão");
        filme.setDuracao("01:45:00");
        filme.setClassificacaoEtaria("16");
        
        File imagemTemp = criarArquivoImagemTemp("imagem para exclusão", ".jpg");
        filmeDAO.inserirFilme(filme, imagemTemp);
        
        // Recupera o filme inserido
        int idFilme = buscarFilmeIdPorTitulo(tituloExcluir)
                .orElseThrow(() -> new RuntimeException("Filme não inserido."));
        filme.setId(idFilme);
        // Guarda o caminho da imagem para checagem após exclusão
        String caminhoImagem = filme.getImagem();
        File imagemArquivo = GerenciadorArquivos.getFilme(caminhoImagem);
        assertTrue(imagemArquivo.exists(), "A imagem associada deve existir antes da exclusão.");
        
        // Act: Exclui o filme
        filmeDAO.excluirFilme(idFilme);
        
        // Assert: Verifica que o filme não está mais na listagem
        List<Filme> filmes = filmeDAO.listarFilmes();
        boolean filmeExiste = filmes.stream().anyMatch(f -> f.getId() == idFilme);
        assertFalse(filmeExiste, "O filme não deve existir após a exclusão.");
        
        // Verifica que o arquivo de imagem foi excluído
        assertFalse(imagemArquivo.exists(), "O arquivo de imagem deve ter sido excluído.");
        
        imagemTemp.delete();
    }
}
