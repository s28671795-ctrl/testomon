/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Ceil<T extends Number>
extends AbstractField<T>
implements QOM.Ceil<T> {
    final Field<T> value;

    Ceil(Field<T> value) {
        super(Names.N_CEIL, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: {
                return false;
            }
            case SQLITE: {
                return false;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case H2: {
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
            case ACCESS: {
                ctx.visit(DSL.field("({clng}({0}) - ({0} - clng({0}) > 0))", this.getDataType(), new QueryPart[]{this.value}));
                break;
            }
            case SQLITE: {
                if (ctx.dialect().precedes(SQLDialect.SQLITE_3_30)) {
                    ctx.sql('(').visit(this.value.cast(SQLDataType.BIGINT)).sql(" + (").visit(this.value).sql(" > ").visit(this.value.cast(SQLDataType.BIGINT)).sql("))");
                    break;
                }
                ctx.visit(DSL.function(Names.N_CEIL, this.getDataType(), this.value));
                break;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case H2: {
                ctx.visit(DSL.function(Names.N_CEILING, this.getDataType(), this.value));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_CEIL, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("ceil(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Ceil<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.Ceil<T>> $constructor() {
        return a1 -> new Ceil(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Ceil) {
            QOM.Ceil o = (QOM.Ceil)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

