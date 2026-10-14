/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.TopicSubscription;
import org.apache.activemq.broker.region.policy.PendingMessageLimitStrategy;

public class ConstantPendingMessageLimitStrategy
implements PendingMessageLimitStrategy {
    private int limit = -1;

    @Override
    public int getMaximumPendingMessageLimit(TopicSubscription subscription) {
        return this.limit;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

