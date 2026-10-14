/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory.buffer;

import java.util.LinkedList;
import org.apache.activemq.memory.buffer.MessageBuffer;
import org.apache.activemq.memory.buffer.MessageQueue;

public class OrderBasedMessageBuffer
implements MessageBuffer {
    private int limit = 0x640000;
    private LinkedList<MessageQueue> list = new LinkedList();
    private int size;
    private Object lock = new Object();

    public OrderBasedMessageBuffer() {
    }

    public OrderBasedMessageBuffer(int limit) {
        this.limit = limit;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int getSize() {
        Object object = this.lock;
        synchronized (object) {
            return this.size;
        }
    }

    @Override
    public MessageQueue createMessageQueue() {
        return new MessageQueue(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onSizeChanged(MessageQueue queue, int delta, int queueSize) {
        Object object = this.lock;
        synchronized (object) {
            this.list.addLast(queue);
            this.size += delta;
            while (this.size > this.limit) {
                MessageQueue biggest = this.list.removeFirst();
                this.size -= biggest.evictMessage();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clear() {
        Object object = this.lock;
        synchronized (object) {
            for (MessageQueue queue : this.list) {
                queue.clear();
            }
            this.size = 0;
        }
    }
}

