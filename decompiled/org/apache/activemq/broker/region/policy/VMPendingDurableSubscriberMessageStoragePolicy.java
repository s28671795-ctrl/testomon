/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.cursors.AbstractPendingMessageCursor;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.cursors.VMPendingMessageCursor;
import org.apache.activemq.broker.region.policy.PendingDurableSubscriberMessageStoragePolicy;

public class VMPendingDurableSubscriberMessageStoragePolicy
implements PendingDurableSubscriberMessageStoragePolicy {
    @Override
    public PendingMessageCursor getSubscriberPendingMessageCursor(Broker broker, String clientId, String name, int maxBatchSize, DurableTopicSubscription sub) {
        return new VMPendingMessageCursor(AbstractPendingMessageCursor.isPrioritizedMessageSubscriber(broker, sub));
    }
}

