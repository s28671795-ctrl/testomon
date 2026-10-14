/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory.buffer;

import java.util.ArrayList;
import java.util.List;
import org.apache.activemq.memory.buffer.MessageBuffer;
import org.apache.activemq.memory.buffer.MessageQueue;

public class SizeBasedMessageBuffer
implements MessageBuffer {
    private int limit = 0x640000;
    private List<MessageQueue> bubbleList = new ArrayList<MessageQueue>();
    private int size;
    private Object lock = new Object();

    public SizeBasedMessageBuffer() {
    }

    public SizeBasedMessageBuffer(int limit) {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public MessageQueue createMessageQueue() {
        MessageQueue queue = new MessageQueue(this);
        Object object = this.lock;
        synchronized (object) {
            queue.setPosition(this.bubbleList.size());
            this.bubbleList.add(queue);
        }
        return queue;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onSizeChanged(MessageQueue queue, int delta, int queueSize) {
        Object object = this.lock;
        synchronized (object) {
            this.bubbleUp(queue, queueSize);
            this.size += delta;
            while (this.size > this.limit) {
                MessageQueue biggest = this.bubbleList.get(0);
                this.size -= biggest.evictMessage();
                this.bubbleDown(biggest, 0);
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
            for (MessageQueue queue : this.bubbleList) {
                queue.clear();
            }
            this.size = 0;
        }
    }

    protected void bubbleUp(MessageQueue queue, int queueSize) {
        MessageQueue pivot;
        int position = queue.getPosition();
        while (--position >= 0 && (pivot = this.bubbleList.get(position)).getSize() < queueSize) {
            this.swap(position, pivot, position + 1, queue);
        }
    }

    protected void bubbleDown(MessageQueue biggest, int position) {
        MessageQueue pivot;
        int queueSize = biggest.getSize();
        int end = this.bubbleList.size();
        int second = position + 1;
        while (second < end && (pivot = this.bubbleList.get(second)).getSize() > queueSize) {
            this.swap(position, biggest, second, pivot);
            position = second++;
        }
    }

    protected void swap(int firstPosition, MessageQueue first, int secondPosition, MessageQueue second) {
        this.bubbleList.set(firstPosition, second);
        this.bubbleList.set(secondPosition, first);
        first.setPosition(secondPosition);
        second.setPosition(firstPosition);
    }
}

