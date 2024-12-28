package br.edu.ifpe.paulista.cinegestor.util;

import br.edu.ifpe.paulista.cinegestor.model.Usuario;

public class Autorizacao {

    // Retorna o usuário atualmente logado
    public static Usuario getUsuarioLogado() {
        return SessaoAtual.getInstancia().getUsuarioLogado();
    }

    // Verifica se o usuário é administrador
    @SuppressWarnings("unlikely-arg-type")
	public static boolean isAdministrador() {
        Usuario usuario = SessaoAtual.getInstancia().getUsuarioLogado();
        return usuario != null && "administrador".equals(usuario.getTipo());
    }

    // Verifica se o usuário é funcionário
    @SuppressWarnings("unlikely-arg-type")
	public static boolean isFuncionario() {
        Usuario usuario = SessaoAtual.getInstancia().getUsuarioLogado();
        return usuario != null && "funcionario".equals(usuario.getTipo());
    }

    // Verifica se há um usuário logado
    public static boolean isLogado() {
        return SessaoAtual.getInstancia().isUsuarioLogado();
    }
    
    public static void encerrarSessao() {
        SessaoAtual.getInstancia().encerrarSessao();
    }
}
