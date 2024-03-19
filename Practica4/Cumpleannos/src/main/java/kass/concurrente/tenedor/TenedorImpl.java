package kass.concurrente.tenedor;

import kass.concurrente.candadosImpl.PetersonLock;
/**
 * Implementación concreta de la interfaz {@code Tenedor} que utiliza el algoritmo de bloqueo de Peterson
 * para gestionar el acceso concurrente. Esta clase representa un "tenedor" en el problema clásico de los
 * inversionistas comensales, permitiendo que sólo un hilo (o inversionista) tome el tenedor a la vez.
 * 
 */

public class TenedorImpl implements Tenedor {
    private int id;
    private int vecesTomado = 0;
    private final PetersonLock lock = new PetersonLock();
    private final long[] threadIds = new long[2]; // Almacena los IDs de los dos hilos, inicialmente cero.

    public TenedorImpl(int id) {
        this.id = id;
        threadIds[0] = threadIds[1] = -1; // Inicializar con un valor que sabemos que no es un ID de hilo válido.
    }

    @Override
    public void tomar() {
        int threadIndex = getThreadIndex(); 
        lock.lock(threadIndex);
        vecesTomado++;
    }

    @Override
    public void soltar() {
        int threadIndex = getThreadIndex(); // Uso del índice asignado anteriormente.
        lock.unlock(threadIndex);
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getVecesTomado() {
        return vecesTomado;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setVecesTomado(int vecesTomado) {
        this.vecesTomado = vecesTomado;
    }

     /**
     * Determina el índice del hilo actual para el bloqueo de Peterson. Este método gestiona la asignación
     * de los hilos a los índices 0 o 1, necesarios para el algoritmo de bloqueo de Peterson.
     *
     * @return El índice asignado al hilo actual, o -1 si se produce un error inesperado.
     */
    
    private int getThreadIndex() {
        long currentThreadId = Thread.currentThread().getId();
        if (threadIds[0] == -1) {
            threadIds[0] = currentThreadId;
            return 0;
        } else if (threadIds[0] == currentThreadId) {
            return 0;
        } else if (threadIds[1] == -1) {
            threadIds[1] = currentThreadId;
            return 1;
        } else if (threadIds[1] == currentThreadId) {
            return 1;
        }
        return -1; 
    }
}