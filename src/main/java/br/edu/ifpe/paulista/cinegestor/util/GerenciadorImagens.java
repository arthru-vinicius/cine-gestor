package br.edu.ifpe.paulista.cinegestor.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GerenciadorImagens {

    private static final String DIRETORIO_IMAGENS = "src/main/resources/img/";

    // Salvar imagem no diretório
    public static String salvarImagem(File arquivoImagem) throws IOException {
        if (arquivoImagem == null || !arquivoImagem.exists()) {
            throw new IllegalArgumentException("Arquivo inválido ou inexistente.");
        }

        // Criar diretório se não existir
        File diretorio = new File(DIRETORIO_IMAGENS);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Gerar nome único para a imagem
        String novoNome = System.currentTimeMillis() + "_" + arquivoImagem.getName();
        File destino = new File(DIRETORIO_IMAGENS + novoNome);

        // Copiar arquivo para o diretório de imagens
        Files.copy(arquivoImagem.toPath(), destino.toPath());

        // Retornar o caminho relativo
        return "img/" + novoNome;
    }

    // Excluir imagem antiga (usado na atualização)
    public static void excluirImagem(String caminhoRelativo) {
        if (caminhoRelativo == null || caminhoRelativo.isEmpty()) {
            return;
        }

        File arquivo = new File("src/main/resources/" + caminhoRelativo);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}
