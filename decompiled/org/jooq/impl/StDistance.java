/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
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
final class StDistance<T extends Spatial>
extends AbstractField<Double>
implements QOM.StDistance<T> {
    final Field<T> arg1;
    final Field<T> arg2;

    StDistance(Field<T> arg1, Field<T> arg2) {
        super(Names.N_ST_DISTANCE, Tools.allNotNull(SQLDataType.DOUBLE, arg1, arg2));
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.GEOMETRY);
        this.arg2 = Tools.nullSafeNotNull(arg2, SQLDataType.GEOMETRY);
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
                ctx.visit(DSL.field("sdo_geom.sdo_distance({0}, {1}, tol => null)", this.getDataType(), new QueryPart[]{this.arg1, this.arg2}));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.field("{0}.STDistance({1})", this.getDataType(), new QueryPart[]{this.arg1, this.arg2}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_DISTANCE, this.getDataType(), this.arg1, this.arg2));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stDistance(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.arg1));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.arg2));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.arg1;
    }

    @Override
    public final Field<T> $arg2() {
        return this.arg2;
    }

    @Override
    public final QOM.StDistance<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.StDistance<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.StDistance<T>> $constructor() {
        return (a1, a2) -> new StDistance(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StDistance) {
            QOM.StDistance o = (QOM.StDistance)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

