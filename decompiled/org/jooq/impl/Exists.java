/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.Select;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class Exists
extends AbstractCondition
implements QOM.Exists {
    final Select<?> query;
    private static final Clause[] CLAUSES_EXISTS = new Clause[]{Clause.CONDITION, Clause.CONDITION_EXISTS};

    Exists(Select<?> query) {
        this.query = query;
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            default: {
                ctx.visit(Keywords.K_EXISTS).sql(' ');
                Tools.visitSubquery(ctx, this.query, 256);
            }
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES_EXISTS;
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("exists(");
        ctx.visitSubquery(this.query);
        ctx.sql(')');
    }

    @Override
    public final Select<?> $arg1() {
        return this.query;
    }

    @Override
    public final QOM.Exists $arg1(Select<?> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Select<?>, ? extends QOM.Exists> $constructor() {
        return a1 -> new Exists((Select<?>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Exists) {
            QOM.Exists o = (QOM.Exists)that;
            return StringUtils.equals(this.$query(), o.$query());
        }
        return super.equals(that);
    }
}

