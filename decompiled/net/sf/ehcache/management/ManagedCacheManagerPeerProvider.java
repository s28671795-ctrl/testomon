/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.management;

import javax.management.MBeanServer;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;

public interface ManagedCacheManagerPeerProvider
extends CacheManagerPeerProvider {
    public void register(MBeanServer var1);
}

