package kas.concurrente;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class ALock implements Lock{

    private ThreadLocal<Integer> mySlotIndex = ThreadLocal.withInitial(() -> 0);
    private AtomicInteger tail;
    private AtomicReferenceArray<Boolean> flag;
    private int size;

    public ALock(int capacity){
        size = capacity;
        tail = new AtomicInteger();
        flag = new AtomicReferenceArray<>(capacity); 
        for (int i = 0; i < capacity; i++) {
            flag.set(i, false); // Initialize all to false
        }
        flag.set(0, true); // The first index should be true
    }

    @Override
    public void lock() {
        int slot = tail.getAndIncrement() % size;
        mySlotIndex.set(slot);
        while (!flag.get(slot)) {
            Thread.yield();
        }
    }

    @Override
    public void unlock() {
        int slot = mySlotIndex.get(); 
        flag.set(slot, false); 
        flag.set((slot + 1) % size, true); 
    }
    
}