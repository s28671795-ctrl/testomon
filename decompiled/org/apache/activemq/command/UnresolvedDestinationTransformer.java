/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import javax.jms.Destination;
import javax.jms.JMSException;
import org.apache.activemq.command.ActiveMQDestination;

public interface UnresolvedDestinationTransformer {
    public ActiveMQDestination transform(Destination var1) throws JMSException;

    public ActiveMQDestination transform(String var1) throws JMSException;
}

