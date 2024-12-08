package Cadena;

import java.util.Random;

public class Empaquetador extends Thread {

    private int tipo;
    private Random rn;
    private int empaqueta = 0;
    private CadenaDeMontaje c;
    private volatile boolean parar = true;

    public Empaquetador(int tipo, CadenaDeMontaje c) {
        super();
        this.tipo = tipo;
        this.c = c;
        this.rn = new Random();
    }

    public void paro() {
        parar = false;
    }

    public int totalRecogido() {
        return empaqueta;
    }

    @Override
    public void run() {
        while (parar) {
            try {
                sleep(100);
                if (c.recogerProducto(tipo)) {
                    empaqueta++;
                    sleep(rn.nextInt(501));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
