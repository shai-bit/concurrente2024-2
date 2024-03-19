package kass.concurrente.tenedor;

import kass.concurrente.candadosImpl.PetersonLock;

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
        int threadIndex = getThreadIndex(); // Potencial condición de carrera aquí.
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
        return -1; // Este caso no debería ocurrir.
    }
}