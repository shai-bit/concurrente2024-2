package kas.concurrente;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import kas.concurrente.modelos.Estacionamiento;

import java.util.logging.Level;

public class Main implements Runnable {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static Estacionamiento estacionamiento;

    private final int idCarro;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_GREEN = "\u001B[32m";

    public Main(int idCarro) {
        this.idCarro = idCarro;
    }

    public static void main(String[] args) throws InterruptedException {
        // Inicialización del Estacionamiento y Semaforo
        final int NUM_CARROS = 10000; // Número de carros que se van a simular.
        estacionamiento = new Estacionamiento(NUM_CARROS);

        // Crear estructura de datos para contener los hilos
        List<Thread> hilos = new ArrayList<>();

        // Inicializar los hilos y agregarlos a la estructura
        for (int i = 0; i < NUM_CARROS; i++) {
            Thread hilo = new Thread(new Main(i));
            hilos.add(hilo);
            hilo.start();
        }

        // Hacer join a todos los hilos
        for (Thread hilo : hilos) {
            hilo.join();
        }

        LOGGER.log(Level.INFO, "Todos los carros han terminado de estacionarse.");
    }

    @Override
    public void run() {
        try {
            // Simulación de carro entrando al estacionamiento
            String enteringMessage = ANSI_BLUE + "Carro con ID " + idCarro + " está intentando entrar al estacionamiento." + ANSI_RESET;
            LOGGER.log(Level.INFO, enteringMessage);
            estacionamiento.entraCarro(idCarro);
            // Simulación terminada
            String exitMessage = ANSI_GREEN + "Carro con ID " + idCarro + " ha salido del estacionamiento." + ANSI_RESET;
            LOGGER.log(Level.INFO, exitMessage);
        } catch (InterruptedException e) {
            String errorMessage = ANSI_RED + "El hilo fue interrumpido" + ANSI_RESET;
            LOGGER.log(Level.SEVERE, errorMessage, e);
            Thread.currentThread().interrupt();
        }
    }
}
