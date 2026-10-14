/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class IsNotNull
extends AbstractCondition
implements QOM.IsNotNull {
    final Field<?> field;
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONDITION, Clause.CONDITION_IS_NOT_NULL};

    IsNotNull(Field<?> field2) {
        this.field = Tools.nullSafeNotNull(field2, SQLDataType.OTHER);
    }

    @Override
    final boolean isNullable() {
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
        } else if (this.field.getDataType().isEmbeddable()) {
            ctx.visit(DSL.row(Tools.embeddedFields(this.field)).isNotNull());
        } else {
            ctx.visit(this.field).sql(' ').visit(Keywords.K_IS_NOT_NULL);
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.field).sql(".isNotNull()");
    }

    @Override
    public final Field<?> $arg1() {
        return this.field;
    }

    @Override
    public final QOM.IsNotNull $arg1(Field<?> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<?>, ? extends QOM.IsNotNull> $constructor() {
        return a1 -> new IsNotNull((Field<?>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.IsNotNull) {
            QOM.IsNotNull o = (QOM.IsNotNull)that;
            return StringUtils.equals(this.$field(), o.$field());
        }
        return super.equals(that);
    }
}

