/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import org.omg.Security.AuditEventType;
import org.omg.Security.SelectorValue;
import org.omg.SecurityLevel2.Credentials;
import org.omg.TimeBase.UtcT;

public interface AuditChannelOperations {
    public void audit_write(AuditEventType var1, Credentials[] var2, UtcT var3, SelectorValue[] var4, byte[] var5);

    public int audit_channel_id();
}

