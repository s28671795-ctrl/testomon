/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import org.apache.activemq.broker.Broker;

public interface BrokerPlugin {
    public Broker installPlugin(Broker var1) throws Exception;
}

