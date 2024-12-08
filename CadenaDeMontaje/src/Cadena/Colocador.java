package Cadena;

import java.util.Random;

public class Colocador extends Thread {

    private Random rn;
    private CadenaDeMontaje c;
    private int producto;
    private volatile boolean parar = true;

    public Colocador(CadenaDeMontaje c) {
        super();
        this.rn = new Random();
        this.c = c;
    }

    public void paro() {
        parar = false;
    }

    @Override
    public void run() {
        while (parar) {
            int time = rn.nextInt(251);
            this.producto = rn.nextInt(3) + 1;
            try {
                sleep(time);
                System.out.println("Produciendo tipo " + this.producto);
                c.colocarProducto(producto);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
