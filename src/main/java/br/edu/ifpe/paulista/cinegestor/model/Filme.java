package br.edu.ifpe.paulista.cinegestor.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Filme {
    private int id;
    private String titulo;
    private String sinopse;
    private String duracao; // Representado como uma String no formato HH:mm:ss
    private String classificacaoEtaria;
    private String imagem;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDuracao() {
        return duracao;
    }
    
    /**
     * Converte a duração no formato HH:mm:ss para uma representação visual em minutos.
     * Por exemplo, "01:30:00" será exibido como "90 minutos".
     *
     * @return A duração em minutos, seguida de " minutos", ou a string original em caso de erro.
     */
    public String getDuracaoVisual() {
        if (duracao == null || duracao.isEmpty()) {
            return "";
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime time = LocalTime.parse(duracao, formatter);
            int totalMinutos = time.getHour() * 60 + time.getMinute();
            return totalMinutos + " minutos";
        } catch (Exception e) {
            // Em caso de erro na conversão, retorna a string original
            return duracao;
        }
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getClassificacaoEtaria() {
        return classificacaoEtaria;
    }

    public void setClassificacaoEtaria(String classificacaoEtaria) {
        this.classificacaoEtaria = classificacaoEtaria;
    }
    
    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
