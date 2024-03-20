package kas.concurrente.candadosImp;

import kas.concurrente.candado.*;

/**
 * Implementación simple de un candado (Lock) que permite el control de acceso
 * entre hilos para evitar condiciones de carrera. Esta implementación específica
 * ofrece métodos para adquirir y liberar el candado, tanto en un contexto general
 * como en un contexto específico de dos hilos.
 * 
 * <p>La clase utiliza una variable booleana volátil {@code locked} para indicar
 * el estado del candado, y un arreglo de booleanos {@code flag} junto con una
 * variable {@code turn} para gestionar el acceso entre dos hilos específicos.</p>
 */
public class SimpleLock implements Lock {
    private volatile boolean[] flag = new boolean[2];
    private volatile int turn;
    private volatile boolean locked = false;

    /**
     * Constructor que inicializa el estado del candado. Prepara el candado para su
     * uso estableciendo el estado inicial de los indicadores de los hilos a {@code false}.
     */
    public SimpleLock() {
        flag[0] = false;
        flag[1] = false;
    }

    /**
     * Método para adquirir el candado en un contexto específico de dos hilos.
     * Un hilo llama a este método pasando su identificador único para intentar
     * adquirir el candado y entrar en la sección crítica.
     * 
     * @param id Identificador del hilo que intenta adquirir el candado.
     *           Se espera que sea 0 o 1 para el funcionamiento correcto de esta implementación.
     */
    public void lock(int id) {
        int otherId = 1 - id;
        flag[id] = true;
        turn = otherId;

        while (flag[otherId] && turn == otherId) {
            // Cede el control a otros hilos para evitar el bloqueo activo y reducir la carga de la CPU.
            Thread.yield();
        }
    }

    /**
     * Método para liberar el candado en un contexto específico de dos hilos.
     * Un hilo llama a este método pasando su identificador único para liberar el candado
     * y permitir que otro hilo pueda entrar en la sección crítica.
     * 
     * @param id Identificador del hilo que libera el candado.
     *           Se espera que sea 0 o 1 para el funcionamiento correcto de esta implementación.
     */
    public void unlock(int id) {
        flag[id] = false;
    }

    /**
     * Método para adquirir el candado de forma exclusiva sin identificador específico.
     * Este método intenta adquirir el candado y bloquea a cualquier otro hilo de entrar
     * en la sección crítica hasta que el candado sea liberado.
     */
    @Override
    public void lock() {
        while (true) {
            if (!locked) {
                locked = true;
                break;
            }
            // Cede el control a otros hilos para evitar el bloqueo activo y reducir la carga de la CPU.
            Thread.yield();
        }
    }

    /**
     * Método para liberar el candado de forma exclusiva.
     * Al llamar a este método, el candado se marca como disponible,
     * permitiendo que otros hilos intenten adquirirlo para entrar en la sección crítica.
     */
    @Override
    public void unlock() {
        locked = false;
    }
}
