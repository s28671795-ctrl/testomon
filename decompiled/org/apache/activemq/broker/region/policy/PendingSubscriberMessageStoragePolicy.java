/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;

public interface PendingSubscriberMessageStoragePolicy {
    public PendingMessageCursor getSubscriberPendingMessageCursor(Broker var1, String var2, int var3, Subscription var4);
}

