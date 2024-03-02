package kass.concurrente.modelos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HabitacionTest {
    Habitacion h;
    Prisionero p;
    Prisionero v;

    @BeforeEach
    void setUp(){
        h = new Habitacion();
        p = new Prisionero(0,false,false, h);
        v = new Vocero(1,true,false, h);

        h.setPrendido(false);
    }

    @Test
    void switchTest1() throws InterruptedException{
        h.entraHabitacion(p);
        assertTrue(h.getPrendido());
    }

    @Test
    void switchTest2() throws InterruptedException{
        h.setPrendido(true);
        h.entraHabitacion(v);
        assertFalse(h.getPrendido());
    }

    @Test
    void simSimple() throws InterruptedException{
        h.entraHabitacion(p);
        h.entraHabitacion(v);
        assertEquals(1, ((Vocero) v).getContador());
    }
}
