package kas.concurrente;

import java.util.concurrent.atomic.AtomicReference;

public class CLHLock implements Lock {
    private final AtomicReference<QNode> tail;
    private final ThreadLocal<QNode> myPred;
    private final ThreadLocal<QNode> myNode;

    public CLHLock() {
        tail = new AtomicReference<>(null);
        myNode = ThreadLocal.withInitial(QNode::new);
        myPred = new ThreadLocal<QNode>(){
            @Override
            protected QNode initialValue() {
                return null;
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred != null && pred.locked) {} // spin
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }

    // ... Puedes agregar más métodos si son necesarios.
}
