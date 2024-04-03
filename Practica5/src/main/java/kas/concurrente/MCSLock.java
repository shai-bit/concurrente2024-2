package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;

public class MCSLock implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    public MCSLock() {
        tail = new AtomicReference<>(null);
        myNode = ThreadLocal.withInitial(QNode::new);
    }

    public void lock() {
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;
            // wait until predecessor gives up the lock
            while (qnode.locked) {
                Thread.yield();
            }
        }
    }

    public void unlock() {
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null)) {
                return;
            }
            // wait until predecessor fills in its next field
            while (qnode.next == null) {}
        }
        qnode.next.locked = false;
        qnode.next = null;
    }
    
    // Inner class QNode should be static and public if it's being used by other classes.
    public static class QNode {
        volatile boolean locked = false;
        volatile QNode next = null;
    }
}
