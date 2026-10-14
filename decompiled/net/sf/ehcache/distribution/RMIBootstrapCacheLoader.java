/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.RemoteCacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMIBootstrapCacheLoader
implements BootstrapCacheLoader,
Cloneable {
    private static final int ONE_SECOND = 1000;
    private static final Logger LOG = LoggerFactory.getLogger(RMIBootstrapCacheLoader.class.getName());
    protected boolean asynchronous;
    protected int maximumChunkSizeBytes;

    public RMIBootstrapCacheLoader(boolean asynchronous, int maximumChunkSize) {
        this.asynchronous = asynchronous;
        this.maximumChunkSizeBytes = maximumChunkSize;
    }

    @Override
    public void load(Ehcache cache) throws RemoteCacheException {
        if (this.asynchronous) {
            BootstrapThread bootstrapThread = new BootstrapThread(cache);
            bootstrapThread.start();
        } else {
            this.doLoad(cache);
        }
    }

    @Override
    public boolean isAsynchronous() {
        return this.asynchronous;
    }

    public void doLoad(Ehcache cache) throws RemoteCacheException {
        List cachePeers = this.acquireCachePeers(cache);
        if (cachePeers == null || cachePeers.size() == 0) {
            LOG.debug("Empty list of cache peers for cache " + cache.getName() + ". No cache peer to bootstrap from.");
            return;
        }
        Random random = new Random();
        int randomPeerNumber = random.nextInt(cachePeers.size());
        CachePeer cachePeer = (CachePeer)cachePeers.get(randomPeerNumber);
        LOG.debug("Bootstrapping " + cache.getName() + " from " + cachePeer);
        try {
            Serializable key;
            Element sampleElement = null;
            List keys2 = cachePeer.getKeys();
            for (int i = 0; i < keys2.size() && ((sampleElement = cachePeer.getQuiet(key = (Serializable)keys2.get(i))) == null || sampleElement.getSerializedSize() == 0L); ++i) {
            }
            if (sampleElement == null) {
                LOG.debug("All cache peer elements were either null or empty. Nothing to bootstrap from. Cache was " + cache.getName() + ". Cache peer was " + cachePeer);
                return;
            }
            long size = sampleElement.getSerializedSize();
            int chunkSize = (int)((long)this.maximumChunkSizeBytes / size);
            ArrayList<Serializable> requestChunk = new ArrayList<Serializable>();
            for (int i = 0; i < keys2.size(); ++i) {
                Serializable serializable = (Serializable)keys2.get(i);
                requestChunk.add(serializable);
                if (requestChunk.size() != chunkSize) continue;
                this.fetchAndPutElements(cache, requestChunk, cachePeer);
                requestChunk.clear();
            }
            this.fetchAndPutElements(cache, requestChunk, cachePeer);
            LOG.debug("Bootstrap of " + cache.getName() + " from " + cachePeer + " finished. " + keys2.size() + " keys requested.");
        }
        catch (Throwable t2) {
            throw new RemoteCacheException("Error bootstrapping from remote peer. Message was: " + t2.getMessage(), t2);
        }
    }

    protected List acquireCachePeers(Ehcache cache) {
        long timeForClusterToForm = 0L;
        CacheManagerPeerProvider cacheManagerPeerProvider = cache.getCacheManager().getCacheManagerPeerProvider("RMI");
        if (cacheManagerPeerProvider != null) {
            timeForClusterToForm = cacheManagerPeerProvider.getTimeForClusterToForm();
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attempting to acquire cache peers for cache " + cache.getName() + " to bootstrap from. Will wait up to " + timeForClusterToForm + "ms for cache to join cluster.");
        }
        List cachePeers = null;
        int i = 0;
        while ((long)i <= timeForClusterToForm && (cachePeers = this.listRemoteCachePeers(cache)) != null && cachePeers.size() <= 0) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException e) {
                LOG.debug("doLoad for " + cache.getName() + " interrupted.");
            }
            i += 1000;
        }
        LOG.debug("cache peers: {}", (Object)cachePeers);
        return cachePeers;
    }

    protected void fetchAndPutElements(Ehcache cache, List requestChunk, CachePeer cachePeer) throws RemoteException {
        List receivedChunk = cachePeer.getElements(requestChunk);
        for (int i = 0; i < receivedChunk.size(); ++i) {
            Element element = (Element)receivedChunk.get(i);
            if (element == null) continue;
            cache.put(element, true);
        }
    }

    protected List listRemoteCachePeers(Ehcache cache) {
        CacheManagerPeerProvider provider = cache.getCacheManager().getCacheManagerPeerProvider("RMI");
        if (provider == null) {
            return null;
        }
        return provider.listRemoteCachePeers(cache);
    }

    public int getMaximumChunkSizeBytes() {
        return this.maximumChunkSizeBytes;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new RMIBootstrapCacheLoader(this.asynchronous, this.maximumChunkSizeBytes);
    }

    private final class BootstrapThread
    extends Thread {
        private Ehcache cache;

        public BootstrapThread(Ehcache cache) {
            super("Bootstrap Thread for cache " + cache.getName());
            this.cache = cache;
            this.setDaemon(true);
            this.setPriority(5);
        }

        @Override
        public final void run() {
            try {
                RMIBootstrapCacheLoader.this.doLoad(this.cache);
            }
            catch (RemoteCacheException e) {
                LOG.warn("Error asynchronously performing bootstrap. The cause was: " + e.getMessage(), e);
            }
            finally {
                this.cache = null;
            }
        }
    }
}

