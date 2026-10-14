/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.pool;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import org.ldaptive.pool.QueueType;

public class Queue<T>
implements Iterable<T> {
    private final QueueType queueType;
    private final Deque<T> queue;

    public Queue(QueueType type) {
        this.queueType = type;
        this.queue = new LinkedList<T>();
    }

    public void add(T t2) {
        if (QueueType.LIFO == this.queueType) {
            this.queue.addFirst(t2);
        } else if (QueueType.FIFO == this.queueType) {
            this.queue.addLast(t2);
        } else {
            throw new IllegalStateException("Unknown queue type: " + this.queueType);
        }
    }

    public T remove() {
        return this.queue.removeFirst();
    }

    public boolean remove(T t2) {
        return this.queue.remove(t2);
    }

    public T element() {
        return this.queue.getFirst();
    }

    public boolean contains(T t2) {
        return this.queue.contains(t2);
    }

    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public int size() {
        return this.queue.size();
    }

    @Override
    public Iterator<T> iterator() {
        return this.queue.iterator();
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "queueType=" + (Object)((Object)this.queueType) + ", " + "queue=" + this.queue + "]";
    }
}

