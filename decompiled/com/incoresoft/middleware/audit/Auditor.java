/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.audit;

import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.model.user.User;
import io.javalin.http.Context;

public interface Auditor {
    public void audit(AuditEvent var1);

    public void audit(Context var1, User var2, AuditEventAction var3, String var4);

    public void audit(Context var1, User var2, AuditEventAction var3, String var4, Object var5);

    public void audit(Context var1, AuditEventAction var2, String var3);

    public void audit(Context var1, AuditEventAction var2, String var3, Object var4);
}

