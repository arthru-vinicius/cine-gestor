package br.edu.ifpe.paulista.cinegestor.tests.util;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import br.edu.ifpe.paulista.cinegestor.util.GerenciadorArquivos;

public class GerenciadorArquivosTest {

    // Diretório onde os arquivos gerais são salvos
    private static final String DESTINO_DIR = GerenciadorArquivos.getDiretorioArquivos();
    
    // Diretório onde as imagens dos filmes são salvas
    private static final String DIRETORIO_IMG = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "img" + File.separator;
    
    /**
     * Cria um arquivo temporário com conteúdo simples para uso nos testes.
     */
    private File criarArquivoTemp(String conteudo, String extensao) throws IOException {
        File tempFile = File.createTempFile("test_", extensao);
        Files.write(tempFile.toPath(), conteudo.getBytes());
        return tempFile;
    }
    
    /**
     * Remove todos os arquivos do diretório de arquivos gerais e de imagens após cada teste.
     */
    @AfterEach
    public void tearDown() {
        // Limpar arquivos gerais
        File dirGeral = new File(DESTINO_DIR);
        if (dirGeral.exists() && dirGeral.isDirectory()) {
            for (File file : dirGeral.listFiles()) {
                file.delete();
            }
        }
        // Limpar arquivos de imagens
        File dirImg = new File(DIRETORIO_IMG);
        if (dirImg.exists() && dirImg.isDirectory()) {
            for (File file : dirImg.listFiles()) {
                file.delete();
            }
        }
    }
    
    // --- Testes para salvarArquivo ---
    
    @Test
    public void testSalvarArquivoComArquivoValido() throws IOException {
        String conteudo = "Conteúdo para arquivo geral.";
        File tempFile = criarArquivoTemp(conteudo, ".txt");
        
        String nomeArquivo = GerenciadorArquivos.salvarArquivo(tempFile);
        File arquivoSalvo = new File(DESTINO_DIR + nomeArquivo);
        
        assertTrue(arquivoSalvo.exists(), "O arquivo geral deve existir no diretório de destino.");
        
        // Cleanup
        tempFile.delete();
    }
    
    @Test
    public void testSalvarArquivoComArquivoNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
            GerenciadorArquivos.salvarArquivo(null);
        }, "Deve lançar IllegalArgumentException para arquivo nulo.");
    }
    
    @Test
    public void testSalvarArquivoComArquivoInexistente() {
        File inexistente = new File("arquivo_que_nao_existe.txt");
        assertThrows(IllegalArgumentException.class, () -> {
            GerenciadorArquivos.salvarArquivo(inexistente);
        }, "Deve lançar IllegalArgumentException para arquivo inexistente.");
    }
    
    // --- Testes para excluirArquivo ---
    
    @Test
    public void testExcluirArquivoComArquivoValido() throws IOException {
        String conteudo = "Teste para exclusão de arquivo.";
        File tempFile = criarArquivoTemp(conteudo, ".txt");
        
        String nomeArquivo = GerenciadorArquivos.salvarArquivo(tempFile);
        File arquivoSalvo = new File(DESTINO_DIR + nomeArquivo);
        assertTrue(arquivoSalvo.exists(), "O arquivo deve existir antes da exclusão.");
        
        GerenciadorArquivos.excluirArquivo(nomeArquivo);
        assertFalse(arquivoSalvo.exists(), "O arquivo deve ter sido excluído.");
        
        tempFile.delete();
    }
    
    @Test
    public void testExcluirArquivoComParametroNulo() {
        // Não deve lançar exceção
        assertDoesNotThrow(() -> GerenciadorArquivos.excluirArquivo(null));
    }
    
    @Test
    public void testExcluirArquivoComParametroVazio() {
        // Não deve lançar exceção
        assertDoesNotThrow(() -> GerenciadorArquivos.excluirArquivo(""));
    }
    
    // --- Testes para salvarFilme ---
    
    @Test
    public void testSalvarFilmeComArquivoValido() throws IOException {
        String conteudo = "Conteúdo para imagem de filme.";
        File tempFile = criarArquivoTemp(conteudo, ".jpg");
        
        String caminhoRelativo = GerenciadorArquivos.salvarFilme(tempFile);
        File arquivoSalvo = new File(DIRETORIO_IMG + caminhoRelativo.substring(caminhoRelativo.indexOf(File.separator) + 1));
        // Observação: Como o caminhoRelativo é "img{sep}nome", para montar o caminho absoluto usamos DIRETORIO_IMG e descartamos "img{sep}"
        // ou podemos ajustar a verificação conforme sua lógica.
        assertTrue(arquivoSalvo.exists(), "A imagem do filme deve existir no diretório de imagens.");
        
        tempFile.delete();
    }
    
    // --- Testes para getFilme ---
    
    @Test
    public void testGetFilmeComCaminhoValido() throws IOException {
        String conteudo = "Imagem para teste getFilme.";
        File tempFile = criarArquivoTemp(conteudo, ".png");
        
        String caminhoRelativo = GerenciadorArquivos.salvarFilme(tempFile);
        File imagem = GerenciadorArquivos.getFilme(caminhoRelativo);
        
        assertNotNull(imagem, "O método getFilme não deve retornar null para caminho válido.");
        assertTrue(imagem.exists(), "O arquivo de imagem deve existir.");
        
        tempFile.delete();
    }
    
    @Test
    public void testGetFilmeComCaminhoNulo() {
        assertNull(GerenciadorArquivos.getFilme(null), "Deve retornar null para caminho nulo.");
    }
    
    @Test
    public void testGetFilmeComCaminhoVazio() {
        assertNull(GerenciadorArquivos.getFilme(""), "Deve retornar null para caminho vazio.");
    }
}
