package _2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

	public Client() {
		Scanner scanner = new Scanner(System.in);

		try {
			Socket clientSocket = new Socket("localhost", 5000);

			DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

			System.out.print("COMMAND: ");
			String command = scanner.nextLine();
			dataOutputStream.writeUTF(command);

			if (command.equals("PUNTUACIONES")) {
				ArrayList<String> puntuaciones = (ArrayList<String>) objectInputStream.readObject();
				for (String string : puntuaciones) {
					System.out.println(string);
				}
				return;
			}

			System.out.println(dataInputStream.readUTF());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Client();
	}

}
