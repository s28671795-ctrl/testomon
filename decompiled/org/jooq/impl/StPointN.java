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
final class StPointN<T extends Spatial>
extends AbstractField<T>
implements QOM.StPointN<T> {
    final Field<T> value;
    final Field<? extends Number> position;

    StPointN(Field<T> value, Field<? extends Number> position) {
        super(Names.N_ST_POINTN, Tools.allNotNull(Tools.dataType(SQLDataType.GEOMETRY, value, false), value, position));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.GEOMETRY);
        this.position = Tools.nullSafeNotNull(position, SQLDataType.INTEGER);
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
                ctx.visit(DSL.field("{0}.STPointN({1})", this.getDataType(), new QueryPart[]{this.value, this.position}));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ST_POINTN, this.getDataType(), this.value, this.position));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("stPointN(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.position));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.position;
    }

    @Override
    public final QOM.StPointN<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<Number>)this.$arg2());
    }

    @Override
    public final QOM.StPointN<T> $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<Number>)newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<? extends Number>, ? extends QOM.StPointN<T>> $constructor() {
        return (a1, a2) -> new StPointN(a1, (Field<? extends Number>)((Field<Number>)a2));
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.StPointN) {
            QOM.StPointN o = (QOM.StPointN)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$position(), o.$position());
        }
        return super.equals(that);
    }
}

