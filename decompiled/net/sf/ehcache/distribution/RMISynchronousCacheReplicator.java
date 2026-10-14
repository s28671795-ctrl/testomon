/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.distribution.CacheManagerPeerProvider;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.CacheReplicator;
import net.sf.ehcache.distribution.RemoteCacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMISynchronousCacheReplicator
implements CacheReplicator {
    private static final Logger LOG = LoggerFactory.getLogger(RMISynchronousCacheReplicator.class.getName());
    protected Status status;
    protected final boolean replicatePuts;
    protected boolean replicatePutsViaCopy;
    protected final boolean replicateUpdates;
    protected final boolean replicateUpdatesViaCopy;
    protected final boolean replicateRemovals;

    public RMISynchronousCacheReplicator(boolean replicatePuts, boolean replicatePutsViaCopy, boolean replicateUpdates, boolean replicateUpdatesViaCopy, boolean replicateRemovals) {
        this.replicatePuts = replicatePuts;
        this.replicatePutsViaCopy = replicatePutsViaCopy;
        this.replicateUpdates = replicateUpdates;
        this.replicateUpdatesViaCopy = replicateUpdatesViaCopy;
        this.replicateRemovals = replicateRemovals;
        this.status = Status.STATUS_ALIVE;
    }

    @Override
    public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
        if (this.notAlive()) {
            return;
        }
        if (!this.replicatePuts) {
            return;
        }
        if (!element.isSerializable()) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Object with key " + element.getObjectKey() + " is not Serializable and cannot be replicated");
            }
            return;
        }
        if (this.replicatePutsViaCopy) {
            RMISynchronousCacheReplicator.replicatePutNotification(cache, element);
        } else {
            RMISynchronousCacheReplicator.replicateRemovalNotification(cache, (Serializable)element.getObjectKey());
        }
    }

    protected static void replicatePutNotification(Ehcache cache, Element element) throws RemoteCacheException {
        List<CachePeer> cachePeers = RMISynchronousCacheReplicator.listRemoteCachePeers(cache);
        Iterator<CachePeer> iterator2 = cachePeers.iterator();
        while (iterator2.hasNext()) {
            CachePeer cachePeer1;
            CachePeer cachePeer = cachePeer1 = iterator2.next();
            try {
                cachePeer.put(element);
            }
            catch (Throwable t2) {
                LOG.error("Exception on replication of putNotification. " + t2.getMessage() + ". Continuing...", t2);
            }
        }
    }

    @Override
    public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
        if (this.notAlive()) {
            return;
        }
        if (!this.replicateUpdates) {
            return;
        }
        if (this.replicateUpdatesViaCopy) {
            if (!element.isSerializable()) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Object with key " + element.getObjectKey() + " is not Serializable and cannot be updated via copy");
                }
                return;
            }
            RMISynchronousCacheReplicator.replicatePutNotification(cache, element);
        } else {
            if (!element.isKeySerializable()) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Key " + element.getObjectKey() + " is not Serializable and cannot be replicated.");
                }
                return;
            }
            RMISynchronousCacheReplicator.replicateRemovalNotification(cache, (Serializable)element.getObjectKey());
        }
    }

    @Override
    public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
        if (this.notAlive()) {
            return;
        }
        if (!this.replicateRemovals) {
            return;
        }
        if (!element.isKeySerializable()) {
            if (LOG.isWarnEnabled()) {
                LOG.warn("Key " + element.getObjectKey() + " is not Serializable and cannot be replicated.");
            }
            return;
        }
        RMISynchronousCacheReplicator.replicateRemovalNotification(cache, (Serializable)element.getObjectKey());
    }

    protected static void replicateRemovalNotification(Ehcache cache, Serializable key) throws RemoteCacheException {
        List<CachePeer> cachePeers = RMISynchronousCacheReplicator.listRemoteCachePeers(cache);
        Iterator<CachePeer> iterator2 = cachePeers.iterator();
        while (iterator2.hasNext()) {
            CachePeer cachePeer1;
            CachePeer cachePeer = cachePeer1 = iterator2.next();
            try {
                cachePeer.remove(key);
            }
            catch (Throwable t2) {
                LOG.error("Exception on replication of removeNotification. " + t2.getMessage() + ". Continuing...", t2);
            }
        }
    }

    @Override
    public final void notifyElementExpired(Ehcache cache, Element element) {
    }

    @Override
    public void notifyElementEvicted(Ehcache cache, Element element) {
    }

    @Override
    public void notifyRemoveAll(Ehcache cache) {
        if (this.notAlive()) {
            return;
        }
        if (!this.replicateRemovals) {
            return;
        }
        this.replicateRemoveAllNotification(cache);
    }

    protected void replicateRemoveAllNotification(Ehcache cache) {
        List<CachePeer> cachePeers = RMISynchronousCacheReplicator.listRemoteCachePeers(cache);
        Iterator<CachePeer> iterator2 = cachePeers.iterator();
        while (iterator2.hasNext()) {
            CachePeer cachePeer1;
            CachePeer cachePeer = cachePeer1 = iterator2.next();
            try {
                cachePeer.removeAll();
            }
            catch (Throwable t2) {
                LOG.error("Exception on replication of removeAllNotification. " + t2.getMessage() + ". Continuing...", t2);
            }
        }
    }

    static List<CachePeer> listRemoteCachePeers(Ehcache cache) {
        CacheManagerPeerProvider provider = cache.getCacheManager().getCacheManagerPeerProvider("RMI");
        return provider.listRemoteCachePeers(cache);
    }

    @Override
    public final boolean isReplicateUpdatesViaCopy() {
        return this.replicateUpdatesViaCopy;
    }

    @Override
    public final boolean notAlive() {
        return !this.alive();
    }

    @Override
    public final boolean alive() {
        if (this.status == null) {
            return false;
        }
        return this.status.equals(Status.STATUS_ALIVE);
    }

    @Override
    public void dispose() {
        this.status = Status.STATUS_SHUTDOWN;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new RMISynchronousCacheReplicator(this.replicatePuts, this.replicatePutsViaCopy, this.replicateUpdates, this.replicateUpdatesViaCopy, this.replicateRemovals);
    }
}

