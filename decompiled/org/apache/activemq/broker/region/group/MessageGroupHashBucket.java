/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import java.util.Map;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.group.EmptyMessageGroupSet;
import org.apache.activemq.broker.region.group.MessageGroupMap;
import org.apache.activemq.broker.region.group.MessageGroupSet;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.memory.LRUMap;

public class MessageGroupHashBucket
implements MessageGroupMap {
    private final int bucketCount;
    private final ConsumerId[] consumers;
    private final LRUMap<String, String> cache;

    public MessageGroupHashBucket(int bucketCount, int cachedSize) {
        this.bucketCount = bucketCount;
        this.consumers = new ConsumerId[bucketCount];
        this.cache = new LRUMap(cachedSize);
    }

    @Override
    public synchronized void put(String groupId, ConsumerId consumerId) {
        int bucket = this.getBucketNumber(groupId);
        this.consumers[bucket] = consumerId;
        if (consumerId != null) {
            this.cache.put(groupId, consumerId.toString());
        }
    }

    @Override
    public synchronized ConsumerId get(String groupId) {
        int bucket = this.getBucketNumber(groupId);
        this.cache.get(groupId);
        return this.consumers[bucket];
    }

    @Override
    public synchronized ConsumerId removeGroup(String groupId) {
        int bucket = this.getBucketNumber(groupId);
        ConsumerId answer = this.consumers[bucket];
        this.consumers[bucket] = null;
        this.cache.remove(groupId);
        return answer;
    }

    @Override
    public synchronized MessageGroupSet removeConsumer(ConsumerId consumerId) {
        MessageGroupSet answer = null;
        for (int i = 0; i < this.consumers.length; ++i) {
            ConsumerId owner = this.consumers[i];
            if (owner == null || !owner.equals(consumerId)) continue;
            answer = this.createMessageGroupSet(i, answer);
            this.consumers[i] = null;
        }
        if (answer == null) {
            answer = EmptyMessageGroupSet.INSTANCE;
        }
        return answer;
    }

    @Override
    public synchronized void removeAll() {
        for (int i = 0; i < this.consumers.length; ++i) {
            this.consumers[i] = null;
        }
    }

    @Override
    public Map<String, String> getGroups() {
        return this.cache;
    }

    @Override
    public String getType() {
        return "bucket";
    }

    @Override
    public void setDestination(Destination destination) {
    }

    public int getBucketCount() {
        return this.bucketCount;
    }

    public String toString() {
        int count = 0;
        for (int i = 0; i < this.consumers.length; ++i) {
            if (this.consumers[i] == null) continue;
            ++count;
        }
        return "active message group buckets: " + count;
    }

    protected MessageGroupSet createMessageGroupSet(int bucketNumber, final MessageGroupSet parent) {
        final MessageGroupSet answer = this.createMessageGroupSet(bucketNumber);
        if (parent == null) {
            return answer;
        }
        return new MessageGroupSet(){

            @Override
            public boolean contains(String groupID) {
                return parent.contains(groupID) || answer.contains(groupID);
            }
        };
    }

    protected MessageGroupSet createMessageGroupSet(final int bucketNumber) {
        return new MessageGroupSet(){

            @Override
            public boolean contains(String groupID) {
                int bucket = MessageGroupHashBucket.this.getBucketNumber(groupID);
                return bucket == bucketNumber;
            }
        };
    }

    protected int getBucketNumber(String groupId) {
        int bucket = groupId.hashCode() % this.bucketCount;
        if (bucket < 0) {
            bucket *= -1;
        }
        return bucket;
    }
}

