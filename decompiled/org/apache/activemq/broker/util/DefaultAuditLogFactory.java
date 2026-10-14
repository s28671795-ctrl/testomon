/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import org.apache.activemq.broker.util.AuditLog;
import org.apache.activemq.broker.util.AuditLogFactory;
import org.apache.activemq.broker.util.DefaultAuditLog;

public class DefaultAuditLogFactory
implements AuditLogFactory {
    private ArrayList<AuditLog> auditLogs = new ArrayList();

    public DefaultAuditLogFactory() {
        ServiceLoader<AuditLog> logs = ServiceLoader.load(AuditLog.class);
        for (AuditLog log : logs) {
            this.auditLogs.add(log);
        }
        if (this.auditLogs.size() == 0) {
            this.auditLogs.add(new DefaultAuditLog());
        }
    }

    @Override
    public List<AuditLog> getAuditLogs() {
        return this.auditLogs;
    }
}

