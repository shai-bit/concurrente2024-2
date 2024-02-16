package kass.concurrente.herencia;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * En esta clase, debes crear un contador extendiendo de la clase Thread
 * @author Kassandra Mirael
 * @version 1.1
 */
public class Hilos extends Thread {
    public static final Integer NUM_THREADS = 10;
    private static int contador = 0;
    private static final Logger LOGGER = Logger.getLogger(Hilos.class.getName());

    @Override
    public void run() {
        synchronized (Hilos.class) {
            contador += 1;
        }
        LOGGER.log(Level.INFO, "{0} incrementó el contador a: {1}", new Object[]{Thread.currentThread().getName(), contador});
    }

    public static void main(String[] args) throws InterruptedException {
        Hilos[] hilos = new Hilos[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            hilos[i] = new Hilos();
            hilos[i].start();
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            hilos[i].join();
        }
        
        LOGGER.log(Level.INFO, "Valor final del contador: {0}", contador);
    }
}
