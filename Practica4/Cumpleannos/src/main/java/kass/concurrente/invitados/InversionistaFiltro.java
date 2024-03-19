package kass.concurrente.invitados;

import kass.concurrente.candados.Semaphore;

/**
 * Clase que modela un Inversionista que utiliza el algoritmo de Filtro para evitar el interbloqueo.
 * @author Kassandra Mirael
 * @version 1.0
 */
public class InversionistaFiltro extends Inversionista {

    public InversionistaFiltro(Semaphore semaphore) {
        super(semaphore);
    }

    @Override
    protected void comer() {
        super.comer();
    }
}
