/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Floor<T extends Number>
extends AbstractField<T>
implements QOM.Floor<T> {
    final Field<T> value;

    Floor(Field<T> value) {
        super(Names.N_FLOOR, Tools.allNotNull(Tools.dataType(SQLDataType.INTEGER, value, false), value));
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
                ctx.sql('(').visit(this.value.cast(SQLDataType.BIGINT)).sql(" - (").visit(this.value).sql(" < ").visit(this.value.cast(SQLDataType.BIGINT)).sql("))");
                break;
            }
            case SQLITE: {
                if (ctx.dialect().precedes(SQLDialect.SQLITE_3_30)) {
                    ctx.sql('(').visit(this.value.cast(SQLDataType.BIGINT)).sql(" - (").visit(this.value).sql(" < ").visit(this.value.cast(SQLDataType.BIGINT)).sql("))");
                    break;
                }
                ctx.visit(DSL.function(Names.N_FLOOR, this.getDataType(), this.value));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_FLOOR, this.getDataType(), this.value));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("floor(");
        ctx.visit(Val.getJavaValue(this.value));
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final QOM.Floor<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Field<T>, ? extends QOM.Floor<T>> $constructor() {
        return a1 -> new Floor(a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Floor) {
            QOM.Floor o = (QOM.Floor)that;
            return StringUtils.equals(this.$value(), o.$value());
        }
        return super.equals(that);
    }
}

