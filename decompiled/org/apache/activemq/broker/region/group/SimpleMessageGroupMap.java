/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.group.MessageGroupMap;
import org.apache.activemq.broker.region.group.MessageGroupSet;
import org.apache.activemq.broker.region.group.SimpleMessageGroupSet;
import org.apache.activemq.command.ConsumerId;

public class SimpleMessageGroupMap
implements MessageGroupMap {
    private Map<String, ConsumerId> map = new ConcurrentHashMap<String, ConsumerId>();

    @Override
    public void put(String groupId, ConsumerId consumerId) {
        this.map.put(groupId, consumerId);
    }

    @Override
    public ConsumerId get(String groupId) {
        return this.map.get(groupId);
    }

    @Override
    public ConsumerId removeGroup(String groupId) {
        return this.map.remove(groupId);
    }

    @Override
    public MessageGroupSet removeConsumer(ConsumerId consumerId) {
        SimpleMessageGroupSet ownedGroups = new SimpleMessageGroupSet();
        Iterator<String> iter = this.map.keySet().iterator();
        while (iter.hasNext()) {
            String group = iter.next();
            ConsumerId owner = this.map.get(group);
            if (!owner.equals(consumerId)) continue;
            ownedGroups.add(group);
            iter.remove();
        }
        return ownedGroups;
    }

    @Override
    public void removeAll() {
        this.map.clear();
    }

    @Override
    public Map<String, String> getGroups() {
        HashMap<String, String> result = new HashMap<String, String>();
        for (Map.Entry<String, ConsumerId> entry : this.map.entrySet()) {
            result.put(entry.getKey(), entry.getValue().toString());
        }
        return result;
    }

    @Override
    public String getType() {
        return "simple";
    }

    @Override
    public void setDestination(Destination destination) {
    }

    public String toString() {
        return "message groups: " + this.map.size();
    }
}

