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
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.impl.Val;
import org.jooq.tools.StringUtils;

final class Ltrim
extends AbstractField<String>
implements QOM.Ltrim {
    final Field<String> string;
    final Field<String> characters;

    Ltrim(Field<String> string) {
        super(Names.N_LTRIM, Tools.allNotNull(SQLDataType.VARCHAR, string));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.characters = null;
    }

    Ltrim(Field<String> string, Field<String> characters) {
        super(Names.N_LTRIM, Tools.allNotNull(SQLDataType.VARCHAR, string, characters));
        this.string = Tools.nullSafeNotNull(string, SQLDataType.VARCHAR);
        this.characters = Tools.nullSafeNotNull(characters, SQLDataType.VARCHAR);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        if (this.characters == null) {
            switch (ctx.family()) {
                case FIREBIRD: {
                    ctx.visit(Names.N_TRIM).sql('(').visit(Keywords.K_LEADING).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.string).sql(')');
                    break;
                }
                default: {
                    ctx.visit(DSL.function(Names.N_LTRIM, this.getDataType(), this.string));
                    break;
                }
            }
        } else {
            switch (ctx.family()) {
                case AURORA_POSTGRES: 
                case REDSHIFT: 
                case SNOWFLAKE: 
                case SQLITE: {
                    ctx.visit(DSL.function(Names.N_LTRIM, this.getDataType(), this.string, this.characters));
                    break;
                }
                default: {
                    ctx.visit(Names.N_TRIM).sql('(').visit(Keywords.K_LEADING).sql(' ').visit(this.characters).sql(' ').visit(Keywords.K_FROM).sql(' ').visit(this.string).sql(')');
                }
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.sql("ltrim(");
        String s2 = "";
        ctx.sql(s2).visit(Val.getJavaValue(this.string));
        s2 = ", ";
        if (this.characters != null) {
            ctx.sql(s2).visit(Val.getJavaValue(this.characters));
            s2 = ", ";
        }
        ctx.sql(')');
    }

    @Override
    public final Field<String> $arg1() {
        return this.string;
    }

    @Override
    public final Field<String> $arg2() {
        return this.characters;
    }

    @Override
    public final QOM.Ltrim $arg1(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)newValue, (Field<String>)this.$arg2());
    }

    @Override
    public final QOM.Ltrim $arg2(Field<String> newValue) {
        return this.$constructor().apply((Field<String>)this.$arg1(), (Field<String>)newValue);
    }

    @Override
    public final Function2<? super Field<String>, ? super Field<String>, ? extends QOM.Ltrim> $constructor() {
        return (a1, a2) -> new Ltrim((Field<String>)a1, (Field<String>)a2);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.Ltrim) {
            QOM.Ltrim o = (QOM.Ltrim)that;
            return StringUtils.equals(this.$string(), o.$string()) && StringUtils.equals(this.$characters(), o.$characters());
        }
        return super.equals(that);
    }
}

