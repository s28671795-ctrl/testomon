/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.cursors.OrderedPendingList;
import org.apache.activemq.broker.region.cursors.PendingList;
import org.apache.activemq.broker.region.cursors.PendingMessageHelper;
import org.apache.activemq.broker.region.cursors.PendingNode;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.management.SizeStatisticImpl;

public class PrioritizedPendingList
implements PendingList {
    private static final Integer MAX_PRIORITY = 10;
    private final OrderedPendingList[] lists = new OrderedPendingList[MAX_PRIORITY.intValue()];
    private final Map<MessageId, PendingNode> map = new HashMap<MessageId, PendingNode>();
    private final SizeStatisticImpl messageSize;
    private final PendingMessageHelper pendingMessageHelper;

    public PrioritizedPendingList() {
        for (int i = 0; i < MAX_PRIORITY; ++i) {
            this.lists[i] = new OrderedPendingList();
        }
        this.messageSize = new SizeStatisticImpl("messageSize", "The size in bytes of the pending messages");
        this.messageSize.setEnabled(true);
        this.pendingMessageHelper = new PendingMessageHelper(this.map, this.messageSize);
    }

    @Override
    public PendingNode addMessageFirst(MessageReference message) {
        PendingNode node = this.getList(message).addMessageFirst(message);
        this.pendingMessageHelper.addToMap(message, node);
        return node;
    }

    @Override
    public PendingNode addMessageLast(MessageReference message) {
        PendingNode node = this.getList(message).addMessageLast(message);
        this.pendingMessageHelper.addToMap(message, node);
        return node;
    }

    @Override
    public void clear() {
        for (int i = 0; i < MAX_PRIORITY; ++i) {
            this.lists[i].clear();
        }
        this.map.clear();
        this.messageSize.reset();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Iterator<MessageReference> iterator() {
        return new PrioritizedPendingListIterator();
    }

    @Override
    public PendingNode remove(MessageReference message) {
        PendingNode node = null;
        if (message != null && (node = this.pendingMessageHelper.removeFromMap(message)) != null) {
            node.getList().removeNode(node);
        }
        return node;
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public long messageSize() {
        return this.messageSize.getTotalSize();
    }

    public String toString() {
        return "PrioritizedPendingList(" + System.identityHashCode(this) + ")";
    }

    protected int getPriority(MessageReference message) {
        int priority = 4;
        if (message.getMessageId() != null) {
            priority = Math.max(message.getMessage().getPriority(), 0);
            priority = Math.min(priority, 9);
        }
        return priority;
    }

    protected OrderedPendingList getList(MessageReference msg) {
        return this.lists[this.getPriority(msg)];
    }

    @Override
    public boolean contains(MessageReference message) {
        if (message != null) {
            return this.map.containsKey(message.getMessageId());
        }
        return false;
    }

    @Override
    public Collection<MessageReference> values() {
        return OrderedPendingList.getValues(this);
    }

    @Override
    public void addAll(PendingList pendingList) {
        for (MessageReference messageReference : pendingList) {
            this.addMessageLast(messageReference);
        }
    }

    @Override
    public MessageReference get(MessageId messageId) {
        PendingNode node = this.map.get(messageId);
        if (node != null) {
            return node.getMessage();
        }
        return null;
    }

    private final class PrioritizedPendingListIterator
    implements Iterator<MessageReference> {
        private final Deque<Iterator<MessageReference>> iterators = new ArrayDeque<Iterator<MessageReference>>();
        private Iterator<MessageReference> current;
        private MessageReference currentMessage;

        PrioritizedPendingListIterator() {
            for (OrderedPendingList list : PrioritizedPendingList.this.lists) {
                if (list.isEmpty()) continue;
                this.iterators.push(list.iterator());
            }
            this.current = this.iterators.poll();
        }

        @Override
        public boolean hasNext() {
            while (this.current != null) {
                if (this.current.hasNext()) {
                    return true;
                }
                this.current = this.iterators.poll();
            }
            return false;
        }

        @Override
        public MessageReference next() {
            MessageReference result = null;
            while (this.current != null) {
                if (this.current.hasNext()) {
                    result = this.currentMessage = this.current.next();
                    break;
                }
                this.current = this.iterators.poll();
            }
            return result;
        }

        @Override
        public void remove() {
            if (this.currentMessage != null) {
                PrioritizedPendingList.this.pendingMessageHelper.removeFromMap(this.currentMessage);
                this.current.remove();
                this.currentMessage = null;
            }
        }
    }
}

