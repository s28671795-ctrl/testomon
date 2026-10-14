/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.group;

import org.apache.activemq.broker.region.group.CachedMessageGroupMap;
import org.apache.activemq.broker.region.group.MessageGroupMap;
import org.apache.activemq.broker.region.group.MessageGroupMapFactory;

public class CachedMessageGroupMapFactory
implements MessageGroupMapFactory {
    private int cacheSize = 1024;

    public int getCacheSize() {
        return this.cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    @Override
    public MessageGroupMap createMessageGroupMap() {
        return new CachedMessageGroupMap(this.getCacheSize());
    }
}

