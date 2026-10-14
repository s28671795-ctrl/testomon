/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.generated;

import com.incoresoft.generated.tables.AuditTrail;
import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

public class Indexes {
    public static final Index AUDIT_EVENT_ACTION_INDEX = Internal.createIndex(DSL.name("audit-event_action-index"), AuditTrail.AUDIT_TRAIL, new OrderField[]{AuditTrail.AUDIT_TRAIL.EVENT_ACTION_ID}, false);
    public static final Index AUDIT_EVENT_CATEGORY_INDEX = Internal.createIndex(DSL.name("audit-event_category-index"), AuditTrail.AUDIT_TRAIL, new OrderField[]{AuditTrail.AUDIT_TRAIL.EVENT_CATEGORY_ID}, false);
}

