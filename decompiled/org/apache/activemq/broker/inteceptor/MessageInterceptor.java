/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.inteceptor;

import org.apache.activemq.broker.ProducerBrokerExchange;
import org.apache.activemq.command.Message;

public interface MessageInterceptor {
    public void intercept(ProducerBrokerExchange var1, Message var2);
}

