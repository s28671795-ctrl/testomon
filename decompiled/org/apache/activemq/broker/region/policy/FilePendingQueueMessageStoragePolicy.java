/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.Queue;
import org.apache.activemq.broker.region.cursors.FilePendingMessageCursor;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.policy.PendingQueueMessageStoragePolicy;

public class FilePendingQueueMessageStoragePolicy
implements PendingQueueMessageStoragePolicy {
    @Override
    public PendingMessageCursor getQueuePendingMessageCursor(Broker broker, Queue queue) {
        return new FilePendingMessageCursor(broker, "PendingCursor:" + queue.getName(), queue.isPrioritizedMessages());
    }
}

