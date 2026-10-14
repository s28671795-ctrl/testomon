/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.region.policy;

import org.apache.activemq.ActiveMQMessageAudit;
import org.apache.activemq.broker.region.policy.DeadLetterStrategy;
import org.apache.activemq.command.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDeadLetterStrategy
implements DeadLetterStrategy {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDeadLetterStrategy.class);
    private boolean processNonPersistent = false;
    private boolean processExpired = true;
    private boolean enableAudit = true;
    private final ActiveMQMessageAudit messageAudit = new ActiveMQMessageAudit();
    private long expiration;

    @Override
    public void rollback(Message message) {
        if (message != null && this.enableAudit) {
            this.messageAudit.rollback(message);
        }
    }

    @Override
    public boolean isSendToDeadLetterQueue(Message message) {
        boolean result = false;
        if (message != null) {
            result = true;
            if (this.enableAudit && this.messageAudit.isDuplicate(message)) {
                result = false;
                LOG.debug("Not adding duplicate to DLQ: {}, dest: {}", (Object)message.getMessageId(), (Object)message.getDestination());
            }
            if (!message.isPersistent() && !this.processNonPersistent) {
                result = false;
            }
            if (message.isExpired() && !this.processExpired) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean isProcessExpired() {
        return this.processExpired;
    }

    @Override
    public void setProcessExpired(boolean processExpired) {
        this.processExpired = processExpired;
    }

    @Override
    public boolean isProcessNonPersistent() {
        return this.processNonPersistent;
    }

    @Override
    public void setProcessNonPersistent(boolean processNonPersistent) {
        this.processNonPersistent = processNonPersistent;
    }

    public boolean isEnableAudit() {
        return this.enableAudit;
    }

    public void setEnableAudit(boolean enableAudit) {
        this.enableAudit = enableAudit;
    }

    @Override
    public long getExpiration() {
        return this.expiration;
    }

    @Override
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public int getMaxProducersToAudit() {
        return this.messageAudit.getMaximumNumberOfProducersToTrack();
    }

    public void setMaxProducersToAudit(int maxProducersToAudit) {
        this.messageAudit.setMaximumNumberOfProducersToTrack(maxProducersToAudit);
    }

    public void setMaxAuditDepth(int maxAuditDepth) {
        this.messageAudit.setAuditDepth(maxAuditDepth);
    }

    public int getMaxAuditDepth() {
        return this.messageAudit.getAuditDepth();
    }
}

