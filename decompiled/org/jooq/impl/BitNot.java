/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
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

final class BitNot<T extends Number>
extends AbstractField<T>
implements QOM.BitNot<T> {
    final Field<T> arg1;

    BitNot(Field<T> arg1) {
        super(Names.N_BIT_NOT, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, arg1, false), arg1));
        this.arg1 = Tools.nullSafeNotNull(arg1, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case INGRES: 
            case ORACLE: 
            case HSQLDB: {
                return false;
            }
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case SNOWFLAKE: 
            case TERADATA: 
            case H2: {
                return true;
            }
            case EXASOL: {
                return true;
            }
            case FIREBIRD: {
                return true;
            }
        }
        return false;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case INGRES: 
            case ORACLE: 
            case HSQLDB: {
                ctx.visit(Internal.isub(Internal.isub(DSL.zero(), this.arg1), DSL.one()));
                break;
            }
            case DB2: 
            case HANA: 
            case INFORMIX: 
            case SNOWFLAKE: 
            case TERADATA: 
            case H2: {
                ctx.visit(DSL.function(Names.N_BITNOT, this.getDataType(), this.arg1));
                break;
            }
            case EXASOL: {
                ctx.visit(DSL.function(Names.N_BIT_NOT, this.getDataType(), this.arg1));
                break;
            }
            case FIREBIRD: {
                ctx.visit(DSL.function(Names.N_BIN_NOT, this.getDataType(), this.arg1));
                break;
            }
            default: {
                if (this.arg1 instanceof AbstractField && ((AbstractField)this.arg1).parenthesised(ctx)) {
                    ctx.sql('~').visit(this.arg1);
                    break;
                }
                ctx.sql("~(").visit(this.arg1).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(Val.getJavaValue(this.arg1)).sql(".bitNot()");
    }

    @Override
    public final Field<T> $arg1() {
        return this.arg1;
    }

    @Override
    public final QOM.BitNot<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.BitNot<T>> $constructor() {
        return a1 -> new BitNot(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.BitNot) {
            QOM.BitNot o = (QOM.BitNot)that;
            return StringUtils.equals(this.$arg1(), o.$arg1());
        }
        return super.equals(that);
    }
}

