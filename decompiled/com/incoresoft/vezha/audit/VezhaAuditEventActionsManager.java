/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.audit;

import com.google.inject.Inject;
import com.incoresoft.audit.AuditEventActionsManager;
import com.incoresoft.audit.CoreAuditEventAction;
import com.incoresoft.plugin.commons.audit.AuditEventActions;
import com.incoresoft.vezha.audit.VezhaAuditEventAction;
import java.util.Set;

public class VezhaAuditEventActionsManager
extends AuditEventActionsManager {
    @Inject
    public VezhaAuditEventActionsManager() {
        this.registerAuditActions("middleware-core", Set.of(VezhaAuditEventAction.values()));
        this.registerAuditActions("middleware-core", Set.of(CoreAuditEventAction.values()));
        this.registerAuditActions("middleware-core", Set.of(AuditEventActions.values()));
    }
}

