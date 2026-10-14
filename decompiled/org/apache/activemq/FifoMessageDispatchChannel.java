/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.activemq.MessageDispatchChannel;
import org.apache.activemq.command.MessageDispatch;

public class FifoMessageDispatchChannel
implements MessageDispatchChannel {
    private final Object mutex = new Object();
    private final LinkedList<MessageDispatch> list = new LinkedList();
    private boolean closed;
    private boolean running;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void enqueue(MessageDispatch message) {
        Object object = this.mutex;
        synchronized (object) {
            this.list.addLast(message);
            this.mutex.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void enqueueFirst(MessageDispatch message) {
        Object object = this.mutex;
        synchronized (object) {
            this.list.addFirst(message);
            this.mutex.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isEmpty() {
        Object object = this.mutex;
        synchronized (object) {
            return this.list.isEmpty();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MessageDispatch dequeue(long timeout2) throws InterruptedException {
        Object object = this.mutex;
        synchronized (object) {
            while (!(timeout2 == 0L || this.closed || !this.list.isEmpty() && this.running)) {
                if (timeout2 == -1L) {
                    this.mutex.wait();
                    continue;
                }
                this.mutex.wait(timeout2);
                break;
            }
            if (this.closed || !this.running || this.list.isEmpty()) {
                return null;
            }
            return this.list.removeFirst();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MessageDispatch dequeueNoWait() {
        Object object = this.mutex;
        synchronized (object) {
            if (this.closed || !this.running || this.list.isEmpty()) {
                return null;
            }
            return this.list.removeFirst();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MessageDispatch peek() {
        Object object = this.mutex;
        synchronized (object) {
            if (this.closed || !this.running || this.list.isEmpty()) {
                return null;
            }
            return this.list.getFirst();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void start() {
        Object object = this.mutex;
        synchronized (object) {
            this.running = true;
            this.mutex.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() {
        Object object = this.mutex;
        synchronized (object) {
            this.running = false;
            this.mutex.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        Object object = this.mutex;
        synchronized (object) {
            if (!this.closed) {
                this.running = false;
                this.closed = true;
            }
            this.mutex.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear() {
        Object object = this.mutex;
        synchronized (object) {
            this.list.clear();
        }
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int size() {
        Object object = this.mutex;
        synchronized (object) {
            return this.list.size();
        }
    }

    @Override
    public Object getMutex() {
        return this.mutex;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<MessageDispatch> removeAll() {
        Object object = this.mutex;
        synchronized (object) {
            ArrayList<MessageDispatch> rc = new ArrayList<MessageDispatch>(this.list);
            this.list.clear();
            return rc;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String toString() {
        Object object = this.mutex;
        synchronized (object) {
            return this.list.toString();
        }
    }
}

