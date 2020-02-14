import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class Activitat2GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Activitat2GUI frame = new Activitat2GUI();
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
	public Activitat2GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		
		JButton btnNewButton = new JButton("Afegir dades");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Connection con = null;
				Driver driver = null;
				// String url = "jdbc:postgresql://localhost:5432/ioc_proves";
				String url = "jdbc:mysql://localhost:3306/alumnes";
				String usuari = "root";
				String password = "";
				Statement statement = null;
				int opcio;
				boolean sortir = false;		

				try {
					// carreguem el controlador en memòria
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException ex) {
					System.out.println("No s'ha trobat el controlador JDBC ("
							+ ex.getMessage() + ")");
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
				
				
				try {
					con.setAutoCommit(false);
					statement = con.createStatement();
					//Transacció per afegir dades inicials
					statement.executeUpdate("insert into poblacions values(42000, 'Girona');");
					Thread.sleep(20000);
					statement.executeUpdate("insert into alumnes values('Jhonny', '45678912F', '01/09/1920', 'Carrer Jaume II', 42000);");
					con.commit();
					con.setAutoCommit(true);
					textField.setText("Dades inicialitzades");
					}
				catch (SQLException e) {
						try {
							con.rollback();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//Missatge d'error
						System.out.println("Error");
					} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					try {
						if ((statement!=null) && (!statement.isClosed())) { 
							try {
								statement.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 6;
		gbc_btnNewButton.gridy = 1;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnEsborrarDades = new JButton("Esborrar dades");
		btnEsborrarDades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				Driver driver = null;
				// String url = "jdbc:postgresql://localhost:5432/ioc_proves";
				String url = "jdbc:mysql://localhost:3306/alumnes";
				String usuari = "root";
				String password = "";
				Statement statement = null;
				int opcio;
				boolean sortir = false;		

				try {
					// carreguem el controlador en memòria
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException ex) {
					System.out.println("No s'ha trobat el controlador JDBC ("
							+ ex.getMessage() + ")");
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
				
				try {
					con.setAutoCommit(false);
					statement = con.createStatement();
					//Transacció per eliminar dades
					statement.executeUpdate("DELETE FROM Poblacions WHERE poblacio = 'Girona'");
					con.commit();
					con.setAutoCommit(true);
					textField.setText("Dades esborrades");
					}
				catch (SQLException f) {
						try {
							con.rollback();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				finally {
					try {
						if ((statement!=null) && (!statement.isClosed())) { 
							statement.close();
							}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
			}
		});
		GridBagConstraints gbc_btnEsborrarDades = new GridBagConstraints();
		gbc_btnEsborrarDades.insets = new Insets(0, 0, 5, 0);
		gbc_btnEsborrarDades.gridx = 6;
		gbc_btnEsborrarDades.gridy = 2;
		contentPane.add(btnEsborrarDades, gbc_btnEsborrarDades);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 6;
		gbc_textField.gridy = 4;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		
	}

}
