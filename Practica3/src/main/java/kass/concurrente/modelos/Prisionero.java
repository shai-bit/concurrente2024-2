package kass.concurrente.modelos;

import java.util.logging.Level;
import java.util.logging.Logger;

import kass.concurrente.Main;
import kass.concurrente.constantes.Contante;

/**
 * Clase que modela un prisionero dentro de un contexto de simulación de concurrencia.
 * Cada prisionero puede ser o no el vocero, estar marcado si ya ha pasado por una habitación específica,
 * y llevar un conteo de las veces que ha entrado a la habitación.
 * <p>
 * Esta clase extiende de {@link Thread}, permitiendo que cada prisionero opere en su propio hilo de ejecución.
 * </p>
 *
 * @version 1.0
 * @author LosNegritosDeIG
 */
public class Prisionero extends Thread {
    protected long id;
    protected boolean esVocero;
    protected Boolean marcado;
    protected Habitacion habitacion;
    protected Integer vecesEnHabitacion;
    private static final Logger LOGGER = Logger.getLogger(Prisionero.class.getName());

    /**
     * Constructor para crear una instancia de Prisionero.
     *
     * @param id         El identificador único del prisionero.
     * @param esVocero   Indica si el prisionero actuará como vocero ({@code true}) o no ({@code false}).
     * @param marcado    Estado inicial del prisionero, si ya ha sido marcado previamente ({@code true}) o no ({@code false}).
     * @param habitacion La habitación asignada al prisionero.
     */
    public Prisionero(Integer id, boolean esVocero, Boolean marcado, Habitacion habitacion) {
        this.id = id;
        this.esVocero = esVocero;
        this.marcado = marcado;
        this.habitacion = habitacion;
        this.vecesEnHabitacion = 0;
    }

    /**
     * Método que define la lógica de ejecución del prisionero cuando el hilo es iniciado.
     * Aquí se maneja el proceso de entrar a la habitación y, en caso de ser el vocero,
     * declarar la victoria si se cumplen ciertas condiciones.
     */
    @Override
    public void run() {
        try {
            while (!interrupted()) {
                Thread.sleep(1000);
                if (!habitacion.entraHabitacion(this)) {
                    this.declaraVictoria();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Método utilizado por el vocero para declarar la victoria cuando se ha determinado
     * que todos los prisioneros han entrado al menos una vez a la habitación.
     */
    public void declaraVictoria() {
        if (Contante.LOGS) LOGGER.log(Level.INFO, Contante.ROJO + "Todos los  prisioneros han entrado al menos una vez a la habitación" + Contante.RESET);
        Main.detenALosPrisioneros();
    }

    // Getters y setters para los atributos de la clase Prisionero.

    @Override
    public long getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean esVocero() {
        return this.esVocero;
    }

    public void setEsVocero(boolean esVocero) {
        this.esVocero = esVocero;
    }

    public Boolean esMarcado() {
        return this.marcado;
    }

    public Boolean getMarcado() {
        return this.marcado;
    }

    public void setMarcado(Boolean marcado) {
        this.marcado = marcado;
    }

    public Habitacion getHabitacion() {
        return this.habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Integer getVecesEnHabitacion() {
        return this.vecesEnHabitacion;
    }

    public void setVecesEnHabitacion(Integer vecesEnHabitacion) {
        this.vecesEnHabitacion = vecesEnHabitacion;
    }

    /**
     * Incrementa en uno el conteo de las veces que este prisionero ha entrado a la habitación.
     */
    public void incrementaVecesEnHabitacion() {
        this.vecesEnHabitacion += 1;
    }
}

