import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
	// atributs
	private JPanel contentPane;
	private static JTextField textUsuari;
	private static JTextField textPsw;
	private JLabel lblResultat;
	private static Statement statement;
	private static Connection con;
	//private JButton btnRegistra;
	//private static int id1 = 3;
	//private static int id2 = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// variables
		Driver driver = null;
		// String url = "jdbc:postgresql://localhost:5432/ioc_proves";
		String url = "jdbc:mysql://localhost:3306/activitat3";
		String usuari = "usuari";
		String password = "usuari";

		try {
			// carreguem el controlador en memòria
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("No s'ha trobat el controlador JDBC (" + ex.getMessage() + ")");
			return;
		}

		try {
			// obtenim una connexió des de DriverManager
			con = DriverManager.getConnection(url, usuari, password);
			System.out.println("Connexió realitzada usant DriverManager");

			statement = con.createStatement();

		} catch (SQLException ex) {
			System.out.println("Error " + ex.getMessage());
		}
		// activa frame
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		// panel
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		// label usuari
		JLabel lblUsuari = new JLabel("Usuari:");
		GridBagConstraints gbc_lblUsuari = new GridBagConstraints();
		gbc_lblUsuari.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuari.gridx = 1;
		gbc_lblUsuari.gridy = 1;
		contentPane.add(lblUsuari, gbc_lblUsuari);

		// textField per usuari
		textUsuari = new JTextField();
		GridBagConstraints gbc_textUsuari = new GridBagConstraints();
		gbc_textUsuari.insets = new Insets(0, 0, 5, 5);
		gbc_textUsuari.fill = GridBagConstraints.HORIZONTAL;
		gbc_textUsuari.gridx = 2;
		gbc_textUsuari.gridy = 1;
		contentPane.add(textUsuari, gbc_textUsuari);
		textUsuari.setColumns(10);

		// label pasword
		JLabel lblPsw = new JLabel("Contrasenya");
		GridBagConstraints gbc_lblPsw = new GridBagConstraints();
		gbc_lblPsw.insets = new Insets(0, 0, 5, 5);
		gbc_lblPsw.gridx = 1;
		gbc_lblPsw.gridy = 2;
		contentPane.add(lblPsw, gbc_lblPsw);

		// textField per pasword
		textPsw = new JTextField();
		GridBagConstraints gbc_textPsw = new GridBagConstraints();
		gbc_textPsw.insets = new Insets(0, 0, 5, 5);
		gbc_textPsw.fill = GridBagConstraints.HORIZONTAL;
		gbc_textPsw.gridx = 2;
		gbc_textPsw.gridy = 2;
		contentPane.add(textPsw, gbc_textPsw);
		textPsw.setColumns(10);

		// botó que activa comprovació i depenent del resultat modifica el label
		// resultat
		JButton btnLogin = new JButton("Comprova");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// 2 opcions: (comentar/descomentar)
				boolean resultat = loginResultatPrepared(); //Amb PreparedStatement
				//boolean resultat = loginResultat(); 		//Amb Statment
				
				
				if (resultat) {
					lblResultat.setText("És correcte");
				} else {
					lblResultat.setText("No és correcte");
				}
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 2;
		gbc_btnLogin.gridy = 4;
		contentPane.add(btnLogin, gbc_btnLogin);

		// botó que activa el registre a la bd
//		btnRegistra = new JButton("Registra");
//		btnRegistra.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				registrar();
//				registrarAmbSeguretat();
//			}
//		});
//		GridBagConstraints gbc_btnRegistra = new GridBagConstraints();
//		gbc_btnRegistra.insets = new Insets(0, 0, 5, 5);
//		gbc_btnRegistra.gridx = 2;
//		gbc_btnRegistra.gridy = 3;
//		contentPane.add(btnRegistra, gbc_btnRegistra);
		

		// label resultat
		lblResultat = new JLabel("Resultat");
		GridBagConstraints gbc_lblResultat = new GridBagConstraints();
		gbc_lblResultat.insets = new Insets(0, 0, 0, 5);
		gbc_lblResultat.gridx = 2;
		gbc_lblResultat.gridy = 6;
		contentPane.add(lblResultat, gbc_lblResultat);
	}

	// mètode que fa la comprovació del que l'usuari introdueix amb el que hi ha a
	// la bd
	public boolean loginResultat() {
		boolean resultat = false;
		String usuari = textUsuari.getText();
		String contrasenya = textPsw.getText();

		//sql utilitzant les variables directament 
		String sSQL = "SELECT usuari, contrasenya, id_usuari FROM usuaris WHERE usuari = '" + usuari
				+ "' AND contrasenya='" + contrasenya + "'";

		try {
			//recollir resultats
			ResultSet rs = statement.executeQuery(sSQL);			

			while (rs.next() && !resultat) {
				//si coincideixen..
				if (rs.getString("usuari").equals(usuari) && rs.getString("contrasenya").equals(contrasenya)) {
					resultat = true;
				} else {
					resultat = false;
				}
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "SQLException:\n" + e, "Error: taulaRegistres",
					JOptionPane.ERROR_MESSAGE);
		}

		return resultat;
	}
	
	// mètode que fa la comprovació del que l'usuari introdueix amb el que hi ha a
		// la bd
		public boolean loginResultatPrepared() {
			boolean resultat = false;
			String usuari = textUsuari.getText();
			String contrasenya = textPsw.getText();
			ResultSet rs;			

			//String sSQL = ;

			try {
				//es prepara consulta sense utilitzar les variables directament
				PreparedStatement stm = con
						.prepareStatement("SELECT * FROM Usuaris WHERE usuari = ? AND contrasenya = ? ");
				//s'introdueixen variables 
				stm.setString(1, usuari); 
				stm.setString(2, contrasenya); 
				//es recullen resultats
				rs = stm.executeQuery();

				while (rs.next() && !resultat) {
					//si coincideixen..
					if (rs.getString("usuari").equals(usuari) && rs.getString("contrasenya").equals(contrasenya)) {
						resultat = true;
					} else {
						resultat = false;
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "SQLException:\n" + e, "Error: taulaRegistres",
						JOptionPane.ERROR_MESSAGE);
			}
			return resultat;
		}

	// mètode per registrar dades amb statment a la bd a través del formulari
//	public static void registrar() {
//		boolean resultat = false;
//		String usuari = textUsuari.getText();
//		String contrasenya = textPsw.getText();
//		String insert = "";
//
//		try {
//			insert = "insert into usuaris values(" + ++id1 + ",'" + usuari + "','" + contrasenya + "');";
//
//			// executa statement
//			statement.execute(insert);
//		} catch (SQLException e) {
//			System.out.println("Error " + e.getMessage());
//		}
//
//	}

	// mètode per registrar dades amb PreparedStatement a la bd a través del
	// formulari
//	public static void registrarAmbSeguretat() {
//		boolean resultat = false;
//		String usuari = textUsuari.getText();
//		String contrasenya = textPsw.getText();
//		String insert = "";
//
//		try {
//			PreparedStatement stm = con
//					.prepareStatement("INSERT INTO usuarisdos (id,usuari,contrasenya)   VALUES(?, ?, ?)");
//			stm.setInt(1, ++id2);
//			stm.setString(2, usuari);
//			stm.setString(3, contrasenya);
//			stm.executeUpdate();
//
//		} catch (SQLException e) {
//			System.out.println("Error " + e.getMessage());
//		}
//
//	}

}
