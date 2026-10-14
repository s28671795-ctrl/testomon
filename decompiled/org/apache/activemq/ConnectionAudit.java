/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq;

import java.util.LinkedHashMap;
import org.apache.activemq.ActiveMQDispatcher;
import org.apache.activemq.ActiveMQMessageAudit;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.Message;
import org.apache.activemq.util.LRUCache;

class ConnectionAudit {
    private boolean checkForDuplicates;
    private LinkedHashMap<ActiveMQDestination, ActiveMQMessageAudit> destinations = new LRUCache<ActiveMQDestination, ActiveMQMessageAudit>(1000);
    private LinkedHashMap<ActiveMQDispatcher, ActiveMQMessageAudit> dispatchers = new LRUCache<ActiveMQDispatcher, ActiveMQMessageAudit>(1000);
    private int auditDepth = 2048;
    private int auditMaximumProducerNumber = 64;

    ConnectionAudit() {
    }

    synchronized void removeDispatcher(ActiveMQDispatcher dispatcher) {
        this.dispatchers.remove(dispatcher);
    }

    synchronized boolean isDuplicate(ActiveMQDispatcher dispatcher, Message message) {
        ActiveMQDestination destination;
        if (this.checkForDuplicates && message != null && (destination = message.getDestination()) != null) {
            if (destination.isQueue()) {
                ActiveMQMessageAudit audit = this.destinations.get(destination);
                if (audit == null) {
                    audit = new ActiveMQMessageAudit(this.auditDepth, this.auditMaximumProducerNumber);
                    this.destinations.put(destination, audit);
                }
                boolean result = audit.isDuplicate(message);
                return result;
            }
            ActiveMQMessageAudit audit = this.dispatchers.get(dispatcher);
            if (audit == null) {
                audit = new ActiveMQMessageAudit(this.auditDepth, this.auditMaximumProducerNumber);
                this.dispatchers.put(dispatcher, audit);
            }
            boolean result = audit.isDuplicate(message);
            return result;
        }
        return false;
    }

    protected synchronized void rollbackDuplicate(ActiveMQDispatcher dispatcher, Message message) {
        ActiveMQDestination destination;
        if (this.checkForDuplicates && message != null && (destination = message.getDestination()) != null) {
            if (destination.isQueue()) {
                ActiveMQMessageAudit audit = this.destinations.get(destination);
                if (audit != null) {
                    audit.rollback(message);
                }
            } else {
                ActiveMQMessageAudit audit = this.dispatchers.get(dispatcher);
                if (audit != null) {
                    audit.rollback(message);
                }
            }
        }
    }

    boolean isCheckForDuplicates() {
        return this.checkForDuplicates;
    }

    void setCheckForDuplicates(boolean checkForDuplicates) {
        this.checkForDuplicates = checkForDuplicates;
    }

    public int getAuditDepth() {
        return this.auditDepth;
    }

    public void setAuditDepth(int auditDepth) {
        this.auditDepth = auditDepth;
    }

    public int getAuditMaximumProducerNumber() {
        return this.auditMaximumProducerNumber;
    }

    public void setAuditMaximumProducerNumber(int auditMaximumProducerNumber) {
        this.auditMaximumProducerNumber = auditMaximumProducerNumber;
    }
}

