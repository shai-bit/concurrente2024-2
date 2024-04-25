package org.practica6.snapshotImpl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import org.practica6.snapshotsImpl.SeqSnapshot;

public class SeqSnapshotTest {

    @Test
    void testInitialization() {
        SeqSnapshot<Integer> snapshot = new SeqSnapshot<>(5, 0);
        Integer[] expected = new Integer[] {0, 0, 0, 0, 0};
        assertArrayEquals(expected, snapshot.scan());
    }

    @Test
    void testSingleThreadUpdateAndScan() {
        SeqSnapshot<String> snapshot = new SeqSnapshot<>(5, "init");
        // Set the thread name to a valid integer
        Thread.currentThread().setName("0");
        snapshot.update("updated");
        String[] expected = new String[] {"updated", "init", "init", "init", "init"};
        assertArrayEquals(expected, snapshot.scan());
    }

    @Test
    void testMultiThreadedUpdateAndScan() throws InterruptedException {
        SeqSnapshot<Integer> snapshot = new SeqSnapshot<>(5, 0);
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int ID = i;
            threads[i] = new Thread(() -> {
                Thread.currentThread().setName(String.valueOf(ID));
                snapshot.update(ID + 1);
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join();
        }
        Integer[] expected = new Integer[] {1, 2, 3, 4, 5};
        assertArrayEquals(expected, snapshot.scan());
    }
}