/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.broker.ConnectionContext;

public class SlowConsumerEntry {
    final ConnectionContext context;
    Object subscription;
    int slowCount = 1;
    int markCount = 0;

    SlowConsumerEntry(ConnectionContext context) {
        this.context = context;
    }

    public void slow() {
        ++this.slowCount;
    }

    public void mark() {
        ++this.markCount;
    }

    public void setSubscription(Object subscriptionObjectName) {
        this.subscription = subscriptionObjectName;
    }

    public Object getSubscription() {
        return this.subscription;
    }

    public int getSlowCount() {
        return this.slowCount;
    }

    public int getMarkCount() {
        return this.markCount;
    }
}

