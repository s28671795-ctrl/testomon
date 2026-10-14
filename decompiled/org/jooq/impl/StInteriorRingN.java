/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
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
final class StInteriorRingN
extends AbstractField<Geometry>
implements QOM.StInteriorRingN {
    final Field<Geometry> value;
    final Field<? extends Number> position;

    StInteriorRingN(Field<Geometry> value, Field<? extends Number> position) {
        super(Names.N_ST_INTERIORRINGN, Tools.allNotNull(SQLDataType.GEOMETRY, value, position));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.GEOMETRY);
        this.position = Tools.nullSafeNotNull(position, SQLDataType.INTEGER);
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
                ctx.visit(DSL.field("sdo_util.extract({0}, 1, {1} + 1)", this.getDataType(), new QueryPart[]{this.value, this.position}));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.field("{0}.STInteriorRingN({1})", this.getDataType(), new QueryPart[]{this.value, this.position}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_INTERIORRINGN, this.getDataType(), this.value, this.position));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stInteriorRingN(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.position));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<Geometry> $arg1() {
        return this.value;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.position;
    }

    @Override
    public final QOM.StInteriorRingN $arg1(Field<Geometry> newValue) {
        return this.$constructor().apply((Field<Geometry>)newValue, (Field<? extends Number>)this.$arg2());
    }

    @Override
    public final QOM.StInteriorRingN $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<Geometry>)this.$arg1(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function2<? super Field<Geometry>, ? super Field<? extends Number>, ? extends QOM.StInteriorRingN> $constructor() {
        return (a1, a2) -> new StInteriorRingN((Field<Geometry>)a1, (Field<? extends Number>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StInteriorRingN) {
            QOM.StInteriorRingN o = (QOM.StInteriorRingN)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$position(), o.$position());
        }
        return super.equals(that);
    }
}

