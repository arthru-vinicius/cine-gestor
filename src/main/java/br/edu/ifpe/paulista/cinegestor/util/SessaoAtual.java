package br.edu.ifpe.paulista.cinegestor.util;

import br.edu.ifpe.paulista.cinegestor.model.Usuario;

public class SessaoAtual {
    private static SessaoAtual instancia;
    private Usuario usuarioLogado;

    private SessaoAtual() {
    }

    public static SessaoAtual getInstancia() {
        if (instancia == null) {
            instancia = new SessaoAtual();
        }
        return instancia;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void encerrarSessao() {
        this.usuarioLogado = null;
    }

    public boolean isUsuarioLogado() {
        return this.usuarioLogado != null;
    }
}
