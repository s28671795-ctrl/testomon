/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityLevel2;

import java.util.Hashtable;

public class AuditDecisionIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("audit_channel", "attribute;org.omg.SecurityLevel2.AuditChannel");
        irInfo.put("audit_needed", "(in:event_type ,in:value_list org.omg.Security.SelectorValueList)");
    }
}

