/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.MulticastKeepaliveHeartbeatReceiver;
import net.sf.ehcache.distribution.MulticastKeepaliveHeartbeatSender;
import net.sf.ehcache.distribution.RMICacheManagerPeerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MulticastRMICacheManagerPeerProvider
extends RMICacheManagerPeerProvider
implements CacheManagerPeerProvider {
    protected static final int SHORT_DELAY = 100;
    private static final Logger LOG = LoggerFactory.getLogger(MulticastRMICacheManagerPeerProvider.class.getName());
    private final MulticastKeepaliveHeartbeatReceiver heartBeatReceiver;
    private final MulticastKeepaliveHeartbeatSender heartBeatSender;

    public MulticastRMICacheManagerPeerProvider(CacheManager cacheManager, InetAddress groupMulticastAddress, Integer groupMulticastPort, Integer timeToLive, InetAddress hostAddress) {
        super(cacheManager);
        this.heartBeatReceiver = new MulticastKeepaliveHeartbeatReceiver(this, groupMulticastAddress, groupMulticastPort, hostAddress);
        this.heartBeatSender = new MulticastKeepaliveHeartbeatSender(cacheManager, groupMulticastAddress, groupMulticastPort, timeToLive, hostAddress);
    }

    @Override
    public final void init() throws CacheException {
        try {
            this.heartBeatReceiver.init();
            this.heartBeatSender.init();
        }
        catch (IOException exception) {
            LOG.error("Error starting heartbeat. Error was: " + exception.getMessage(), exception);
            throw new CacheException(exception.getMessage());
        }
    }

    @Override
    public final void registerPeer(String rmiUrl) {
        try {
            CachePeerEntry cachePeerEntry = (CachePeerEntry)this.peerUrls.get(rmiUrl);
            if (cachePeerEntry == null || this.stale(cachePeerEntry.date)) {
                CachePeer cachePeer = this.lookupRemoteCachePeer(rmiUrl);
                cachePeerEntry = new CachePeerEntry(cachePeer, new Date());
                this.peerUrls.put(rmiUrl, cachePeerEntry);
            } else {
                cachePeerEntry.date = new Date();
            }
        }
        catch (IOException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unable to lookup remote cache peer for " + rmiUrl + ". Removing from peer list. Cause was: " + e.getMessage());
            }
            this.unregisterPeer(rmiUrl);
        }
        catch (NotBoundException e) {
            this.peerUrls.remove(rmiUrl);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Unable to lookup remote cache peer for " + rmiUrl + ". Removing from peer list. Cause was: " + e.getMessage());
            }
        }
        catch (Throwable t2) {
            LOG.error("Unable to lookup remote cache peer for " + rmiUrl + ". Cause was not due to an IOException or NotBoundException which will occur in normal operation: " + t2.getMessage());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final synchronized List listRemoteCachePeers(Ehcache cache) throws CacheException {
        ArrayList<CachePeer> remoteCachePeers = new ArrayList<CachePeer>();
        ArrayList<String> staleList = new ArrayList<String>();
        Map map = this.peerUrls;
        synchronized (map) {
            for (String rmiUrl : this.peerUrls.keySet()) {
                String rmiUrlCacheName = MulticastRMICacheManagerPeerProvider.extractCacheName(rmiUrl);
                try {
                    if (!rmiUrlCacheName.equals(cache.getName())) continue;
                    CachePeerEntry cachePeerEntry = (CachePeerEntry)this.peerUrls.get(rmiUrl);
                    Date date = cachePeerEntry.date;
                    if (!this.stale(date)) {
                        CachePeer cachePeer = cachePeerEntry.cachePeer;
                        remoteCachePeers.add(cachePeer);
                        continue;
                    }
                    LOG.debug("rmiUrl is stale. Either the remote peer is shutdown or the network connectivity has been interrupted. Will be removed from list of remote cache peers", (Object)rmiUrl);
                    staleList.add(rmiUrl);
                }
                catch (Exception exception) {
                    LOG.error(exception.getMessage(), exception);
                    throw new CacheException("Unable to list remote cache peers. Error was " + exception.getMessage());
                }
            }
            for (int i = 0; i < staleList.size(); ++i) {
                String rmiUrl;
                rmiUrl = (String)staleList.get(i);
                this.peerUrls.remove(rmiUrl);
            }
        }
        return remoteCachePeers;
    }

    @Override
    public final void dispose() {
        this.heartBeatSender.dispose();
        this.heartBeatReceiver.dispose();
    }

    @Override
    public long getTimeForClusterToForm() {
        return MulticastKeepaliveHeartbeatSender.getHeartBeatInterval() * 2L + 100L;
    }

    protected long getStaleTime() {
        return MulticastKeepaliveHeartbeatSender.getHeartBeatStaleTime();
    }

    @Override
    protected final boolean stale(Date date) {
        long now = System.currentTimeMillis();
        return date.getTime() < now - this.getStaleTime();
    }

    public MulticastKeepaliveHeartbeatReceiver getHeartBeatReceiver() {
        return this.heartBeatReceiver;
    }

    public MulticastKeepaliveHeartbeatSender getHeartBeatSender() {
        return this.heartBeatSender;
    }

    protected static final class CachePeerEntry {
        private final CachePeer cachePeer;
        private Date date;

        public CachePeerEntry(CachePeer cachePeer, Date date) {
            this.cachePeer = cachePeer;
            this.date = date;
        }

        public final CachePeer getCachePeer() {
            return this.cachePeer;
        }

        public final Date getDate() {
            return this.date;
        }
    }
}

