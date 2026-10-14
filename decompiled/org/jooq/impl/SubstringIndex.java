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
import org.jooq.impl.Internal;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class SubstringIndex
extends AbstractField<String>
implements QOM.SubstringIndex {
    final Field<String> string;
    final Field<String> delimiter;
    final Field<? extends Number> n;

    SubstringIndex(Field<String> string, Field<String> delimiter, Field<? extends Number> n) {
        super(Names.N_SUBSTRING_INDEX, Tools.allNotNull(SQLDataType.VARCHAR, string, delimiter, n));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.delimiter = Tools.nullSafeNotNull(delimiter, SQLDataType.VARCHAR);
        this.n = Tools.nullSafeNotNull(n, SQLDataType.INTEGER);
    }

    @Override
    final boolean parenthesised(Context<?> ctx) {
        switch (ctx.family()) {
            case DB2: 
            case ORACLE: 
            case VERTICA: {
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
            case DB2: 
            case ORACLE: 
            case VERTICA: {
                ctx.visit(DSL.coalesce(DSL.substring(this.string, DSL.one(), Internal.isub(DSL.nullif(DSL.function(Names.N_INSTR, SQLDataType.INTEGER, this.string, this.delimiter, DSL.one(), this.n), DSL.zero()), DSL.one())), this.string));
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_SUBSTRING_INDEX, this.getDataType(), this.string, this.delimiter, this.n));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("substringIndex(");
        String s2 = "";
        ctx.sql(s2).visit(this.string);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.delimiter));
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.n));
        s2 = ", ";
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final Field<String> $arg2() {
        return this.delimiter;
    }

    @Override
    public final Field<? extends Number> $arg3() {
        return this.n;
    }

    @Override
    public final QOM.SubstringIndex $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<String>)this.$arg2(), (Field<? extends Number>)this.$arg3());
    }

    @Override
    public final QOM.SubstringIndex $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)newValue, (Field<? extends Number>)this.$arg3());
    }

    @Override
    public final QOM.SubstringIndex $arg3(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)this.$arg2(), (Field<? extends Number>)newValue);
    }

    @Override
    public final Function3<? super Field<String>, ? super Field<String>, ? super Field<? extends Number>, ? extends QOM.SubstringIndex> $constructor() {
        return (a1, a2, a3) -> new SubstringIndex((Field<String>)a1, (Field<String>)a2, (Field<? extends Number>)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.SubstringIndex) {
            QOM.SubstringIndex o = (QOM.SubstringIndex)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$delimiter(), o.$delimiter()) && StringUtils.equals(this.$n(), o.$n());
        }
        return super.equals(that);
    }
}

