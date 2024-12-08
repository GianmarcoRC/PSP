package PracticaObHilos.PracticaObHilos2.src.Hilo;

public class HiloTransferencia extends Thread {
	private final Controlador controlador;
	private final String nombre;
	private double suma;

	public HiloTransferencia(String nombre, Controlador controlador) {
		this.nombre = nombre;
		this.controlador = controlador;
		this.suma = 0.0;
	}

	public String getNombre() {
		return nombre;
	}

	public double getSuma() {
		return suma;
	}

	@Override
	public void run() {
		String saldoCuenta, cuenta;
		double sueldo;
		controlador.añadirHilo();
		
		while (!controlador.getSeacabo()) {
			saldoCuenta = controlador.devolverTransaccion();
			if (saldoCuenta == null) {
				controlador.setSeacabo(true);
				break;
			}
			cuenta = saldoCuenta.split(";")[0];
			sueldo = Double.parseDouble(saldoCuenta.split(";")[1]);

			if (controlador.getSaldo() - sueldo >= 0) {
				if (cuenta.split("")[0].equalsIgnoreCase("1")) {
				transferenciaInterna(cuenta, saldoCuenta, sueldo);
			
				}else if (cuenta.split("")[0].equalsIgnoreCase("2")) {
					transferenciaExterna(cuenta, saldoCuenta, sueldo);
				}
			}else {
				System.out.println("No hay saldo para la siguiente transferencia de: " + sueldo + "€.");
				controlador.agregarTransferencia(0, saldoCuenta);
			}
			this.suma += sueldo;
		}
		controlador.hiloTerminado(this);
	}

	public void transferenciaInterna(String cuenta, String saldoCuenta,double sueldo) {
		
		
				try {
					System.out.println("Cuenta: " + cuenta + " - Actualizamos saldo de la cuenta con el importe: "
							+ sueldo + " euros.\n" + "\tGrabamos la transferencia interna. Cuenta: " + cuenta + "\n"
							+ "\tSaldo restante: " + controlador.restarSaldo(sueldo) + "€.");
					controlador.agregarTransferencia(1, saldoCuenta);
					Thread.sleep(1000);

				} catch (InterruptedException e) {
					System.out.println("No se pudo añadir");
					e.printStackTrace();
				}
	}
	public void transferenciaExterna(String cuenta, String saldoCuenta,double sueldo) {
		
		System.out.println("Cuenta: " + cuenta + " - Actualizamos saldo de la cuenta con el importe: "
				+ sueldo + " euros.\n" + "\tGrabamos la transferencia externa. Cuenta: " + cuenta + "\n"
				+ "\tSaldo restante: " + controlador.restarSaldo(sueldo) + "€.");
		controlador.agregarTransferencia(2, saldoCuenta);
		}
	}
	


	
