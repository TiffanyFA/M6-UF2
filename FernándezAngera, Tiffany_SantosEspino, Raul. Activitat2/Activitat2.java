import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Activitat2 {
	
	// menu
		public static int menu() {
			Scanner teclat = new Scanner(System.in);

			System.out.println("1. Dades inicials");
			System.out.println("2. Eliminar dades");
			System.out.println("3. Mostrar taula");
			System.out.println("4. Sortir");
			return teclat.nextInt();
		}
		
		public static void afegir(Connection con, Statement statement) throws SQLException, InterruptedException {
			try {
				con.setAutoCommit(false);
				statement = con.createStatement();
				//Transacció per afegir dades inicials
				statement.executeUpdate("insert into poblacions values(42000, 'Girona');");
				Thread.sleep(20000);
				statement.executeUpdate("insert into alumnes values('Jhonny', '45678912F', '01/09/1920', 'Carrer Jaume II', 42000);");
				con.commit();
				con.setAutoCommit(true);
				System.out.println("Dades inicialtizades");
				}
			catch (SQLException e) {
					con.rollback();
					//Missatge d'error
					System.out.println("Error");
				}
			finally {
				if ((statement!=null) && (!statement.isClosed())) { 
					statement.close();
					}
				}
		}
		
		public static void eliminar(Connection con, Statement statement) throws SQLException, InterruptedException {
			try {
				con.setAutoCommit(false);
				statement = con.createStatement();
				//Transacció per eliminar dades
				statement.executeUpdate("DELETE FROM Poblacions WHERE poblacio = 'Girona'");
				con.commit();
				con.setAutoCommit(true);
				System.out.println("Dades esborrades");
				}
			catch (SQLException e) {
					con.rollback();
				}
			finally {
				if ((statement!=null) && (!statement.isClosed())) { 
					statement.close();
					}
				}
		}
		
		public static void mostrar(Statement statement){
			Scanner teclat = new Scanner(System.in);
			String taula;		
			ResultSet resultSet;
			
			try {
				System.out.println("Indica taula a mostrar:");
				taula = teclat.nextLine();
				
				//recull resultat
				resultSet = statement.executeQuery("SELECT * FROM " + taula);
			
				while (resultSet.next()){
					if (taula.equalsIgnoreCase("alumnes")) {
						System.out.println("DNI:= " + resultSet.getObject("dni") +
											", NOM:= " + resultSet.getObject("nom") +
											", DATA NAIXEMENT:= " + resultSet.getObject("data_naix") +
											", DIRECCIÓ:= " + resultSet.getObject("a_postal") +
											", CP:= " + resultSet.getObject("codi_postal"));
					}
					if (taula.equalsIgnoreCase("poblacions")) {
						System.out.println("CP:= " + resultSet.getObject("codi_postal") +
											", NOM:= " + resultSet.getObject("poblacio"));
					}
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	public static void main(String[] args) throws InterruptedException {
		// variables
		Connection con = null;
		Driver driver = null;
		// String url = "jdbc:postgresql://localhost:5432/ioc_proves";
		String url = "jdbc:mysql://localhost:3306/alumnes";
		String usuari = "root";
		String password = "";
		Statement statement;
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
			

			while (!sortir) {
				// menu
				System.out.println();
				opcio = menu();
				if (opcio == 1) {
					afegir(con, statement);
				} else if (opcio == 2) {
					eliminar(con, statement);
				} else if (opcio == 3){
					mostrar(statement);
				} else {
					con.close();
					sortir = true;
				}
			}

		} catch (SQLException ex) {
			System.out.println("Error " + ex.getMessage());
		}

	}
}
