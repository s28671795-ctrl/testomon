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
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

@Pro
final class StStartPoint<T extends Spatial>
extends AbstractField<T>
implements QOM.StStartPoint<T> {
    final Field<T> value;

    StStartPoint(Field<T> value) {
        super(Names.N_ST_STARTPOINT, Tools.allNotNull(Tools.dataType(SQLDataType.GEOMETRY, value, false), value));
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
                ctx.visit(DSL.field("{0}.STStartPoint()", this.getDataType(), new QueryPart[]{this.value}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_STARTPOINT, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stStartPoint(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.StStartPoint<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.StStartPoint<T>> $constructor() {
        return a1 -> new StStartPoint(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StStartPoint) {
            QOM.StStartPoint o = (QOM.StStartPoint)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

