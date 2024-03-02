package kass.concurrente.modelos;

import java.util.logging.Level;
import java.util.logging.Logger;

import kass.concurrente.Main;
import kass.concurrente.constantes.Contante;
/**
 * Clase que modela un prisioner
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
     * Metodo constructor para generar un prisionero
     * @param id El identificador del prisionero
     * @param esVocero true si es Vocero false en otro caso
     * @param marcado true si ya paso
     */
    public Prisionero(Integer id, boolean esVocero, Boolean marcado, Habitacion habitacion) {
        this.id = id;
        this.esVocero = esVocero;
        this.marcado = marcado;
        this.habitacion = habitacion;
        this.vecesEnHabitacion = 0;
    }

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
    
    public void declaraVictoria() {
        if (Contante.LOGS) LOGGER.log(Level.INFO, Contante.ROJO + "Todos los {0} prisioneros han entrado al menos una vez a la habitación" + Contante.RESET, new Object[]{Contante.PRISIONEROS - 1});
        Main.detenALosPrisioneros();
    }

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

    public void incrementaVecesEnHabitacion() {
        this.vecesEnHabitacion += 1;
    }
}

