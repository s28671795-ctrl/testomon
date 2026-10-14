/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Param;
import org.jooq.Record;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;

final class RecordCondition
extends AbstractCondition
implements QOM.UEmpty {
    private final Record record;

    RecordCondition(Record record) {
        this.record = record;
    }

    @Override
    public void accept(Context<?> ctx) {
        Condition c = DSL.noCondition();
        int size = this.record.size();
        for (int i = 0; i < size; ++i) {
            Object value = this.record.get(i);
            if (value == null) continue;
            Field<?> f1 = this.record.field(i);
            Param f2 = DSL.val(value, f1.getDataType());
            c = c.and(f1.eq(f2));
        }
        ctx.visit(c);
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return null;
    }
}

