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
    private volatile int enSeccionCritica;
    private int hilos;
    private int maxHilosConcurrentes;
    private volatile boolean lock;

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
        this.enSeccionCritica = 0;
        this.lock = false;
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
            acquireInternalLock();
            level[threadId] = i;
            victim[i] = threadId;
            releaseInternalLock();
            boolean conflict;
            do {
                conflict = false;
                for (int k = 0; k < hilos; k++) {
                    if (k != threadId && level[k] >= i && victim[i] == threadId) {
                        conflict = true;
                        break;
                    }
                }
            } while (conflict);
        }

        while (true) {
            acquireInternalLock();
            if (enSeccionCritica < maxHilosConcurrentes) {
                enSeccionCritica++;
                releaseInternalLock();
                break;
            }
            releaseInternalLock();
        }
    }

    @Override
    public void release() {
        int threadId = getThreadId();
        acquireInternalLock();
        level[threadId] = 0;
        enSeccionCritica--;
        releaseInternalLock();
    }

    private int getThreadId() {
        return Integer.parseInt(Thread.currentThread().getName());
    }

    /*
     * Método auxiliar para adquirir el candado interno que 
     * gestiona el acceso a recursos compartidos internos.
     */
    private void acquireInternalLock() {
        while (true) {
            if (!lock) {
                lock = true;
                if (!lock) { // checamos dos veces
                    continue;
                }
                break;
            }
        }
    }
    
    /*
     * Método auxiliar para liberar el candado interno que  
     * gestiona el acceso a recursos compartidos internos.
     */
    private void releaseInternalLock() {
        lock = false;
    }
    
}