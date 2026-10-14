/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigDecimal;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Ln
extends AbstractField<BigDecimal>
implements QOM.Ln {
    final Field<? extends Number> value;

    Ln(Field<? extends Number> value) {
        super(Names.N_LN, Tools.allNotNull(SQLDataType.NUMERIC, value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case COCKROACHDB: {
                return false;
            }
            case INFORMIX: {
                return true;
            }
            case ACCESS: 
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SQLITE: {
                return true;
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
            case COCKROACHDB: {
                if (this.value.getDataType().isInteger()) {
                    ctx.visit(DSL.function(Names.N_LN, SQLDataType.NUMERIC, DSL.cast(this.value, SQLDataType.NUMERIC)));
                    break;
                }
                ctx.visit(DSL.function(Names.N_LN, SQLDataType.NUMERIC, this.value));
                break;
            }
            case INFORMIX: {
                ctx.visit(DSL.function(Names.N_LOGN, this.getDataType(), this.value));
                break;
            }
            case ACCESS: 
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SQLITE: {
                ctx.visit(DSL.function(Names.N_LOG, this.getDataType(), this.value));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_LN, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("ln(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<? extends Number> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Ln $arg1(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<? extends Number>)newValue);
    }

    @Override
    public final Function1<? super Field<? extends Number>, ? extends QOM.Ln> $constructor() {
        return a1 -> new Ln((Field<? extends Number>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Ln) {
            QOM.Ln o = (QOM.Ln)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

