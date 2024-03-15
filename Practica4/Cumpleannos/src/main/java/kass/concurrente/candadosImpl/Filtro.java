package kass.concurrente.candadosImpl;

import kass.concurrente.candados.Semaphore;

/**
 * Clase que modela el Algoritmo del Filtro Modificado
 * Este algoritmo es similar al del filtro, lo diferente es que
 * permite una cantidad m de hilos SIMULTANEOS en la seccion critica
 * Todo es casi igual, solo realiza la modificacion pertinente para esto
 * @version 1.0
 * @author Kassandra Mirael
 */
public class Filtro implements Semaphore {
    private volatile int[] level;
    private volatile int[] victim;
    private int hilos;
    private int maxHilosConcurrentes;

    /**
     * Constructor del Filtro
     * @param hilos El numero de Hilos Permitidos
     * @param maxHilosConcurrentes EL numero de hilos concurrentes simultaneos
     */
    public Filtro(int hilos, int maxHilosConcurrentes) {
        this.hilos = hilos;
        this.maxHilosConcurrentes = maxHilosConcurrentes;
        this.level = new int[hilos];
        this.victim = new int[hilos];
        for (int i = 0; i < hilos; i++) {
            level[i] = 0;
        }
    }

    @Override
    public int getPermitsOnCriticalSection() {
        return this.maxHilosConcurrentes;
    }

    @Override
    public void acquire() {
        int threadId = getThreadId();

        for (int i = 1; i < hilos; i++) {
            level[threadId] = i;
            victim[i] = threadId;
            boolean found;
            System.out.printf("Thread %d, en ronda %d", threadId, i);
            do {
                found = false;
                for (int k = 0; k < hilos; k++) {
                    if (k != threadId && level[k] >= i && victim[i] == threadId) {
                        found = true;
                        break;
                    }
                }
                if (countThreadsInCriticalSection(i) >= maxHilosConcurrentes) {
                    found = true;
                }
            } while (found);
        }
    }

    @Override
    public void release() {
        int threadId = (int) Thread.currentThread().getId() % hilos;
        level[threadId] = 0; // Leave the critical section, making it available for others
    }

    private int getThreadId() {
        return Integer.parseInt(Thread.currentThread().getName());
    }

    private int countThreadsInCriticalSection(int currentLevel) {
        int count = 0;
        for (int lvl : level) {
            if (lvl >= currentLevel) {
                count++;
            }
        }
        return count;
    }
    
}
