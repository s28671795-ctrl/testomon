/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import org.apache.activemq.broker.BrokerContext;

public interface BrokerContextAware {
    public void setBrokerContext(BrokerContext var1);

    public BrokerContext getBrokerContext();
}

