package kas.concurrente.modelos;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.concurrent.Semaphore;

/**
 * Clase que representa un estacionamiento con control de acceso concurrente.
 * Utiliza un semáforo para gestionar el acceso a un número limitado de lugares.
 */
public class Estacionamiento {

    private Lugar[] lugares; // Arreglo de lugares disponibles en el estacionamiento
    private Semaphore semaforoLugares; // Semáforo para controlar el acceso a los lugares disponibles
    private int lugaresDisponibles; // Contador de lugares disponibles (no usado en la lógica actual)
    private static final Logger LOGGER = Logger.getLogger(Estacionamiento.class.getName()); // Logger para registrar
                                                                                            // eventos

    /**
     * Constructor que inicializa el estacionamiento con una capacidad determinada.
     * 
     * @param capacidad La capacidad total de lugares en el estacionamiento.
     */
    public Estacionamiento(int capacidad) {
        this.lugares = new Lugar[capacidad];
        this.semaforoLugares = new Semaphore(capacidad, true); // Modo justo (FIFO)
        this.lugaresDisponibles = capacidad;
        inicializaLugares();
    }

    /**
     * Inicializa los lugares del estacionamiento, asignando un identificador único
     * a cada uno.
     */
    private void inicializaLugares() {
        for (int i = 0; i < lugares.length; i++) {
            lugares[i] = new Lugar(i);
        }
    }

    /**
     * Comprueba si el estacionamiento está lleno basándose en la disponibilidad de
     * permisos en el semáforo.
     * 
     * @return true si no hay permisos disponibles, lo que indica que el
     *         estacionamiento está lleno.
     */
    public boolean estaLleno() {
        return semaforoLugares.availablePermits() == 0;
    }

    /**
     * Intenta ingresar un carro al estacionamiento. Bloquea si no hay lugares
     * disponibles hasta que se libera un lugar.
     * 
     * @param nombre El identificador del carro que intenta entrar.
     * @throws InterruptedException Si el hilo es interrumpido mientras espera.
     */
    public void entraCarro(int nombre) throws InterruptedException {
        semaforoLugares.acquire();
        try {
            int lugarAsignado = obtenLugar();
            if (lugarAsignado >= 0) {
                asignaLugar(lugarAsignado);
                String mensaje = "\u001B[34mEl carro de ID " + nombre + " ha entrado." + "\u001B[0m";
                LOGGER.log(Level.INFO, mensaje);
            }
        } finally {
            // Nota: Considerar dónde y cómo se libera este permiso adecuadamente en el
            // contexto de tu aplicación.
            semaforoLugares.release();
        }
    }

    /**
     * Asigna un lugar al carro que está entrando al estacionamiento.
     * 
     * @param lugar El índice del lugar asignado en el arreglo de lugares.
     * @throws InterruptedException Si el hilo es interrumpido durante la operación.
     */
    private void asignaLugar(int lugar) throws InterruptedException {
        lugares[lugar].estaciona();
    }

    /**
     * Método auxiliar para obtener el número de lugares disponibles basándose en el
     * semáforo.
     * No utilizado en la lógica actual, se mantiene para propósitos ilustrativos.
     * 
     * @return El número de permisos disponibles en el semáforo.
     */
    public int getLugaresDisponiblesAux() {
        return semaforoLugares.availablePermits();
    }

    /**
     * Busca y devuelve el índice del primer lugar disponible.
     * 
     * @return El índice del lugar disponible o -1 si no hay lugares disponibles.
     */
    private int obtenLugar() {
        for (int i = 0; i < lugares.length; i++) {
            if (lugares[i].getDisponible()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Retorna el arreglo de lugares en el estacionamiento.
     * 
     * @return El arreglo de lugares.
     */
    public Lugar[] getLugares() {
        return lugares;
    }

    /*
     * Retorna los lugares disponibles
     * 
     * @return lugaresDisponibles.
     */
    public int getLugaresDisponibles() {
        return lugaresDisponibles;
    }

}
