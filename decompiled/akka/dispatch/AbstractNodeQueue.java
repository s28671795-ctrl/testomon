/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.util.Unsafe;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractNodeQueue<T>
extends AtomicReference<Node<T>> {
    private volatile Node<T> _tailDoNotCallMeDirectly;
    private static final long tailOffset;

    protected AbstractNodeQueue() {
        Node node = new Node();
        this._tailDoNotCallMeDirectly = node;
        this.set(node);
    }

    protected final Node<T> peekNode() {
        Node node = (Node)Unsafe.instance.getObjectVolatile(this, tailOffset);
        Node node2 = node.next();
        if (node2 == null && this.get() != node) {
            while ((node2 = node.next()) == null) {
            }
        }
        return node2;
    }

    public final T peek() {
        Node<T> node = this.peekNode();
        return node != null ? (T)node.value : null;
    }

    public final void add(T t2) {
        Node<T> node = new Node<T>(t2);
        this.getAndSet(node).setNext(node);
    }

    public final void addNode(Node<T> node) {
        node.setNext(null);
        this.getAndSet(node).setNext(node);
    }

    public final boolean isEmpty() {
        return Unsafe.instance.getObjectVolatile(this, tailOffset) == this.get();
    }

    public final int count() {
        int n = 0;
        Node node = (Node)this.get();
        for (Node node2 = ((Node)Unsafe.instance.getObjectVolatile(this, tailOffset)).next(); node2 != null && n < Integer.MAX_VALUE; ++n, node2 = node2.next()) {
            if (node2 == node) break;
        }
        return n;
    }

    public final T poll() {
        Node<T> node = this.pollNode();
        if (node == null) {
            return null;
        }
        Object t2 = node.value;
        node.value = null;
        return t2;
    }

    public final Node<T> pollNode() {
        Node node = (Node)Unsafe.instance.getObjectVolatile(this, tailOffset);
        Node node2 = node.next();
        if (node2 == null && this.get() != node) {
            while ((node2 = node.next()) == null) {
            }
        }
        if (node2 == null) {
            return null;
        }
        node.value = node2.value;
        node2.value = null;
        Unsafe.instance.putOrderedObject(this, tailOffset, node2);
        node.setNext(null);
        return node;
    }

    static {
        try {
            tailOffset = Unsafe.instance.objectFieldOffset(AbstractNodeQueue.class.getDeclaredField("_tailDoNotCallMeDirectly"));
        }
        catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }

    public static class Node<T> {
        public T value;
        private volatile Node<T> _nextDoNotCallMeDirectly;
        private static final long nextOffset;

        public Node() {
            this(null);
        }

        public Node(T t2) {
            this.value = t2;
        }

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

