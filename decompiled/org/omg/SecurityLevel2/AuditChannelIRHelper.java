/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import java.util.Hashtable;

public class AuditChannelIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("audit_write", "(in:event_type ,in:creds org.omg.SecurityLevel2.CredentialsList,in:time org.omg.Security.UtcT,in:descriptors org.omg.Security.SelectorValueList,in:event_specific_data org.omg.Security.Opaque)");
        irInfo.put("audit_channel_id", "attribute;org.omg.Security.AuditChannelId");
    }
}

