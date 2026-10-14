/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import java.util.HashMap;
import java.util.Map;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.group.MessageGroupMap;
import org.apache.activemq.broker.region.group.MessageGroupSet;
import org.apache.activemq.broker.region.group.SimpleMessageGroupSet;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.memory.LRUMap;

public class CachedMessageGroupMap
implements MessageGroupMap {
    private final LRUMap<String, ConsumerId> cache;
    private final int maximumCacheSize;
    Destination destination;

    CachedMessageGroupMap(int size) {
        this.cache = new LRUMap<String, ConsumerId>(size){

            @Override
            public boolean removeEldestEntry(Map.Entry eldest) {
                boolean remove = super.removeEldestEntry(eldest);
                if (remove && CachedMessageGroupMap.this.destination != null) {
                    for (Subscription s2 : CachedMessageGroupMap.this.destination.getConsumers()) {
                        if (!s2.getConsumerInfo().getConsumerId().equals(eldest.getValue())) continue;
                        s2.getConsumerInfo().decrementAssignedGroupCount(CachedMessageGroupMap.this.destination.getActiveMQDestination());
                        break;
                    }
                }
                return remove;
            }
        };
        this.maximumCacheSize = size;
    }

    @Override
    public synchronized void put(String groupId, ConsumerId consumerId) {
        this.cache.put(groupId, consumerId);
    }

    @Override
    public synchronized ConsumerId get(String groupId) {
        return (ConsumerId)this.cache.get(groupId);
    }

    @Override
    public synchronized ConsumerId removeGroup(String groupId) {
        return (ConsumerId)this.cache.remove(groupId);
    }

    @Override
    public synchronized MessageGroupSet removeConsumer(ConsumerId consumerId) {
        SimpleMessageGroupSet ownedGroups = new SimpleMessageGroupSet();
        HashMap<String, ConsumerId> map = new HashMap<String, ConsumerId>();
        map.putAll(this.cache);
        for (String group : map.keySet()) {
            ConsumerId owner = (ConsumerId)map.get(group);
            if (!owner.equals(consumerId)) continue;
            ownedGroups.add(group);
        }
        for (String group : ownedGroups.getUnderlyingSet()) {
            this.cache.remove(group);
        }
        return ownedGroups;
    }

    @Override
    public synchronized void removeAll() {
        this.cache.clear();
        if (this.destination != null) {
            for (Subscription s2 : this.destination.getConsumers()) {
                s2.getConsumerInfo().clearAssignedGroupCount(this.destination.getActiveMQDestination());
            }
        }
    }

    @Override
    public synchronized Map<String, String> getGroups() {
        HashMap<String, String> result = new HashMap<String, String>();
        for (Map.Entry entry : this.cache.entrySet()) {
            result.put((String)entry.getKey(), ((ConsumerId)entry.getValue()).toString());
        }
        return result;
    }

    @Override
    public String getType() {
        return "cached";
    }

    public int getMaximumCacheSize() {
        return this.maximumCacheSize;
    }

    public String toString() {
        return "message groups: " + this.cache.size();
    }

    @Override
    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}

