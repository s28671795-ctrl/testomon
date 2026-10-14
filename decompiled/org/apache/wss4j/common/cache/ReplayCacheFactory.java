/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.cache;

import java.io.IOException;
import java.net.URL;
import org.apache.wss4j.common.cache.EHCacheReplayCacheFactory;
import org.apache.wss4j.common.cache.MemoryReplayCacheFactory;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.util.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ReplayCacheFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ReplayCacheFactory.class);
    private static boolean ehCacheInstalled;

    public static synchronized boolean isEhCacheInstalled() {
        return ehCacheInstalled;
    }

    public static ReplayCacheFactory newInstance() {
        if (ReplayCacheFactory.isEhCacheInstalled()) {
            return new EHCacheReplayCacheFactory();
        }
        return new MemoryReplayCacheFactory();
    }

    public abstract ReplayCache newReplayCache(String var1, Object var2);

    protected URL getConfigFileURL(Object o) {
        if (o instanceof String) {
            try {
                URL url2 = Loader.getResource((String)o);
                if (url2 == null) {
                    url2 = new URL((String)o);
                }
                return url2;
            }
            catch (IOException e) {
                LOG.debug(e.getMessage());
            }
        } else if (o instanceof URL) {
            return (URL)o;
        }
        return null;
    }

    static {
        try {
            Class<?> cacheManagerClass = Class.forName("net.sf.ehcache.CacheManager");
            if (cacheManagerClass != null) {
                ehCacheInstalled = true;
            }
        }
        catch (Exception e) {
            LOG.debug(e.getMessage());
        }
    }
}

