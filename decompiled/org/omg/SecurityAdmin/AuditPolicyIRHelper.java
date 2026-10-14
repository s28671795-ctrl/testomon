/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import java.util.Hashtable;

public class AuditPolicyIRHelper {
    public static Hashtable irInfo = new Hashtable();

    static {
        irInfo.put("set_audit_selectors", "(in:object_type org.omg.CORBA.RepositoryId,in:events org.omg.Security.AuditEventTypeList,in:selectors org.omg.Security.SelectorValueList,in:audit_combinator )");
        irInfo.put("replace_audit_selectors", "(in:object_type org.omg.CORBA.RepositoryId,in:events org.omg.Security.AuditEventTypeList,in:selectors org.omg.Security.SelectorValueList,in:audit_combinator )");
        irInfo.put("set_audit_channel", "(in:audit_channel_id org.omg.Security.AuditChannelId)");
        irInfo.put("clear_audit_selectors", "(in:object_type org.omg.CORBA.RepositoryId,in:events org.omg.Security.AuditEventTypeList)");
        irInfo.put("get_audit_selectors", "(in:object_type org.omg.CORBA.RepositoryId,in:event_type ,out:selectors org.omg.Security.SelectorValueList,out:audit_combinator )");
    }
}

