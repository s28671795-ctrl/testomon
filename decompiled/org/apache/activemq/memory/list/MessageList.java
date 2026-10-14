/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.memory.list;

import java.util.List;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;

public interface MessageList {
    public void add(MessageReference var1);

    public List getMessages(ActiveMQDestination var1);

    public Message[] browse(ActiveMQDestination var1);

    public void clear();
}

