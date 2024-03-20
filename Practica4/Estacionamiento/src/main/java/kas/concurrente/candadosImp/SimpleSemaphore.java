package kas.concurrente.candadosImp;

import kas.concurrente.candado.*;

/**
 * Implementación simple de un semáforo que controla el acceso a una cantidad
 * finita de recursos o permisos. Esta clase permite a los hilos adquirir y
 * liberar permisos para acceder a secciones críticas de manera controlada,
 * asegurando que no se exceda el número limitado de permisos disponibles.
 *
 * <p>Utiliza {@link SimpleLock} para garantizar la exclusividad al modificar
 * el contador de permisos, proporcionando así una sincronización efectiva entre
 * hilos concurrentes.</p>
 */
public class SimpleSemaphore implements Semaphore {
    private volatile int permits;
    private final SimpleLock lock = new SimpleLock();

    /**
     * Construye un semáforo con el número especificado de permisos disponibles.
     *
     * @param permits el número inicial de permisos disponibles en este semáforo.
     */
    public SimpleSemaphore(int permits) {
        this.permits = permits;
    }

    /**
     * Obtiene el número de permisos disponibles en la sección crítica.
     * 
     * @return el número actual de permisos disponibles.
     */
    @Override
    public int getPermitsOnCriticalSection() {
        return permits;
    }

    /**
     * Adquiere un permiso del semáforo, bloqueando si es necesario hasta que
     * un permiso esté disponible. Una vez adquirido, el número de permisos
     * disponibles se decrementa.
     *
     * <p>Utiliza un {@link SimpleLock} para asegurar que la adquisición de permisos
     * sea atómica y evitar condiciones de carrera.</p>
     */
    @Override
    public void acquire() {
        while (true) {
            lock.lock(0); // Usando 0 como ID genérico para adquisición.
            if (permits > 0) {
                permits--; // Decrementa el contador de permisos
                lock.unlock(0);
                break;
            }
            lock.unlock(0);
            // Cede el control a otros hilos para evitar el bloqueo activo.
            Thread.yield();
        }
    }

    /**
     * Libera un permiso, incrementando el número de permisos disponibles.
     * Debe ser llamado por un hilo que previamente adquirió un permiso.
     *
     * <p>Utiliza un {@link SimpleLock} para asegurar que la liberación de permisos
     * sea atómica y evitar condiciones de carrera.</p>
     */
    @Override
    public void release() {
        lock.lock(0); // Usando 0 como ID genérico para liberación.
        permits++; // Incrementa el contador de permisos
        lock.unlock(0);
    }

    /**
     * Devuelve el número de permisos actualmente disponibles en este semáforo.
     *
     * @return el número de permisos disponibles.
     */
    public int availablePermits() {
        return permits;
    }
}
