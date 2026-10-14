/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http.params;

import com.incoresoft.middleware.audit.AuditEventAction;
import com.incoresoft.middleware.audit.AuditEventCategory;
import java.util.Set;

public interface AuditTrailParams {
    public Set<? extends AuditEventAction> getActions();

    public Set<? extends AuditEventCategory> getCategories();

    public String getLogin();
}

