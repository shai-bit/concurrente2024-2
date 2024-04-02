package kas.concurrente;

import java.util.concurrent.atomic.AtomicBoolean;

public class BackoffLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);
    private static final int MIN_DELAY = 1; // Reemplaza ... con el valor deseado
    private static final int MAX_DELAY = 1000; // Reemplaza ... con el valor deseado

    @Override
    public void lock() {
        Backoff backoff = new Backoff(MIN_DELAY, MAX_DELAY);
        while (true) {
            while (state.get()) {}; // Espera activa mientras el candado esté ocupado
            if (!state.getAndSet(true)) { // Intenta adquirir el candado
                return; // Si tiene éxito, retorna y sale del método
            } else { // Si no tiene éxito, realiza backoff
                try {
                    backoff.backoff();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restablece el estado de interrupción
                    // Puedes manejar la interrupción como consideres necesario para tu aplicación
                }
            }
        }
    }

    @Override
    public void unlock() {
        state.set(false); // Libera el candado
    }
    
}
