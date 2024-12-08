package Cadena;

public class Principal {

	public static void main(String[] args) {

		CadenaDeMontaje c = new CadenaDeMontaje();
		Colocador colocador = new Colocador(c);
		Empaquetador em1 = new Empaquetador(1, c);
		Empaquetador em2 = new Empaquetador(2, c);
		Empaquetador em3 = new Empaquetador(3, c);

		colocador.start();
		em1.start();
		em2.start();
		em3.start();

		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		colocador.paro();
		em1.paro();
		em2.paro();
		em3.paro();
		
		c.quedanenCinta();
		System.out.println("Puestos en total " + c.getColocadosTotal());
		System.out.println("Recogidos por tipo : [" + em1.totalRecogido() + "," + em2.totalRecogido() + "," + em3.totalRecogido() + "]" );
		try {
			colocador.join();
			em1.join();
			em2.join();
			em3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}