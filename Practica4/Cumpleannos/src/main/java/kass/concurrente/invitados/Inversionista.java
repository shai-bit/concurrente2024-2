package kass.concurrente.invitados;

import kass.concurrente.candados.Semaphore;
import kass.concurrente.tenedor.Tenedor;

/**
 * La clase modela un inversionista en el contexto de un problema
 * de concurrencia, donde múltiples inversionistas intentan comer utilizando tenedores compartidos.
 * Se implementa como una tarea que se ejecuta en un hilo, donde cada inversionista intenta tomar
 * dos tenedores adyacentes para comer y luego los libera.
 * 
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Inversionista implements Runnable {
    protected Semaphore semaphore;
    protected Tenedor tenedorIzq;
    protected Tenedor tenedorDer;
    protected int id;
    protected int vecesComido;

    /**
     * Crea un nuevo inversionista con acceso a un semáforo compartido
     * @param semaphore el semáforo utilizado para controlar el acceso a los tenedores
     */
    public Inversionista(Semaphore semaphore) {
        this.semaphore = semaphore;
        this.vecesComido = 0;
    }

    /**
     * Ejecuta la tarea del inversionista, intentando comer repetidamente hasta que el hilo
     * se interrumpe. Cada intento de comer involucra adquirir el semáforo, tomar ambos tenedores,
     * comer, soltar los tenedores y liberar el semáforo.
     */
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

    /**
     * Establece el identificador del inversionista.
     * @param id el identificador único del inversionista
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Asigna el tenedor izquierdo al inversionista.
     * @param tenedorIzq el tenedor que se usará en la mano izquierda
     */
    public void setTenedorIzq(Tenedor tenedorIzq) {
        this.tenedorIzq = tenedorIzq;
    }

    /**
     * Asigna el tenedor derecho al inversionista.
     * @param tenedorDer el tenedor que se usará en la mano derecha
     */
    public void setTenedorDer(Tenedor tenedorDer) {
        this.tenedorDer = tenedorDer;
    }

    /**
     * Incrementa el contador de veces que el inversionista ha comido.
     * Este método es llamado cada vez que el inversionista logra comer.
     */
    protected void comer() {
        vecesComido++;
    }

    /**
     * Devuelve el número de veces que el inversionista ha comido.
     * @return el número de veces que el inversionista ha comido
     */
    public int getVecesComido() {
        return vecesComido;
    }

    /**
     * Obtiene el tenedor izquierdo asignado al inversionista.
     * @return el tenedor izquierdo
     */
    public Tenedor getTenedorIzq() {
        return tenedorIzq;
    }

    /**
     * Obtiene el tenedor derecho asignado al inversionista.
     * @return el tenedor derecho
     */
    public Tenedor getTenedorDer() {
       return tenedorDer;
    }
}
