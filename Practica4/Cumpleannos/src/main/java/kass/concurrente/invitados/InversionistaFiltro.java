package kass.concurrente.invitados;

import kass.concurrente.candados.Semaphore;

/**
 * Clase que modela un Inversionista que utiliza el algoritmo de Filtro para evitar el interbloqueo.
 * @author Kassandra Mirael
 * @version 1.0
 */
public class InversionistaFiltro extends Inversionista {

    /**
     * Crea un nuevo inversionista que utiliza el algoritmo de Filtro para gestionar el acceso
     * a los recursos compartidos.
     * 
     * @param semaphore el semáforo utilizado para controlar el acceso a los tenedores,
     * siguiendo la estructura de la clase padre.
     */
    public InversionistaFiltro(Semaphore semaphore) {
        super(semaphore);
    }

    /**
     * Realiza la acción de comer, utilizando la implementación base de la clase 
     * y permitiendo la incorporación del algoritmo de Filtro para gestionar el acceso a los tenedores.
     */
    @Override
    protected void comer() {
        super.comer();
    }
}
