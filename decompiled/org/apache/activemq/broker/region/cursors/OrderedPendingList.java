/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.cursors.PendingList;
import org.apache.activemq.broker.region.cursors.PendingMessageHelper;
import org.apache.activemq.broker.region.cursors.PendingNode;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.management.SizeStatisticImpl;
import org.apache.activemq.util.LinkedNode;

public class OrderedPendingList
implements PendingList {
    private PendingNode root = null;
    private PendingNode tail = null;
    private final Map<MessageId, PendingNode> map = new HashMap<MessageId, PendingNode>();
    private final SizeStatisticImpl messageSize = new SizeStatisticImpl("messageSize", "The size in bytes of the pending messages");
    private final PendingMessageHelper pendingMessageHelper;

    public OrderedPendingList() {
        this.messageSize.setEnabled(true);
        this.pendingMessageHelper = new PendingMessageHelper(this.map, this.messageSize);
    }

    @Override
    public PendingNode addMessageFirst(MessageReference message) {
        PendingNode node = new PendingNode(this, message);
        if (this.root == null) {
            this.root = node;
            this.tail = node;
        } else {
            this.root.linkBefore(node);
            this.root = node;
        }
        this.pendingMessageHelper.addToMap(message, node);
        return node;
    }

    @Override
    public PendingNode addMessageLast(MessageReference message) {
        PendingNode node = new PendingNode(this, message);
        if (this.root == null) {
            this.root = node;
        } else {
            this.tail.linkAfter(node);
        }
        this.tail = node;
        this.pendingMessageHelper.addToMap(message, node);
        return node;
    }

    @Override
    public void clear() {
        this.root = null;
        this.tail = null;
        this.map.clear();
        this.messageSize.reset();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public Iterator<MessageReference> iterator() {
        return new Iterator<MessageReference>(){
            private PendingNode current = null;
            private PendingNode next = OrderedPendingList.access$000(OrderedPendingList.this);

            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public MessageReference next() {
                MessageReference result = null;
                this.current = this.next;
                result = this.current.getMessage();
                this.next = (PendingNode)this.next.getNext();
                return result;
            }

            @Override
            public void remove() {
                if (this.current != null && this.current.getMessage() != null) {
                    OrderedPendingList.this.pendingMessageHelper.removeFromMap(this.current.getMessage());
                }
                OrderedPendingList.this.removeNode(this.current);
            }
        };
    }

    @Override
    public PendingNode remove(MessageReference message) {
        PendingNode node = null;
        if (message != null) {
            node = this.pendingMessageHelper.removeFromMap(message);
            this.removeNode(node);
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

    void removeNode(PendingNode node) {
        if (node != null) {
            this.pendingMessageHelper.removeFromMap(node.getMessage());
            if (this.root == node) {
                this.root = (PendingNode)node.getNext();
            }
            if (this.tail == node) {
                this.tail = (PendingNode)node.getPrevious();
            }
            node.unlink();
        }
    }

    List<PendingNode> getAsList() {
        ArrayList<PendingNode> result = new ArrayList<PendingNode>(this.size());
        for (PendingNode node = this.root; node != null; node = (PendingNode)node.getNext()) {
            result.add(node);
        }
        return result;
    }

    public String toString() {
        return "OrderedPendingList(" + System.identityHashCode(this) + ")";
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

    public static Collection<MessageReference> getValues(PendingList pendingList) {
        ArrayList<MessageReference> messageReferences = new ArrayList<MessageReference>();
        Iterator<MessageReference> iterator2 = pendingList.iterator();
        while (iterator2.hasNext()) {
            messageReferences.add(iterator2.next());
        }
        return messageReferences;
    }

    @Override
    public void addAll(PendingList pendingList) {
        if (pendingList != null) {
            for (MessageReference messageReference : pendingList) {
                this.addMessageLast(messageReference);
            }
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

    public void insertAtHead(List<MessageReference> list) {
        if (list != null && !list.isEmpty()) {
            PendingNode newHead = null;
            LinkedNode appendNode = null;
            for (MessageReference ref : list) {
                PendingNode node = new PendingNode(this, ref);
                this.pendingMessageHelper.addToMap(ref, node);
                if (newHead == null) {
                    newHead = node;
                    appendNode = node;
                    continue;
                }
                appendNode.linkAfter(node);
                appendNode = node;
            }
            if (this.root == null) {
                this.root = newHead;
                this.tail = appendNode;
            } else {
                appendNode.linkAfter(this.root);
                this.root = newHead;
            }
        }
    }

    static /* synthetic */ PendingNode access$000(OrderedPendingList x0) {
        return x0.root;
    }
}

