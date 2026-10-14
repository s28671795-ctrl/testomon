/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.RMICacheManagerPeerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ManualRMICacheManagerPeerProvider
extends RMICacheManagerPeerProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ManualRMICacheManagerPeerProvider.class.getName());

    @Override
    public final void init() {
    }

    @Override
    public long getTimeForClusterToForm() {
        return 0L;
    }

    @Override
    public final synchronized void registerPeer(String rmiUrl) {
        this.peerUrls.put(rmiUrl, new Date());
    }

    @Override
    public final synchronized List listRemoteCachePeers(Ehcache cache) throws CacheException {
        ArrayList<CachePeer> remoteCachePeers = new ArrayList<CachePeer>();
        ArrayList<String> staleList = new ArrayList<String>();
        for (String rmiUrl : this.peerUrls.keySet()) {
            String rmiUrlCacheName = ManualRMICacheManagerPeerProvider.extractCacheName(rmiUrl);
            if (!rmiUrlCacheName.equals(cache.getName())) continue;
            Date date = (Date)this.peerUrls.get(rmiUrl);
            if (!this.stale(date)) {
                CachePeer cachePeer = null;
                try {
                    cachePeer = this.lookupRemoteCachePeer(rmiUrl);
                    remoteCachePeers.add(cachePeer);
                }
                catch (Exception e) {
                    if (!LOG.isDebugEnabled()) continue;
                    LOG.debug("Looking up rmiUrl " + rmiUrl + " through exception " + e.getMessage() + ". This may be normal if a node has gone offline. Or it may indicate network connectivity difficulties", e);
                }
                continue;
            }
            LOG.debug("rmiUrl {} should never be stale for a manually configured cluster.", (Object)rmiUrl);
            staleList.add(rmiUrl);
        }
        for (int i = 0; i < staleList.size(); ++i) {
            String rmiUrl;
            rmiUrl = (String)staleList.get(i);
            this.peerUrls.remove(rmiUrl);
        }
        return remoteCachePeers;
    }

    @Override
    protected final boolean stale(Date date) {
        return false;
    }
}

