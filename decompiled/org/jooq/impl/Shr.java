/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
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

final class Shr<T extends Number>
extends AbstractField<T>
implements QOM.Shr<T> {
    final Field<T> value;
    final Field<? extends Number> count;

    Shr(Field<T> value, Field<? extends Number> count) {
        super(Names.N_SHR, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value, count));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
        this.count = Tools.nullSafeNotNull(count, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case FIREBIRD: {
                return true;
            }
            case H2: {
                return true;
            }
            case EXASOL: {
                return true;
            }
            case SNOWFLAKE: {
                return true;
            }
            case TERADATA: {
                return true;
            }
            case SQLSERVER: {
                return false;
            }
            case ASE: 
            case DB2: 
            case INFORMIX: 
            case INGRES: 
            case ORACLE: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
            case HSQLDB: {
                return false;
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
            case FIREBIRD: {
                ctx.visit(DSL.function(Names.N_BIN_SHR, this.getDataType(), this.value, this.count));
                break;
            }
            case H2: {
                ctx.visit(DSL.function(Names.N_RSHIFT, this.getDataType(), this.value, this.count));
                break;
            }
            case EXASOL: {
                ctx.visit(DSL.function(Names.N_BIT_RSHIFT, this.getDataType(), this.value, this.count));
                break;
            }
            case SNOWFLAKE: {
                ctx.visit(DSL.function(Names.N_BITSHIFTRIGHT, this.getDataType(), this.value, this.count));
                break;
            }
            case TERADATA: {
                ctx.visit(DSL.function(Names.N_SHIFTRIGHT, this.getDataType(), this.value, this.count));
                break;
            }
            case SQLSERVER: {
                if (ctx.dialect().precedesStrictly(SQLDialect.SQLSERVER2022)) {
                    ctx.visit(Internal.idiv(this.value, Tools.castIfNeeded(DSL.power(DSL.two(), this.count), this.value)));
                    break;
                }
                ctx.sql('(').visit(this.value).sql(" >> ").visit(this.count).sql(')');
                break;
            }
            case ASE: 
            case DB2: 
            case INFORMIX: 
            case INGRES: 
            case ORACLE: 
            case SQLDATAWAREHOUSE: 
            case SYBASE: 
            case HSQLDB: {
                ctx.visit(Internal.idiv(this.value, Tools.castIfNeeded(DSL.power(DSL.two(), this.count), this.value)));
                break;
            }
            default: {
                ctx.sql('(').visit(this.value).sql(" >> ").visit(this.count).sql(')');
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(Val.getJavaValue(this.value)).sql(".shr(").visit(Val.getJavaValue(this.count)).sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.count;
    }

    @Override
    public final QOM.Shr<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<Number>)this.$arg2());
    }

    @Override
    public final QOM.Shr<T> $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), (Field<Number>)newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<? extends Number>, ? extends QOM.Shr<T>> $constructor() {
        return (a1, a2) -> new Shr(a1, (Field<? extends Number>)((Field<Number>)a2));
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Shr) {
            QOM.Shr o = (QOM.Shr)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$count(), o.$count());
        }
        return super.equals(that);
    }
}

