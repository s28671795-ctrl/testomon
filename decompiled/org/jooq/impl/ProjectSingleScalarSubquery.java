/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Select;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.TableLike;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.Tools;

final class ProjectSingleScalarSubquery<T>
extends AbstractField<T>
implements QOM.UTransient {
    final Select<?> query;
    final int index;

    ProjectSingleScalarSubquery(Select<?> query, int index) {
        super(Names.NQ_SELECT, query.getSelect().get(index).getDataType());
        this.query = query;
        this.index = index;
    }

    @Override
    public final void accept(Context<?> ctx) {
        SelectQueryImpl q = Tools.selectQueryImpl(this.query);
        if (q.$distinct() || !q.$orderBy().isEmpty() || q.hasUnions()) {
            Field<?>[] f = Tools.fields(this.query.getSelect().size());
            ctx.visit(DSL.field(DSL.select(f[this.index]).from((TableLike<?>)this.query.asTable(DSL.table(DSL.name("t")), f))));
        } else {
            ctx.visit(DSL.field(this.query.$select(Arrays.asList((SelectFieldOrAsterisk)this.query.$select().get(this.index)))));
        }
    }
}

