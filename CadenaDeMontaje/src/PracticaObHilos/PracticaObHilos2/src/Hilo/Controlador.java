package PracticaObHilos.PracticaObHilos2.src.Hilo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Controlador {
	private final String ruta;
	private double saldo;
	private boolean seacabo;
	private int hilos;
	private Scanner sc;
	private final List<String> internas, externas, sinSaldo;

	public Controlador(double saldo, String ruta,File archivo) {
		this.saldo = saldo;
		this.ruta = ruta;
		this.seacabo = false;
		this.hilos = 0;
		this.internas = new ArrayList<>();
		this.externas = new ArrayList<>();
		this.sinSaldo = new ArrayList<>();
		try {
			this.sc = new Scanner(archivo);
		} catch (FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
			e.printStackTrace();
		}
	}
	
	
	public boolean getSeacabo() {
		return seacabo;
	}


	public synchronized double getSaldo() {
		return saldo;
	}

	public synchronized double restarSaldo(double importe) {
		return this.saldo -= importe;
	}
	public synchronized void añadirHilo() {
		this.hilos++;
	}

	public synchronized void agregarTransferencia(int tipo, String trans) {
		switch (tipo) {
		case 0 -> this.sinSaldo.add(trans);
		case 1 -> this.internas.add(trans);
		case 2 -> this.externas.add(trans);
		}
	}


	public synchronized void hiloTerminado(HiloTransferencia ht) {
		this.hilos--;
			seacabo = false;
		if (this.hilos == 1) {
			seacabo  =true;
		}
		if (this.hilos > 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		} else {
			notifyAll();
		}
		if (seacabo) {
			try {
				Files.write(Paths.get(ruta + "tranferenciasExt.txt"), externas);
				Files.write(Paths.get(ruta + "tranferenciasInt.txt"), internas);
				Files.write(Paths.get(ruta + "tranferenciasSinSaldo.txt"), sinSaldo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Quedan " + getSaldo() + " euros en la cuenta.");
			System.out.println("Fin de programa.");
		}
	}

	public synchronized String  devolverTransaccion() {
		
		if (this.sc.hasNextLine()) {

			String mostrar;
			mostrar = this.sc.nextLine();

			if (!this.sc.hasNextLine()) {
				this.seacabo = true;
				this.sc.close();
			}

			return mostrar;
		}
		return null;
	}


	public void setSeacabo(boolean seacabo) {
		this.seacabo = seacabo;
	}


}
