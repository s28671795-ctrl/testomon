/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.util.Properties;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.distribution.CacheManagerPeerListener;

public abstract class CacheManagerPeerListenerFactory {
    public abstract CacheManagerPeerListener createCachePeerListener(CacheManager var1, Properties var2);
}

