/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;

public interface PendingQueueMessageStoragePolicy {
    public PendingMessageCursor getQueuePendingMessageCursor(Broker var1, Queue var2);
}

