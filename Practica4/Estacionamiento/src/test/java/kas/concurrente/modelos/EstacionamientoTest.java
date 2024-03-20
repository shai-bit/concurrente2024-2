package kas.concurrente.modelos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstacionamientoTest {
    Estacionamiento es;
    final static int NUMLUGARES = 4;
    List<Thread> hilos;

    @BeforeEach
    void setUp() {
        es = new Estacionamiento(NUMLUGARES);
        initHilos();
    }

    /**
     * Teste que revisa si tiene todos los lugares disponibles al iniciar
     */
    @Test
    void lugaresDisponiblesITest() {
        assertEquals(NUMLUGARES, es.getLugaresDisponiblesAux());
    }

    /**
     * Test que comprueba que el numero de veces que se estaciona sea correcto
     * 
     * @throws InterruptedException
     */
    @Test
    void conteoVecesEstacionado() throws InterruptedException {
        for (int i = 0; i < NUMLUGARES; i++) {
            es.getLugares()[i].estaciona();
        }
        assertEquals(NUMLUGARES, verificaVecesEstacionado());
    }

    @Test
    void conteoGlobalVecesEstacionado() throws InterruptedException {
        for (Thread t : hilos) {
            t.start();
        }

        for (Thread t : hilos) {
            t.join();
        }

        assertEquals(NUMLUGARES * 2, verificaVecesEstacionado());
    }

    int verificaVecesEstacionado() {
        int res = 0;
        for (int i = 0; i < es.getLugares().length; ++i) {
            res += es.getLugares()[i].getVecesEstacionado();
        }

        return res;
    }

    /**
     * AGREGA 2 TEST MAS
     * TEST bien hechos
     */
    @Test
    void estacionamientoSeMarcaComoLleno() throws InterruptedException {
        int carritos = 5;
        for (int i = 0; i < carritos; i++) {
            new Thread(() -> {
                try {
                    es.entraCarro(ThreadLocalRandom.current().nextInt(1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
        // espera un poco para que todos los carros se intenten estacionar.
        Thread.sleep(1000);

        // Verifica si el estacionamiento se marca como lleno correctamente.
        assertEquals(true, es.estaLleno(), "El estacionamiento debería estar lleno.");
    }

    void initHilos() {
        hilos = new ArrayList<>();

        for (int i = 0; i < NUMLUGARES * 2; ++i) {
            Thread t = new Thread(this::simulaCS, "" + i);
            hilos.add(t);
        }
    }

    void simulaCS() {
        try {
            int id = Integer.parseInt(Thread.currentThread().getName());
            es.entraCarro(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    void verificaInicializacionCorrectaDelEstacionamiento() {
        // verifica que la cantidad de lugares disponibles coincida con la capacidad inicial
        assertEquals(NUMLUGARES, es.getLugaresDisponiblesAux(),
                "La cantidad inicial de lugares disponibles debe coincidir con la capacidad especificada.");

        // verifica que el arreglo de lugares tenga la longitud esperada
        assertEquals(NUMLUGARES, es.getLugares().length,
                "El arreglo de lugares debe tener una longitud que coincida con la capacidad especificada.");

        // verifica que todos los lugares estén inicialmente disponibles
        for (Lugar lugar : es.getLugares()) {
            assertTrue(lugar.getDisponible(), "Todos los lugares deben estar disponibles inicialmente.");
        }
    }

}
