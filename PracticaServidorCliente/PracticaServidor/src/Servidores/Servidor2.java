package Servidores;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Hilo.HiloJuego;

public class Servidor2 {

    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en puerto " + PUERTO + ". Esperando jugadores...");

            while (true) {
                Socket socketCliente = servidor.accept();
                System.out.println("Nuevo cliente conectado...");

                Thread hiloJuego = new HiloJuego(socketCliente);
                hiloJuego.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
