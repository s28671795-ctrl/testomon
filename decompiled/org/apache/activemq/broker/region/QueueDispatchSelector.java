/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.policy.SimpleDispatchSelector;
import org.apache.activemq.command.ActiveMQDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueueDispatchSelector
extends SimpleDispatchSelector {
    private static final Logger LOG = LoggerFactory.getLogger(QueueDispatchSelector.class);
    private Subscription exclusiveConsumer;
    private boolean paused;

    public QueueDispatchSelector(ActiveMQDestination destination) {
        super(destination);
    }

    public Subscription getExclusiveConsumer() {
        return this.exclusiveConsumer;
    }

    public void setExclusiveConsumer(Subscription exclusiveConsumer) {
        this.exclusiveConsumer = exclusiveConsumer;
    }

    public boolean isExclusiveConsumer(Subscription s2) {
        return s2 == this.exclusiveConsumer;
    }

    public boolean canSelect(Subscription subscription, MessageReference m4) throws Exception {
        boolean result;
        boolean bl = result = !this.paused && super.canDispatch(subscription, m4);
        if (result && !subscription.isBrowser()) {
            result = this.exclusiveConsumer == null || this.exclusiveConsumer == subscription;
        }
        return result;
    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
    }

    public boolean isPaused() {
        return this.paused;
    }
}

