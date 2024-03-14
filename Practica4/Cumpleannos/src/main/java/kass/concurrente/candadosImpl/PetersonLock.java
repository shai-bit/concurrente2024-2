package kass.concurrente.candadosImpl;

import kass.concurrente.candados.Lock;

/**
 * Clase que implementa el candado usando el Legendario
 * algoritmo de PeterGod.
 * No hay mucho que decir, ya saben que hacer
 * @version 1.0
 * @author Kassandra Mirael
 */
public class PetersonLock implements Lock {
    private volatile boolean[] flag = new boolean[2];
    private volatile int turn;

    @Override
    public void lock() {
        int id = getThreadId();
        int otherId = 1 - id;
        flag[id] = true;
        turn = otherId;
        
        while (flag[otherId] && turn == otherId) {
            Thread.yield();
        }
    }

    @Override
    public void unlock() {
        int id = getThreadId();
        flag[id] = false;
    }

    private int getThreadId() {
        return Integer.parseInt(Thread.currentThread().getName());
    }
    
}
