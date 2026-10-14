/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.util.Properties;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;

public abstract class CacheManagerPeerProviderFactory {
    public abstract CacheManagerPeerProvider createCachePeerProvider(CacheManager var1, Properties var2);
}

