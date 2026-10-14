/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.util.List;
import org.apache.activemq.broker.util.AuditLog;

public interface AuditLogFactory {
    public List<AuditLog> getAuditLogs();
}

