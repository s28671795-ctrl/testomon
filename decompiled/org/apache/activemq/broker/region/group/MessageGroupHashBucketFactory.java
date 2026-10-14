/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import org.apache.activemq.broker.region.group.MessageGroupHashBucket;
import org.apache.activemq.broker.region.group.MessageGroupMap;
import org.apache.activemq.broker.region.group.MessageGroupMapFactory;

public class MessageGroupHashBucketFactory
implements MessageGroupMapFactory {
    private int bucketCount = 1024;
    private int cacheSize = 64;

    @Override
    public MessageGroupMap createMessageGroupMap() {
        return new MessageGroupHashBucket(this.getBucketCount(), this.getCacheSize());
    }

    public int getBucketCount() {
        return this.bucketCount;
    }

    public void setBucketCount(int bucketCount) {
        this.bucketCount = bucketCount;
    }

    public int getCacheSize() {
        return this.cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }
}

