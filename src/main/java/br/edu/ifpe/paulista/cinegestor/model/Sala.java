package br.edu.ifpe.paulista.cinegestor.model;

public class Sala {
    private int id;
    private String numeroSala;
    private int qtdCadeiras;
    private int colunas;
    private int linhas;
    private String ladoEntrada; // 'esquerda' ou 'direita'

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
    }

    public int getQtdCadeiras() {
        return qtdCadeiras;
    }

    public void setQtdCadeiras(int qtdCadeiras) {
        this.qtdCadeiras = qtdCadeiras;
    }

    public int getColunas() {
        return colunas;
    }

    public void setColunas(int colunas) {
        this.colunas = colunas;
    }

    public int getLinhas() {
        return linhas;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public String getLadoEntrada() {
        return ladoEntrada;
    }

    public void setLadoEntrada(String ladoEntrada) {
        this.ladoEntrada = ladoEntrada;
    }
}
