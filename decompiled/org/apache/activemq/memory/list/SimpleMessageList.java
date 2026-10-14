/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.filter.DestinationFilter;
import org.apache.activemq.memory.list.MessageList;

public class SimpleMessageList
implements MessageList {
    private final LinkedList<MessageReference> list = new LinkedList();
    private int maximumSize = 0x640000;
    private int size;
    private final Object lock = new Object();

    public SimpleMessageList() {
    }

    public SimpleMessageList(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void add(MessageReference node) {
        int delta = node.getMessageHardRef().getSize();
        Object object = this.lock;
        synchronized (object) {
            this.list.add(node);
            this.size += delta;
            while (this.size > this.maximumSize) {
                MessageReference evicted = this.list.removeFirst();
                this.size -= evicted.getMessageHardRef().getSize();
            }
        }
    }

    @Override
    public List<MessageReference> getMessages(ActiveMQDestination destination) {
        return this.getList();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Message[] browse(ActiveMQDestination destination) {
        ArrayList<Message> result = new ArrayList<Message>();
        DestinationFilter filter2 = DestinationFilter.parseFilter(destination);
        Object object = this.lock;
        synchronized (object) {
            for (MessageReference ref : this.list) {
                Message msg = ref.getMessage();
                if (!filter2.matches(msg.getDestination())) continue;
                result.add(msg);
            }
        }
        return result.toArray(new Message[result.size()]);
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
    public void clear() {
        Object object = this.lock;
        synchronized (object) {
            this.list.clear();
            this.size = 0;
        }
    }
}

