package _1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PuntuacionesController {

	private ArrayList<String> puntuaciones;
	private Path txtPath;

	public PuntuacionesController() {
		this.txtPath = Paths.get("./src/_1/puntuaciones.txt");
		try {
			if (!Files.exists(txtPath)) {
				Files.createFile(txtPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.puntuaciones = getPuntuaciones();
	}

	public ArrayList<String> getPuntuaciones() {
		ArrayList<String> puntuaciones = new ArrayList<String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.txtPath.toString()));
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				puntuaciones.add(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return puntuaciones;
	}

	public void addPuntuacion(String newPuntuacion) {
		puntuaciones.add(newPuntuacion);
		writePuntuaciones();
	}

	public void writePuntuaciones() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(txtPath.toString()));
			for (String puntuacion : puntuaciones) {
				writer.println(puntuacion);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
