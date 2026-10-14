/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import org.apache.activemq.broker.util.AuditLog;
import org.apache.activemq.broker.util.AuditLogEntry;
import org.apache.activemq.broker.util.AuditLogFactory;
import org.apache.activemq.broker.util.DefaultAuditLogFactory;

public class AuditLogService {
    private AuditLogFactory factory = new DefaultAuditLogFactory();
    private static AuditLogService auditLog;

    public static AuditLogService getAuditLog() {
        if (auditLog == null) {
            auditLog = new AuditLogService();
        }
        return auditLog;
    }

    private AuditLogService() {
    }

    public void log(AuditLogEntry entry) {
        for (AuditLog log : this.factory.getAuditLogs()) {
            log.log(entry);
        }
    }

    public void setFactory(AuditLogFactory factory2) {
        this.factory = factory2;
    }
}

