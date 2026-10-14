/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.region.DurableTopicSubscription;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.broker.region.cursors.StoreDurableSubscriberCursor;
import org.apache.activemq.broker.region.policy.PendingDurableSubscriberMessageStoragePolicy;

public class StorePendingDurableSubscriberMessageStoragePolicy
implements PendingDurableSubscriberMessageStoragePolicy {
    boolean immediatePriorityDispatch = true;
    boolean useCache = true;

    public boolean isImmediatePriorityDispatch() {
        return this.immediatePriorityDispatch;
    }

    public void setImmediatePriorityDispatch(boolean immediatePriorityDispatch) {
        this.immediatePriorityDispatch = immediatePriorityDispatch;
    }

    public boolean isUseCache() {
        return this.useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    @Override
    public PendingMessageCursor getSubscriberPendingMessageCursor(Broker broker, String clientId, String name, int maxBatchSize, DurableTopicSubscription sub) {
        StoreDurableSubscriberCursor cursor = new StoreDurableSubscriberCursor(broker, clientId, name, maxBatchSize, sub);
        cursor.setUseCache(this.isUseCache());
        cursor.setImmediatePriorityDispatch(this.isImmediatePriorityDispatch());
        return cursor;
    }
}

