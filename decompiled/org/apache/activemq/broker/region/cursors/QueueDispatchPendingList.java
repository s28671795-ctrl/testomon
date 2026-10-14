/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.cursors.OrderedPendingList;
import org.apache.activemq.broker.region.cursors.PendingList;
import org.apache.activemq.broker.region.cursors.PendingNode;
import org.apache.activemq.broker.region.cursors.PrioritizedPendingList;
import org.apache.activemq.command.MessageId;

public class QueueDispatchPendingList
implements PendingList {
    private PendingList pagedInPendingDispatch = new OrderedPendingList();
    private PendingList redeliveredWaitingDispatch = new OrderedPendingList();
    private boolean prioritized = false;

    @Override
    public boolean isEmpty() {
        return this.pagedInPendingDispatch.isEmpty() && this.redeliveredWaitingDispatch.isEmpty();
    }

    @Override
    public void clear() {
        this.pagedInPendingDispatch.clear();
        this.redeliveredWaitingDispatch.clear();
    }

    @Override
    public PendingNode addMessageFirst(MessageReference message) {
        return this.pagedInPendingDispatch.addMessageFirst(message);
    }

    @Override
    public PendingNode addMessageLast(MessageReference message) {
        return this.pagedInPendingDispatch.addMessageLast(message);
    }

    @Override
    public PendingNode remove(MessageReference message) {
        if (this.pagedInPendingDispatch.contains(message)) {
            return this.pagedInPendingDispatch.remove(message);
        }
        if (this.redeliveredWaitingDispatch.contains(message)) {
            return this.redeliveredWaitingDispatch.remove(message);
        }
        return null;
    }

    @Override
    public int size() {
        return this.pagedInPendingDispatch.size() + this.redeliveredWaitingDispatch.size();
    }

    @Override
    public long messageSize() {
        return this.pagedInPendingDispatch.messageSize() + this.redeliveredWaitingDispatch.messageSize();
    }

    @Override
    public Iterator<MessageReference> iterator() {
        if (this.prioritized && this.hasRedeliveries()) {
            final QueueDispatchPendingList delegate = this;
            final PrioritizedPendingList priorityOrderedRedeliveredAndPending = new PrioritizedPendingList();
            priorityOrderedRedeliveredAndPending.addAll(this.redeliveredWaitingDispatch);
            priorityOrderedRedeliveredAndPending.addAll(this.pagedInPendingDispatch);
            return new Iterator<MessageReference>(){
                Iterator<MessageReference> combinedIterator;
                MessageReference current;
                {
                    this.combinedIterator = priorityOrderedRedeliveredAndPending.iterator();
                    this.current = null;
                }

                @Override
                public boolean hasNext() {
                    return this.combinedIterator.hasNext();
                }

                @Override
                public MessageReference next() {
                    this.current = this.combinedIterator.next();
                    return this.current;
                }

                @Override
                public void remove() {
                    if (this.current != null) {
                        delegate.remove(this.current);
                    }
                }
            };
        }
        return new Iterator<MessageReference>(){
            Iterator<MessageReference> redeliveries;
            Iterator<MessageReference> pendingDispatch;
            Iterator<MessageReference> current;
            {
                this.redeliveries = QueueDispatchPendingList.this.redeliveredWaitingDispatch.iterator();
                this.pendingDispatch = QueueDispatchPendingList.this.pagedInPendingDispatch.iterator();
                this.current = this.redeliveries;
            }

            @Override
            public boolean hasNext() {
                if (!this.redeliveries.hasNext() && this.current == this.redeliveries) {
                    this.current = this.pendingDispatch;
                }
                return this.current.hasNext();
            }

            @Override
            public MessageReference next() {
                return this.current.next();
            }

            @Override
            public void remove() {
                this.current.remove();
            }
        };
    }

    @Override
    public boolean contains(MessageReference message) {
        return this.pagedInPendingDispatch.contains(message) || this.redeliveredWaitingDispatch.contains(message);
    }

    @Override
    public Collection<MessageReference> values() {
        ArrayList<MessageReference> messageReferences = new ArrayList<MessageReference>();
        Iterator<MessageReference> iterator2 = this.iterator();
        while (iterator2.hasNext()) {
            messageReferences.add(iterator2.next());
        }
        return messageReferences;
    }

    @Override
    public void addAll(PendingList pendingList) {
        this.pagedInPendingDispatch.addAll(pendingList);
    }

    @Override
    public MessageReference get(MessageId messageId) {
        MessageReference rc = this.pagedInPendingDispatch.get(messageId);
        if (rc == null) {
            return this.redeliveredWaitingDispatch.get(messageId);
        }
        return rc;
    }

    public void setPrioritizedMessages(boolean prioritizedMessages) {
        this.prioritized = prioritizedMessages;
        if (prioritizedMessages && this.pagedInPendingDispatch instanceof OrderedPendingList) {
            this.pagedInPendingDispatch = new PrioritizedPendingList();
            this.redeliveredWaitingDispatch = new PrioritizedPendingList();
        } else if (this.pagedInPendingDispatch instanceof PrioritizedPendingList) {
            this.pagedInPendingDispatch = new OrderedPendingList();
            this.redeliveredWaitingDispatch = new OrderedPendingList();
        }
    }

    public boolean hasRedeliveries() {
        return !this.redeliveredWaitingDispatch.isEmpty();
    }

    public void addForRedelivery(List<MessageReference> list, boolean noConsumers) {
        if (noConsumers && this.redeliveredWaitingDispatch instanceof OrderedPendingList && this.willBeInOrder(list)) {
            ((OrderedPendingList)this.redeliveredWaitingDispatch).insertAtHead(list);
        } else {
            for (MessageReference ref : list) {
                this.redeliveredWaitingDispatch.addMessageLast(ref);
            }
        }
    }

    private boolean willBeInOrder(List<MessageReference> list) {
        return !this.redeliveredWaitingDispatch.isEmpty() && list != null && !list.isEmpty() && this.redeliveredWaitingDispatch.iterator().next().getMessageId().getBrokerSequenceId() > list.get(list.size() - 1).getMessageId().getBrokerSequenceId();
    }
}

