/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Geometry;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

@Pro
final class StX
extends AbstractField<Double>
implements QOM.StX {
    final Field<Geometry> value;

    StX(Field<Geometry> value) {
        super(Names.N_ST_X, Tools.allNotNull(SQLDataType.DOUBLE, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.GEOMETRY);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ORACLE: {
                return false;
            }
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
            case ORACLE: {
                ctx.visit(DSL.field("({0}).sdo_point.x", this.getDataType(), new QueryPart[]{this.value}));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.field("{0}.STX", this.getDataType(), new QueryPart[]{this.value}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_X, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stX(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<Geometry> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.StX $arg1(Field<Geometry> newValue) {
        return this.$constructor().apply((Field<Geometry>)newValue);
    }

    @Override
    public final Function1<? super Field<Geometry>, ? extends QOM.StX> $constructor() {
        return a1 -> new StX((Field<Geometry>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StX) {
            QOM.StX o = (QOM.StX)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

