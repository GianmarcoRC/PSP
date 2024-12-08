package Clientes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {

	public static void main(String[] args) {
		try (Socket socket = new Socket("localhost", 5000);
				BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				OutputStream salida = socket.getOutputStream();
				Scanner sc = new Scanner(System.in)) {

			String mensajeServidor;
			while ((mensajeServidor = entrada.readLine()) != null) {
				System.out.println(mensajeServidor);

				if (mensajeServidor.contains("Juego terminado")) {
					break;
				}

				if (mensajeServidor.contains("Tu turno:")) {
					String jugada = sc.nextLine();
					salida.write((jugada + "\n").getBytes());
					salida.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
