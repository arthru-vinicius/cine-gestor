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

public class cinegestordesktopMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable ventaTabela;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cinegestordesktopMain frame = new cinegestordesktopMain();
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
	public cinegestordesktopMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1066, 572);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Cine Gestor");
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 22));
		btnNewButton.setBounds(188, 11, 818, 68);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Tela Inicial");
		btnNewButton_1.setBounds(0, 11, 178, 68);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Vendas");
		btnNewButton_2.setBounds(0, 90, 178, 68);
		contentPane.add(btnNewButton_2);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(188, 90, 818, 406);
		contentPane.add(tabbedPane);
		
		table_1 = new JTable();
		table_1.setCellSelectionEnabled(true);
		table_1.setColumnSelectionAllowed(true);
		tabbedPane.addTab("Tela Inicial", null, table_1, null);
		
		ventaTabela = new JTable();
		tabbedPane.addTab("New tab", null, ventaTabela, null);
	}
}
