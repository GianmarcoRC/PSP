package PracticaObHilos.PracticaObHilos1.src.Proceso;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GenerarFicheroTransferencias {
	public static void main(String[] args) {
		Random rand = new Random();
		int numeroAleatorio = rand.nextInt(101);

		if (numeroAleatorio < 30) {
			System.err.println("Error: El fichero no ha llegado.");
			System.exit(-2);
		}

		try {
			Scanner sc = new Scanner(System.in);
			//No e puesto pa pedir pero lo hace
			String nombre = sc.nextLine(), path = sc.nextLine();
			int numero = Integer.parseInt(sc.nextLine());

			ArrayList<String> line = new ArrayList<String>();
			Double suma = 0.0, sueldo;

			for (int i = 0; i < numero; i++) {
				sueldo = Double.parseDouble(Sueldorand(rand));
				suma += sueldo;
				line.add(Cuentarand(rand) + ";" + sueldo);
			}

			Files.write(Paths.get(path + nombre), line);

			System.out.println("Suma total de las tranferencias: " + suma);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		System.out.println("Esta bien");
		System.exit(0);
	}

	public static String Cuentarand(Random rand) {

		int cuenta = (rand.nextInt(2) + 1) * 100000000 + rand.nextInt(90000000);
		return String.valueOf(cuenta);
	}

	public static String Sueldorand(Random rand) {
		double importe = 1500 + rand.nextDouble() * 1500;
		return String.valueOf(importe);
	}
}
