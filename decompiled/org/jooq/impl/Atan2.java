/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Atan2
extends AbstractField<BigDecimal>
implements QOM.Atan2 {
    final Field<? extends Number> x;
    final Field<? extends Number> y;

    Atan2(Field<? extends Number> x, Field<? extends Number> y) {
        super(Names.N_ATAN2, Tools.allNotNull(SQLDataType.NUMERIC, x, y));
        this.x = Tools.nullSafeNotNull(x, SQLDataType.INTEGER);
        this.y = Tools.nullSafeNotNull(y, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: {
                ctx.visit(DSL.function(Names.N_ATN2, this.getDataType(), this.x, this.y));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_ATAN2, this.getDataType(), this.x, this.y));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("atan2(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.x));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.y));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.x;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.y;
    }

    @Override
    public final QOM.Atan2 $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue, (Field<? extends Number>)this.$arg2());
    }

    @Override
    public final QOM.Atan2 $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)this.$arg1(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function2<? super Field<? extends Number>, ? super Field<? extends Number>, ? extends QOM.Atan2> $constructor() {
        return (a1, a2) -> new Atan2((Field<? extends Number>)a1, (Field<? extends Number>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Atan2) {
            QOM.Atan2 o = (QOM.Atan2)that;
            return StringUtils.equals(this.$x(), o.$x()) && StringUtils.equals(this.$y(), o.$y());
        }
        return super.equals(that);
    }
}

