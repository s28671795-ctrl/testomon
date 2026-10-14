/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Function3;
import org.jooq.Pro;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Translate
extends AbstractField<String>
implements QOM.Translate {
    final Field<String> string;
    final Field<String> from;
    final Field<String> to;

    Translate(Field<String> string, Field<String> from, Field<String> to) {
        super(Names.N_TRANSLATE, Tools.allNotNull(SQLDataType.VARCHAR, string, from, to));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.from = Tools.nullSafeNotNull(from, SQLDataType.VARCHAR);
        this.to = Tools.nullSafeNotNull(to, SQLDataType.VARCHAR);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case DB2: {
                return false;
            }
            case TERADATA: {
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
            case DB2: {
                ctx.visit(DSL.function(Names.N_TRANSLATE, this.getDataType(), this.string, this.to, this.from));
                break;
            }
            case TERADATA: {
                ctx.visit(DSL.function(Names.N_OTRANSLATE, this.getDataType(), this.string, this.from, this.to));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_TRANSLATE, this.getDataType(), this.string, this.from, this.to));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("translate(");
        String s2 = "";
        ctx.sql(s2).visit(this.string);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.from));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.to));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final Field<String> $arg2() {
        return this.from;
    }

    @Override
    public final Field<String> $arg3() {
        return this.to;
    }

    @Override
    public final QOM.Translate $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<String>)this.$arg2(), (Field<String>)this.$arg3());
    }

    @Override
    public final QOM.Translate $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)newValue, (Field<String>)this.$arg3());
    }

    @Override
    public final QOM.Translate $arg3(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)this.$arg2(), (Field<String>)newValue);
    }

    @Override
    public final Function3<? super Field<String>, ? super Field<String>, ? super Field<String>, ? extends QOM.Translate> $constructor() {
        return (a1, a2, a3) -> new Translate((Field<String>)a1, (Field<String>)a2, (Field<String>)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Translate) {
            QOM.Translate o = (QOM.Translate)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$from(), o.$from()) && StringUtils.equals(this.$to(), o.$to());
        }
        return super.equals(that);
    }
}

