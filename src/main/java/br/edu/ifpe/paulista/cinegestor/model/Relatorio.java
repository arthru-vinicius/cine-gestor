package br.edu.ifpe.paulista.cinegestor.model;

public class Relatorio {
    private String descricao; // Exemplo: Nome da Sala, Filme ou Vendedor
    private int total;        // Exemplo: Total de Sessões ou Ingressos Vendidos

    // Getters e Setters
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
