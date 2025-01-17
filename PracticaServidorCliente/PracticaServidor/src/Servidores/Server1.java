package Servidores;

import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(5000);
        System.out.println("Servidor en espera de conexión...");

        try (Socket socketCliente1 = servidor.accept();
             Socket socketCliente2 = servidor.accept();
             BufferedReader entrada1 = new BufferedReader(new InputStreamReader(socketCliente1.getInputStream()));
             BufferedReader entrada2 = new BufferedReader(new InputStreamReader(socketCliente2.getInputStream()));
             OutputStream salida1 = socketCliente1.getOutputStream();
             OutputStream salida2 = socketCliente2.getOutputStream()) {

            System.out.println("Clientes conectados. Iniciando partida...");

            int winsCliente1 = 0, winsCliente2 = 0;

            while (winsCliente1 < 3 && winsCliente2 < 3) {
                salida1.write("Tu turno: Piedra, Papel o Tijera\n".getBytes());
                salida1.flush();
                String jugada1 = entrada1.readLine().toLowerCase();

                salida2.write("Tu turno: Piedra, Papel o Tijera\n".getBytes());
                salida2.flush();
                String jugada2 = entrada2.readLine().toLowerCase();

                String resultado = evaluarJugada(jugada1, jugada2);
                String mensajeCliente1;
                String mensajeCliente2;

                if (resultado.equals("Cliente 1 gana")) {
                    winsCliente1++;
                    mensajeCliente1 = "Has ganado esta ronda.\n";
                    mensajeCliente2 = "Has perdido esta ronda.\n";
                } else if (resultado.equals("Cliente 2 gana")) {
                    winsCliente2++;
                    mensajeCliente1 = "Has perdido esta ronda.\n";
                    mensajeCliente2 = "Has ganado esta ronda.\n";
                } else {
                    mensajeCliente1 = "Empate en esta ronda.\n";
                    mensajeCliente2 = "Empate en esta ronda.\n";
                }

                mensajeCliente1 += "Marcador: " + winsCliente1 + " - " + winsCliente2 + "\n";
                mensajeCliente2 += "Marcador: " + winsCliente1 + " - " + winsCliente2 + "\n";

                salida1.write(mensajeCliente1.getBytes());
                salida1.flush();
                salida2.write(mensajeCliente2.getBytes());
                salida2.flush();
            }

            String ganador = (winsCliente1 == 3) ? "Cliente 1" : "Cliente 2";
            salida1.write(("Juego terminado. Ganador: " + ganador + "\n").getBytes());
            salida1.flush();
            salida2.write(("Juego terminado. Ganador: " + ganador + "\n").getBytes());
            salida2.flush();
        } finally {
            servidor.close();
        }
    }

    private static String evaluarJugada(String jugada1, String jugada2) {
        if (jugada1.equals(jugada2)) {
            return "Empate";
        }
        if ((jugada1.equals("piedra") && jugada2.equals("tijera")) ||
            (jugada1.equals("papel") && jugada2.equals("piedra")) ||
            (jugada1.equals("tijera") && jugada2.equals("papel"))) {
            return "Cliente 1 gana";
        }
        return "Cliente 2 gana";
    }
}