package kas.concurrente;

import java.util.concurrent.atomic.AtomicBoolean;

public class TTASLock implements Lock {
    private AtomicBoolean state = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (true) {
            // Primero, se verifica el estado sin cambiarlo,
            // para evitar llamadas innecesarias a getAndSet() que es más costosa.
            while (state.get()) {}

            // Luego, se intenta bloquear el candado usando getAndSet()
            // Si es exitoso, es decir, el estado era falso y ahora es verdadero, se sale del ciclo.
            if (!state.getAndSet(true)) {
                return;
            }
        }
    }

    @Override
    public void unlock() {
        state.set(false); // Desbloquear el candado
    }
}
