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
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Lower
extends AbstractField<String>
implements QOM.Lower {
    final Field<String> string;

    Lower(Field<String> string) {
        super(Names.N_LOWER, Tools.allNotNull(SQLDataType.VARCHAR, string));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
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
            case ACCESS: {
                ctx.visit(DSL.function(Names.N_LCASE, this.getDataType(), this.string));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_LOWER, this.getDataType(), this.string));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("lower(");
        ctx.visit(Val.getJavaValue(this.string));
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final QOM.Lower $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue);
    }

    @Override
    public final Function1<? super Field<String>, ? extends QOM.Lower> $constructor() {
        return a1 -> new Lower((Field<String>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Lower) {
            QOM.Lower o = (QOM.Lower)that;
            return StringUtils.equals(this.$string(), o.$string());
        }
        return super.equals(that);
    }
}

