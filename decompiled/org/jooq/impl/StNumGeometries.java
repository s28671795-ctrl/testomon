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
final class StNumGeometries
extends AbstractField<Integer>
implements QOM.StNumGeometries {
    final Field<? extends Spatial> value;

    StNumGeometries(Field<? extends Spatial> value) {
        super(Names.N_ST_NUMGEOMETRIES, Tools.allNotNull(SQLDataType.INTEGER, value));
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
                ctx.visit(DSL.field("sdo_util.getnumelem({0})", this.getDataType(), new QueryPart[]{this.value}));
                break;
            }
            case SQLSERVER: {
                ctx.visit(DSL.field("{0}.STNumGeometries()", this.getDataType(), new QueryPart[]{this.value}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_NUMGEOMETRIES, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stNumGeometries(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Spatial> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.StNumGeometries $arg1(Field<? extends Spatial> newValue) {
        return this.$constructor().apply((Field<? extends Spatial>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Spatial>, ? extends QOM.StNumGeometries> $constructor() {
        return a1 -> new StNumGeometries((Field<? extends Spatial>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StNumGeometries) {
            QOM.StNumGeometries o = (QOM.StNumGeometries)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

