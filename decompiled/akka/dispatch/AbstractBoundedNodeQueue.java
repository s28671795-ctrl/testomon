/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.util.Unsafe;

public abstract class AbstractBoundedNodeQueue<T> {
    private final int capacity;
    private volatile Node<T> _enqDoNotCallMeDirectly;
    private volatile Node<T> _deqDoNotCallMeDirectly;
    private static final long enqOffset;
    private static final long deqOffset;

    protected AbstractBoundedNodeQueue(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("AbstractBoundedNodeQueue.capacity must be >= 0");
        }
        this.capacity = n;
        Node node = new Node();
        this.setDeq(node);
        this.setEnq(node);
    }

    private void setEnq(Node<T> node) {
        Unsafe.instance.putObjectVolatile(this, enqOffset, node);
    }

    private Node<T> getEnq() {
        return (Node)Unsafe.instance.getObjectVolatile(this, enqOffset);
    }

    private boolean casEnq(Node<T> node, Node<T> node2) {
        return Unsafe.instance.compareAndSwapObject(this, enqOffset, node, node2);
    }

    private void setDeq(Node<T> node) {
        Unsafe.instance.putObjectVolatile(this, deqOffset, node);
    }

    private Node<T> getDeq() {
        return (Node)Unsafe.instance.getObjectVolatile(this, deqOffset);
    }

    private boolean casDeq(Node<T> node, Node<T> node2) {
        return Unsafe.instance.compareAndSwapObject(this, deqOffset, node, node2);
    }

    protected final Node<T> peekNode() {
        Node<T> node;
        Node<T> node2;
        while ((node2 = (node = this.getDeq()).next()) == null && this.getEnq() != node) {
        }
        return node2;
    }

    public final T peek() {
        Node<T> node = this.peekNode();
        return node != null ? (T)node.value : null;
    }

    public final int capacity() {
        return this.capacity;
    }

    public final boolean add(T t2) {
        block2: {
            Node node;
            Node node2 = null;
            do {
                node = this.getEnq();
                int n = node.count;
                if (n - this.getDeq().count >= this.capacity) break block2;
                if (node2 == null) {
                    node2 = new Node();
                    node2.value = t2;
                }
                node2.count = n + 1;
            } while (!this.casEnq(node, node2));
            node.setNext(node2);
            return true;
        }
        return false;
    }

    public final boolean addNode(Node<T> node) {
        block1: {
            Node node2;
            node.setNext(null);
            do {
                node2 = this.getEnq();
                int n = node2.count;
                if (n - this.getDeq().count >= this.capacity) break block1;
                node.count = n + 1;
            } while (!this.casEnq(node2, node));
            node2.setNext(node);
            return true;
        }
        return false;
    }

    public final boolean isEmpty() {
        return this.getEnq() == this.getDeq();
    }

    public final int size() {
        int n;
        int n2;
        int n3;
        do {
            n2 = this.getDeq().count;
            n = this.getEnq().count;
        } while ((n3 = this.getDeq().count) != n2);
        return n - n3;
    }

    public final T poll() {
        Node<T> node = this.pollNode();
        return node != null ? (T)node.value : null;
    }

    public final Node<T> pollNode() {
        while (true) {
            Node node;
            Node<T> node2;
            if ((node2 = (node = this.getDeq()).next()) != null) {
                if (!this.casDeq(node, node2)) continue;
                node.value = node2.value;
                node.setNext(null);
                node2.value = null;
                return node;
            }
            if (this.getEnq() == node) break;
        }
        return null;
    }

    static {
        try {
            enqOffset = Unsafe.instance.objectFieldOffset(AbstractBoundedNodeQueue.class.getDeclaredField("_enqDoNotCallMeDirectly"));
            deqOffset = Unsafe.instance.objectFieldOffset(AbstractBoundedNodeQueue.class.getDeclaredField("_deqDoNotCallMeDirectly"));
        }
        catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }

    public static class Node<T> {
        protected T value;
        private volatile Node<T> _nextDoNotCallMeDirectly;
        protected int count;
        private static final long nextOffset;

        public final Node<T> next() {
            return (Node)Unsafe.instance.getObjectVolatile(this, nextOffset);
        }

        protected final void setNext(Node<T> node) {
            Unsafe.instance.putOrderedObject(this, nextOffset, node);
        }

        static {
            try {
                nextOffset = Unsafe.instance.objectFieldOffset(Node.class.getDeclaredField("_nextDoNotCallMeDirectly"));
            }
            catch (Throwable throwable) {
                throw new ExceptionInInitializerError(throwable);
            }
        }
    }
}

