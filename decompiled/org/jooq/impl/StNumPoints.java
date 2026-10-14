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
final class StNumPoints
extends AbstractField<Integer>
implements QOM.StNumPoints {
    final Field<? extends Spatial> value;

    StNumPoints(Field<? extends Spatial> value) {
        super(Names.N_ST_NUMPOINTS, Tools.allNotNull(SQLDataType.INTEGER, value));
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
                ctx.visit(DSL.field("{0}.STNumPoints()", this.getDataType(), new QueryPart[]{this.value}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_NUMPOINTS, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stNumPoints(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Spatial> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.StNumPoints $arg1(Field<? extends Spatial> newValue) {
        return this.$constructor().apply((Field<? extends Spatial>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Spatial>, ? extends QOM.StNumPoints> $constructor() {
        return a1 -> new StNumPoints((Field<? extends Spatial>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StNumPoints) {
            QOM.StNumPoints o = (QOM.StNumPoints)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

