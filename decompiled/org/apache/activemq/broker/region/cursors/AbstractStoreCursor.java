/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.cursors.AbstractPendingMessageCursor;
import org.apache.activemq.broker.region.cursors.OrderedPendingList;
import org.apache.activemq.broker.region.cursors.PendingList;
import org.apache.activemq.broker.region.cursors.PrioritizedPendingList;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.store.MessageRecoveryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractStoreCursor
extends AbstractPendingMessageCursor
implements MessageRecoveryListener {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractStoreCursor.class);
    protected final Destination regionDestination;
    protected final PendingList batchList;
    private Iterator<MessageReference> iterator = null;
    protected boolean batchResetNeeded = false;
    protected int size;
    private final LinkedList<MessageId> pendingCachedIds = new LinkedList();
    private static int SYNC_ADD = 0;
    private static int ASYNC_ADD = 1;
    final MessageId[] lastCachedIds = new MessageId[2];
    protected boolean hadSpace = false;
    final LinkedList<Message> duplicatesFromStore = new LinkedList();

    protected AbstractStoreCursor(Destination destination) {
        super(destination != null ? destination.isPrioritizedMessages() : false);
        this.regionDestination = destination;
        this.batchList = this.prioritizedMessages ? new PrioritizedPendingList() : new OrderedPendingList();
    }

    @Override
    public final synchronized void start() throws Exception {
        if (!this.isStarted()) {
            super.start();
            this.resetBatch();
            this.resetSize();
            this.setCacheEnabled(this.size == 0 && this.useCache);
        }
    }

    protected void resetSize() {
        this.size = this.getStoreSize();
    }

    @Override
    public void rebase() {
        this.resetSize();
    }

    @Override
    public final synchronized void stop() throws Exception {
        this.resetBatch();
        super.stop();
        this.gc();
    }

    @Override
    public final boolean recoverMessage(Message message) throws Exception {
        return this.recoverMessage(message, false);
    }

    public synchronized boolean recoverMessage(Message message, boolean cached) throws Exception {
        boolean recovered = false;
        message.setRegionDestination(this.regionDestination);
        if (this.recordUniqueId(message.getMessageId())) {
            if (!cached && message.getMemoryUsage() == null) {
                message.setMemoryUsage(this.getSystemUsage().getMemoryUsage());
            }
            message.incrementReferenceCount();
            this.batchList.addMessageLast(message);
            this.clearIterator(true);
            recovered = true;
        } else if (!cached) {
            if (this.duplicateFromStoreExcepted(message)) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("{} store replayed pending message due to concurrentStoreAndDispatchQueues {} seq: {}", this, message.getMessageId(), message.getMessageId().getFutureOrSequenceLong());
                }
            } else {
                LOG.warn("{} - cursor got duplicate from store {} seq: {}", this, message.getMessageId(), message.getMessageId().getFutureOrSequenceLong());
                this.duplicate(message);
            }
        } else {
            LOG.warn("{} - cursor got duplicate send {} seq: {}", this, message.getMessageId(), message.getMessageId().getFutureOrSequenceLong());
            if (AbstractStoreCursor.gotToTheStore(message)) {
                this.duplicate(message);
            }
        }
        return recovered;
    }

    protected boolean duplicateFromStoreExcepted(Message message) {
        return message.isRecievedByDFBridge();
    }

    public static boolean gotToTheStore(Message message) throws Exception {
        if (message.isRecievedByDFBridge()) {
            Object sequence2;
            Object possibleFuture = message.getMessageId().getFutureOrSequenceLong();
            if (possibleFuture instanceof Future) {
                ((Future)possibleFuture).get();
            }
            return (sequence2 = message.getMessageId().getFutureOrSequenceLong()) != null && sequence2 instanceof Long && Long.compare((Long)sequence2, -1L) != 0;
        }
        return true;
    }

    private void duplicate(Message message) {
        this.duplicatesFromStore.add(message);
    }

    void dealWithDuplicates() {
        for (Message message : this.duplicatesFromStore) {
            this.regionDestination.duplicateFromStore(message, this.getSubscription());
        }
        this.duplicatesFromStore.clear();
    }

    @Override
    public final synchronized void reset() {
        if (this.batchList.isEmpty()) {
            try {
                this.fillBatch();
            }
            catch (Exception e) {
                LOG.error("{} - Failed to fill batch", (Object)this, (Object)e);
                throw new RuntimeException(e);
            }
        }
        this.clearIterator(true);
        this.size();
    }

    @Override
    public synchronized void release() {
        this.clearIterator(false);
    }

    private synchronized void clearIterator(boolean ensureIterator) {
        boolean haveIterator = this.iterator != null;
        this.iterator = null;
        if (haveIterator && ensureIterator) {
            this.ensureIterator();
        }
    }

    private synchronized void ensureIterator() {
        if (this.iterator == null) {
            this.iterator = this.batchList.iterator();
        }
    }

    public final void finished() {
    }

    @Override
    public final synchronized boolean hasNext() {
        if (this.batchList.isEmpty()) {
            try {
                this.fillBatch();
            }
            catch (Exception e) {
                LOG.error("{} - Failed to fill batch", (Object)this, (Object)e);
                throw new RuntimeException(e);
            }
        }
        this.ensureIterator();
        return this.iterator.hasNext();
    }

    @Override
    public final synchronized MessageReference next() {
        MessageReference result = null;
        if (!this.batchList.isEmpty() && this.iterator.hasNext()) {
            result = this.iterator.next();
        }
        this.last = result;
        if (result != null) {
            result.incrementReferenceCount();
        }
        return result;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public synchronized boolean tryAddMessageLast(MessageReference node, long wait) throws Exception {
        boolean disableCache = false;
        if (this.hasSpace()) {
            if (this.isCacheEnabled()) {
                if (!this.recoverMessage(node.getMessage(), true)) {
                    this.dealWithDuplicates();
                    return false;
                }
                this.trackLastCached(node);
            }
        } else {
            disableCache = true;
        }
        if (disableCache && this.isCacheEnabled()) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("{} - disabling cache on add {} {}", this, node.getMessageId(), node.getMessageId().getFutureOrSequenceLong());
            }
            this.syncWithStore(node.getMessage());
            this.setCacheEnabled(false);
        }
        ++this.size;
        return true;
    }

    @Override
    public synchronized boolean isCacheEnabled() {
        return super.isCacheEnabled() || this.enableCacheNow();
    }

    protected boolean enableCacheNow() {
        boolean result = false;
        if (this.canEnableCash()) {
            this.setCacheEnabled(true);
            result = true;
            if (LOG.isTraceEnabled()) {
                LOG.trace("{} enabling cache on empty store", (Object)this);
            }
        }
        return result;
    }

    protected boolean canEnableCash() {
        return this.useCache && this.size == 0 && this.hasSpace() && this.isStarted();
    }

    @Override
    public boolean canRecoveryNextMessage() {
        return this.parentHasSpace(90);
    }

    private void syncWithStore(Message currentAdd) throws Exception {
        MessageId candidate;
        this.pruneLastCached();
        ListIterator<MessageId> it = this.pendingCachedIds.listIterator(this.pendingCachedIds.size());
        while (it.hasPrevious()) {
            MessageId lastPending = it.previous();
            Object futureOrLong = lastPending.getFutureOrSequenceLong();
            if (futureOrLong instanceof Future) {
                Future future = (Future)futureOrLong;
                if (future.isCancelled()) continue;
                try {
                    future.get(5L, TimeUnit.SECONDS);
                    this.setLastCachedId(ASYNC_ADD, lastPending);
                    break;
                }
                catch (CancellationException ok) {
                    continue;
                }
                catch (TimeoutException potentialDeadlock) {
                    LOG.debug("{} timed out waiting for async add", (Object)this, (Object)potentialDeadlock);
                    break;
                }
                catch (Exception worstCaseWeReplay) {
                    LOG.debug("{} exception waiting for async add", (Object)this, (Object)worstCaseWeReplay);
                    break;
                }
            }
            this.setLastCachedId(ASYNC_ADD, lastPending);
            break;
        }
        if ((candidate = this.lastCachedIds[ASYNC_ADD]) != null && !AbstractStoreCursor.isAsync(currentAdd) && Long.compare((Long)currentAdd.getMessageId().getFutureOrSequenceLong(), (Long)this.lastCachedIds[ASYNC_ADD].getFutureOrSequenceLong()) < 0) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("no set batch from async:" + candidate.getFutureOrSequenceLong() + " >= than current: " + currentAdd.getMessageId().getFutureOrSequenceLong() + ", " + this);
            }
            candidate = null;
        }
        if (candidate == null) {
            candidate = this.lastCachedIds[SYNC_ADD];
        }
        if (candidate != null) {
            this.setBatch(candidate);
        }
        this.lastCachedIds[AbstractStoreCursor.ASYNC_ADD] = null;
        this.lastCachedIds[AbstractStoreCursor.SYNC_ADD] = null;
        this.pendingCachedIds.clear();
    }

    private void trackLastCached(MessageReference node) {
        if (AbstractStoreCursor.isAsync(node.getMessage())) {
            this.pruneLastCached();
            this.pendingCachedIds.add(node.getMessageId());
        } else {
            this.setLastCachedId(SYNC_ADD, node.getMessageId());
        }
    }

    private static final boolean isAsync(Message message) {
        return message.isRecievedByDFBridge() || message.getMessageId().getFutureOrSequenceLong() instanceof Future;
    }

    private void pruneLastCached() {
        Iterator it = this.pendingCachedIds.iterator();
        while (it.hasNext()) {
            MessageId candidate = (MessageId)it.next();
            Object futureOrLong = candidate.getFutureOrSequenceLong();
            if (futureOrLong instanceof Future) {
                Future future = (Future)futureOrLong;
                if (!future.isCancelled()) break;
                it.remove();
                continue;
            }
            this.setLastCachedId(ASYNC_ADD, candidate);
            if (this.lastCachedIds[SYNC_ADD] != null) {
                long next = 1L + (Long)this.lastCachedIds[SYNC_ADD].getFutureOrSequenceLong();
                if (Long.compare((Long)futureOrLong, next) == 0) {
                    this.setLastCachedId(SYNC_ADD, candidate);
                }
            }
            it.remove();
        }
    }

    private void setLastCachedId(int index, MessageId candidate) {
        MessageId lastCacheId = this.lastCachedIds[index];
        if (lastCacheId == null) {
            this.lastCachedIds[index] = candidate;
        } else {
            Object lastCacheFutureOrSequenceLong = lastCacheId.getFutureOrSequenceLong();
            Object candidateOrSequenceLong = candidate.getFutureOrSequenceLong();
            if (lastCacheFutureOrSequenceLong == null) {
                this.lastCachedIds[index] = candidate;
            } else if (candidateOrSequenceLong != null && Long.compare((Long)candidateOrSequenceLong, (Long)lastCacheFutureOrSequenceLong) > 0) {
                this.lastCachedIds[index] = candidate;
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("no set last cached[" + index + "] current:" + lastCacheFutureOrSequenceLong + " <= than candidate: " + candidateOrSequenceLong + ", " + this);
            }
        }
    }

    protected void setBatch(MessageId messageId) throws Exception {
    }

    @Override
    public synchronized void addMessageFirst(MessageReference node) throws Exception {
        this.setCacheEnabled(false);
        ++this.size;
    }

    @Override
    public final synchronized void remove() {
        --this.size;
        if (this.iterator != null) {
            this.iterator.remove();
        }
        if (this.last != null) {
            this.last.decrementReferenceCount();
        }
    }

    @Override
    public final synchronized void remove(MessageReference node) {
        if (this.batchList.remove(node) != null) {
            --this.size;
            this.setCacheEnabled(false);
        }
    }

    @Override
    public final synchronized void clear() {
        this.gc();
    }

    @Override
    public synchronized void gc() {
        for (MessageReference msg : this.batchList) {
            this.rollback(msg.getMessageId());
            msg.decrementReferenceCount();
        }
        this.batchList.clear();
        this.clearIterator(false);
        this.batchResetNeeded = true;
        this.setCacheEnabled(false);
    }

    @Override
    protected final synchronized void fillBatch() {
        if (LOG.isTraceEnabled()) {
            LOG.trace("{} fillBatch", (Object)this);
        }
        if (this.batchResetNeeded) {
            this.resetSize();
            this.setMaxBatchSize(Math.min(this.regionDestination.getMaxPageSize(), this.size));
            this.resetBatch();
            this.batchResetNeeded = false;
        }
        if (this.batchList.isEmpty() && this.size > 0) {
            try {
                this.doFillBatch();
            }
            catch (Exception e) {
                LOG.error("{} - Failed to fill batch", (Object)this, (Object)e);
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public final synchronized boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public final synchronized boolean hasMessagesBufferedToDeliver() {
        return !this.batchList.isEmpty();
    }

    @Override
    public final synchronized int size() {
        if (this.size < 0) {
            this.size = this.getStoreSize();
        }
        return this.size;
    }

    @Override
    public final synchronized long messageSize() {
        return this.getStoreMessageSize();
    }

    public String toString() {
        return super.toString() + ":" + this.regionDestination.getActiveMQDestination().getPhysicalName() + ",batchResetNeeded=" + this.batchResetNeeded + ",size=" + this.size + ",cacheEnabled=" + this.isCacheEnabled() + ",maxBatchSize:" + this.maxBatchSize + ",hasSpace:" + this.hasSpace() + ",pendingCachedIds.size:" + this.pendingCachedIds.size() + ",lastSyncCachedId:" + this.lastCachedIds[SYNC_ADD] + ",lastSyncCachedId-seq:" + (this.lastCachedIds[SYNC_ADD] != null ? this.lastCachedIds[SYNC_ADD].getFutureOrSequenceLong() : "null") + ",lastAsyncCachedId:" + this.lastCachedIds[ASYNC_ADD] + ",lastAsyncCachedId-seq:" + (this.lastCachedIds[ASYNC_ADD] != null ? this.lastCachedIds[ASYNC_ADD].getFutureOrSequenceLong() : "null");
    }

    protected abstract void doFillBatch() throws Exception;

    protected abstract void resetBatch();

    protected abstract int getStoreSize();

    protected abstract long getStoreMessageSize();

    protected abstract boolean isStoreEmpty();

    public Subscription getSubscription() {
        return null;
    }
}

