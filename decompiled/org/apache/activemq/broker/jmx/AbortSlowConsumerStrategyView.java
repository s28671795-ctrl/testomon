/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.jmx;

import java.util.Map;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;
import org.apache.activemq.broker.jmx.AbortSlowConsumerStrategyViewMBean;
import org.apache.activemq.broker.jmx.ManagedRegionBroker;
import org.apache.activemq.broker.jmx.OpenTypeSupport;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.AbortSlowConsumerStrategy;
import org.apache.activemq.broker.region.policy.SlowConsumerEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbortSlowConsumerStrategyView
implements AbortSlowConsumerStrategyViewMBean {
    private static final Logger LOG = LoggerFactory.getLogger(AbortSlowConsumerStrategyView.class);
    private ManagedRegionBroker broker;
    private AbortSlowConsumerStrategy strategy;

    public AbortSlowConsumerStrategyView(ManagedRegionBroker managedRegionBroker, AbortSlowConsumerStrategy slowConsumerStrategy) {
        this.broker = managedRegionBroker;
        this.strategy = slowConsumerStrategy;
    }

    @Override
    public long getMaxSlowCount() {
        return this.strategy.getMaxSlowCount();
    }

    @Override
    public void setMaxSlowCount(long maxSlowCount) {
        this.strategy.setMaxSlowCount(maxSlowCount);
    }

    @Override
    public long getMaxSlowDuration() {
        return this.strategy.getMaxSlowDuration();
    }

    @Override
    public void setMaxSlowDuration(long maxSlowDuration) {
        this.strategy.setMaxSlowDuration(maxSlowDuration);
    }

    @Override
    public long getCheckPeriod() {
        return this.strategy.getCheckPeriod();
    }

    @Override
    public TabularData getSlowConsumers() throws OpenDataException {
        OpenTypeSupport.OpenTypeFactory factory2 = OpenTypeSupport.getFactory(SlowConsumerEntry.class);
        CompositeType ct = factory2.getCompositeType();
        TabularType tt = new TabularType("SlowConsumers", "Table of current slow Consumers", ct, new String[]{"subscription"});
        TabularDataSupport rc = new TabularDataSupport(tt);
        boolean index = false;
        Map<Subscription, SlowConsumerEntry> slowConsumers = this.strategy.getSlowConsumers();
        for (Map.Entry<Subscription, SlowConsumerEntry> entry : slowConsumers.entrySet()) {
            entry.getValue().setSubscription(this.broker.getSubscriberObjectName(entry.getKey()));
            rc.put(OpenTypeSupport.convert(entry.getValue()));
        }
        return rc;
    }

    @Override
    public void abortConsumer(ObjectName consumerToAbort) {
        Subscription sub = this.broker.getSubscriber(consumerToAbort);
        if (sub != null) {
            LOG.info("aborting consumer via jmx: {}", (Object)sub.getConsumerInfo().getConsumerId());
            this.strategy.abortConsumer(sub, false);
        } else {
            LOG.warn("cannot resolve subscription matching name: {}", (Object)consumerToAbort);
        }
    }

    @Override
    public void abortConnection(ObjectName consumerToAbort) {
        Subscription sub = this.broker.getSubscriber(consumerToAbort);
        if (sub != null) {
            LOG.info("aborting consumer connection via jmx: {}", (Object)sub.getConsumerInfo().getConsumerId().getConnectionId());
            this.strategy.abortConsumer(sub, true);
        } else {
            LOG.warn("cannot resolve subscription matching name: {}", (Object)consumerToAbort);
        }
    }

    @Override
    public void abortConsumer(String objectNameOfConsumerToAbort) {
        this.abortConsumer(this.toObjectName(objectNameOfConsumerToAbort));
    }

    @Override
    public void abortConnection(String objectNameOfConsumerToAbort) {
        this.abortConnection(this.toObjectName(objectNameOfConsumerToAbort));
    }

    private ObjectName toObjectName(String objectName) {
        ObjectName result = null;
        try {
            result = new ObjectName(objectName);
        }
        catch (Exception e) {
            LOG.warn("cannot create subscription ObjectName to abort, from string: {}", (Object)objectName);
        }
        return result;
    }
}

