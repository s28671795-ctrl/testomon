/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.util.List;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;

public interface CacheManagerPeerProvider {
    public void registerPeer(String var1);

    public void unregisterPeer(String var1);

    public List listRemoteCachePeers(Ehcache var1) throws CacheException;

    public void init();

    public void dispose() throws CacheException;

    public long getTimeForClusterToForm();

    public String getScheme();
}

