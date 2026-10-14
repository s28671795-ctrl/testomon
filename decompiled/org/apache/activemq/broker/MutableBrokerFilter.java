/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.concurrent.atomic.AtomicReference;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.BrokerFilter;

public class MutableBrokerFilter
extends BrokerFilter {
    protected AtomicReference<Broker> next = new AtomicReference();

    public MutableBrokerFilter(Broker next) {
        super(null);
        this.next.set(next);
    }

    @Override
    public Broker getAdaptor(Class<?> type) {
        if (type.isInstance(this)) {
            return this;
        }
        return this.next.get().getAdaptor(type);
    }

    @Override
    public Broker getNext() {
        return this.next.get();
    }

    public void setNext(Broker next) {
        this.next.set(next);
    }
}

