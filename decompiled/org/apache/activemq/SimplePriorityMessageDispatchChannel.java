/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.activemq.MessageDispatchChannel;
import org.apache.activemq.command.MessageDispatch;

public class SimplePriorityMessageDispatchChannel
implements MessageDispatchChannel {
    private static final Integer MAX_PRIORITY = 10;
    private final Object mutex = new Object();
    private final LinkedList<MessageDispatch>[] lists = new LinkedList[MAX_PRIORITY.intValue()];
    private boolean closed;
    private boolean running;
    private int size = 0;

    public SimplePriorityMessageDispatchChannel() {
        for (int i = 0; i < MAX_PRIORITY; ++i) {
            this.lists[i] = new LinkedList();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void enqueue(MessageDispatch message) {
        Object object = this.mutex;
        synchronized (object) {
            this.getList(message).addLast(message);
            ++this.size;
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
            this.getList(message).addFirst(message);
            ++this.size;
            this.mutex.notify();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MessageDispatch dequeue(long timeout2) throws InterruptedException {
        Object object = this.mutex;
        synchronized (object) {
            while (!(timeout2 == 0L || this.closed || !this.isEmpty() && this.running)) {
                if (timeout2 == -1L) {
                    this.mutex.wait();
                    continue;
                }
                this.mutex.wait(timeout2);
                break;
            }
            if (this.closed || !this.running || this.isEmpty()) {
                return null;
            }
            return this.removeFirst();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MessageDispatch dequeueNoWait() {
        Object object = this.mutex;
        synchronized (object) {
            if (this.closed || !this.running || this.isEmpty()) {
                return null;
            }
            return this.removeFirst();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MessageDispatch peek() {
        Object object = this.mutex;
        synchronized (object) {
            if (this.closed || !this.running || this.isEmpty()) {
                return null;
            }
            return this.getFirst();
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
            for (int i = 0; i < MAX_PRIORITY; ++i) {
                this.lists[i].clear();
            }
            this.size = 0;
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
            return this.size;
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
            ArrayList<MessageDispatch> result = new ArrayList<MessageDispatch>(this.size());
            for (int i = MAX_PRIORITY - 1; i >= 0; --i) {
                LinkedList<MessageDispatch> list = this.lists[i];
                result.addAll(list);
                this.size -= list.size();
                list.clear();
            }
            return result;
        }
    }

    public String toString() {
        String result = "";
        for (int i = MAX_PRIORITY - 1; i >= 0; --i) {
            result = result + i + ":{" + this.lists[i].toString() + "}";
        }
        return result;
    }

    protected int getPriority(MessageDispatch message) {
        int priority = 4;
        if (message.getMessage() != null) {
            priority = Math.max(message.getMessage().getPriority(), 0);
            priority = Math.min(priority, 9);
        }
        return priority;
    }

    protected LinkedList<MessageDispatch> getList(MessageDispatch md) {
        return this.lists[this.getPriority(md)];
    }

    private final MessageDispatch removeFirst() {
        if (this.size > 0) {
            for (int i = MAX_PRIORITY - 1; i >= 0; --i) {
                LinkedList<MessageDispatch> list = this.lists[i];
                if (list.isEmpty()) continue;
                --this.size;
                return list.removeFirst();
            }
        }
        return null;
    }

    private final MessageDispatch getFirst() {
        if (this.size > 0) {
            for (int i = MAX_PRIORITY - 1; i >= 0; --i) {
                LinkedList<MessageDispatch> list = this.lists[i];
                if (list.isEmpty()) continue;
                return list.getFirst();
            }
        }
        return null;
    }
}

