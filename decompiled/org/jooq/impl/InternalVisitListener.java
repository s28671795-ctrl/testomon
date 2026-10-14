/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.List;
import org.jooq.Field;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.VisitContext;
import org.jooq.VisitListener;
import org.jooq.impl.DSL;
import org.jooq.impl.Tools;

final class InternalVisitListener
implements VisitListener {
    InternalVisitListener() {
    }

    @Override
    @Pro
    public void visitStart(VisitContext ctx) {
        Object[] object;
        QueryPart part = ctx.queryPart();
        if (part instanceof Field && !ctx.context().declareFields() && (object = (Object[])ctx.data(Tools.SimpleDataKey.DATA_OVERRIDE_ALIASES_IN_ORDER_BY)) != null && !Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_UNALIAS_ALIASED_EXPRESSIONS))) {
            List originalFields = (List)object[0];
            List aliasedFields = (List)object[1];
            for (int i = 0; i < originalFields.size(); ++i) {
                if (!part.equals(originalFields.get(i))) continue;
                ctx.queryPart(DSL.field(DSL.name(((Field)aliasedFields.get(i)).getName())));
            }
        }
    }
}

