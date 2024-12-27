import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;

public class TelaLogin {

	private JFrame frame;
	private JTextField textFieldEmailLogin;
	private JTextField textFieldSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin window = new TelaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 813, 497);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Login de Usuário");
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton.setBounds(135, 38, 505, 60);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnEmailLogin = new JButton("Email:");
		btnEmailLogin.setFont(new Font("Arial Black", Font.PLAIN, 20));
		btnEmailLogin.setBounds(32, 168, 175, 49);
		frame.getContentPane().add(btnEmailLogin);
		
		JButton btnSenha = new JButton("Senha:");
		btnSenha.setFont(new Font("Arial Black", Font.BOLD, 20));
		btnSenha.setBounds(32, 247, 175, 49);
		frame.getContentPane().add(btnSenha);
		
		textFieldEmailLogin = new JTextField();
		textFieldEmailLogin.setBounds(217, 168, 397, 49);
		frame.getContentPane().add(textFieldEmailLogin);
		textFieldEmailLogin.setColumns(10);
		
		textFieldSenha = new JTextField();
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(217, 247, 397, 49);
		frame.getContentPane().add(textFieldSenha);
		
		JButton btnEsqueceuSenha = new JButton("Esqueceu sua senha? clique aqui!");
		btnEsqueceuSenha.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16));
		btnEsqueceuSenha.setForeground(new Color(0, 0, 255));
		btnEsqueceuSenha.setBounds(217, 415, 353, 32);
		frame.getContentPane().add(btnEsqueceuSenha);
		
		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.setFont(new Font("Arial", Font.BOLD, 22));
		btnEntrar.setBounds(276, 332, 163, 32);
		frame.getContentPane().add(btnEntrar);
	}
}
