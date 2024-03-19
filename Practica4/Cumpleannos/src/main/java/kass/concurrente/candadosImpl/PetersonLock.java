package kass.concurrente.candadosImpl;

import kass.concurrente.candados.Lock;

public class PetersonLock implements Lock {
    private volatile boolean[] flag = new boolean[2];
    private volatile int turn;

    // Acepta un índice de hilo como argumento
    public void lock(int id) {
        int otherId = 1 - id;
        flag[id] = true;
        turn = otherId;
        
        while (flag[otherId] && turn == otherId) {
            Thread.yield();
        }
    }

    // Acepta un índice de hilo como argumento para unlock también
    public void unlock(int id) {
        flag[id] = false;
    }

    // Los métodos lock y unlock sin argumentos se mantienen para cumplir con la interfaz Lock,
    // pero no se utilizarán en esta implementación.
    @Override
    public void lock() {
    }

    @Override
    public void unlock() {
    }
}
