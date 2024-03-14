package kas.concurrente.modelos;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Representa un lugar individual dentro de un estacionamiento.
 * Cada lugar tiene un identificador único y puede estar disponible o no, dependiendo de si está ocupado por un carro.
 * La clase utiliza un semáforo para asegurar el acceso exclusivo a cada lugar por parte de los carros, evitando así condiciones de carrera.
 */
public class Lugar {
    private Integer id; // Identificador único para el lugar
    private boolean disponible = true; // Estado de disponibilidad del lugar
    private Semaphore semaforo = new Semaphore(1, true); // Semaforo justo para controlar el acceso al lugar
    private int vecesEstacionado = 0; // Contador de cuántas veces se ha estacionado un carro en este lugar
    private static final Logger LOGGER = Logger.getLogger(Lugar.class.getName()); // Logger para registrar eventos

    /**
     * Constructor que crea un lugar con un identificador específico.
     * Por defecto, el lugar está disponible y listo para ser ocupado por un carro.
     *
     * @param id El identificador del lugar.
     */
    public Lugar(int id) {
        this.id = id;
    }

    /**
     * Constructor vacío.
     * Utilizado para instanciar un lugar sin asignarle un identificador inmediatamente.
     */
    public Lugar() {
    }

    /**
     * Simula el proceso de un carro estacionándose en este lugar.
     * Este método adquiere un permiso del semáforo, lo que garantiza que sólo un carro pueda ocupar el lugar a la vez.
     * Registra cuántas veces se ha ocupado el lugar incrementando el contador de veces estacionado.
     *
     * @throws InterruptedException si el hilo actual es interrumpido mientras espera el permiso del semáforo.
     */
    public void estaciona() throws InterruptedException {
        semaforo.acquire();
        try {
            disponible = false;
            vecesEstacionado++; // Incrementa el contador al estacionarse
            vePorPastel();
        } finally {
            LOGGER.log(Level.INFO, "\u001B[31mCarro en lugar {0} va a salir.\u001B[0m", id);
            disponible = true;
            semaforo.release();
        }
    }

    /**
     * Genera un tiempo de espera simulado para representar el carro ocupando el lugar.
     * El tiempo de espera es pseudoaleatorio, entre 1 y 5 segundos.
     *
     * @throws InterruptedException si el hilo es interrumpido mientras está en espera.
     */
    public void vePorPastel() throws InterruptedException {
        int espera = ThreadLocalRandom.current().nextInt(1, 6);
        Thread.sleep(espera * 1000);
    }

    /**
     * Obtiene el identificador del lugar.
     *
     * @return El identificador del lugar.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Comprueba si el lugar está disponible para ser ocupado por un carro.
     *
     * @return Verdadero si el lugar está disponible, falso si está ocupado.
     */
    public boolean getDisponible() {
        return disponible;
    }

    /**
     * Obtiene el número de veces que se ha estacionado un carro en este lugar.
     *
     * @return El número de veces que el lugar ha sido ocupado.
     */
    public int getVecesEstacionado() {
        return vecesEstacionado;
    }

    /**
     * Devuelve el semáforo asociado a este lugar.
     * Se utiliza para inspecciones o manipulaciones avanzadas del mecanismo de sincronización.
     *
     * @return El semáforo que controla el acceso al lugar.
     */
    public Semaphore getFiltroModificado() {
        return semaforo;
    }
}
