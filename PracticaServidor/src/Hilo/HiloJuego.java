package Hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class HiloJuego extends Thread {
    private Socket socketCliente;
    private BufferedReader entrada;
    private OutputStream salida;
    private Random random;

    private int winsCliente = 0;
    private int winsServidor = 0;

    public HiloJuego(Socket socket) {
        this.socketCliente = socket;
        this.random = new Random();
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
             OutputStream salida = socketCliente.getOutputStream()) {

            this.entrada = entrada;
            this.salida = salida;

            System.out.println("Iniciando partida con un cliente...");

            while (winsCliente < 3 && winsServidor < 3) {
                salida.write("Tu turno: Piedra, Papel o Tijera\n".getBytes());
                salida.flush();

                String jugadaCliente = entrada.readLine().toLowerCase();
                String jugadaServidor = generarJugadaAleatoria();
                String resultado = evaluarJugada(jugadaCliente, jugadaServidor);

                salida.write(("Servidor elige: " + jugadaServidor + "\n").getBytes());
                salida.write(("Resultado: " + resultado + "\n").getBytes());
                salida.flush();

                if (resultado.equals("Has ganado esta ronda")) {
                    winsCliente++;
                } else if (resultado.equals("Has perdido esta ronda")) {
                    winsServidor++;
                }

                salida.write(("Marcador: " + winsCliente + " - " + winsServidor + "\n").getBytes());
                salida.flush();

                if (resultado.equals("Has ganado esta ronda")) {
                    System.out.println("Servidor: Has perdido esta ronda");
                } else if (resultado.equals("Has perdido esta ronda")) {
                    System.out.println("Servidor: Has ganado esta ronda");
                } else {
                    System.out.println("Servidor: Empate en esta ronda");
                }

                System.out.println("Marcador actualizado: Cliente " + winsCliente + " - Servidor " + winsServidor);

                if (winsCliente == 3 || winsServidor == 3) {
                    String ganador = (winsCliente == 3) ? "Cliente" : "Servidor";
                    salida.write(("Juego terminado. ¡" + ganador + " ha ganado!\n").getBytes());
                    salida.flush();
                    System.out.println("Juego terminado. ¡" + ganador + " ha ganado!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generarJugadaAleatoria() {
        String[] opciones = {"piedra", "papel", "tijera"};
        return opciones[random.nextInt(3)];
    }

    private String evaluarJugada(String jugadaCliente, String jugadaServidor) {
        if (jugadaCliente.equals(jugadaServidor)) {
            return "Empate";
        }
        if ((jugadaCliente.equals("piedra") && jugadaServidor.equals("tijera")) ||
            (jugadaCliente.equals("papel") && jugadaServidor.equals("piedra")) ||
            (jugadaCliente.equals("tijera") && jugadaServidor.equals("papel"))) {
            return "Has ganado esta ronda";
        }
        return "Has perdido esta ronda";
    }
}
