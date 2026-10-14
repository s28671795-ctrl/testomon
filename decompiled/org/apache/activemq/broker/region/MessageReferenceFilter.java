/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import javax.jms.JMSException;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.MessageReference;

public interface MessageReferenceFilter {
    public boolean evaluate(ConnectionContext var1, MessageReference var2) throws JMSException;
}

