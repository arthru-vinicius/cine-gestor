import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JDesktopPane;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JList;
import java.awt.Color;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextPane;

public class cinegestordesktopMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cinegestordesktopMain containerDasTelas = new cinegestordesktopMain();
					containerDasTelas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public cinegestordesktopMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1066, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCineGestor = new JButton("Cine Gestor");
		btnCineGestor.setForeground(new Color(0, 0, 0));
		btnCineGestor.setFont(new Font("Arial", Font.PLAIN, 22));
		btnCineGestor.setBounds(188, 8, 818, 68);
		contentPane.add(btnCineGestor);
		
		JButton btnTelaInicial = new JButton("Tela Inicial");
		btnTelaInicial.setBounds(0, 11, 178, 68);
		contentPane.add(btnTelaInicial);
		
		JButton btnVendas = new JButton("Vendas");
		btnVendas.setBounds(0, 90, 178, 49);
		contentPane.add(btnVendas);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(188, 122, 818, 400);
		contentPane.add(tabbedPane);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(0, 150, 178, 49);
		contentPane.add(btnCancelar);
		
		JButton btnEditarDados = new JButton("Editar dados");
		btnEditarDados.setBounds(0, 210, 178, 49);
		contentPane.add(btnEditarDados);
		
		JButton btnSair = new JButton("Sair");
		btnSair.setBounds(0, 278, 178, 49);
		contentPane.add(btnSair);
	}
}