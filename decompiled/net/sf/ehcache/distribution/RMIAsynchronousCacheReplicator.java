/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.distribution;

import java.lang.ref.SoftReference;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import net.sf.ehcache.distribution.CachePeer;
import net.sf.ehcache.distribution.EventMessage;
import net.sf.ehcache.distribution.RMISynchronousCacheReplicator;
import net.sf.ehcache.distribution.RmiEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMIAsynchronousCacheReplicator
extends RMISynchronousCacheReplicator {
    private static final Logger LOG = LoggerFactory.getLogger(RMIAsynchronousCacheReplicator.class.getName());
    private final Thread replicationThread = new ReplicationThread();
    private final int replicationInterval;
    private final int maximumBatchSize;
    private final Queue<Object> replicationQueue = new ConcurrentLinkedQueue<Object>();

    public RMIAsynchronousCacheReplicator(boolean replicatePuts, boolean replicatePutsViaCopy, boolean replicateUpdates, boolean replicateUpdatesViaCopy, boolean replicateRemovals, int replicationInterval, int maximumBatchSize) {
        super(replicatePuts, replicatePutsViaCopy, replicateUpdates, replicateUpdatesViaCopy, replicateRemovals);
        this.replicationInterval = replicationInterval;
        this.maximumBatchSize = maximumBatchSize;
        this.status = Status.STATUS_ALIVE;
        this.replicationThread.start();
    }

    private void replicationThreadMain() {
        while (true) {
            if (this.alive() && this.replicationQueue != null && this.replicationQueue.isEmpty()) {
                try {
                    Thread.sleep(this.replicationInterval);
                }
                catch (InterruptedException e) {
                    LOG.debug("Spool Thread interrupted.");
                    return;
                }
            }
            if (this.notAlive()) {
                return;
            }
            try {
                this.writeReplicationQueue();
                continue;
            }
            catch (Throwable e) {
                LOG.error("Exception on flushing of replication queue: " + e.getMessage() + ". Continuing...", e);
                continue;
            }
            break;
        }
    }

    @Override
    public final void notifyElementPut(Ehcache cache, Element element) throws CacheException {
        if (this.notAlive()) {
            return;
        }
        if (!this.replicatePuts) {
            return;
        }
        if (this.replicatePutsViaCopy) {
            if (!element.isSerializable()) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Object with key " + element.getObjectKey() + " is not Serializable and cannot be replicated.");
                }
                return;
            }
            this.addToReplicationQueue(new RmiEventMessage(cache, RmiEventMessage.RmiEventType.PUT, null, element));
        } else {
            if (!element.isKeySerializable()) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Object with key " + element.getObjectKey() + " does not have a Serializable key and cannot be replicated via invalidate.");
                }
                return;
            }
            this.addToReplicationQueue(new RmiEventMessage(cache, RmiEventMessage.RmiEventType.REMOVE, element.getKey(), null));
        }
    }

    @Override
    public final void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
        if (this.notAlive()) {
            return;
        }
        if (!this.replicateUpdates) {
            return;
        }
        if (this.replicateUpdatesViaCopy) {
            if (!element.isSerializable()) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Object with key " + element.getObjectKey() + " is not Serializable and cannot be updated via copy.");
                }
                return;
            }
            this.addToReplicationQueue(new RmiEventMessage(cache, RmiEventMessage.RmiEventType.PUT, null, element));
        } else {
            if (!element.isKeySerializable()) {
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Object with key " + element.getObjectKey() + " does not have a Serializable key and cannot be replicated via invalidate.");
                }
                return;
            }
            this.addToReplicationQueue(new RmiEventMessage(cache, RmiEventMessage.RmiEventType.REMOVE, element.getKey(), null));
        }
    }

    @Override
    public final void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
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
        this.addToReplicationQueue(new RmiEventMessage(cache, RmiEventMessage.RmiEventType.REMOVE, element.getKey(), null));
    }

    @Override
    public void notifyRemoveAll(Ehcache cache) {
        if (this.notAlive()) {
            return;
        }
        if (!this.replicateRemovals) {
            return;
        }
        this.addToReplicationQueue(new RmiEventMessage(cache, RmiEventMessage.RmiEventType.REMOVE_ALL, null, null));
    }

    protected void addToReplicationQueue(RmiEventMessage eventMessage) {
        if (!this.replicationThread.isAlive()) {
            LOG.error("CacheEventMessages cannot be added to the replication queue because the replication thread has died.");
        } else {
            switch (eventMessage.getType()) {
                case PUT: {
                    this.replicationQueue.add(new SoftReference<RmiEventMessage>(eventMessage));
                    break;
                }
                default: {
                    this.replicationQueue.add(eventMessage);
                }
            }
        }
    }

    private void writeReplicationQueue() {
        List<EventMessage> eventMessages = this.extractEventMessages(this.maximumBatchSize);
        if (!eventMessages.isEmpty()) {
            for (CachePeer cachePeer : RMIAsynchronousCacheReplicator.listRemoteCachePeers(eventMessages.get(0).getEhcache())) {
                try {
                    cachePeer.send(eventMessages);
                }
                catch (UnmarshalException e) {
                    String message = e.getMessage();
                    if (message.contains("Read time out") || message.contains("Read timed out")) {
                        LOG.warn("Unable to send message to remote peer due to socket read timeout. Consider increasing the socketTimeoutMillis setting in the cacheManagerPeerListenerFactory. Message was: " + message);
                        continue;
                    }
                    LOG.debug("Unable to send message to remote peer.  Message was: " + message);
                }
                catch (Throwable t2) {
                    LOG.warn("Unable to send message to remote peer.  Message was: " + t2.getMessage(), t2);
                }
            }
        }
    }

    private void flushReplicationQueue() {
        while (!this.replicationQueue.isEmpty()) {
            this.writeReplicationQueue();
        }
    }

    private List<EventMessage> extractEventMessages(int limit) {
        Object polled;
        ArrayList<EventMessage> list = new ArrayList<EventMessage>(Math.min(this.replicationQueue.size(), limit));
        int droppedMessages = 0;
        while (list.size() < limit && (polled = this.replicationQueue.poll()) != null) {
            if (polled instanceof EventMessage) {
                list.add((EventMessage)polled);
                continue;
            }
            EventMessage message = (EventMessage)((SoftReference)polled).get();
            if (message == null) {
                ++droppedMessages;
                continue;
            }
            list.add(message);
        }
        if (droppedMessages > 0) {
            LOG.warn(droppedMessages + " messages were discarded on replicate due to reclamation of SoftReferences by the VM. Consider increasing the maximum heap size and/or setting the starting heap size to a higher value.");
        }
        return list;
    }

    @Override
    public final void dispose() {
        this.status = Status.STATUS_SHUTDOWN;
        this.flushReplicationQueue();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        return new RMIAsynchronousCacheReplicator(this.replicatePuts, this.replicatePutsViaCopy, this.replicateUpdates, this.replicateUpdatesViaCopy, this.replicateRemovals, this.replicationInterval, this.maximumBatchSize);
    }

    private final class ReplicationThread
    extends Thread {
        public ReplicationThread() {
            super("Replication Thread");
            this.setDaemon(true);
            this.setPriority(5);
        }

        @Override
        public final void run() {
            RMIAsynchronousCacheReplicator.this.replicationThreadMain();
        }
    }
}

