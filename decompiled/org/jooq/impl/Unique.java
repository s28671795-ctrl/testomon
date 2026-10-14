/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.GroupField;
import org.jooq.Pro;
import org.jooq.Select;
import org.jooq.SelectHavingConditionStep;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Unique
extends AbstractCondition
implements QOM.Unique {
    final Select<?> query;

    Unique(Select<?> query) {
        this.query = query;
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case H2: {
                ctx.visit(Keywords.K_UNIQUE).sql(' ');
                Tools.visitSubquery(ctx, this.query, 256);
                break;
            }
            default: {
                Table queryTable = this.query.asTable("t");
                GroupField[] queryFields = queryTable.fields();
                SelectHavingConditionStep subquery = DSL.select(DSL.one()).from((TableLike<?>)queryTable).where(DSL.row(queryFields).isNotNull()).groupBy(queryFields).having(DSL.count().gt(DSL.one()));
                ctx.visit(DSL.notExists(subquery));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("unique(");
        ctx.visitSubquery(this.query);
        ctx.sql(')');
    }

    @Override
    public final Select<?> $arg1() {
        return this.query;
    }

    @Override
    public final QOM.Unique $arg1(Select<?> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Select<?>, ? extends QOM.Unique> $constructor() {
        return a1 -> new Unique((Select<?>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Unique) {
            QOM.Unique o = (QOM.Unique)that;
            return StringUtils.equals(this.$query(), o.$query());
        }
        return super.equals(that);
    }
}

