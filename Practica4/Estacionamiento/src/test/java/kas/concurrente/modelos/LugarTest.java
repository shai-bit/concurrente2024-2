package kas.concurrente.modelos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.Semaphore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LugarTest {
    Semaphore semaforo;
    Lugar lugar;
    List<Thread> hilos;
    static final int numHilos = 15;

    @BeforeEach
    void setUp() throws InterruptedException {
        lugar = new Lugar(1);
        semaforo = new Semaphore(1);
    }

    @Test
    void constructorTest() {
        assertTrue(lugar.getId() == 1 && lugar.getDisponible() == true && lugar.getFiltroModificado() != null);
    }

    @Test
    void estacionaTest() throws InterruptedException {
        lugar.estaciona();
        assertTrue(lugar.getDisponible());
    }

    /**
     * AGREGA 2 TEST MAS
     * TEST bien hechos
     */

    @Test
    void lugarNoDisponibleDespuesDeEstacionar() throws InterruptedException {
        lugar.estacionaSinPastel();
        assertFalse(lugar.getDisponible(), "El lugar no debería estar disponible después de estacionar un carro.");
    }

    @Test
    void lugarDisponibleDespuesDeSalirCarro() {
        lugar.saleCarro();
        assertTrue(lugar.getDisponible(), "El lugar debería estar disponible después de que el carro sale.");
    }

}
