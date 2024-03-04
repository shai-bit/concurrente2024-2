package kass.concurrente;

import kass.concurrente.modelos.Habitacion;
import kass.concurrente.modelos.Prisionero;
import kass.concurrente.modelos.Vocero;
import kass.concurrente.constantes.Contante;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Clase principal que inicia la simulación del problema de los prisioneros. Esta clase configura el entorno,
 * crea a los prisioneros (incluyendo un vocero) y los coloca en una habitación común.
 * Inicia la simulación y gestiona la terminación de la misma una vez que se cumplen las condiciones de victoria.
 * 
 * @author LosNegritosDeIG
 * @version 1.0
 */
public class Main {

    private static List<Prisionero> prisioneros;
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    /**
     * Método principal que configura y comienza la simulación.
     * Crea una habitación, un conjunto de prisioneros, y asigna un vocero. 
     * Luego inicia cada prisionero en su propio hilo para comenzar la simulación.
     * 
     * @param args Argumentos pasados a la línea de comandos (no se utilizan en esta simulación).
     */
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, Contante.VERDE + "Iniciando la simulación..." + Contante.RESET);
        Habitacion habitacion = new Habitacion();
        prisioneros = new ArrayList<>();
        Vocero vocero = new Vocero(1, true, false, habitacion);
        prisioneros.add(vocero);

        for (int i = 2; i <= Contante.PRISIONEROS ; i++) {
            prisioneros.add(new Prisionero(i, false, false, habitacion));
        }

        for (Prisionero prisionero : prisioneros) {
            prisionero.start();
        }
        LOGGER.log(Level.INFO, Contante.VERDE + "Todos los prisioneros han empezado la simulación." + Contante.RESET);
    }

    /**
     * Detiene a todos los prisioneros una vez que se cumple la condición de victoria.
     * Este método interrumpe todos los hilos de los prisioneros, finalizando la simulación.
     */
    public static void detenALosPrisioneros() {
        LOGGER.log(Level.INFO, Contante.VERDE + "Deteniendo a todos los prisioneros..." + Contante.RESET);
        for (Prisionero prisionero : prisioneros) {
            prisionero.interrupt();
        }
        LOGGER.log(Level.INFO, Contante.VERDE + "Todos los prisioneros han sido detenidos, victoria." + Contante.RESET);
    }
}
