/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.cache;

import java.net.URL;
import org.apache.wss4j.common.cache.EHCacheReplayCache;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.cache.ReplayCacheFactory;

public class EHCacheReplayCacheFactory
extends ReplayCacheFactory {
    @Override
    public ReplayCache newReplayCache(String key, Object configuration) {
        URL configFileURL = this.getConfigFileURL(configuration);
        return new EHCacheReplayCache(key, configFileURL);
    }
}

