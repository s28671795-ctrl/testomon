/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory.buffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.Message;
import org.apache.activemq.memory.buffer.MessageBuffer;

public class MessageQueue {
    private MessageBuffer buffer;
    private LinkedList<MessageReference> list = new LinkedList();
    private int size;
    private Object lock = new Object();
    private int position;

    public MessageQueue(MessageBuffer buffer) {
        this.buffer = buffer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void add(MessageReference messageRef) {
        Message message = messageRef.getMessageHardRef();
        int delta = message.getSize();
        int newSize = 0;
        Object object = this.lock;
        synchronized (object) {
            this.list.add(messageRef);
            this.size += delta;
            newSize = this.size;
        }
        this.buffer.onSizeChanged(this, delta, newSize);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void add(ActiveMQMessage message) {
        int delta = message.getSize();
        int newSize = 0;
        Object object = this.lock;
        synchronized (object) {
            this.list.add(message);
            this.size += delta;
            newSize = this.size;
        }
        this.buffer.onSizeChanged(this, delta, newSize);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int evictMessage() {
        Object object = this.lock;
        synchronized (object) {
            if (!this.list.isEmpty()) {
                ActiveMQMessage message = (ActiveMQMessage)this.list.removeFirst();
                int messageSize = message.getSize();
                this.size -= messageSize;
                return messageSize;
            }
        }
        return 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<MessageReference> getList() {
        Object object = this.lock;
        synchronized (object) {
            return new ArrayList<MessageReference>(this.list);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void appendMessages(List<MessageReference> answer) {
        Object object = this.lock;
        synchronized (object) {
            Iterator iter = this.list.iterator();
            while (iter.hasNext()) {
                answer.add((MessageReference)iter.next());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getSize() {
        Object object = this.lock;
        synchronized (object) {
            return this.size;
        }
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clear() {
        Object object = this.lock;
        synchronized (object) {
            this.list.clear();
            this.size = 0;
        }
    }
}

