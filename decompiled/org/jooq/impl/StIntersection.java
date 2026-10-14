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
final class StIntersection<T extends Spatial>
extends AbstractField<T>
implements QOM.StIntersection<T> {
    final Field<T> arg1;
    final Field<T> arg2;

    StIntersection(Field<T> arg1, Field<T> arg2) {
        super(Names.N_ST_INTERSECTION, Tools.allNotNull(Tools.dataType(SQLDataType.GEOMETRY, arg1, false), arg1, arg2));
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
                ctx.visit(DSL.field("sdo_geom.sdo_intersection({0}, {1}, tol => null)", this.getDataType(), new QueryPart[]{this.arg1, this.arg2}));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.field("{0}.STIntersection({1})", this.getDataType(), new QueryPart[]{this.arg1, this.arg2}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_INTERSECTION, this.getDataType(), this.arg1, this.arg2));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stIntersection(");
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
    public final QOM.StIntersection<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.StIntersection<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.StIntersection<T>> $constructor() {
        return (a1, a2) -> new StIntersection(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StIntersection) {
            QOM.StIntersection o = (QOM.StIntersection)that;
            return StringUtils.equals(this.$arg1(), o.$arg1()) && StringUtils.equals(this.$arg2(), o.$arg2());
        }
        return super.equals(that);
    }
}

