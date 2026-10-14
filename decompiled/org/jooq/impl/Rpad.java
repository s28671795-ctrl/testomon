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

final class Rpad
extends AbstractField<String>
implements QOM.Rpad {
    final Field<String> string;
    final Field<? extends Number> length;
    final Field<String> character;

    Rpad(Field<String> string, Field<? extends Number> length) {
        super(Names.N_RPAD, Tools.allNotNull(SQLDataType.VARCHAR, string, length));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.length = Tools.nullSafeNotNull(length, SQLDataType.INTEGER);
        this.character = null;
    }

    Rpad(Field<String> string, Field<? extends Number> length, Field<String> character) {
        super(Names.N_RPAD, Tools.allNotNull(SQLDataType.VARCHAR, string, length, character));
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
                ctx.sql('(').visit(this.string).sql(" & ").visit(Names.N_REPLACE).sql('(').visit(Names.N_SPACE).sql('(').visit(this.length).sql(" - ").visit(Names.N_LEN).sql('(').visit(this.string).sql(")), ' ', ").visit(this.characterOrBlank()).sql("))");
                break;
            }
            case ASE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: {
                ctx.visit(DSL.concat(this.string, DSL.repeat(this.characterOrBlank(), Internal.isub(this.length, DSL.length(this.string)))));
                break;
            }
            case SQLITE: {
                ctx.visit(this.string).sql(" || ").visit(Names.N_SUBSTR).sql('(').visit(Names.N_REPLACE).sql('(').visit(Names.N_HEX).sql('(').visit(Names.N_ZEROBLOB).sql('(').visit(this.length).sql(")), '00', ").visit(this.characterOrBlank()).sql("), 1, ").visit(this.length).sql(" - ").visit(Names.N_LENGTH).sql('(').visit(this.string).sql(')').sql(')');
                break;
            }
            default: {
                ctx.visit(DSL.function(Names.N_RPAD, this.getDataType(), this.string, this.length, this.characterOrBlank()));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("rpad(");
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
    public final QOM.Rpad $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<? extends Number>)this.$arg2(), (Field<String>)this.$arg3());
    }

    @Override
    public final QOM.Rpad $arg2(Field<? extends Number> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<? extends Number>)newValue, (Field<String>)this.$arg3());
    }

    @Override
    public final QOM.Rpad $arg3(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<? extends Number>)this.$arg2(), (Field<String>)newValue);
    }

    @Override
    public final Function3<? super Field<String>, ? super Field<? extends Number>, ? super Field<String>, ? extends QOM.Rpad> $constructor() {
        return (a1, a2, a3) -> new Rpad((Field<String>)a1, (Field<? extends Number>)a2, (Field<String>)a3);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Rpad) {
            QOM.Rpad o = (QOM.Rpad)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$length(), o.$length()) && StringUtils.equals(this.$character(), o.$character());
        }
        return super.equals(that);
    }
}

