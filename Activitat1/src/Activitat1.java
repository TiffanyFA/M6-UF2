import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		System.out.println("4. Mostrar taula");
		System.out.println("5. Sortir");
		return teclat.nextInt();
	}

	//mètode per fer INSERT a les taules de la db
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
			taula = teclat.nextLine();	
			//si la taula és la de alumnes..
			if (taula.equalsIgnoreCase("alumnes")) {
				System.out.println("dni:");
				dni = teclat.nextLine();
				System.out.println("nom:");
				nom = teclat.nextLine();
				System.out.println("data naixement:");
				data = teclat.nextLine();
				System.out.println("direccio:");
				direccio = teclat.nextLine();
				System.out.println("poblacio:");
				poblacio = teclat.nextLine();

				insert = "insert into alumnes values('" + dni + "','" + nom + "','"
						+ data + "','" + direccio + "',(\r\n" + "SELECT cp\r\n"
						+ "FROM poblacions\r\n" + "WHERE poblacio LIKE '"
						+ poblacio + "')\r\n" + ");";
			}

			//si la taula és la de poblacions..
			if (taula.equalsIgnoreCase("poblacions")) {
				System.out.println("nom:");
				poblacio = teclat.nextLine();
				System.out.println(poblacio);
				System.out.println("cp");
				cp = (new Integer(teclat.nextLine())).intValue();

				insert = "insert into poblacions values(" + cp + ", '" + poblacio
						+ "');";
			}
			
			//executa statement
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

	//mètode per fer UPDATE
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
			taula = teclat.nextLine();

			//si la taula és alumnes..
			if (taula.equalsIgnoreCase("alumnes")) {
				System.out.println("Camp a modificar");
				camp = teclat.nextLine();
				//si el camp és enter
				if (camp.equalsIgnoreCase("cp")) {
					System.out.println("nou valor:");
					nouValorInt = (new Integer(teclat.nextLine())).intValue();
					System.out.println("valor antic:");
					anticValorInt = (new Integer(teclat.nextLine())).intValue();

					update = "UPDATE alumnes SET " + camp + " = " + nouValorInt
							+ " WHERE " + camp + " = " + anticValorInt + ";";
				} else {
					System.out.println("nou valor:");
					nouValor = teclat.nextLine();
					System.out.println("antic valor:");
					anticValor = teclat.nextLine();

					update = "UPDATE alumnes SET " + camp + " = '" + nouValor
							+ "' WHERE " + camp + " = '" + anticValor + "';";
				}
			}

			//si la taula és poblacions
			if (taula.equalsIgnoreCase("poblacions")) {

				System.out.println("nou valor:");
				nouValor = teclat.nextLine();
				System.out.println("antic valor:");
				anticValor = teclat.nextLine();

				update = "UPDATE poblacions SET poblacio = '" + nouValor
						+ "' WHERE poblacio = '" + anticValor + "';";
			}

			//executa statement
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

	//mètode per fer DELETE
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
			taula = teclat.nextLine();			
			System.out.println("Camp:");
			camp = teclat.nextLine();
			//si el camp és enter
			if (camp.equalsIgnoreCase("cp")) {
				System.out.println("valor:");
				valorInt = (new Integer(teclat.nextLine())).intValue();				
				
				delete = "DELETE FROM " + taula + " WHERE " + camp + " = " + valorInt + ";";
				
			} else {
				System.out.println("valor:");
				valor = teclat.nextLine();								

				delete = "DELETE FROM " + taula + " WHERE " + camp + " = '" + valor + "';";
			}			

			//executa statement
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
										", DATA NAIXEMENT:= " + resultSet.getObject("data_naixement") +
										", DIRECCIÓ:= " + resultSet.getObject("direccio") +
										", CP:= " + resultSet.getObject("cp"));
				}
				if (taula.equalsIgnoreCase("poblacions")) {
					System.out.println("CP:= " + resultSet.getObject("cp") +
										", NOM:= " + resultSet.getObject("poblacio"));
				}
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					afegir(statement);
				} else if (opcio == 2) {
					modificar(statement);
				} else if (opcio == 3) {
					eliminar(statement);
				} else if (opcio == 4){
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
