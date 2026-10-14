/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.cursors.AbstractPendingMessageCursor;
import org.apache.activemq.broker.region.cursors.FilePendingMessageCursor;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.policy.PendingSubscriberMessageStoragePolicy;

public class FilePendingSubscriberMessageStoragePolicy
implements PendingSubscriberMessageStoragePolicy {
    @Override
    public PendingMessageCursor getSubscriberPendingMessageCursor(Broker broker, String name, int maxBatchSize, Subscription subs) {
        return new FilePendingMessageCursor(broker, "PendingCursor:" + name, AbstractPendingMessageCursor.isPrioritizedMessageSubscriber(broker, subs));
    }
}

