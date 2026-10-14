/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.audit.template.update;

import com.incoresoft.plugin.commons.audit.template.AuditMessage;
import com.incoresoft.plugin.commons.audit.template.update.ObjectUpdateChangesAnalyzer;
import com.incoresoft.plugin.commons.audit.template.update.changes.FieldChange;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ObjectUpdateAuditMessage<T>
extends AuditMessage {
    private static final Logger log = LoggerFactory.getLogger(ObjectUpdateAuditMessage.class);
    private static final ObjectUpdateChangesAnalyzer CHANGES_ANALYZER = new ObjectUpdateChangesAnalyzer();
    private final List<FieldChange> changes;

    public ObjectUpdateAuditMessage(T old, T updated) {
        List<FieldChange> changes;
        try {
            changes = CHANGES_ANALYZER.getChanges(old, updated);
        }
        catch (Exception e) {
            log.error("Failed to get objects changes when creating updated system entity message", e);
            changes = List.of();
        }
        this.changes = changes;
    }

    public List<FieldChange> getChanges() {
        return this.changes;
    }
}

