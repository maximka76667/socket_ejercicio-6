package _1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public Server() {
		PuntuacionesController puntuacionesController = new PuntuacionesController();

		try {
			ServerSocket serverSocket = new ServerSocket(5000);
			System.out.println("Connected");

			while (true) {
				Socket clientSocket = serverSocket.accept();

				DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

				String command = dataInputStream.readUTF();

				if (command.equals("PUNTUACIONES")) {
					objectOutputStream.writeObject(puntuacionesController.getPuntuaciones());
					continue;
				}

				puntuacionesController.addPuntuacion(command);
				dataOutputStream.writeUTF("OK");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server();
	}

}
