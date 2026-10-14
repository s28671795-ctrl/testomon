/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.region.TopicSubscription;
import org.apache.activemq.broker.region.policy.PendingMessageLimitStrategy;

public class PrefetchRatePendingMessageLimitStrategy
implements PendingMessageLimitStrategy {
    private double multiplier = 0.5;

    @Override
    public int getMaximumPendingMessageLimit(TopicSubscription subscription) {
        int prefetchSize = subscription.getConsumerInfo().getPrefetchSize();
        return (int)((double)prefetchSize * this.multiplier);
    }

    public double getMultiplier() {
        return this.multiplier;
    }

    public void setMultiplier(double rate) {
        this.multiplier = rate;
    }
}

