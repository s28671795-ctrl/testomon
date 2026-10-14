/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.QueueMessageReference;
import org.apache.activemq.broker.region.cursors.AbstractPendingMessageCursor;
import org.apache.activemq.broker.region.cursors.OrderedPendingList;
import org.apache.activemq.broker.region.cursors.PendingList;
import org.apache.activemq.broker.region.cursors.PrioritizedPendingList;

public class VMPendingMessageCursor
extends AbstractPendingMessageCursor {
    private final PendingList list;
    private Iterator<MessageReference> iter;

    public VMPendingMessageCursor(boolean prioritizedMessages) {
        super(prioritizedMessages);
        this.list = this.prioritizedMessages ? new PrioritizedPendingList() : new OrderedPendingList();
    }

    @Override
    public synchronized List<MessageReference> remove(ConnectionContext context, Destination destination) throws Exception {
        ArrayList<MessageReference> rc = new ArrayList<MessageReference>();
        Iterator<MessageReference> iterator2 = this.list.iterator();
        while (iterator2.hasNext()) {
            MessageReference r = iterator2.next();
            if (r.getRegionDestination() != destination) continue;
            r.decrementReferenceCount();
            rc.add(r);
            iterator2.remove();
        }
        return rc;
    }

    @Override
    public synchronized boolean isEmpty() {
        if (this.list.isEmpty()) {
            return true;
        }
        Iterator<MessageReference> iterator2 = this.list.iterator();
        while (iterator2.hasNext()) {
            MessageReference node = iterator2.next();
            if (node == QueueMessageReference.NULL_MESSAGE) continue;
            if (!node.isDropped()) {
                return false;
            }
            iterator2.remove();
        }
        return true;
    }

    @Override
    public synchronized void reset() {
        this.iter = this.list.iterator();
        this.last = null;
    }

    @Override
    public synchronized boolean tryAddMessageLast(MessageReference node, long maxWait) {
        node.incrementReferenceCount();
        this.list.addMessageLast(node);
        return true;
    }

    @Override
    public synchronized void addMessageFirst(MessageReference node) {
        node.incrementReferenceCount();
        this.list.addMessageFirst(node);
    }

    @Override
    public synchronized boolean hasNext() {
        return this.iter.hasNext();
    }

    @Override
    public synchronized MessageReference next() {
        this.last = this.iter.next();
        if (this.last != null) {
            this.last.incrementReferenceCount();
        }
        return this.last;
    }

    @Override
    public synchronized void remove() {
        if (this.last != null) {
            this.last.decrementReferenceCount();
        }
        this.iter.remove();
    }

    @Override
    public synchronized int size() {
        return this.list.size();
    }

    @Override
    public synchronized long messageSize() {
        return this.list.messageSize();
    }

    @Override
    public synchronized void clear() {
        for (MessageReference ref : this.list) {
            ref.decrementReferenceCount();
        }
        this.list.clear();
    }

    @Override
    public synchronized void remove(MessageReference node) {
        this.list.remove(node);
        node.decrementReferenceCount();
    }

    @Override
    public LinkedList<MessageReference> pageInList(int maxItems) {
        LinkedList<MessageReference> result = new LinkedList<MessageReference>();
        for (MessageReference ref : this.list) {
            ref.incrementReferenceCount();
            result.add(ref);
            if (result.size() < maxItems) continue;
            break;
        }
        return result;
    }

    @Override
    public boolean isTransient() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        super.destroy();
        this.clear();
    }
}

