package br.edu.ifpe.paulista.cinegestor.tests.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import br.edu.ifpe.paulista.cinegestor.util.GeradorPDFBox;
import br.edu.ifpe.paulista.cinegestor.model.Assento;
import br.edu.ifpe.paulista.cinegestor.model.Ingresso;

class GeradorPDFBoxTest {

	private static final String TEST_DIR = "target/test-output/";

	@BeforeAll
	public static void setUpBeforeClass() throws IOException {
	    File dir = new File(TEST_DIR);
	    if (!dir.exists()) {
	        dir.mkdirs();
	    }
	}

	@AfterEach
	public void tearDown() {
	    // Limpar arquivos PDF criados durante os testes
	    File dir = new File(TEST_DIR);
	    if (dir.exists() && dir.isDirectory()) {
	        for (File file : dir.listFiles()) {
	            if (file.getName().endsWith(".pdf")) {
	                file.delete();
	            }
	        }
	    }
	}

	/**
	 * Cenário: Gerar PDF com texto simples
	 * Descrição: Verifica se, ao fornecer um caminho e um conteúdo de texto válido, o método
	 * gerarPDF(String, String) gera um arquivo PDF contendo o texto informado.
	 */
	@Test
	public void testGerarPDFComTextoSimples() throws Exception {
	    // Arrange
	    String caminhoArquivo = TEST_DIR + "testeTexto.pdf";
	    String conteudo = "Teste de conteúdo";

	    // Act
	    GeradorPDFBox.gerarPDF(caminhoArquivo, conteudo);

	    // Assert: verificar se o arquivo foi criado
	    File pdfFile = new File(caminhoArquivo);
	    assertTrue(pdfFile.exists(), "O arquivo PDF deveria existir.");

	    // Ler o conteúdo do PDF e confirmar se o texto está presente
	    try (PDDocument document = PDDocument.load(pdfFile)) {
	        PDFTextStripper stripper = new PDFTextStripper();
	        String pdfText = stripper.getText(document);
	        assertTrue(pdfText.contains(conteudo), "O conteúdo do PDF não contém o texto esperado.");
	    }
	}

	/**
	 * Cenário: Gerar PDF a partir de um objeto Ingresso
	 * Descrição: Cria um objeto Ingresso com dados predefinidos, chama o método gerarPDF(Ingresso, String)
	 * e verifica se o PDF gerado contém as informações do ingresso.
	 */
	@Test
	public void testGerarPDFComIngresso() throws Exception {
	    // Arrange
	    String caminhoArquivo = TEST_DIR + "testeIngresso.pdf";
	    Assento assento = new Assento();
	    assento.setIdentificador("A1");
	    
	    // Criar objeto Ingresso dummy
	    Ingresso ingresso = new Ingresso();
	    // Utilizando setters para gerar informações do ingresso
	    ingresso.setId(123);
	    ingresso.setNomeFilme("Filme Teste");
	    ingresso.setHorarioSessao("18:00");
	    ingresso.setNomeSala("Sala 1");
	    ingresso.setAssento(assento.getIdentificador());
	    ingresso.setPrecoPago(25.50);
	    ingresso.setTipo("Inteira");
	    ingresso.setDataVenda(java.time.LocalDateTime.of(2025, 2, 16, 0, 0));

	    // Act
	    GeradorPDFBox.gerarPDF(ingresso, caminhoArquivo);

	    // Assert: verificar se o arquivo foi criado
	    File pdfFile = new File(caminhoArquivo);
	    assertTrue(pdfFile.exists(), "O arquivo PDF deveria existir.");

	    // Ler o conteúdo do PDF e confirmar se os dados do ingresso estão presentes
	    try (PDDocument document = PDDocument.load(pdfFile)) {
	        PDFTextStripper stripper = new PDFTextStripper();
	        String pdfText = stripper.getText(document);
	        assertTrue(pdfText.contains("Ingresso #123"), "O PDF deveria conter o ID do ingresso.");
	        assertTrue(pdfText.contains("Filme: Filme Teste"), "O PDF deveria conter o nome do filme.");
	        assertTrue(pdfText.contains("Horário: 18:00"), "O PDF deveria conter o horário da sessão.");
	        assertTrue(pdfText.contains("Sala: Sala 1"), "O PDF deveria conter o nome da sala.");
	    }
	}
}
