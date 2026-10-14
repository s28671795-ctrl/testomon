/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;

public interface PendingDurableSubscriberMessageStoragePolicy {
    public PendingMessageCursor getSubscriberPendingMessageCursor(Broker var1, String var2, String var3, int var4, DurableTopicSubscription var5);
}

