/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.cursors;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.activemq.ActiveMQMessageAudit;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.region.Destination;
import org.apache.activemq.broker.region.MessageReference;
import org.apache.activemq.broker.region.Subscription;
import org.apache.activemq.broker.region.cursors.PendingMessageCursor;
import org.apache.activemq.command.MessageId;
import org.apache.activemq.usage.MemoryUsage;
import org.apache.activemq.usage.SystemUsage;

public abstract class AbstractPendingMessageCursor
implements PendingMessageCursor {
    protected int memoryUsageHighWaterMark = 70;
    protected int maxBatchSize = 200;
    protected SystemUsage systemUsage;
    protected int maxProducersToAudit = 64;
    protected int maxAuditDepth = 10000;
    protected boolean enableAudit = true;
    protected ActiveMQMessageAudit audit;
    protected boolean useCache = true;
    private boolean cacheEnabled = true;
    private boolean started = false;
    protected MessageReference last = null;
    protected final boolean prioritizedMessages;

    public AbstractPendingMessageCursor(boolean prioritizedMessages) {
        this.prioritizedMessages = prioritizedMessages;
    }

    @Override
    public synchronized void start() throws Exception {
        if (!this.started && this.enableAudit && this.audit == null) {
            this.audit = new ActiveMQMessageAudit(this.maxAuditDepth, this.maxProducersToAudit);
        }
        this.started = true;
    }

    @Override
    public synchronized void stop() throws Exception {
        this.started = false;
        this.gc();
    }

    @Override
    public void add(ConnectionContext context, Destination destination) throws Exception {
    }

    @Override
    public List<MessageReference> remove(ConnectionContext context, Destination destination) throws Exception {
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean isRecoveryRequired() {
        return true;
    }

    @Override
    public void addMessageFirst(MessageReference node) throws Exception {
    }

    @Override
    public boolean addMessageLast(MessageReference node) throws Exception {
        return this.tryAddMessageLast(node, 0L);
    }

    @Override
    public boolean tryAddMessageLast(MessageReference node, long maxWaitTime) throws Exception {
        return true;
    }

    @Override
    public void addRecoveredMessage(MessageReference node) throws Exception {
        this.addMessageLast(node);
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isEmpty(Destination destination) {
        return this.isEmpty();
    }

    @Override
    public MessageReference next() {
        return null;
    }

    @Override
    public void remove() {
    }

    @Override
    public void reset() {
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int getMaxBatchSize() {
        return this.maxBatchSize;
    }

    @Override
    public void setMaxBatchSize(int maxBatchSize) {
        this.maxBatchSize = maxBatchSize;
    }

    protected void fillBatch() throws Exception {
    }

    @Override
    public void resetForGC() {
        this.reset();
    }

    @Override
    public void remove(MessageReference node) {
    }

    @Override
    public void gc() {
    }

    @Override
    public void setSystemUsage(SystemUsage usageManager) {
        this.systemUsage = usageManager;
    }

    @Override
    public boolean hasSpace() {
        return this.systemUsage != null ? !this.isParentFull() && this.systemUsage.getMemoryUsage().getPercentUsage() < this.memoryUsageHighWaterMark : true;
    }

    boolean parentHasSpace(int waterMark) {
        boolean result = true;
        if (this.systemUsage != null && this.systemUsage.getMemoryUsage().getParent() != null) {
            return ((MemoryUsage)this.systemUsage.getMemoryUsage().getParent()).getPercentUsage() <= waterMark;
        }
        return result;
    }

    private boolean isParentFull() {
        boolean result = false;
        if (this.systemUsage != null && this.systemUsage.getMemoryUsage().getParent() != null) {
            return ((MemoryUsage)this.systemUsage.getMemoryUsage().getParent()).getPercentUsage() >= 100;
        }
        return result;
    }

    @Override
    public boolean isFull() {
        return this.systemUsage != null ? this.systemUsage.getMemoryUsage().isFull() : false;
    }

    @Override
    public void release() {
    }

    @Override
    public boolean hasMessagesBufferedToDeliver() {
        return false;
    }

    @Override
    public int getMemoryUsageHighWaterMark() {
        return this.memoryUsageHighWaterMark;
    }

    @Override
    public void setMemoryUsageHighWaterMark(int memoryUsageHighWaterMark) {
        this.memoryUsageHighWaterMark = memoryUsageHighWaterMark;
    }

    @Override
    public SystemUsage getSystemUsage() {
        return this.systemUsage;
    }

    @Override
    public void destroy() throws Exception {
        this.stop();
    }

    @Override
    public LinkedList<MessageReference> pageInList(int maxItems) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public synchronized int getMaxProducersToAudit() {
        return this.maxProducersToAudit;
    }

    @Override
    public synchronized void setMaxProducersToAudit(int maxProducersToAudit) {
        this.maxProducersToAudit = maxProducersToAudit;
        if (this.audit != null) {
            this.audit.setMaximumNumberOfProducersToTrack(maxProducersToAudit);
        }
    }

    @Override
    public synchronized int getMaxAuditDepth() {
        return this.maxAuditDepth;
    }

    @Override
    public synchronized void setMaxAuditDepth(int maxAuditDepth) {
        this.maxAuditDepth = maxAuditDepth;
        if (this.audit != null) {
            this.audit.setAuditDepth(maxAuditDepth);
        }
    }

    @Override
    public boolean isEnableAudit() {
        return this.enableAudit;
    }

    @Override
    public synchronized void setEnableAudit(boolean enableAudit) {
        this.enableAudit = enableAudit;
        if (enableAudit && this.started && this.audit == null) {
            this.audit = new ActiveMQMessageAudit(this.maxAuditDepth, this.maxProducersToAudit);
        }
    }

    @Override
    public boolean isTransient() {
        return false;
    }

    @Override
    public void setMessageAudit(ActiveMQMessageAudit audit) {
        this.audit = audit;
    }

    @Override
    public ActiveMQMessageAudit getMessageAudit() {
        return this.audit;
    }

    @Override
    public boolean isUseCache() {
        return this.useCache;
    }

    @Override
    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public synchronized boolean isDuplicate(MessageId messageId) {
        boolean unique = this.recordUniqueId(messageId);
        this.rollback(messageId);
        return !unique;
    }

    public synchronized boolean recordUniqueId(MessageId messageId) {
        if (!this.enableAudit || this.audit == null) {
            return true;
        }
        return !this.audit.isDuplicate(messageId);
    }

    @Override
    public synchronized void rollback(MessageId id) {
        if (this.audit != null) {
            this.audit.rollback(id);
        }
    }

    public synchronized boolean isStarted() {
        return this.started;
    }

    public static boolean isPrioritizedMessageSubscriber(Broker broker, Subscription sub) {
        boolean result = false;
        Set<Destination> destinations = broker.getDestinations(sub.getActiveMQDestination());
        if (destinations != null) {
            for (Destination dest : destinations) {
                if (!dest.isPrioritizedMessages()) continue;
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public synchronized boolean isCacheEnabled() {
        return this.cacheEnabled;
    }

    public synchronized void setCacheEnabled(boolean val) {
        this.cacheEnabled = val;
    }

    @Override
    public void rebase() {
    }
}

