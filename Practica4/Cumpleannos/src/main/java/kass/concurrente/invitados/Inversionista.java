package kass.concurrente.invitados;

import kass.concurrente.candados.Semaphore;
import kass.concurrente.tenedor.Tenedor;

/**
 * Clase que modela un Inversionista.
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Inversionista implements Runnable {
    protected Semaphore semaphore;
    protected Tenedor tenedorIzq;
    protected Tenedor tenedorDer;
    protected int id;
    protected int vecesComido;

    public Inversionista(Semaphore semaphore) {
        this.semaphore = semaphore;
        this.vecesComido = 0;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            semaphore.acquire();
            tenedorIzq.tomar();
            tenedorDer.tomar();
            comer();
            tenedorIzq.soltar();
            tenedorDer.soltar();
            semaphore.release();
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTenedorIzq(Tenedor tenedorIzq) {
        this.tenedorIzq = tenedorIzq;
    }

    public void setTenedorDer(Tenedor tenedorDer) {
        this.tenedorDer = tenedorDer;
    }

    protected void comer() {
        vecesComido++;
    }

    public int getVecesComido() {
        return vecesComido;
    }

    public Tenedor getTenedorIzq() {
        return tenedorIzq;
    }

    public Tenedor getTenedorDer() {
       return tenedorDer;
    }
}
