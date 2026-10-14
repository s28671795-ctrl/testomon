/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import org.apache.activemq.broker.util.AuditLog;
import org.apache.activemq.broker.util.AuditLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultAuditLog
implements AuditLog {
    private static final Logger LOG = LoggerFactory.getLogger("org.apache.activemq.audit");

    @Override
    public void log(AuditLogEntry entry) {
        LOG.info(entry.toString());
    }
}

