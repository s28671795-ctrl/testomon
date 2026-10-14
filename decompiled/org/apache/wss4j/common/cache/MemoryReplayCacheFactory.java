/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.cache;

import org.apache.wss4j.common.cache.MemoryReplayCache;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.cache.ReplayCacheFactory;

public class MemoryReplayCacheFactory
extends ReplayCacheFactory {
    @Override
    public ReplayCache newReplayCache(String key, Object configuration) {
        return new MemoryReplayCache();
    }
}

