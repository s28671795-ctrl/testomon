/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Radians
extends AbstractField<BigDecimal>
implements QOM.Radians {
    final Field<? extends Number> degrees;

    Radians(Field<? extends Number> degrees2) {
        super(Names.N_RADIANS, Tools.allNotNull(SQLDataType.NUMERIC, degrees2));
        this.degrees = Tools.nullSafeNotNull(degrees2, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case BIGQUERY: 
            case HANA: 
            case INGRES: 
            case ORACLE: 
            case FIREBIRD: {
                return false;
            }
            case SQLITE: {
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
            case ACCESS: 
            case BIGQUERY: 
            case HANA: 
            case INGRES: 
            case ORACLE: 
            case FIREBIRD: {
                ctx.visit(Internal.idiv(Internal.imul(Tools.castIfNeeded(this.degrees, BigDecimal.class), DSL.pi()), DSL.inline(180)));
                break;
            }
            case SQLITE: {
                if (ctx.dialect().precedes(SQLDialect.SQLITE_3_30)) {
                    ctx.visit(Internal.idiv(Internal.imul(Tools.castIfNeeded(this.degrees, BigDecimal.class), DSL.pi()), DSL.inline(180)));
                    break;
                }
                ctx.visit(DSL.function(Names.N_RADIANS, this.getDataType(), this.degrees));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_RADIANS, this.getDataType(), this.degrees));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("rad(");
        ctx.visit(Val.getJavaValue(this.degrees));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.degrees;
    }

    @Override
    public final QOM.Radians $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Radians> $constructor() {
        return a1 -> new Radians((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Radians) {
            QOM.Radians o = (QOM.Radians)that;
            return StringUtils.equals(this.$degrees(), o.$degrees());
        }
        return super.equals(that);
    }
}

