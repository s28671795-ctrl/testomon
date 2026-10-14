/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region;

import org.apache.activemq.broker.region.LockOwner;
import org.apache.activemq.broker.region.QueueMessageReference;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.Message;
import org.apache.activemq.command.MessageId;

public class IndirectMessageReference
implements QueueMessageReference {
    private LockOwner lockOwner;
    private boolean dropped;
    private boolean acked;
    private final Message message;
    private final MessageId messageId;

    public IndirectMessageReference(Message message) {
        this.message = message;
        this.messageId = message.getMessageId().copy();
        message.getMessageId();
        message.getGroupID();
        message.getGroupSequence();
    }

    @Override
    public Message getMessageHardRef() {
        return this.message;
    }

    @Override
    public int getReferenceCount() {
        return this.message.getReferenceCount();
    }

    @Override
    public int incrementReferenceCount() {
        return this.message.incrementReferenceCount();
    }

    @Override
    public int decrementReferenceCount() {
        return this.message.decrementReferenceCount();
    }

    @Override
    public Message getMessage() {
        return this.message;
    }

    public String toString() {
        return "Message " + this.message.getMessageId() + " dropped=" + this.dropped + " acked=" + this.acked + " locked=" + (this.lockOwner != null);
    }

    @Override
    public void incrementRedeliveryCounter() {
        this.message.incrementRedeliveryCounter();
    }

    @Override
    public synchronized boolean isDropped() {
        return this.dropped;
    }

    @Override
    public synchronized void drop() {
        this.dropped = true;
        this.lockOwner = null;
        this.message.decrementReferenceCount();
    }

    @Override
    public synchronized boolean dropIfLive() {
        if (this.isDropped()) {
            return false;
        }
        this.drop();
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean lock(LockOwner subscription) {
        IndirectMessageReference indirectMessageReference = this;
        synchronized (indirectMessageReference) {
            if (this.dropped || this.lockOwner != null) {
                return false;
            }
            this.lockOwner = subscription;
            return true;
        }
    }

    @Override
    public synchronized boolean unlock() {
        boolean result = this.lockOwner != null;
        this.lockOwner = null;
        return result;
    }

    @Override
    public synchronized LockOwner getLockOwner() {
        return this.lockOwner;
    }

    @Override
    public int getRedeliveryCounter() {
        return this.message.getRedeliveryCounter();
    }

    @Override
    public MessageId getMessageId() {
        return this.messageId;
    }

    @Override
    public Message.MessageDestination getRegionDestination() {
        return this.message.getRegionDestination();
    }

    @Override
    public boolean isPersistent() {
        return this.message.isPersistent();
    }

    public synchronized boolean isLocked() {
        return this.lockOwner != null;
    }

    @Override
    public synchronized boolean isAcked() {
        return this.acked;
    }

    @Override
    public synchronized void setAcked(boolean b) {
        this.acked = b;
    }

    @Override
    public String getGroupID() {
        return this.message.getGroupID();
    }

    @Override
    public int getGroupSequence() {
        return this.message.getGroupSequence();
    }

    @Override
    public ConsumerId getTargetConsumerId() {
        return this.message.getTargetConsumerId();
    }

    @Override
    public long getExpiration() {
        return this.message.getExpiration();
    }

    @Override
    public boolean isExpired() {
        return this.message.isExpired();
    }

    @Override
    public synchronized int getSize() {
        return this.message.getSize();
    }

    @Override
    public boolean isAdvisory() {
        return this.message.isAdvisory();
    }

    @Override
    public boolean canProcessAsExpired() {
        return this.message.canProcessAsExpired();
    }
}

