/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;
import org.jooq.TableLike;
import org.jooq.impl.AbstractResultQuery;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.DSL;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordImpl1;

final class FetchCount
extends AbstractResultQuery<Record1<Integer>>
implements QOM.UEmpty {
    private final Field<?>[] count = new Field[]{DSL.count().as("c")};
    private final Select<?> query;

    FetchCount(Configuration configuration, Select<?> query) {
        super(configuration);
        this.query = query;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(DSL.select(this.count).from((TableLike<?>)((Object)new AliasedSelect(this.query, true, true, false).as("t"))));
    }

    @Override
    public final Class<? extends Record1<Integer>> getRecordType0() {
        return RecordImpl1.class;
    }

    @Override
    public final Field<?>[] getFields() {
        return this.count;
    }
}

