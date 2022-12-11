package _2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.mysql.jdbc.CallableStatement;

public class PuntuacionesController {

	private ArrayList<String> puntuaciones;
	private Connection connectionDatabase;

	public PuntuacionesController() {
		this.puntuaciones = getPuntuaciones();
		connect();
	}

	public void connect() {
		try {
			Properties props = new Properties();
			props.put("user", "root");
			props.put("password", "root");
//			props.put("useSSL", true);

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", props);
			Statement statement = connection.createStatement();

			statement.execute(
					"CREATE DATABASE IF NOT EXISTS puntuaciones DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;");

			this.connectionDatabase = DriverManager.getConnection("jdbc:mysql://localhost/puntuaciones", props);

			Statement databaseStatement = connectionDatabase.createStatement();
			databaseStatement.execute("DROP TABLE IF EXISTS `puntuacion`;");
			databaseStatement.execute("CREATE TABLE IF NOT EXISTS puntuacion (" + "puntos int(10) NOT NULL,"
					+ "nombre varchar(70) NOT NULL," + "PRIMARY KEY (nombre)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public ArrayList<String> getPuntuaciones() {
		ArrayList<String> puntuaciones = new ArrayList<String>();

		try {
			CallableStatement callablaStatement = (CallableStatement) connectionDatabase
					.prepareCall("SELECT * FROM puntacion");
			ResultSet resultSet = callablaStatement.executeQuery();

			while (resultSet.next()) {

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return puntuaciones;
	}

	public void addPuntuacion(String newPuntuacion) {
		puntuaciones.add(newPuntuacion);
		writePuntuaciones();
	}

	public void writePuntuaciones() {

	}
}
