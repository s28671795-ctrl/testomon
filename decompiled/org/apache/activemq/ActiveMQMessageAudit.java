/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import org.apache.activemq.ActiveMQMessageAuditNoSync;
import org.apache.activemq.command.MessageId;

public class ActiveMQMessageAudit
extends ActiveMQMessageAuditNoSync {
    private static final long serialVersionUID = 1L;

    public ActiveMQMessageAudit() {
    }

    public ActiveMQMessageAudit(int auditDepth, int maximumNumberOfProducersToTrack) {
        super(auditDepth, maximumNumberOfProducersToTrack);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isDuplicate(String id) {
        ActiveMQMessageAudit activeMQMessageAudit = this;
        synchronized (activeMQMessageAudit) {
            return super.isDuplicate(id);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isDuplicate(MessageId id) {
        ActiveMQMessageAudit activeMQMessageAudit = this;
        synchronized (activeMQMessageAudit) {
            return super.isDuplicate(id);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void rollback(MessageId id) {
        ActiveMQMessageAudit activeMQMessageAudit = this;
        synchronized (activeMQMessageAudit) {
            super.rollback(id);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isInOrder(String id) {
        ActiveMQMessageAudit activeMQMessageAudit = this;
        synchronized (activeMQMessageAudit) {
            return super.isInOrder(id);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isInOrder(MessageId id) {
        ActiveMQMessageAudit activeMQMessageAudit = this;
        synchronized (activeMQMessageAudit) {
            return super.isInOrder(id);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setMaximumNumberOfProducersToTrack(int maximumNumberOfProducersToTrack) {
        ActiveMQMessageAudit activeMQMessageAudit = this;
        synchronized (activeMQMessageAudit) {
            super.setMaximumNumberOfProducersToTrack(maximumNumberOfProducersToTrack);
        }
    }
}

