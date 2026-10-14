/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.cache;

import java.net.URL;
import net.sf.ehcache.CacheManager;
import org.apache.cxf.Bus;
import org.apache.wss4j.common.cache.EHCacheManagerHolder;

public final class EHCacheUtils {
    public static final String GLOBAL_EHCACHE_MANAGER_NAME = "ws-security.global.ehcachemanager";

    private EHCacheUtils() {
    }

    public static CacheManager getCacheManager(Bus bus, URL configFileURL) {
        CacheManager cacheManager = null;
        String globalCacheManagerName = EHCacheUtils.getGlobalCacheManagerName(bus);
        if (globalCacheManagerName != null) {
            cacheManager = CacheManager.getCacheManager(globalCacheManagerName);
        }
        if (cacheManager == null) {
            String confName = "";
            if (bus != null) {
                confName = bus.getId();
            }
            cacheManager = EHCacheManagerHolder.getCacheManager(confName, configFileURL);
        }
        return cacheManager;
    }

    private static String getGlobalCacheManagerName(Bus bus) {
        if (bus != null) {
            return (String)bus.getProperty(GLOBAL_EHCACHE_MANAGER_NAME);
        }
        return null;
    }
}

