/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import java.util.Map;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.group.MessageGroupSet;
import org.apache.activemq.command.ConsumerId;

public interface MessageGroupMap {
    public void put(String var1, ConsumerId var2);

    public ConsumerId get(String var1);

    public ConsumerId removeGroup(String var1);

    public MessageGroupSet removeConsumer(ConsumerId var1);

    public void removeAll();

    public Map<String, String> getGroups();

    public String getType();

    public void setDestination(Destination var1);
}

