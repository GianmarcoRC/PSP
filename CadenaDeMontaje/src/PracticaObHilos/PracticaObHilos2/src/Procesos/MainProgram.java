package PracticaObHilos.PracticaObHilos2.src.Procesos;

import java.io.*;
import java.util.*;

import PracticaObHilos.PracticaObHilos2.src.Hilo.Controlador;
import PracticaObHilos.PracticaObHilos2.src.Hilo.HiloTransferencia;

public class MainProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Dime el nombre del fichero: ");
        String nombreFichero = scanner.nextLine();
        System.out.print("Dime la ruta del fichero(acabalo en barra): ");
        String ruta = scanner.nextLine();
        System.out.print("Dime el número de transferencias a generar: ");
        int numTransferencias = scanner.nextInt();
        scanner.close();
        
        int randomValue = random.nextInt(300001 - 200000) + 200000;
        double saldo = (randomValue / 100.0) * numTransferencias;
        
		String datos = nombreFichero + "\n" + numTransferencias + "\n" + String.valueOf(numTransferencias) + "\n";
		
        int errores = generacionFichero(datos);
        switch (errores) {
		case -2:
			System.out.println("No ha llegado el archivo de transferencias.");
			break;
		case -1:
			System.out.println("Error al generar el archivo.");
			break;
		case 0:
			File archivo = new File(ruta + nombreFichero);
			Generarhilos(archivo, saldo, ruta);

			break;
		}
        
    }
    public static int generacionFichero(String datos) {
		try {
			File directorio = new File("..\\PracticaObHilos1\\bin");
			ProcessBuilder pb = new ProcessBuilder("java", "Proceso.GenerarFicheroTransferencias");
			pb.directory(directorio);
			Process p = pb.start();
			OutputStream os = p.getOutputStream();
			os.write(datos.getBytes());
			os.flush();
			os.close();

			int exitVal = -1;
			exitVal = p.waitFor();
			System.out.println("Valor de salida: " + exitVal);

			if (exitVal==0) {
				try (InputStream i = p.getInputStream()){
					
					Scanner sc = new Scanner(i);
					System.out.println(sc.nextLine());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return exitVal;

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
    public static void Generarhilos(File archivo, Double saldo, String ruta) {
		Controlador controlador = new Controlador(saldo, ruta,archivo);
		System.out.println("El saldo de la cuenta es: " + controlador.getSaldo());
		for (int i = 1; i <= 3; i++)
			new HiloTransferencia("Hilo" + i, controlador).start();
	}
}
