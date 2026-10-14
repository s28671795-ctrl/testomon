/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.TopicSubscription;

public interface PendingMessageLimitStrategy {
    public int getMaximumPendingMessageLimit(TopicSubscription var1);
}

