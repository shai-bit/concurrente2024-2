package kass.concurrente.constantes;

/**
 * Clase de constantes que alberga nuestro programa
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Contante {
    private Contante() {
        // private constructor to hide the implicit public one
    }
    
    public static final Integer PRISIONEROS = 3;
    public static final boolean LOGS = true;
    public static final Integer DIEZ_SEGUNDOS = 10000;
    public static final Integer CINCO_SEGUNDOS = 5000;
    public static final String VERDE = "\u001B[32m";
    public static final String ROJO = "\u001B[31m";
    public static final String AZUL = "\u001B[34m";
    public static final String RESET = "\u001B[0m";
}
