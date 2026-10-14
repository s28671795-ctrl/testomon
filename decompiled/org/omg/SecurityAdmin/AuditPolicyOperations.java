/*
 * Decompiled with CFR 0.152.
 */
package org.omg.SecurityAdmin;

import org.omg.CORBA.PolicyOperations;
import org.omg.Security.AuditCombinator;
import org.omg.Security.AuditCombinatorHolder;
import org.omg.Security.AuditEventType;
import org.omg.Security.SelectorValue;
import org.omg.Security.SelectorValueListHolder;

public interface AuditPolicyOperations
extends PolicyOperations {
    public void set_audit_selectors(String var1, AuditEventType[] var2, SelectorValue[] var3, AuditCombinator var4);

    public void clear_audit_selectors(String var1, AuditEventType[] var2);

    public void replace_audit_selectors(String var1, AuditEventType[] var2, SelectorValue[] var3, AuditCombinator var4);

    public void get_audit_selectors(String var1, AuditEventType var2, SelectorValueListHolder var3, AuditCombinatorHolder var4);

    public void set_audit_channel(int var1);
}

