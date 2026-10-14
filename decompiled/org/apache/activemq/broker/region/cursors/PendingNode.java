/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.cursors.OrderedPendingList;
import org.apache.activemq.util.LinkedNode;

public class PendingNode
extends LinkedNode {
    private final MessageReference message;
    private final OrderedPendingList list;

    public PendingNode(OrderedPendingList list, MessageReference message) {
        this.list = list;
        this.message = message;
    }

    MessageReference getMessage() {
        return this.message;
    }

    OrderedPendingList getList() {
        return this.list;
    }

    public String toString() {
        PendingNode n = (PendingNode)this.getNext();
        String str = "PendingNode(";
        str = str + System.identityHashCode(this) + "),root=" + this.isHeadNode() + ",next=" + (n != null ? Integer.valueOf(System.identityHashCode(n)) : "NULL");
        return str;
    }
}

