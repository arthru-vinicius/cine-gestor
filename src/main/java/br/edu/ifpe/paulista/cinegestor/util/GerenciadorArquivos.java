package br.edu.ifpe.paulista.cinegestor.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GerenciadorArquivos {

	// Define o diretório "CineGestor" dentro da pasta Documents do usuário
	private static final String DIRETORIO_ARQUIVOS = System.getProperty("user.home")
	        + File.separator + "Documents" + File.separator + "CineGestor" + File.separator;
	
	private static final String DIRETORIO_IMAGEM = "src" + File.separator + "main" + File.separator
			+ "resources" + File.separator + "img" + File.separator;

	/**
	 * Salva o arquivo recebido no diretório CineGestor.
	 *
	 * @param arquivo o arquivo a ser salvo
	 * @return o nome único do arquivo salvo (caminho relativo dentro do diretório CineGestor)
	 * @throws IOException se ocorrer erro ao copiar o arquivo
	 * @throws IllegalArgumentException se o arquivo for nulo ou inexistente
	 */
	public static String salvarArquivo(File arquivo) throws IOException {
	    if (arquivo == null || !arquivo.exists()) {
	        throw new IllegalArgumentException("Arquivo inválido ou inexistente.");
	    }

	    // Criar diretório se não existir
	    File diretorio = new File(DIRETORIO_ARQUIVOS);
	    if (!diretorio.exists()) {
	        diretorio.mkdirs();
	    }

	    // Gerar nome único para o arquivo
	    String novoNome = System.currentTimeMillis() + "_" + arquivo.getName();
	    File destino = new File(DIRETORIO_ARQUIVOS + novoNome);

	    // Copiar o arquivo para o diretório CineGestor
	    Files.copy(arquivo.toPath(), destino.toPath());

	    // Retornar o nome único do arquivo salvo (pode ser usado para referência futura)
	    return novoNome;
	}

	/**
	 * Exclui o arquivo com o nome informado, considerando que ele está no diretório CineGestor.
	 *
	 * @param nomeArquivo o nome do arquivo a ser excluído (como retornado por salvarArquivo)
	 */
	public static void excluirArquivo(String nomeArquivo) {
	    if (nomeArquivo == null || nomeArquivo.isEmpty()) {
	        return;
	    }
	    
	    File arquivo;
	    // Se o arquivo é uma imagem de filme, seu caminho relativo começa com "img" + File.separator.
	    if (nomeArquivo.startsWith("img" + File.separator)) {
	        String DIRETORIO_IMAGEM = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
	        arquivo = new File(DIRETORIO_IMAGEM + nomeArquivo);
	    } else {
	        // Para outros arquivos, usa o diretório geral (CineGestor em Documents)
	        arquivo = new File(DIRETORIO_ARQUIVOS + nomeArquivo);
	    }
	    
	    if (arquivo.exists()) {
	        arquivo.delete();
	    }
	}

	/**
	 * Retorna o caminho absoluto do diretório onde os arquivos são armazenados.
	 *
	 * @return o caminho absoluto do diretório CineGestor
	 */
	public static String getDiretorioArquivos() {
	    return DIRETORIO_ARQUIVOS;
	}
	
	/**
	 * Salva a imagem de um filme na pasta de recursos
	 * 
	 * @param arquivo a imagemo a ser salva
	 * @return o caminho relativo da imagem, que será armazenado no banco
	 */
	public static String salvarFilme(File arquivo) throws IOException {
	    if (arquivo == null || !arquivo.exists()) {
	        throw new IllegalArgumentException("Arquivo inválido ou inexistente.");
	    }

	    // Define o diretório de imagens (em src/main/resources/img/)
	    File diretorio = new File(DIRETORIO_IMAGEM);
	    if (!diretorio.exists()) {
	        diretorio.mkdirs();
	    }

	    // Gerar nome único para a imagem
	    String novoNome = System.currentTimeMillis() + "_" + arquivo.getName();
	    File destino = new File(DIRETORIO_IMAGEM + novoNome);

	    // Copiar arquivo para o diretório de imagens
	    Files.copy(arquivo.toPath(), destino.toPath());

	    return "img" + File.separator + novoNome;
	}

	/**
	 * Obtém o objeto File da imagem do filme a partir do caminho relativo.
	 * 
	 * @param caminhoRelativo O caminho relativo da imagem (exemplo: "img/novoNome.jpg")
	 * @return O objeto File correspondente à imagem, ou null se o caminho for nulo ou vazio.
	 */
	public static File getFilme(String caminhoRelativo) {
	    if (caminhoRelativo == null || caminhoRelativo.isEmpty()) {
	        return null;
	    }
	    return new File(DIRETORIO_IMAGEM + caminhoRelativo);
	}
}
