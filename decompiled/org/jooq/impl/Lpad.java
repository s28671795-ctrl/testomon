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

final class Lpad
extends AbstractField<String>
implements QOM.Lpad {
    final Field<String> string;
    final Field<? extends Number> length;
    final Field<String> character;

    Lpad(Field<String> string, Field<? extends Number> length) {
        super(Names.N_LPAD, Tools.allNotNull(SQLDataType.VARCHAR, string, length));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.length = Tools.nullSafeNotNull(length, SQLDataType.INTEGER);
        this.character = null;
    }

    Lpad(Field<String> string, Field<? extends Number> length, Field<String> character) {
        super(Names.N_LPAD, Tools.allNotNull(SQLDataType.VARCHAR, string, length, character));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.length = Tools.nullSafeNotNull(length, SQLDataType.INTEGER);
        this.character = Tools.nullSafeNotNull(character, SQLDataType.VARCHAR);
    }

    private final Field<String> characterOrBlank() {
        return this.character == null ? DSL.inline(" ") : this.character;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: {
                ctx.sql('(').visit(Names.N_REPLACE).sql('(').visit(Names.N_SPACE).sql('(').visit(this.length).sql(" - ").visit(Names.N_LEN).sql('(').visit(this.string).sql(")), ' ', ").visit(this.characterOrBlank()).sql(") & ").visit(this.string).sql(')');
                break;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: {
                ctx.visit(DSL.concat(DSL.repeat(this.characterOrBlank(), Internal.isub(this.length, DSL.length(this.string))), this.string));
                break;
            }
            case SQLITE: {
                ctx.visit(Names.N_SUBSTR).sql('(').visit(Names.N_REPLACE).sql('(').visit(Names.N_HEX).sql('(').visit(Names.N_ZEROBLOB).sql('(').visit(this.length).sql(")), '00', ").visit(this.characterOrBlank()).sql("), 1, ").visit(this.length).sql(" - ").visit(Names.N_LENGTH).sql('(').visit(this.string).sql(")) || ").visit(this.string);
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_LPAD, this.getDataType(), this.string, this.length, this.characterOrBlank()));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("lpad(");
        String s2 = "";
        ctx.sql(s2).visit(this.string);
        s2 = ", ";
        ctx.sql(s2).visit(Val.getJavaValue(this.length));
        s2 = ", ";
        if (this.character != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.character));
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final Field<? extends Number> $arg2() {
        return this.length;
    }

    @Override
    public final Field<String> $arg3() {
        return this.character;
    }

    @Override
    public final QOM.Lpad $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<? extends Number>)this.$arg2(), (Field<String>)this.$arg3());
    }

    @Override
    public final QOM.Lpad $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<? extends Number>)newValue, (Field<String>)this.$arg3());
    }

    @Override
    public final QOM.Lpad $arg3(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<? extends Number>)this.$arg2(), (Field<String>)newValue);
    }

    @Override
    public final Function3<? super Field<String>, ? super Field<? extends Number>, ? super Field<String>, ? extends QOM.Lpad> $constructor() {
        return (a1, a2, a3) -> new Lpad((Field<String>)a1, (Field<? extends Number>)a2, (Field<String>)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Lpad) {
            QOM.Lpad o = (QOM.Lpad)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$length(), o.$length()) && StringUtils.equals(this.$character(), o.$character());
        }
        return super.equals(that);
    }
}

