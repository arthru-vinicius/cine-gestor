package br.edu.ifpe.paulista.cinegestor.view;

import br.edu.ifpe.paulista.cinegestor.util.Autorizacao;

import javax.swing.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Tela Principal - Cine Gestor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        getContentPane().setLayout(null);

        JLabel lblBemVindo = new JLabel("Bem-vindo, " + Autorizacao.getUsuarioLogado().getLogin());
        lblBemVindo.setBounds(20, 20, 400, 25);
        getContentPane().add(lblBemVindo);

        JButton btnAdmin = new JButton("Configurações Administrativas");
        btnAdmin.setBounds(20, 100, 200, 30);
        btnAdmin.setEnabled(Autorizacao.isAdministrador());
        getContentPane().add(btnAdmin);

        JButton btnVendas = new JButton("Gerenciar Vendas");
        btnVendas.setBounds(20, 150, 200, 30);
        btnVendas.setEnabled(Autorizacao.isFuncionario() || Autorizacao.isAdministrador());
        getContentPane().add(btnVendas);

        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(20, 200, 100, 30);
        getContentPane().add(btnSair);

        btnSair.addActionListener(e -> {
        	Autorizacao.encerrarSessao();
            JOptionPane.showMessageDialog(this, "Sessão encerrada!");
            dispose();
            new TelaLogin().setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}
