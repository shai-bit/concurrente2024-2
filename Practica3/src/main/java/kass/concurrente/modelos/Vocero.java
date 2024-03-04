package kass.concurrente.modelos;

import kass.concurrente.constantes.Contante;

/**
 * Extiende la clase Prisionero para incorporar la funcionalidad del vocero, que tiene la responsabilidad adicional
 * de llevar la cuenta de los prisioneros que han entrado a la habitación. Esta clase incluye un contador específico
 * para este propósito.
 * 
 * @author LosNegritosDeIG
 * @version 1.0
 */
public class Vocero extends Prisionero{
    protected Integer contador;

    /**
     * Constructor para crear una instancia de un vocero.
     * 
     * @param id El identificador único del prisionero.
     * @param esVocero Debe ser true, ya que esta instancia es un vocero.
     * @param marcado Indica si el prisionero ha sido marcado previamente (true) o no (false).
     * @param habitacion La habitación asociada con el prisionero.
     */
    public Vocero(Integer id, Boolean esVocero, Boolean marcado, Habitacion habitacion) {
        super(id, esVocero, marcado, habitacion);
        this.contador = 0;   
    }

    /**
     * Verifica si se han cumplido las condiciones para terminar el problema, basándose en el contador de prisioneros.
     * 
     * @return true si se ha alcanzado el número necesario de prisioneros marcados, false en caso contrario.
     */
    public boolean checaTerminado () {
        return this.contador == 2 * (Contante.PRISIONEROS - 1);
    }

    public Integer getContador() {
        return this.contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public void incrementaContador() {
        this.contador += 1;
    }
    
}