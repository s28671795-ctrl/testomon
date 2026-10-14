/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.database.converters;

import com.google.inject.Inject;
import com.incoresoft.audit.AuditEventActionsManager;
import com.incoresoft.audit.FallbackAuditAction;
import com.incoresoft.database.converters.AuditRecordConverter;
import com.incoresoft.middleware.audit.AuditEventAction;
import java.util.UUID;

public class VezhaAuditRecordConverter
extends AuditRecordConverter {
    private final AuditEventActionsManager auditEventActionsManager;

    @Override
    protected AuditEventAction getActionById(UUID id) {
        return this.auditEventActionsManager.getAction(id).orElseGet(FallbackAuditAction::new);
    }

    @Inject
    public VezhaAuditRecordConverter(AuditEventActionsManager auditEventActionsManager) {
        this.auditEventActionsManager = auditEventActionsManager;
    }
}

