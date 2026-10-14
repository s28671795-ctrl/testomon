/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import org.apache.activemq.broker.jmx.AbortSlowAckConsumerStrategyViewMBean;
import org.apache.activemq.broker.jmx.AbortSlowConsumerStrategyView;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.region.policy.AbortSlowAckConsumerStrategy;

public class AbortSlowAckConsumerStrategyView
extends AbortSlowConsumerStrategyView
implements AbortSlowAckConsumerStrategyViewMBean {
    private final AbortSlowAckConsumerStrategy strategy;

    public AbortSlowAckConsumerStrategyView(ManagedRegionBroker managedRegionBroker, AbortSlowAckConsumerStrategy slowConsumerStrategy) {
        super(managedRegionBroker, slowConsumerStrategy);
        this.strategy = slowConsumerStrategy;
    }

    @Override
    public long getMaxTimeSinceLastAck() {
        return this.strategy.getMaxTimeSinceLastAck();
    }

    @Override
    public void setMaxTimeSinceLastAck(long maxTimeSinceLastAck) {
        this.strategy.setMaxTimeSinceLastAck(maxTimeSinceLastAck);
    }

    @Override
    public boolean isIgnoreIdleConsumers() {
        return this.strategy.isIgnoreIdleConsumers();
    }

    @Override
    public void setIgnoreIdleConsumers(boolean ignoreIdleConsumers) {
        this.strategy.setIgnoreIdleConsumers(ignoreIdleConsumers);
    }

    @Override
    public boolean isIgnoreNetworkConsumers() {
        return this.strategy.isIgnoreNetworkSubscriptions();
    }

    @Override
    public void setIgnoreNetworkConsumers(boolean ignoreNetworkConsumers) {
        this.strategy.setIgnoreNetworkConsumers(ignoreNetworkConsumers);
    }
}

