/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.net.URI;
import org.apache.activemq.broker.BrokerService;

public interface BrokerFactoryHandler {
    public BrokerService createBroker(URI var1) throws Exception;
}

