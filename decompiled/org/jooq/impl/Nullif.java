/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function2;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Nullif<T>
extends AbstractField<T>
implements QOM.Nullif<T> {
    final Field<T> value;
    final Field<T> other;

    Nullif(Field<T> value, Field<T> other) {
        super(Names.N_NULLIF, Tools.nullable(Tools.dataType(value), value, other));
        this.value = Tools.nullSafeNotNull(value, SQLDataType.OTHER);
        this.other = Tools.nullSafeNotNull(other, SQLDataType.OTHER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: {
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
                ctx.visit(Names.N_IIF).sql('(').visit(this.value).sql(" = ").visit(this.other).sql(", ").visit(Keywords.K_NULL).sql(", ").visit(this.value).sql(')');
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_NULLIF, this.getDataType(), this.value, this.other));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("nullif(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.value));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.other));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<T> $arg1() {
        return this.value;
    }

    @Override
    public final Field<T> $arg2() {
        return this.other;
    }

    @Override
    public final QOM.Nullif<T> $arg1(Field<T> newValue) {
        return this.$constructor().apply(newValue, (Field<T>)this.$arg2());
    }

    @Override
    public final QOM.Nullif<T> $arg2(Field<T> newValue) {
        return this.$constructor().apply((Field<T>)this.$arg1(), newValue);
    }

    @Override
    public final Function2<? super Field<T>, ? super Field<T>, ? extends QOM.Nullif<T>> $constructor() {
        return (a1, a2) -> new Nullif(a1, a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Nullif) {
            QOM.Nullif o = (QOM.Nullif)that;
            return StringUtils.equals(this.$value(), o.$value()) && StringUtils.equals(this.$other(), o.$other());
        }
        return super.equals(that);
    }
}

