package br.edu.ifpe.paulista.cinegestor.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class GerenciadorArquivos {

    // Diretório para armazenar as imagens dos filmes dentro do projeto
    private static final String DIRETORIO_IMAGEM = "src/main/resources/static/img/";

    private static final String DIRETORIO_RELATORIOS = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "CineGestor" + File.separator + "relatorios" + File.separator;
    
    private static final String DIRETORIO_INGRESSOS = System.getProperty("user.home") 
            + File.separator + "Documents" + File.separator + "CineGestor" 
            + File.separator + "ingressos" + File.separator;

    
    public String salvarRelatorio(String nomeArquivo, byte[] conteudo) throws IOException {
        File diretorio = new File(DIRETORIO_RELATORIOS);
        if (!diretorio.exists()) {
            diretorio.mkdirs(); // Criar diretório caso não exista
        }

        String caminhoCompleto = DIRETORIO_RELATORIOS + nomeArquivo;
        Files.write(Paths.get(caminhoCompleto), conteudo);
        return caminhoCompleto;
    }

    public byte[] carregarRelatorio(String nomeArquivo) throws IOException {
        return Files.readAllBytes(Paths.get(DIRETORIO_RELATORIOS + nomeArquivo));
    }

    /**
     * Salva a imagem de um filme na pasta de recursos.
     * 
     * @param arquivo a imagem a ser salva
     * @return o caminho relativo da imagem, que será armazenado no banco
     */
    public String salvarFilme(MultipartFile arquivo) throws IOException {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo inválido ou inexistente.");
        }

        // Criar diretório se não existir
        File diretorio = new File(DIRETORIO_IMAGEM);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Gerar nome único para a imagem
        String novoNome = System.currentTimeMillis() + "_" + arquivo.getOriginalFilename();
        File destino = new File(DIRETORIO_IMAGEM + novoNome);

        // Copiar arquivo para o diretório de imagens
        arquivo.transferTo(destino);

        return "img/" + novoNome;
    }

    /**
     * Exclui a imagem de um filme.
     * 
     * @param caminhoRelativo O caminho relativo da imagem (exemplo: "img/novoNome.jpg")
     */
    public void excluirArquivo(String caminhoRelativo) {
        if (caminhoRelativo == null || caminhoRelativo.isEmpty()) {
            return;
        }
        
        File arquivo = new File(DIRETORIO_IMAGEM + caminhoRelativo.replace("img/", ""));
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }

    /**
     * Obtém o objeto File da imagem do filme a partir do caminho relativo.
     * 
     * @param caminhoRelativo O caminho relativo da imagem (exemplo: "img/novoNome.jpg")
     * @return O objeto File correspondente à imagem, ou null se o caminho for inválido.
     */
    public File getFilme(String caminhoRelativo) {
        if (caminhoRelativo == null || caminhoRelativo.isEmpty()) {
            return null;
        }
        return new File(DIRETORIO_IMAGEM + caminhoRelativo.replace("img/", ""));
    }
    
    public String salvarIngresso(String nomeArquivo, byte[] conteudo) throws IOException {
        File diretorio = new File(DIRETORIO_INGRESSOS);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        String caminhoCompleto = DIRETORIO_INGRESSOS + nomeArquivo;
        Files.write(Paths.get(caminhoCompleto), conteudo);
        return caminhoCompleto;
    }

    public byte[] carregarIngresso(String nomeArquivo) throws IOException {
        return Files.readAllBytes(Paths.get(DIRETORIO_INGRESSOS + nomeArquivo));
    }
}
