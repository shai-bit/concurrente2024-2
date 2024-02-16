package kass.concurrente.contador;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Contador implements Runnable {
    public static final int RONDAS = 10000;
    private int valor;
    private static final Logger LOGGER = Logger.getLogger(Contador.class.getName());

    /** Metodo constructor. */
    public Contador() {
        this.valor = 0;
    }

    /**
     * Metodo que obtiene el valor.
     * @return El valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * Metodo que asigna un nuevo valor.
     * @param valor El nuevo valor
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "DENTRO RUN");
        suma();
    }

    /**
     * Metodo que suma
     */
    public void suma() {
        for(int i = 0; i < RONDAS; ++i){
            try {
                this.valor += 1;
                Thread.sleep(0, 5); // Dormimos cada thread tantito para disminuir la probabilidad de que haya race conditions.
                LOGGER.log(Level.INFO, "{0} incrementó el contador a: {1}", new Object[]{Thread.currentThread().getName(), this.valor});
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Contador c = new Contador();

        Thread h1 = new Thread(c,"1");
        Thread h2 = new Thread(c,"2");

        h1.start(); h2.start();

        h1.join(); h2.join();

        LOGGER.log(Level.INFO, "EL VALOR ES: {0}", c.getValor());
    }
}
