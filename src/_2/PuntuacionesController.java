package _2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class PuntuacionesController {

	private ArrayList<String> puntuaciones;
	private Connection connectionDatabase;

	public PuntuacionesController() {
		connect();
		this.puntuaciones = new ArrayList<String>();
		fetchPuntuaciones();
	}

	public void connect() {
		try {
			Properties props = new Properties();
			props.put("user", "root");
			props.put("password", "root");

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost", props);
			Statement statement = connection.createStatement();

			statement.execute(
					"CREATE DATABASE IF NOT EXISTS puntuaciones DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;");

			this.connectionDatabase = DriverManager.getConnection("jdbc:mysql://localhost/puntuaciones", props);

			Statement databaseStatement = this.connectionDatabase.createStatement();
			databaseStatement.execute("CREATE TABLE IF NOT EXISTS puntuacion (" + "puntos int(10) NOT NULL,"
					+ "nombre varchar(70) NOT NULL," + "PRIMARY KEY (nombre)"
					+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void fetchPuntuaciones() {
		this.puntuaciones = new ArrayList<String>();

		try {
			CallableStatement callablaStatement = this.connectionDatabase.prepareCall("SELECT * FROM puntuacion");
			ResultSet resultSet = callablaStatement.executeQuery();

			while (resultSet.next()) {
				String rowData = "";
				for (int i = 1; i <= 2; i++) {
					rowData += resultSet.getString(i) + " ";
				}

				puntuaciones.add(rowData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void addPuntuacion(String newPuntuacion) {
		puntuaciones.add(newPuntuacion);
		insertPuntuacion(newPuntuacion);
	}

	public void insertPuntuacion(String newPuntuacion) {
		try {
			PreparedStatement ps = this.connectionDatabase.prepareStatement("INSERT INTO puntuacion VALUES(?, ?)");

			int indexOfSpace = newPuntuacion.indexOf(" ");

			int puntos = Integer.parseInt(newPuntuacion.substring(0, indexOfSpace));
			String nombre = newPuntuacion.substring(indexOfSpace + 1);

			ps.setInt(1, puntos);
			ps.setString(2, nombre);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<String> getPuntuaciones() {
		fetchPuntuaciones();
		return this.puntuaciones;
	}
}
