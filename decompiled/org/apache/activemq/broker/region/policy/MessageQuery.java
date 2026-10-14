/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import javax.jms.MessageListener;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;

public interface MessageQuery {
    public void execute(ActiveMQDestination var1, MessageListener var2) throws Exception;

    public boolean validateUpdate(Message var1);
}

