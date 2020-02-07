import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Activitat1 {

	// localhost:8081/phpmyadmin

	// menu
	public static int menu() {
		Scanner teclat = new Scanner(System.in);

		System.out.println("1. Afegir");
		System.out.println("2. Modificar");
		System.out.println("3. Eliminar");
		System.out.println("4. Sortir");
		return teclat.nextInt();
	}

	public static void afegir(Statement statement) {
		Scanner teclat = new Scanner(System.in);
		String nom;
		String dni;
		String direccio;
		String data;
		int cp;
		String poblacio;
		String insert = "";
		String taula;
		Boolean resposta;

		try {
			System.out.println("Indica taula");
			taula = teclat.next();			
			if (taula.equalsIgnoreCase("alumnes")) {
				System.out.println("dni:");
				dni = teclat.next();
				System.out.println("nom:");
				nom = teclat.nextLine();
				System.out.println("data naixement:");
				data = teclat.next();
				System.out.println("direccio:");
				direccio = teclat.nextLine();
				System.out.println("nom:");
				poblacio = teclat.nextLine();

				insert = "insert into alumnes values('" + dni + "','" + nom + "','"
						+ data + "','" + direccio + "',(\r\n" + "SELECT cp\r\n"
						+ "FROM poblacions\r\n" + "WHERE poblacio LIKE '"
						+ poblacio + "')\r\n" + ");";
			}

			if (taula.equalsIgnoreCase("poblacions")) {
				System.out.println("nom:");
				poblacio = teclat.nextLine();
				teclat.next();
				System.out.println("cp");
				cp = teclat.nextInt();

				insert = "insert into poblacions values(" + cp + ", '" + poblacio
						+ "');";
			}

			statement.execute(insert);

			System.out.println("Vols introduir-ne més? (true/false)");
			resposta = teclat.nextBoolean();
			if (resposta) {
				afegir(statement);
			}
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}

	}

	public static void modificar(Statement statement) {
		Scanner teclat = new Scanner(System.in);
		String update = "";
		String taula;
		Boolean resposta;
		String camp;
		String nouValor;
		String anticValor;
		int nouValorInt;
		int anticValorInt;

		try {
			System.out.println("Indica taula");
			taula = teclat.next();

			if (taula.equalsIgnoreCase("alumnes")) {
				System.out.println("Camp a modificar");
				camp = teclat.next();
				if (camp.equalsIgnoreCase("cp")) {
					System.out.println("nou valor:");
					nouValorInt = teclat.nextInt();
					System.out.println("valor antic:");
					anticValorInt = teclat.nextInt();

					update = "UPDATE alumnes SET " + camp + " = " + nouValorInt
							+ " WHERE " + camp + " = " + anticValorInt + ";";
				} else {
					System.out.println("nou valor:");
					nouValor = teclat.nextLine();
					System.out.println("antic valor:");
					anticValor = teclat.nextLine();

					update = "UPDATE alumnes SET " + camp + " = " + nouValor
							+ " WHERE " + camp + " = " + anticValor + ";";
				}
			}

			if (taula.equalsIgnoreCase("poblacions")) {

				System.out.println("nou valor:");
				nouValor = teclat.nextLine();
				System.out.println("antic valor:");
				anticValor = teclat.nextLine();

				update = "UPDATE poblacions SET poblacio = " + nouValor
						+ " WHERE poblacio = " + anticValor + ";";
			}

			statement.execute(update);

			System.out.println("Vols modificar-ne més? (true/false)");
			resposta = teclat.nextBoolean();
			if (resposta) {
				modificar(statement);
			}
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}

	}

	public static void eliminar(Statement statement){
		Scanner teclat = new Scanner(System.in);		
		String delete = "";
		String taula;
		Boolean resposta;
		String camp;
		String valor;		
		int valorInt;		

		try {
			System.out.println("Indica taula");
			taula = teclat.next();
			
			
			System.out.println("Camp");
			camp = teclat.next();
			if (camp.equalsIgnoreCase("cp")) {
				System.out.println("valor:");
				valorInt = teclat.nextInt();				
				
				delete = "DELETE FROM " + taula + " WHERE " + camp + " = " + valorInt + ";";
				
			} else {
				System.out.println("valor:");
				valor = teclat.nextLine();								

				delete = "DELETE FROM " + taula + " WHERE " + camp + " = " + valor + ";";
			}			

			statement.execute(delete);

			System.out.println("Vols eliminar-ne més? (true/false)");
			resposta = teclat.nextBoolean();
			if (resposta) {
				eliminar(statement);
			}
		} catch (SQLException e) {
			System.out.println("Error " + e.getMessage());
		}
		
	}

	public static void main(String[] args) {
		// variables
		Connection con = null;
		Driver driver = null;
		// String url = "jdbc:postgresql://localhost:5432/ioc_proves";
		String url = "jdbc:mysql://localhost:3306/activitat1";
		String usuari = "root";
		String password = "";
		Statement statement;
		int opcio;
		boolean sortir = false;		

		System.out.println("Connexió");
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
				opcio = menu();
				if (opcio == 1) {
					afegir(statement);
				} else if (opcio == 2) {
					modificar(statement);
				} else if (opcio == 3) {
					eliminar(statement);
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
