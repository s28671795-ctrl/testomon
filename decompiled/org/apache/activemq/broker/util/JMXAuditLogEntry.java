/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker.util;

import java.util.Arrays;
import org.apache.activemq.broker.util.AuditLogEntry;

public class JMXAuditLogEntry
extends AuditLogEntry {
    public static final String[] VERBS = new String[]{" called ", " ended "};
    private int state = 0;
    protected String target;

    public void complete() {
        this.setTimestamp(System.currentTimeMillis());
        this.state = 1;
    }

    public String getTarget() {
        return this.target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String toString() {
        return this.user.trim() + VERBS[this.state] + this.operation + Arrays.toString((Object[])this.parameters.get("arguments")) + (this.target != null ? " on " + this.target : "") + " at " + this.getFormattedTime();
    }
}

