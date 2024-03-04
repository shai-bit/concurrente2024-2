package kass.concurrente.modelos;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import kass.concurrente.constantes.Contante;

import java.util.logging.Level;

/**
 * Clase que fungira como habitacion
 * Se sabe que existe un interruptor que nos dice
 * si el foco esta prendido o no
 * Se desconoce el estado inicial del foco (Usar un random, para que sea
 * pseudoaleatorio el estado inicial)
 * @author LosNegritosDeIG
 * @version 1.0
 */
public class Habitacion {
    private volatile boolean prendido;
    private final ReentrantLock lock;
    private static final Logger LOGGER = Logger.getLogger(Habitacion.class.getName());

    /**
     * Metodo Constructor
     * Inicializa el estado del foco a un valor aleatorio
     */
    public Habitacion(){
        this.prendido = new Random().nextBoolean();
        if (Contante.LOGS) LOGGER.log(Level.INFO, Contante.VERDE + "La habitación fue inicializada con el foco {0}" + Contante.RESET, new Object[]{this.prendido});
        this.lock = new ReentrantLock();
    }

    /**
     * Metodo que permite al prisionero entrar a la habitacion
     * Recordemos que solo uno pasa a la vez, esta es la SECCION CRITICA
     * En este caso se controla desde fuera
     * Es similar al algoritmo que progonan y similar al de su tarea
     * El prisionero espera una cantidad finita de tiempo
     * @param prisionero El prisionero que viene entrando
     * @return false si ya pasaron todos, true en otro caso
     * @throws InterruptedException Si falla algun hilo
     */
    public boolean entraHabitacion(Prisionero prisionero) throws InterruptedException{
        lock.lock();
        try {
            if (Contante.LOGS) LOGGER.log(Level.INFO, Contante.AZUL + "El prisionero con id: {0} entró a la habitación" + Contante.RESET, new Object[]{prisionero.getId()});
            //El vocero entra al cuarto
            if (prisionero.esVocero() && this.prendido) {
                this.prendido = false;
                ((Vocero) prisionero).incrementaContador();
                if (Contante.LOGS) LOGGER.log(Level.INFO, Contante.ROJO + "El contador del vocero va en: {0}" + Contante.RESET, new Object[]{((Vocero) prisionero).getContador()});
                return !((Vocero) prisionero).checaTerminado();

            }

            //Prisionero entra al cuarto
            if (prisionero.getVecesEnHabitacion() < 2 && !this.prendido) {
                this.prendido = true;
                prisionero.incrementaVecesEnHabitacion();
                if (prisionero.getVecesEnHabitacion() >= 2) {
                    prisionero.setMarcado(true);
                }
            }
            return true;
        } finally {
            lock.unlock();
        }
        
    }

    public boolean isPrendido() {
        return prendido;
    }

    public Boolean getPrendido() {
        return this.prendido;
    }

    public void setPrendido(boolean prendido) {
        this.prendido = prendido;
    }
}
