import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class TelaRecuperarSenha extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaRecuperarSenha frame = new TelaRecuperarSenha();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaRecuperarSenha() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Recuperar a senha? Que vacilo ein! Informe o email para seguir adiante.");
		btnNewButton.setBounds(66, 24, 516, 51);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Email:");
		btnNewButton_1.setBounds(66, 117, 124, 51);
		contentPane.add(btnNewButton_1);
	}
}
