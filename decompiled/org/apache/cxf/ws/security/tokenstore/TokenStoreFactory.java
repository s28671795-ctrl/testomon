/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.tokenstore;

import org.apache.cxf.message.Message;
import org.apache.cxf.ws.security.tokenstore.EHCacheTokenStoreFactory;
import org.apache.cxf.ws.security.tokenstore.MemoryTokenStoreFactory;
import org.apache.cxf.ws.security.tokenstore.TokenStore;

public abstract class TokenStoreFactory {
    private static boolean ehCacheInstalled;

    public static synchronized boolean isEhCacheInstalled() {
        return ehCacheInstalled;
    }

    public static TokenStoreFactory newInstance() {
        if (TokenStoreFactory.isEhCacheInstalled()) {
            return new EHCacheTokenStoreFactory();
        }
        return new MemoryTokenStoreFactory();
    }

    public abstract TokenStore newTokenStore(String var1, Message var2);

    static {
        try {
            Class<?> cacheManagerClass = Class.forName("net.sf.ehcache.CacheManager");
            if (cacheManagerClass != null) {
                ehCacheInstalled = true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

