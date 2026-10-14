/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.Security.AuditEventType;
import org.omg.Security.SelectorValue;
import org.omg.SecurityLevel2.AuditChannel;

public interface AuditDecisionOperations {
    public boolean audit_needed(AuditEventType var1, SelectorValue[] var2);

    public AuditChannel audit_channel();
}

