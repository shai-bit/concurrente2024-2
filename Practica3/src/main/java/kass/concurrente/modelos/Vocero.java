package kass.concurrente.modelos;

import kass.concurrente.constantes.Contante;

/**
 * Este ess quien lleva la cuenta de los prisioneros que han entrado a la habitacion
 * a parte de los atributos de Prisionero, tambien posee un contador
 * @author LosNegritosDeIG
 * @version 1.0
 */
public class Vocero extends Prisionero{
    protected Integer contador;
    public Vocero(Integer id, Boolean esVocero, Boolean marcado, Habitacion habitacion) {
        super(id, esVocero, marcado, habitacion);
        this.contador = 0;   
    }

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