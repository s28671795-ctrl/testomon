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
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Power
extends AbstractField<BigDecimal>
implements QOM.Power {
    final Field<? extends Number> base;
    final Field<? extends Number> exponent;

    Power(Field<? extends Number> base, Field<? extends Number> exponent) {
        super(Names.N_POWER, Tools.allNotNull(SQLDataType.NUMERIC, base, exponent));
        this.base = Tools.nullSafeNotNull(base, SQLDataType.INTEGER);
        this.exponent = Tools.nullSafeNotNull(exponent, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: {
                return false;
            }
            case DERBY: {
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
            case ACCESS: {
                ctx.sql('(').visit(this.base).sql(" ^ ").visit(this.exponent).sql(')');
                break;
            }
            case DERBY: {
                ctx.visit(DSL.exp(Internal.imul(DSL.ln(this.base), this.exponent)));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_POWER, this.getDataType(), this.base, this.exponent));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(Val.getJavaValue(this.base)).sql(".power(").visit(Val.getJavaValue(this.exponent)).sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.base;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.exponent;
    }

    @Override
    public final QOM.Power $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue, (Field<? extends Number>)this.$arg2());
    }

    @Override
    public final QOM.Power $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)this.$arg1(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function2<? super Field<? extends Number>, ? super Field<? extends Number>, ? extends QOM.Power> $constructor() {
        return (a1, a2) -> new Power((Field<? extends Number>)a1, (Field<? extends Number>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Power) {
            QOM.Power o = (QOM.Power)that;
            return StringUtils.equals(this.$base(), o.$base()) && StringUtils.equals(this.$exponent(), o.$exponent());
        }
        return super.equals(that);
    }
}

