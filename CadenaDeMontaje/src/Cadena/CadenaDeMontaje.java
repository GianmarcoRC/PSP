package Cadena;

public class CadenaDeMontaje {

	private Object[] estanteria = new Object[3];
	private int colocadosTotal = 0;
	private int empaquetadosTotal = 0;

	public int getColocadosTotal() {
		return colocadosTotal;
	}

	public CadenaDeMontaje() {
	}

	public synchronized void colocarProducto(int producto) {
		while (estanteria[0] != null && estanteria[1] != null && estanteria[2] != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < estanteria.length; i++) {
			if (estanteria[i] == null) {
				estanteria[i] = producto;
				System.out.println("Coloco un producto " + producto + " en la posición " + i + "\n [" + estanteria[0]
						+ "," + estanteria[1] + "," + estanteria[2] + "]");
				colocadosTotal++;
				break;
			}
		}
		notifyAll();
	}

	public synchronized boolean recogerProducto(int tipo) {
		while (estanteria[0] == null && estanteria[1] == null && estanteria[2] == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < estanteria.length; i++) {
			if (estanteria[i] != null && estanteria[i].equals(tipo)) {
				estanteria[i] = null;
				empaquetadosTotal++;
				System.out.println("Recogido producto " + tipo + " de la posición " + i);
				notifyAll();
				return true;
			}
		}
		return false;
	}

	public int getEmpaquetadoTotal() {
		return empaquetadosTotal;
	}

	public String quedanenCinta() {

		return "Se quedan en la cinta [" + estanteria[0] + "," + estanteria[1] + "," + estanteria[2] + "]";

	}
}
