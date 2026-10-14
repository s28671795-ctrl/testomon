/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Spatial;
import org.jooq.impl.AbstractCondition;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

@Pro
final class StIsEmpty
extends AbstractCondition
implements QOM.StIsEmpty {
    final Field<? extends Spatial> value;

    StIsEmpty(Field<? extends Spatial> value) {
        this.value = Tools.nullSafeNotNull(value, SQLDataType.GEOMETRY);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case SQLSERVER: {
                return false;
            }
        }
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.condition(DSL.field("{0}.STIsEmpty()", SQLDataType.BOOLEAN, new QueryPart[]{this.value})));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_ISEMPTY, SQLDataType.BOOLEAN, this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stIsEmpty(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Spatial> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.StIsEmpty $arg1(Field<? extends Spatial> newValue) {
        return this.$constructor().apply((Field<? extends Spatial>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Spatial>, ? extends QOM.StIsEmpty> $constructor() {
        return a1 -> new StIsEmpty((Field<? extends Spatial>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StIsEmpty) {
            QOM.StIsEmpty o = (QOM.StIsEmpty)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

