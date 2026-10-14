/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RMICacheManagerPeerProvider
implements CacheManagerPeerProvider {
    private static final Logger LOG = LoggerFactory.getLogger(RMICacheManagerPeerProvider.class.getName());
    protected final Map peerUrls = Collections.synchronizedMap(new HashMap());
    protected CacheManager cacheManager;

    public RMICacheManagerPeerProvider(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public RMICacheManagerPeerProvider() {
    }

    @Override
    public abstract void init();

    @Override
    public abstract void registerPeer(String var1);

    static String extractCacheName(String rmiUrl) {
        return rmiUrl.substring(rmiUrl.lastIndexOf(47) + 1);
    }

    @Override
    public final synchronized void unregisterPeer(String rmiUrl) {
        this.peerUrls.remove(rmiUrl);
    }

    @Override
    public abstract List listRemoteCachePeers(Ehcache var1) throws CacheException;

    protected abstract boolean stale(Date var1);

    public CachePeer lookupRemoteCachePeer(String url2) throws MalformedURLException, NotBoundException, RemoteException {
        LOG.debug("Lookup URL {}", (Object)url2);
        CachePeer cachePeer = (CachePeer)Naming.lookup(url2);
        return cachePeer;
    }

    @Override
    public void dispose() throws CacheException {
    }

    public final CacheManager getCacheManager() {
        return this.cacheManager;
    }

    @Override
    public String getScheme() {
        return "RMI";
    }
}

